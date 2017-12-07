package com.moreoptions.prototype.level;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.moreoptions.prototype.gameEngine.components.*;
import com.moreoptions.prototype.gameEngine.data.Consts;
import com.moreoptions.prototype.gameEngine.data.Room;
import com.moreoptions.prototype.gameEngine.data.exceptions.MissdefinedTileException;

import java.util.ArrayList;

/**
 * Created by denwe on 17.11.2017.
 */
public class RoomDefinition {

    //lets define a room

    private boolean doorNorth       = false;
    private boolean doorSouth       = false;
    private boolean doorWest        = false;
    private boolean doorEast        = false;

    private boolean treasureRoom    = false;
    private boolean bossRoom        = false;
    private boolean shopRoom        = false;
    private boolean standardRoom    = false;

    private TiledMap tiledMap;

    private final int TREASURE_ROOM = 1;
    private final int BOSS_ROOM     = 2;
    private final int SHOP_ROOM     = 3;
    private TileLayer tileLayer;
    private EnemyLayer enemyLayer;

    public RoomDefinition(TiledMap map) {

        this.tiledMap = map;
        registerMapProperties(map);

    }

    private void registerMapProperties(TiledMap map) {

        if(map.getProperties().containsKey("doorNorth")) {
            doorNorth = map.getProperties().get("doorNorth", boolean.class);
        }

        if(map.getProperties().containsKey("doorSouth")) {
            doorSouth = map.getProperties().get("doorSouth", boolean.class);
        }

        if(map.getProperties().containsKey("doorWest")) {
            doorWest = map.getProperties().get("doorWest", boolean.class);
        }

        if(map.getProperties().containsKey("doorEast")) {
            doorEast = map.getProperties().get("doorEast", boolean.class);
        }

        if(map.getProperties().containsKey("roomType"))  {
            switch (map.getProperties().get("roomType", Integer.class)) {
                case TREASURE_ROOM:
                    treasureRoom = true;
                    break;
                case BOSS_ROOM:
                    bossRoom = true;
                    break;
                case SHOP_ROOM:
                    shopRoom = true;
                    break;
                default:
                    standardRoom = true;
                    break;
            }
        } else {
            standardRoom = true;
        }

    }


    public boolean isDoorNorth() {
        return doorNorth;
    }

    public boolean isDoorSouth() {
        return doorSouth;
    }

    public boolean isDoorWest() {
        return doorWest;
    }

    public boolean isDoorEast() {
        return doorEast;
    }

    public DestructibleLayer getDestLayer() throws MissdefinedTileException {

        TiledMapTileLayer t = (TiledMapTileLayer) tiledMap.getLayers().get("DestructibleLayer");

        Entity[][] entities = new Entity[t.getWidth()][t.getHeight()];

        for(int x = 0; x < t.getWidth(); x++) {
            for(int y = 0; y < t.getHeight(); y++) {
                Entity tile = new Entity();

                if(t.getCell(x,y) == null) continue;

                tile.add(new TileGraphicComponent(t.getCell(x, y).getTile().getTextureRegion(),1));

                if (!t.getCell(x, y).getTile().getProperties().containsKey("blocked")) {
                    throw new MissdefinedTileException("No blocked flag set");
                }

                if (!t.getCell(x, y).getTile().getProperties().containsKey("destructible")) {
                    throw new MissdefinedTileException("No destructible flag set");
                }

                boolean blocked = t.getCell(x, y).getTile().getProperties().get("blocked", boolean.class);
                if (blocked) {
                    tile.add(new BlockedTileComponent());
                } else {
                    tile.add(new WalkableTileComponent());
                }

                boolean destructible = t.getCell(x, y).getTile().getProperties().get("destructible", boolean.class);
                if(destructible) tile.add(new DestructibleComponent());

                if (t.getCell(x, y).getTile().getProperties().containsKey("inner")) {
                    if(t.getCell(x, y).getTile().getProperties().get("inner", boolean.class))
                        tile.add(new InnerTileComponent());
                }


                tile.add(new PositionComponent(x * Consts.TILE_SIZE, y * Consts.TILE_SIZE));
                tile.add(new SquareCollisionComponent(x * Consts.TILE_SIZE, y * Consts.TILE_SIZE, Consts.TILE_SIZE));

                entities[x][y] = tile;
            }
        }

        return new DestructibleLayer(entities, t.getWidth(),t.getHeight());
    }

    public TileLayer getTileLayer() throws MissdefinedTileException {
        TiledMapTileLayer t = (TiledMapTileLayer) tiledMap.getLayers().get("TileLayer");

        ArrayList<Entity> tiles = new ArrayList<Entity>();

        Entity[][] entities = new Entity[t.getWidth()][t.getHeight()];

        for(int x = 0; x < t.getWidth(); x++) {
            for(int y = 0; y < t.getHeight(); y++) {
                Entity tile = new Entity();

                if(t.getCell(x,y) == null) continue;

                tile.add(new TileGraphicComponent(t.getCell(x, y).getTile().getTextureRegion(),1));
                if (!t.getCell(x, y).getTile().getProperties().containsKey("blocked")) {
                    throw new MissdefinedTileException("No blocked flag set");
                } else {
                    boolean blocked = t.getCell(x, y).getTile().getProperties().get("blocked", boolean.class);
                    if (blocked) {
                        tile.add(new BlockedTileComponent());
                    } else {
                        tile.add(new WalkableTileComponent());
                    }
                    tile.add(new PositionComponent(x * Consts.TILE_SIZE, y * Consts.TILE_SIZE));
                    tile.add(new SquareCollisionComponent(x * Consts.TILE_SIZE, y * Consts.TILE_SIZE, Consts.TILE_SIZE));
                }

                entities[x][y] = tile;
                tiles.add(tile);
            }
        }

        return new TileLayer(entities, t.getWidth(),t.getHeight());
    }

    public EnemyLayer getEnemyLayer(Room room) {

        enemyLayer = new EnemyLayer();

        for(MapObject p : tiledMap.getLayers().get("EnemyLayer").getObjects()) {
            RectangleMapObject t = (RectangleMapObject)p;
            int id = t.getProperties().get("enemyID", Integer.class);
            enemyLayer.addEnemy(id, t.getRectangle().getX(), t.getRectangle().y, room);

        }

        return enemyLayer;
    }
}
