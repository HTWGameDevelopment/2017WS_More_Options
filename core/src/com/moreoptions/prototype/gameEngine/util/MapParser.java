package com.moreoptions.prototype.gameEngine.util;

//Creates Rooms based on Tiled maps


import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.moreoptions.prototype.gameEngine.GameEngine;
import com.moreoptions.prototype.gameEngine.components.*;
import com.moreoptions.prototype.gameEngine.data.Consts;
import com.moreoptions.prototype.gameEngine.data.Room;
import com.moreoptions.prototype.gameEngine.exceptions.MissdefinedTileException;

import java.util.ArrayList;

public class MapParser {


    public static ArrayList<Entity> loadRooms() throws MissdefinedTileException {

        long startTime = System.currentTimeMillis();

        FileHandle mapFolder = Gdx.files.internal("rooms/level1/");

        ArrayList<Entity> tileList = new ArrayList<Entity>();


        for(FileHandle mapFile : mapFolder.list()) {
            if(!mapFile.name().contains("tmx")) continue;
            TiledMap tiledMap = new TmxMapLoader().load(mapFile.path());
            tileList.addAll(loadTileLayer(tiledMap));
            tileList.addAll(loadBackgroundLayer(tiledMap));
        }

        Gdx.app.log("MapParser","Loadtime: " + 0.001*(System.currentTimeMillis() - startTime) + " seconds");

        return tileList;
    }

    private static ArrayList<Entity> loadTileLayer(TiledMap tiledMap) throws MissdefinedTileException {

        TiledMapTileLayer t = (TiledMapTileLayer) tiledMap.getLayers().get("TileLayer");
        ArrayList<Entity> tiles = new ArrayList<Entity>();

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
                tiles.add(tile);
            }
        }
        return tiles;
    }

    public static ArrayList<Entity> loadBackgroundLayer(TiledMap tiledMap) throws MissdefinedTileException {
        TiledMapTileLayer t = (TiledMapTileLayer) tiledMap.getLayers().get("BackgroundLayer");
        ArrayList<Entity> tiles = new ArrayList<Entity>();

        for(int x = 0; x < t.getWidth(); x++) {
            for(int y = 0; y < t.getHeight(); y++) {
                Entity tile = new Entity();

                if(t.getCell(x,y) == null) continue;

                tile.add(new TileGraphicComponent(t.getCell(x, y).getTile().getTextureRegion(),0));
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
                tiles.add(tile);
            }
        }
        return tiles;
    }

}
