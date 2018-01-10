package com.moreoptions.prototype.gameEngine.data.ai;

import com.badlogic.ashley.core.Entity;
import com.moreoptions.prototype.gameEngine.components.EnemyComponent;
import com.moreoptions.prototype.gameEngine.components.PositionComponent;
import com.moreoptions.prototype.gameEngine.data.Consts;
import com.moreoptions.prototype.gameEngine.data.callback.GameEvent;
import com.moreoptions.prototype.gameEngine.util.EnemyFactory;
import com.moreoptions.prototype.gameEngine.util.eventBus.Event;
import com.moreoptions.prototype.gameEngine.util.eventBus.EventBus;

/**
 * Created by denwe on 10.01.2018.
 */
public class Splitter {


    public static class OnDeathEvent implements GameEvent {

        @Override
        public boolean onTrigger(Entity us, Entity them) {
            PositionComponent pos = us.getComponent(PositionComponent.class);
            EnemyComponent en = us.getComponent(EnemyComponent.class);
            Entity one = EnemyFactory.createEnemy(4, pos.getX() + 5, pos.getY(), en.getRoom());
            Entity two = EnemyFactory.createEnemy(4, pos.getX() - 5, pos.getY(), en.getRoom());
            Event e = new Event(Consts.SPAWN_ENEMY);
            e.addData("1", one);
            e.addData("2", two);
            EventBus.getInstance().addEvent(e);
            return true;
        }
    }

    public static class SubOnDeathEvent implements GameEvent {

        @Override
        public boolean onTrigger(Entity us, Entity them) {
            PositionComponent pos = us.getComponent(PositionComponent.class);
            EnemyComponent en = us.getComponent(EnemyComponent.class);
            Entity one = EnemyFactory.createEnemy(5, pos.getX() + 5, pos.getY(), en.getRoom());
            Entity two = EnemyFactory.createEnemy(5, pos.getX() - 5, pos.getY(), en.getRoom());
            Event e = new Event(Consts.SPAWN_ENEMY);
            e.addData("1", one);
            e.addData("2", two);
            EventBus.getInstance().addEvent(e);
            return true;
        }
    }

}
