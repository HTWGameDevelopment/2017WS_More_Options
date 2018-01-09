package com.moreoptions.prototype.gameEngine.level;

/**
 * Created by denwe on 04.11.2017.
 */
public interface LevelGenerator {

    Level getLevel(int width, int height, int roomCount);

}
