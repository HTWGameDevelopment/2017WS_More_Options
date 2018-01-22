package com.moreoptions.prototype.gameEngine.util;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.moreoptions.prototype.gameEngine.components.DoorComponent;
import com.moreoptions.prototype.gameEngine.data.Consts;
import com.moreoptions.prototype.gameEngine.util.eventBus.Event;
import com.moreoptions.prototype.gameEngine.util.eventBus.EventBus;

public class EventFactory {


    public static void createShot(Entity self, Vector2 dir) {

        Event e = new Event(Consts.SHOOT_EVENT);
        e.addData(Consts.ENTITY, self);
        e.addData(Consts.DIRECTION, dir);
        EventBus.getInstance().addEvent(e);

    }

    public static void takeCollisionDamage(Entity player, Entity hitter) {

        Event e = new Event(Consts.DAMAGE_EVENT);
        e.addData(Consts.SELF, hitter);
        e.addData(Consts.HIT, player);
        EventBus.getInstance().addEvent(e);

    }

    public static void projectileHit(Entity projectile, Entity self) {

        Event e = new Event(Consts.DAMAGE_EVENT_PROJECTILE);
        e.addData(Consts.PROJECTILE, projectile);
        e.addData(Consts.HIT, self);
        EventBus.getInstance().addEvent(e);


    }

    public static void createDamageText(Entity hit, float dmg) {
        Event e = new Event(Consts.DAMAGE_COMBAT_TEXT_EVENT);
        e.addData(Consts.DAMAGE_AMOUNT, dmg);
        e.addData(Consts.HIT, hit);
        EventBus.getInstance().addEvent(e);
    }

    public static void gameOver() {
        Event e = new Event(Consts.GAME_OVER);
        EventBus.getInstance().addEvent(e);
    }

    public static void takeDamage(Entity player) {
        Event e = new Event(Consts.CONTACT_DAMAGE_EVENT);
        e.addData(Consts.SELF, player);
        EventBus.getInstance().addEvent(e);
    }

    public static void changeLevel(DoorComponent door) {
        System.out.println("Firing next level event");
        Event e = new Event(Consts.ADVANCE_LEVEL_EVENT);
        e.addData("door", door);
        EventBus.getInstance().addEvent(e);

    }

    public static void saveGame() {
        System.out.println("Saving game!");
        Event e = new Event(Consts.SAVE_GAME);
        EventBus.getInstance().addEvent(e);
    }
}
