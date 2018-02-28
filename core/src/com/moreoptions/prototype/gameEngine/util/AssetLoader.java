package com.moreoptions.prototype.gameEngine.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.moreoptions.prototype.gameEngine.data.ItemDatabase;
import com.moreoptions.prototype.gameEngine.data.SoundDatabase;
import com.moreoptions.prototype.gameEngine.level.RoomDefinition;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Singleton that handles all assets and their databaseregistration
 * TODO: fracture loading into steps to easier display progress
 */
public class AssetLoader {
    private static AssetLoader ourInstance = new AssetLoader();
    private HashMap<String, String> musics = new HashMap<String, String>();

    public static AssetLoader getInstance() {
        return ourInstance;
    }

    private AssetManager assetManager;
    private ArrayList<RoomDefinition> definitions = new ArrayList();

    private ArrayList<Pair<String,String>> sounds = new ArrayList<Pair<String, String>>();

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
        loadMusic();
    }

    private void loadSounds() {

        FileHandle soundFolder = Gdx.files.internal("sound/");
        for(FileHandle f : soundFolder.list()) {
            if(f.name().contains("Thumbs")) continue;
            String[] s = f.name().split("_");
            sounds.add(new Pair<String, String>(soundFolder.name() + "/" + f.name(),s[0]));
            assetManager.load(soundFolder.name() + "/" + f.name(), Sound.class);
        }

    }

    private void loadMusic() {
        FileHandle musicFolder = Gdx.files.internal("music/");
        for(FileHandle f : musicFolder.list()) {
            String[] s = f.name().split("_");

            musics.put(musicFolder.name() + "/" + f.name(),s[0]);
            assetManager.load(musicFolder.name() + "/" + f.name(), Music.class);
        }
    }

    private void loadFonts() {

        FreetypeFontLoader.FreeTypeFontLoaderParameter params = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        params.fontFileName = "fonts/RobotoSlab-Bold.ttf";
        params.fontParameters.size = 10;
        params.fontParameters.borderWidth =1;
        params.fontParameters.borderColor = Color.BLACK;
        params.fontParameters.genMipMaps = true;

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
            System.out.println("Done pre - loading");
            System.out.println("Loaded "+assetManager.getAll(TiledMap.class, new Array<TiledMap>()).size + " assets.");
            System.out.println("Loading items");
            for(TiledMap t : assetManager.getAll(TiledMap.class, new Array<TiledMap>())) {
                RoomDefinition r = new RoomDefinition(t);
                definitions.add(r);
                System.out.println("Loaded Entity with: " + r.getRoomKind());
            }

            for(Pair<String, String> p : sounds) {

                String fileName = p.getKey();
                String tag = p.getValue();

                SoundDatabase.getInstance().registerSound(tag, assetManager.get(fileName, Sound.class));

            }


            for(Map.Entry<String, String> p : musics.entrySet()) {

                String fileName = p.getKey();
                String tag = p.getValue();

                SoundDatabase.getInstance().registerMusic(tag, assetManager.get(fileName, Music.class));

            }
            return true;
        } return false;
    }



    public ArrayList<RoomDefinition> definition(boolean hasDoorTop, boolean hasDoorBottom, boolean hasDoorLeft, boolean hasDoorRight, int kind) {
        ArrayList<RoomDefinition> fits = new ArrayList<RoomDefinition>();
        for(RoomDefinition def : definitions) {
            if((def.isDoorNorth() == hasDoorTop || !hasDoorTop)
                && (def.isDoorSouth() == hasDoorBottom || !hasDoorBottom)
                && (def.isDoorWest() == hasDoorLeft || !hasDoorLeft)
                && (def.isDoorEast() == hasDoorRight || !hasDoorRight)
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

    public Skin getSkin() {
        return assetManager.get("comic/skin2/uiskin.json", Skin.class);
    }

    public void loadSkin() {
        assetManager.load("comic/skin2/uiskin.json", Skin.class);
    }
}
