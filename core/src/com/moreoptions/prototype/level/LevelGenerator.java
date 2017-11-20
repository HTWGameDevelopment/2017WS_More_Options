package com.moreoptions.prototype.level;

/**
 * Created by denwe on 04.11.2017.
 */
public interface LevelGenerator {

    LevelBlueprint getLevel(int width, int height, int roomCount);


}
