package com.moreoptions.prototype.gameEngine.data.enemy;

import com.badlogic.ashley.core.Entity;
import com.moreoptions.prototype.gameEngine.components.EnemyComponent;
import com.moreoptions.prototype.gameEngine.components.PositionComponent;
import com.moreoptions.prototype.gameEngine.data.Consts;
import com.moreoptions.prototype.gameEngine.data.Room;
import com.moreoptions.prototype.gameEngine.data.Statistics;
import com.moreoptions.prototype.gameEngine.data.StatisticsBuilder;
import com.moreoptions.prototype.gameEngine.data.ai.Splitter;
import com.moreoptions.prototype.gameEngine.data.ai.attacking.SplitterAttackState;
import com.moreoptions.prototype.gameEngine.data.ai.attacking.SplitterSubAttackState;
import com.moreoptions.prototype.gameEngine.data.ai.attacking.SplitterSubSubAttackState;
import com.moreoptions.prototype.gameEngine.data.ai.attacking.StandardAttackState;
import com.moreoptions.prototype.gameEngine.data.ai.movement.BlinkerMoveState;
import com.moreoptions.prototype.gameEngine.data.ai.movement.ChasedMoveState;
import com.moreoptions.prototype.gameEngine.data.ai.movement.SplitterMoveState;
import com.moreoptions.prototype.gameEngine.data.ai.movement.StandardMoveState;
import com.moreoptions.prototype.gameEngine.data.callback.GameEvent;
import com.moreoptions.prototype.gameEngine.util.EnemyFactory;
import com.moreoptions.prototype.gameEngine.util.eventBus.Event;
import com.moreoptions.prototype.gameEngine.util.eventBus.EventBus;

import java.util.HashMap;

/**
 * Created by denwe on 10.01.2018.
 */
public class EnemyDatabase {
    private static EnemyDatabase ourInstance = new EnemyDatabase();

    public static EnemyDatabase getInstance() {
        return ourInstance;
    }

    HashMap<Integer, Enemy> enemyMap = new HashMap<Integer, Enemy>();

    private EnemyDatabase() {

        registerEnemy(0, new EnemyBuilder()
                .setBehavior(new EnemyBehavior.EnemyBehaviorBuilder()
                        .addState(Consts.Ai.MOVE, new StandardMoveState())
                        .setStartState("Move")
                        .build())
                .setStats(new StatisticsBuilder()
                        .speed(100)
                        .maxHealth(3)
                        .build())
                .createEnemy());

        registerEnemy(1, new EnemyBuilder()
                .setBehavior(new EnemyBehavior.EnemyBehaviorBuilder()
                        .addState(Consts.Ai.MOVE, new BlinkerMoveState())
                        .addState(Consts.Ai.ATTACK, new StandardAttackState())
                        .setStartState(Consts.Ai.MOVE)
                        .build())
                .setStats(new StatisticsBuilder()
                        .speed(100)
                        .maxHealth(3)
                        .build())
                .createEnemy());

        registerEnemy(2, new EnemyBuilder()
                .setBehavior(new EnemyBehavior.EnemyBehaviorBuilder()
                        .addState(Consts.Ai.MOVE, new ChasedMoveState())
                        .setStartState(Consts.Ai.MOVE)
                        .build())
                .setStats(new StatisticsBuilder()
                        .speed(100)
                        .maxHealth(3)
                        .build())
                .createEnemy());

        registerEnemy(3, new EnemyBuilder()
                .setBehavior(new EnemyBehavior.EnemyBehaviorBuilder()
                        .addState(Consts.Ai.ATTACK, new SplitterAttackState())
                        .addState(Consts.Ai.MOVE, new SplitterMoveState())
                        .setStartState(Consts.Ai.MOVE)
                        .build())
                .setStats(new StatisticsBuilder()
                        .speed(100)
                        .maxHealth(10)
                        .build())
                .setOnDeathEvent(new Splitter.OnDeathEvent())
                .createEnemy());

        registerEnemy(4, new EnemyBuilder()
                .setBehavior(new EnemyBehavior.EnemyBehaviorBuilder()
                        .addState(Consts.Ai.ATTACK, new SplitterSubAttackState())
                        .addState(Consts.Ai.MOVE, new SplitterMoveState())
                        .setStartState(Consts.Ai.MOVE)
                        .build())
                .setStats(new StatisticsBuilder()
                        .speed(100)
                        .maxHealth(3)
                        .build())
                .setOnDeathEvent(new Splitter.SubOnDeathEvent())
                .createEnemy());

        registerEnemy(5, new EnemyBuilder()
                .setBehavior(new EnemyBehavior.EnemyBehaviorBuilder()
                        .addState(Consts.Ai.ATTACK, new SplitterSubSubAttackState())
                        .addState(Consts.Ai.MOVE, new SplitterMoveState())
                        .setStartState(Consts.Ai.MOVE)
                        .build())
                .setStats(new StatisticsBuilder()
                        .speed(100)
                        .maxHealth(1)
                        .build())
                .createEnemy());




    }

    private void registerEnemy(int i, Enemy enemy) {
        if(!enemyMap.containsKey(i)) enemyMap.put(i, enemy);
        else try {
            throw new Exception("Already contains ID "+ i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Entity createEnemy(int enemyId, float x, float y, Room room) {
        System.out.println("CREATED UNIQUE ENEMY");
        if(!enemyMap.containsKey(enemyId)) return enemyMap.get(0).getEntity(x,y, room);
        return enemyMap.get(0).getEntity(x,y, room);

    }
}
