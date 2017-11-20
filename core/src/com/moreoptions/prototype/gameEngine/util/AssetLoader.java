package com.moreoptions.prototype.gameEngine.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.maps.tiled.AtlasTmxMapLoader;
import com.badlogic.gdx.maps.tiled.TideMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Array;
import com.moreoptions.prototype.level.RoomDefinition;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by denwe on 17.11.2017.
 */
public class AssetLoader {
    private static AssetLoader ourInstance = new AssetLoader();

    public static AssetLoader getInstance() {
        return ourInstance;
    }


    private AssetManager assetManager;
    private ArrayList<RoomDefinition> definitions = new ArrayList();

    private AssetLoader() {
        assetManager = new AssetManager();
        assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));

    }

    public void loadAll() {
        loadRooms();
    }


    public void loadRooms() {

        FileHandle t = Gdx.files.internal("rooms/");

        for(FileHandle file : t.list()) {
            if(file.name().endsWith(".tmx")) assetManager.load(file.path(), TiledMap.class);

        }

    }

    public boolean update() {
        if(assetManager.update()) {
            System.out.println("Done loading");
            for(TiledMap t : assetManager.getAll(TiledMap.class, new Array<TiledMap>())) {
                definitions.add(new RoomDefinition(t));
            }
            return true;
        } return false;
    }

    public ArrayList<RoomDefinition> definition(boolean hasDoorTop, boolean hasDoorBottom, boolean hasDoorLeft, boolean hasDoorRight) {

        ArrayList<RoomDefinition> fits = new ArrayList<RoomDefinition>();
        for(RoomDefinition def : definitions) {
            if((def.isDoorNorth() == hasDoorTop || hasDoorTop == false)
                && (def.isDoorSouth() == hasDoorBottom || hasDoorBottom == false)
                && (def.isDoorWest() == hasDoorLeft || hasDoorLeft == false)
                && (def.isDoorEast() == hasDoorRight || hasDoorRight== false)) {
                fits.add(def);
            }
        }

        return fits;
    }
}
