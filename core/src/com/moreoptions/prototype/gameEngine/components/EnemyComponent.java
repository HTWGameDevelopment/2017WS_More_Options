package com.moreoptions.prototype.gameEngine.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.moreoptions.prototype.gameEngine.data.Room;
import com.moreoptions.prototype.gameEngine.data.Statistics;
import com.moreoptions.prototype.gameEngine.data.callback.OnDeathEvent;
import com.moreoptions.prototype.gameEngine.util.eventBus.Event;

/**
 * Created by denwe on 15.11.2017.
 */
public class EnemyComponent implements Component {

    private Vector2 startPosition;
    private boolean dead;

    private OnDeathEvent onDeath;


    private Room room;

    private int enemyId;

    public EnemyComponent(float x, float y,Room room, int enemyId ) {
        startPosition = new Vector2(x,y);
        this.room = room;
        this.enemyId = enemyId;
    }

    public OnDeathEvent getOnDeath() {
        return onDeath;
    }

    public void setOnDeath(OnDeathEvent onDeath) {
        this.onDeath = onDeath;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public Room getRoom() {
        return room;
    }


    public int getEnemyId() {
        return enemyId;
    }

    public void setEnemyId(int enemyId) {
        enemyId = enemyId;
    }
}
