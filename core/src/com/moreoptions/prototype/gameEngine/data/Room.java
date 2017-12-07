package com.moreoptions.prototype.gameEngine.data;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.moreoptions.prototype.gameEngine.components.*;
import com.moreoptions.prototype.gameEngine.data.pathfinding.NavGraph;
import com.moreoptions.prototype.gameEngine.data.callback.ChangeRoomEvent;
import com.moreoptions.prototype.gameEngine.data.exceptions.MissdefinedTileException;
import com.moreoptions.prototype.gameEngine.util.AssetLoader;
import com.moreoptions.prototype.level.*;

import java.util.ArrayList;
import java.util.Random;

/**
 * Rooms are: 15x9, consist of 2 maps. This is just a prototype implementation with everything hardcoded.
 *
 * 1 walls
 * 1 entities
 *
 *
 * Ex:  {
 *          {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
 *          {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
 *          {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
 *          {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
 *          {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
 *          {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
 *          {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
 *          {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
 *          {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
 *      }
 *      
 *      {
 *          {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
 *          {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
 *          {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
 *          {0,0,0,0,0,0,5,5,5,0,0,0,0,0,0},
 *          {0,0,0,0,0,0,5,5,5,0,0,0,0,0,0},
 *          {0,0,0,0,0,0,5,5,5,0,0,0,0,0,0},
 *          {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
 *          {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
 *          {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}
 *      }
 *
 */
public class Room {

    DestructibleLayer destLayer;
    TileLayer tileLayer;
    EnemyLayer enemyLayer;

    NavGraph navGraph = new NavGraph();

    private Room leftNeighbour;
    private Room rightNeighbour;
    private Room topNeighbour;
    private Room bottomNeighbour;
    private int x;
    private int y;
    private int id;

    RoomBlueprint blueprint;

    ArrayList<Entity> playerList = new ArrayList<Entity>();
    ArrayList<Entity> doors = new ArrayList<Entity>();



    public Room(RoomBlueprint roomBlueprint) {

        this.blueprint = roomBlueprint;

        x = roomBlueprint.getXCoord();
        y = roomBlueprint.getYCoord();

        Random r = new Random();

        //TODO refactor this!
        ArrayList<RoomDefinition> roomlist = AssetLoader
                .getInstance()
                .definition(roomBlueprint.isTop(),roomBlueprint.isDown(),roomBlueprint.isLeft(),roomBlueprint.isRight());

        RoomDefinition rq = roomlist.get(r.nextInt(roomlist.size()));

        try {
            destLayer = rq.getDestLayer();
            tileLayer = rq.getTileLayer();
            enemyLayer = rq.getEnemyLayer(this);
        } catch (MissdefinedTileException e) {
            e.printStackTrace();
        }

        for(Entity e : destLayer.getEntities()) {
            navGraph.addEntity(e);
        }


    }

    public void generateBarriers() {

        if(leftNeighbour != null) {
            doors.add(createDoor(0,5,leftNeighbour, Offset.LEFT));
        } else {
            doors.add(createWall(0,5,Offset.LEFT));
            doors.add(createWall(1,5,Offset.LEFT));
        }
        if(rightNeighbour != null) {
            doors.add(createDoor(16,5,rightNeighbour, Offset.RIGHT));
        } else {
            doors.add(createWall(16,5,Offset.RIGHT));
            doors.add(createWall(15,5,Offset.RIGHT));
        }
        if(topNeighbour != null) {
            doors.add(createDoor(8,10,topNeighbour, Offset.TOP));
        } else {
            doors.add(createWall(8,10,Offset.TOP));
            doors.add(createWall(8,9,Offset.TOP));
        }
        if(bottomNeighbour != null) {
            doors.add(createDoor(8,0,bottomNeighbour, Offset.DOWN));
        } else {
            doors.add(createWall(8,0,Offset.DOWN));
            doors.add(createWall(8,1,Offset.DOWN));
        }
    }

