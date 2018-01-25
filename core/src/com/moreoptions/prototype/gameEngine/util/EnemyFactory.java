package com.moreoptions.prototype.gameEngine.util;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.moreoptions.prototype.gameEngine.components.EnemyComponent;
import com.moreoptions.prototype.gameEngine.components.PositionComponent;
import com.moreoptions.prototype.gameEngine.data.Room;
import com.moreoptions.prototype.gameEngine.data.enemy.EnemyDatabase;

/**
 * Created by Andreas on 07.12.2017.
 */
public class EnemyFactory {

    private static ComponentMapper<EnemyComponent> enMapper = ComponentMapper.getFor(EnemyComponent.class);
    private static ComponentMapper<PositionComponent> posMapper = ComponentMapper.getFor(PositionComponent.class);

    public static Entity createEnemy(int enemyId, float x, float y, Room room) {

        return EnemyDatabase.getInstance().createEnemy(enemyId, x,y ,room);

    }

}
