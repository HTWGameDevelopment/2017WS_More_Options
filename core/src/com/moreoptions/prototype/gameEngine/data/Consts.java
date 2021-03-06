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
    public static final int MAX_GOLD = 99;
    public static final String REMOVE_ITEM = "Remove_ITEM" ;
    public static final int SPECIAL_ITEM = 1;
    public static final String HEALTH_CHANGE = "Health_Change";
    public static final String HEALTH_CHANGE_VALUE = "Health_Change_Value" ;
    public static final String ADVANCE_LEVEL_EVENT = "Advance";
    public static final String SAVE_GAME = "Save_Game";
    public static final String TITLE_MUSIC = "TitleMusic";
    public static final float MOVE_DISPLACEMENT_TEST = 10;
    public static String SHOOT_EVENT = "Shoot";
    public static String ENTITY = "Entity";
    public static String DIRECTION = "Direction";

    public class Sound {

        public static final String GAME_OVER_SOUND = "gameOver";
        public static final String TILE_MUSIC = "TitleMusic";
        public static final String COIN_PICKUP = "coin";
        public static final String DUNGEON_MUSIC = "dungeon";
        public static final String HIT = "hit";
        public static final String PURCHASE_ITEM = "purchase";
    }

    public class Error {
        public static final String BEHAVIOR_ALREADY_DEFINED = "Supplied Behavior was already defined";
        public static final String STARTSTATE_NOT_FOUND = "StartState was not found" ;
    }

    public class Ai {
        public static final String MOVE = "Move";
        public static final String ATTACK = "Attack";
    }

    public class Network {
        public static final String SALT = "ENTER_CUSTOM_SALT";
        public static final String SERVER_ADRESS = "http://localhost:8080";
        public static final int SUCCESS = 200;
    }
}