    public String toString() {

        return ("Room: "+ id + " Doors: TOP("+blueprint.isTop()+"), DOWN("+blueprint.isDown()+"), LEFT("+blueprint.isLeft()+"), RIGHT("+blueprint.isRight()+"), " );

    }

    public void draw(ShapeRenderer renderer) {
        navGraph.draw(renderer);
    }

    public ArrayList<Entity> getTileEntities() {
        return tileLayer.getEntities();
    }

    public ArrayList<Entity> getDestructibleEntities() {
        return destLayer.getEntities();
    }

    public ArrayList<Entity> getEntities() {

        ArrayList<Entity> entities = new ArrayList<Entity>();
        entities.addAll(destLayer.getEntities());
        entities.addAll(tileLayer.getEntities());
        entities.addAll(enemyLayer.getAliveEntities());
        entities.addAll(doors);

        return entities;
    }

    public ArrayList<Entity> getPlayerList() {
        return playerList;
    }

    private Entity createDoor(int x, int y, Room room, Offset offset) {
        Entity e = new Entity();

        e.add(new PositionComponent(x * Consts.TILE_SIZE, y * Consts.TILE_SIZE));
        e.add(new CollisionComponent(new ChangeRoomEvent(room)));
        e.add(new DoorComponent(offset));
        e.add(new BlockedTileComponent());
        e.add(new SquareCollisionComponent(x * Consts.TILE_SIZE, y * Consts.TILE_SIZE, Consts.TILE_SIZE));
        e.add(new CircleCollisionComponent(x * Consts.TILE_SIZE, y * Consts.TILE_SIZE, Consts.TILE_SIZE/2));
        e.add(new DebugColorComponent(Color.FOREST));

        return e;

    }

    private Entity createWall(int x, int y, Offset offset) {
        Entity e = new Entity();
        e.add(new PositionComponent(x* Consts.TILE_SIZE,y* Consts.TILE_SIZE));
        e.add(new CollisionComponent());
        e.add(new BlockedTileComponent());
        e.add(new SquareCollisionComponent(x* Consts.TILE_SIZE,y * Consts.TILE_SIZE , Consts.TILE_SIZE));
        e.add(new DebugColorComponent(com.badlogic.gdx.graphics.Color.BROWN));
        return e;
    }

    private void openAllDoors() {
        ComponentMapper<DoorComponent> dcm = ComponentMapper.getFor(DoorComponent.class);

        for(Entity e : doors) {
            if(dcm.has(e)) {
                DoorComponent dc = dcm.get(e);
                BlockedTileComponent b = e.getComponent(BlockedTileComponent.class);
                b.setBlocked(false);
                dc.setState(DoorComponent.DOOR_OPEN);
            }
        }
    }

    public void addPlayer(Entity player) {
        playerList.add(player);
    }

    public void setLeftNeighbour(Room leftNeighbour) {
        this.leftNeighbour = leftNeighbour;
    }

    public void setRightNeighbour(Room rightNeighbour) {
        this.rightNeighbour = rightNeighbour;
    }

    public void setTopNeighbour(Room topNeighbour) {
        this.topNeighbour = topNeighbour;
    }

    public void setBottomNeighbour(Room bottomNeighbour) {
        this.bottomNeighbour = bottomNeighbour;
    }

    public Room getLeftNeighbour() {
        return leftNeighbour;
    }

    public Room getRightNeighbour() {
        return rightNeighbour;
    }

    public Room getTopNeighbour() {
        return topNeighbour;
    }

    public Room getBottomNeighbour() {
        return bottomNeighbour;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public NavGraph getNavGraph() {
        return navGraph;
    }

    public void checkForClear() {
        System.out.println("Checking! " + enemyLayer.getAliveEntities().size());
        if(enemyLayer.getAliveEntities().size() == 0) {
            openAllDoors();
            System.out.println("OPENING DOORS");
        }
    }
}
