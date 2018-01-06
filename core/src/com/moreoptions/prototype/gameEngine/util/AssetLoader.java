package com.moreoptions.prototype.gameEngine.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Array;
import com.moreoptions.prototype.gameEngine.data.Consts;
import com.moreoptions.prototype.gameEngine.data.SoundDatabase;
import com.moreoptions.prototype.level.RoomDefinition;

import java.util.ArrayList;

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
        assetManager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(new InternalFileHandleResolver()));
        assetManager.setLoader(BitmapFont.class, new FreetypeFontLoader(new InternalFileHandleResolver()));

    }

    public void loadAll() {
        loadRooms();
        loadFonts();
        loadSounds();
    }

    private void loadSounds() {

        assetManager.load("sound/gameOver.mp3", Sound.class);

    }

    private void loadFonts() {

        FreetypeFontLoader.FreeTypeFontLoaderParameter params = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        params.fontFileName = "fonts/RobotoSlab-Bold.ttf";
        params.fontParameters.size = 11;
        assetManager.load("fonts/RobotoSlab-Bold.ttf", BitmapFont.class, params);

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
            System.out.println("Loaded "+assetManager.getAll(TiledMap.class, new Array<TiledMap>()).size + " assets.");
            for(TiledMap t : assetManager.getAll(TiledMap.class, new Array<TiledMap>())) {
                RoomDefinition r = new RoomDefinition(t);
                definitions.add(r);
                System.out.println("Loaded Entity with: " + r.getRoomKind());
            }

            System.out.println("TEST: "+ assetManager.getAll(TiledMap.class, new Array<TiledMap>()).size);

            SoundDatabase.getInstance().registerSound(Consts.Sound.GAME_OVER_SOUND, assetManager.get("sound/gameOver.mp3", Sound.class));
            return true;
        } return false;
    }

    public ArrayList<RoomDefinition> definition(boolean hasDoorTop, boolean hasDoorBottom, boolean hasDoorLeft, boolean hasDoorRight, int kind) {
        ArrayList<RoomDefinition> fits = new ArrayList<RoomDefinition>();
        for(RoomDefinition def : definitions) {
            if((def.isDoorNorth() == hasDoorTop || hasDoorTop == false)
                && (def.isDoorSouth() == hasDoorBottom || hasDoorBottom == false)
                && (def.isDoorWest() == hasDoorLeft || hasDoorLeft == false)
                && (def.isDoorEast() == hasDoorRight || hasDoorRight== false)
                    && (def.getRoomKind() == kind)) {
                fits.add(def);
            }
        }

        if(fits.size() == 0) {
            System.out.println("DoorNorth: " + hasDoorTop +
            "DoorSouth: "+hasDoorBottom +
            "DoorLeft: "+hasDoorLeft +
            "DoorRight "+hasDoorRight +
            "Kind" + kind);
        }

        return fits;
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }
}
