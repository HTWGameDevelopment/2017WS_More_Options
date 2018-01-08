package com.moreoptions.prototype.gameEngine.data;

/**
 * Constants
 */
public class Consts {

    public static final int TILE_SIZE = 32;
    public static final int GAME_WIDTH = 17 * TILE_SIZE;
    public static final int GAME_HEIGHT = 11 * TILE_SIZE;

    public static final String SPAWN_ENEMY = "SPAWN_ENEMY";
    public static final int ENEMY = 0;
    public static final int ITEM = 1;
    public static final String DOOR_STAT = "DOOR_STAT";
    public static final String ACHIEVEMENT_EVENT_ID = "Achievement_Event";
    public static final String INT = "Int";
    public static final String DAMAGE_EVENT = "Damage_Event";
    public static final String SELF = "SELF";
    public static final String HIT = "HIT";
    public static final String DAMAGE_EVENT_PROJECTILE ="Damage_Event_Projectile" ;
    public static final String PROJECTILE = "PROJECTILE";
    public static final String DAMAGE_COMBAT_TEXT_EVENT = "COMBAT_TEXT_EVENT";
    public static final String DAMAGE_AMOUNT = "DAMAGE_AMOUNT";
    public static final String GAME_OVER = "GAME_OVER";
    public static final String CONTACT_DAMAGE_EVENT = "CONTACT_DAMAGE";
    public static final float MAX_GOLD = 99f;
    public static final String REMOVE_ITEM = "Remove_ITEM" ;
    public static final int SPECIAL_ITEM = 1;
    public static final String HEALTH_CHANGE = "Health_Change";
    public static final String HEALTH_CHANGE_VALUE = "Health_Change_Value" ;
    public static String SHOOT_EVENT = "Shoot";
    public static String ENTITY = "Entity";
    public static String DIRECTION = "Direction";

    public class Sound {

        public static final String GAME_OVER_SOUND = "gameOver";
    }
}