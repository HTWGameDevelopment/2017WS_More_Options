package com.moreoptions.prototype.gameEngine.level;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.utils.ImmutableArray;
import com.moreoptions.prototype.gameEngine.GameWorld;
import com.moreoptions.prototype.gameEngine.components.*;
import com.moreoptions.prototype.gameEngine.data.GameState;
import com.moreoptions.prototype.gameEngine.data.Player;
import com.moreoptions.prototype.gameEngine.data.Room;
import com.moreoptions.prototype.gameEngine.util.EntityTools;
import com.moreoptions.prototype.gameEngine.util.EventFactory;

import java.util.ArrayList;

/**
 * Created by denwe on 15.11.2017.
 */
public class LevelManager {

    private Room currentRoom;       //The current room

    private ComponentMapper<EnemyComponent> ecm = ComponentMapper.getFor(EnemyComponent.class);
    private ComponentMapper<DestructibleComponent> dcm = ComponentMapper.getFor(DestructibleComponent.class);

    private GameWorld world;

    private LevelGenerator levelGenerator;
    private Level currentLevel;

    public LevelManager(GameWorld world) {
        this.levelGenerator = new StandardLevelGenerator();
        this.world = world;

        generateNewLevel(10,10,10);
    }

    public boolean changeRoom(Room targetRoom, Offset offset) {

        EventFactory.saveGame();

        System.out.println(targetRoom);

        if(targetRoom == null) return false;
        //First, we check if all monsters are dead. If one is alive, revive all other monsters and return them to their spawn position.
        //Also, if a monster is alive, restore every entity back to its natural state.

        ImmutableArray<Entity> currentRoomEntities = world.getEntities(); //All current Entities

        if(currentRoom != null) currentRoom.getPlayerList().clear();

        for(Entity e : currentRoomEntities) {
            if(ecm.has(e)) EntityTools.resetEnemy(e);
            if(dcm.has(e)) EntityTools.resetDestructible(e);
            //remove references to player
            world.removeEntity(e);
        }

        //Then, we set our bufferRoom
        currentRoom = targetRoom;

        //Add new entities
        for(Entity e : currentRoom.getEntities()) {
            world.addEntity(e);
        }
        //Add player entity
        addPlayerEntities(offset,targetRoom);
        //add debug item
        currentRoom.checkForClear();
        return true;
    }


    public void addPlayerEntities(Offset offset, Room targetroom) {
        ArrayList<Player> players = GameState.getInstance().getPlayerList();    //GET ALL Players
        for (Player p : players) {
            Entity playerEntity = p.getEntity(offset);
            world.addEntity(playerEntity);
            targetroom.addPlayer(playerEntity);
        }
    }


    public void generateNewLevel(int width, int height, int roomCount) {
        this.currentLevel = levelGenerator.getLevel(width,height,roomCount);
        changeRoom(currentLevel.getStartingRoom(), Offset.NONE);
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

}
