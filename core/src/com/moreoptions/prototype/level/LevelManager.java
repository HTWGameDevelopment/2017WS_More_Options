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

    private Room currentRoom;       //The current room
    private Room bufferRoom;        //A buffer to handle smooth room transitions.

    private ComponentMapper<EnemyComponent> ecm = ComponentMapper.getFor(EnemyComponent.class);
    private ComponentMapper<DestructibleComponent> dcm = ComponentMapper.getFor(DestructibleComponent.class);

    private GameWorld world;

    private LevelGenerator levelGenerator;
    private Level currentLevel;

    public LevelManager(GameWorld world) {

        levelGenerator = new StandardLevelGenerator();

        currentLevel = levelGenerator.getLevel(10,10,10);


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

    public boolean changeRoom(Room targetRoom, Offset offset) {

        System.out.println(targetRoom);

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

        //Add new entities
        for(Entity e : currentRoom.getEntities()) {
            world.addEntity(e);
        }
        //Add player entity
        addPlayerEntities(offset);
        return true;
    }

    public void addPlayerEntities(Offset offset) {
        ArrayList<Player> players = GameState.getInstance().getPlayerList();    //GET ALL Players
        for (Player p : players) {
            world.addEntity(p.getEntity(offset));
        }
    }

    public void initStartingLevel() {
        initStartingRoom(currentRoom);
    }
}
