package com.moreoptions.prototype.level;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.moreoptions.prototype.gameEngine.GameWorld;
import com.moreoptions.prototype.gameEngine.components.*;
import com.moreoptions.prototype.gameEngine.data.GameState;
import com.moreoptions.prototype.gameEngine.data.Player;
import com.moreoptions.prototype.gameEngine.data.Room;
import com.moreoptions.prototype.gameEngine.data.pathfinding.Node;
import com.moreoptions.prototype.gameEngine.util.EntityTools;

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
        addDebugMonster();
        return true;
    }

    public void addDebugMonster() {
        addDebugChased();
        addDebugChaser();
        addDebugBlinker();
    }

    private void addDebugChased() {
        Entity debugMonsterEntity = new Entity();
        debugMonsterEntity.add(new PositionComponent(150, 100));
        debugMonsterEntity.add(new CollisionComponent());
        debugMonsterEntity.add(new CircleCollisionComponent(150f, 100f, 4));
        debugMonsterEntity.add(new DebugCircleComponent(10));
        debugMonsterEntity.add(new VelocityComponent(0f, 0f));
        debugMonsterEntity.add(new DebugColorComponent(Color.FIREBRICK));
        debugMonsterEntity.add(new EnemyHitboxComponent(10));
        debugMonsterEntity.add(new AIComponent(2));
        debugMonsterEntity.add(new EnemyComponent());
        world.addEntity(debugMonsterEntity);
    }

    private void addDebugChaser() {
        Entity debugChaser = new Entity();
        debugChaser.add(new PositionComponent(150, 100));
        debugChaser.add(new CollisionComponent());
        debugChaser.add(new CircleCollisionComponent(150f, 100f, 4));
        debugChaser.add(new DebugCircleComponent(10));
        debugChaser.add(new VelocityComponent(0f, 0f));
        debugChaser.add(new DebugColorComponent(Color.BLUE));
        debugChaser.add(new EnemyHitboxComponent(10));
        debugChaser.add(new AIComponent(1));
        debugChaser.add(new EnemyComponent());

        world.addEntity(debugChaser);
    }

    private void addDebugBlinker() {
        Entity debugBlinker = new Entity();
        debugBlinker.add(new PositionComponent(150, 100));
        debugBlinker.add(new CollisionComponent());
        debugBlinker.add(new CircleCollisionComponent(150f, 150f, 4));
        debugBlinker.add(new DebugCircleComponent(10));
        debugBlinker.add(new VelocityComponent(0f, 0f));
        debugBlinker.add(new DebugColorComponent(Color.CORAL));
        debugBlinker.add(new AIComponent(3));
        debugBlinker.add(new EnemyHitboxComponent(10));
        debugBlinker.add(new EnemyComponent());

        world.addEntity(debugBlinker);
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

    public ArrayList<Node> getPath(Entity debugMonsterEntity, Entity entity) {



        return  getCurrentRoom().getNavGraph().getPath(debugMonsterEntity.getComponent(PositionComponent.class).getX(),debugMonsterEntity.getComponent(PositionComponent.class).getY(),entity.getComponent(PositionComponent.class).getX(),entity.getComponent(PositionComponent.class).getY() );

    }
}
