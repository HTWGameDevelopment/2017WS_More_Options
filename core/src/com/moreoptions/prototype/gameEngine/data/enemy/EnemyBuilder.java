package com.moreoptions.prototype.gameEngine.data.enemy;

import com.badlogic.gdx.graphics.Color;
import com.moreoptions.prototype.gameEngine.data.Statistics;
import com.moreoptions.prototype.gameEngine.data.callback.GameEvent;

public class EnemyBuilder {
    private Color color = Color.WHITE;
    private Color borderColor = Color.BLACK;
    private int size = 4;
    private int displaySize = 10;
    private EnemyBehavior behavior;
    private Statistics stats = new Statistics();
    private Loot loot = new Loot();
    private GameEvent onDeathEvent = new GameEvent.NullEvent();
    private GameEvent onCollisionEvent = new GameEvent.NullEvent();
    private GameEvent onHitEvent = new GameEvent.NullEvent();

    public EnemyBuilder setColor(Color color) {
        this.color = color;
        return this;
    }

    public EnemyBuilder setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
        return this;
    }

    public EnemyBuilder setSize(int size) {
        this.size = size;
        return this;
    }

    public EnemyBuilder setDisplaySize(int displaySize) {
        this.displaySize = displaySize;
        return this;
    }

    public EnemyBuilder setBehavior(EnemyBehavior behavior) {
        this.behavior = behavior;
        return this;
    }

    public EnemyBuilder setStats(Statistics stats) {
        this.stats = stats;
        return this;
    }

    public EnemyBuilder setLoot(Loot loot){
        this.loot = loot;
        return this;
    }

    public EnemyBuilder setOnDeathEvent(GameEvent onDeathEvent) {
        this.onDeathEvent = onDeathEvent;
        return this;
    }

    public EnemyBuilder setOnCollisionEvent(GameEvent onCollisionEvent) {
        this.onCollisionEvent = onCollisionEvent;
        return this;
    }

    public EnemyBuilder setOnHitEvent(GameEvent onHitEvent) {
        this.onHitEvent = onHitEvent;
        return this;
    }

    public Enemy createEnemy() {
        return new Enemy(color, borderColor, size, displaySize, behavior, stats, onDeathEvent, onCollisionEvent, onHitEvent);
    }


}