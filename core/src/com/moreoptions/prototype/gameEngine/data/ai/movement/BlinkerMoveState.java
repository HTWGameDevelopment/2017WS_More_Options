package com.moreoptions.prototype.gameEngine.data.ai.movement;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.moreoptions.prototype.gameEngine.components.AIComponent;
import com.moreoptions.prototype.gameEngine.components.DebugColorComponent;
import com.moreoptions.prototype.gameEngine.components.PositionComponent;
import com.moreoptions.prototype.gameEngine.data.Consts;
import com.moreoptions.prototype.gameEngine.data.Room;
import com.moreoptions.prototype.gameEngine.data.ai.AIState;
import com.moreoptions.prototype.gameEngine.util.EventFactory;
import com.moreoptions.prototype.gameEngine.util.ProjectileFactory;

import java.util.ArrayList;


/**
 * Created by Andreas on 06.12.2017.
 */
public class BlinkerMoveState implements AIState {

    private static final float COOLDOWN = 3.0f;
    private float shotCooldownProgress = 0;

    private float timeAfterAppearing = 0;
    private boolean attacking = true;

    Color moveColor = new Color(0.5f,0.5f,0,1);
    Color attackColor = new Color(0,0,0,1);

    private boolean shot = false;

    private ComponentMapper<AIComponent> aiMapper = ComponentMapper.getFor(AIComponent.class);
    private float timeAfterAttacking = 0;

    @Override
    public void update(Room room, Entity self, float delta) {

        //Alright. Blinker "blinks" around, appearing in random places. After blinking and having line of sight towards the players, shoot straight at him.

        if(timeAfterAppearing > COOLDOWN) {
            attackPlayer(room,self);
            timeAfterAppearing = 0;
            attacking = false;

            self.getComponent(DebugColorComponent.class).setColor(moveColor);
            moveColor.set(0,0.5f, 0.5f, 1);

        } else if(attacking) {
            timeAfterAppearing += delta;
            attackColor.set(timeAfterAppearing/COOLDOWN,0,0,1);
            self.getComponent(DebugColorComponent.class).setColor(attackColor);
        } else if(timeAfterAttacking > COOLDOWN) {
            teleport(room,self);
            timeAfterAttacking = 0;
            attacking = true;
            attackColor.set(0,0.5f,0,1);
            self.getComponent(DebugColorComponent.class).setColor(attackColor);
        } else {
            timeAfterAttacking += delta;
            moveColor.set(0.5f,0.5f, timeAfterAttacking/COOLDOWN, 1);
        }

    }

    private void attackPlayer(Room room, Entity self) {

        PositionComponent p = getClosestPlayer(room.getPlayerList(), self).getComponent(PositionComponent.class);
        PositionComponent ownPos = self.getComponent(PositionComponent.class);

        Vector2 dir = p.getPosition().cpy().sub(ownPos.getPosition().cpy()).nor();


        EventFactory.createShot(self, dir);
    }

    private void teleport(Room room, Entity self) {

        Vector2 randPos = room.getNavGraph().getRandomPosition(self);

        PositionComponent p = getClosestPlayer(room.getPlayerList(), self).getComponent(PositionComponent.class);
        if(randPos.dst(p.getPosition()) < 20) {
            //TODO maybe implement a recursion. but rather not.
        } else {
            self.getComponent(PositionComponent.class).setX(randPos.cpy().x);
            self.getComponent(PositionComponent.class).setY(randPos.cpy().y);
        }

    }

    @Override
    public void draw(ShapeRenderer renderer) {

    }

    public Entity getClosestPlayer (ArrayList<Entity> playerList, Entity self) {
        return playerList.get(0);
    }
}
