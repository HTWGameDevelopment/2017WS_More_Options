package com.moreoptions.prototype.gameEngine.data.ai;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.moreoptions.prototype.gameEngine.data.Room;

/**
 * Created by denwe on 10.01.2018.
 */
public class Sentry  {

    public class AttackState implements AIState {

        float attackCooldown = 2;
        float getAttackCooldownProgress = 2;



        @Override
        public void update(Room room, Entity self, float deltaTime) {

        }

        @Override
        public void draw(ShapeRenderer renderer) {

        }
    }
}
