package com.moreoptions.prototype.gameEngine.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import com.moreoptions.prototype.gameEngine.components.*;
import com.moreoptions.prototype.gameEngine.data.InputState;

/**
 * Created by User on 11/20/2017.
 */
public class ProjectileSystem extends EntitySystem {
    private Family family = Family.all(PlayerComponent.class).get();

    private Family projFamily = Family.all(ProjectileComponent.class).get();

    @Override
    public void update(float deltatime){
        ImmutableArray<Entity> entities = getEngine().getEntitiesFor(family);

        for (Entity e : entities){
            InputState state = e.getComponent(PlayerComponent.class).getPlayer().getInputState();

            //if leftShoot = true


        }

        ImmutableArray<Entity> proj = getEngine().getEntitiesFor(projFamily);

    }
}
