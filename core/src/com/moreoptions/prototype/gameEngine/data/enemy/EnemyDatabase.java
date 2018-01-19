package com.moreoptions.prototype.gameEngine.data.enemy;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
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

    private HashMap<Integer, Enemy> enemyMap = new HashMap<Integer, Enemy>();

    private EnemyDatabase() {

        registerEnemy(0, new EnemyBuilder()
                .setBehavior(new EnemyBehavior.EnemyBehaviorBuilder()
                        .addState(Consts.Ai.MOVE, new StandardMoveState())
                        .setStartState(Consts.Ai.MOVE)
                        .build())
                .setStats(new StatisticsBuilder()
                        .speed(100)
                        .maxHealth(5)
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
                .setLoot(Loot.getLootById(2))
                .setStats(new StatisticsBuilder()
                        .speed(200)
                        .maxHealth(5)
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
                        .setSize(30)
                        .setDisplaySize(30)
                        .setColor(Color.BLUE)
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
                        .setSize(20)
                        .setDisplaySize(20)
                        .setColor(Color.PURPLE)
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
                        .setSize(15)
                        .setDisplaySize(15)
                        .setColor(Color.ROYAL)
                .setOnDeathEvent(new Splitter.SubSubOnDeathEvent())
                .createEnemy());




    }

    private void registerEnemy(int id, Enemy enemy) {
        if(!enemyMap.containsKey(id)) enemyMap.put(id, enemy);
        else try {
            throw new Exception("Already contains ID: "+ id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Entity createEnemy(int enemyId, float x, float y, Room room) {
        if(!enemyMap.containsKey(enemyId)) {
            System.out.println("[ENEMYDATABASE] EnemyID "+ enemyId +" was not assigned!");
            return enemyMap.get(0).getEntity(x,y, room);
        }
        return enemyMap.get(enemyId).getEntity(x,y, room);

    }

    public Enemy getEnemyById(int enemyId){
        return enemyMap.get(enemyId);
    }
}
