package com.moreoptions.prototype.level;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.utils.ImmutableArray;
import com.moreoptions.prototype.gameEngine.GameWorld;
import com.moreoptions.prototype.gameEngine.components.*;
import com.moreoptions.prototype.gameEngine.data.GameState;
import com.moreoptions.prototype.gameEngine.data.Player;
import com.moreoptions.prototype.gameEngine.data.Room;
import com.moreoptions.prototype.gameEngine.util.EntityTools;

import java.util.ArrayList;

/**
 * Created by denwe on 15.11.2017.
 */
public class LevelManager {

    /*
        Also!
        Derzeit ist ein Raum halt eine Ansammlung von Entities.
        Wir manipulieren die Spielwelt
        Indem wir Räume laden und entladen
        Wenn wir einen Raum laden, spielen wir alle Entities in die Engine. Alle Entities die zu einem anderen Raum gehören, werden gelöscht
        Letztendlich entfernen wir alle Entities und befüllen die Engine neu.
        Wenn wir einen Raum entladen, erneuern wir alle Entities im Raum mit denen im geladenen Raum
        es seidem der Raum ist != clear
        Dann lassen wir das.
        Wenn wir einen Raum laden erstellen wir eine neue PlayerEntity anhand unserer PlayerStats Blaupause
        in der Richtung, von der der Spieler gekommen ist
        Sieht das gut aus?
        hab ich was vergessen?
        Ich denke auch, dass wir Räume versetzt laden können. Das heißt in unsererm "RoomManager" hat man zwei Felder für Räume. Wenn man einen Raum neuläd wird der raum versetzt links/rechts/oben/unten gemalt. dann werden die räume in die richtige richtung verschoben
     */


    private Room currentRoom;       //The current room
    private Room bufferRoom;        //A buffer to handle smooth room transitions.

    private ComponentMapper<EnemyComponent> ecm = ComponentMapper.getFor(EnemyComponent.class);
    private ComponentMapper<DestructibleComponent> dcm = ComponentMapper.getFor(DestructibleComponent.class);

    private GameWorld world;

    private LevelGenerator levelGenerator;
    private Level currentLevel;

    public LevelManager(GameWorld world) {

        levelGenerator = new StandardLevelGenerator();

        currentLevel = levelGenerator.getLevel(10,10,20);


        currentRoom = currentLevel.getStartingRoom();
        this.world = world;

    }

    private void initStartingRoom(Room currentRoom) {
        for(Entity e : currentRoom.getEntities()) {
            if(e != null) {
                world.addEntity(e);
            }
        }
    }

    public void test() {
        changeRoom(currentRoom.getLeftNeighbour(), null);
        System.out.println("Changed");
    }

    public boolean changeRoom(Room targetRoom, Offset offset) {

        if(targetRoom == null) return false;
        //First, we check if all monsters are dead. If one is alive, revive all other monsters and return them to their spawn position.
        //Also, if a monster is alive, restore every entity back to its natural state.

        ImmutableArray<Entity> currentRoomEntities = world.getEntities(); //All current Entities

        for(Entity e : currentRoomEntities) {
            if(ecm.has(e)) EntityTools.resetEnemy(e);
            if(dcm.has(e)) EntityTools.resetDestructible(e);
            world.removeEntity(e);
        }



        //Then, we set our bufferRoom

        currentRoom = targetRoom;

        //Clear world


        //Add new entities
        for(Entity e : currentRoom.getEntities()) {
            world.addEntity(e);
            System.out.println("Adding new entity:" + e.getComponent(PositionComponent.class).getX());
            System.out.println(world.getEntities().size());
            if((e.getComponent(TileGraphicComponent.class)) != null) System.out.println("Tile");
        }
        //Add player entity
        ArrayList<Player> players = GameState.getInstance().getPlayerList();    //GET ALL Players
        for (Player p : players) {
            world.addEntity(p.getEntity(offset));
        }

        return true;
    }



    public void initStartingLevel() {
        initStartingRoom(currentRoom);
    }
}
