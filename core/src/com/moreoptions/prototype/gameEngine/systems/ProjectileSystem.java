package com.moreoptions.prototype.gameEngine.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.moreoptions.prototype.gameEngine.GameWorld;
import com.moreoptions.prototype.gameEngine.components.*;
import com.moreoptions.prototype.gameEngine.data.InputState;
import com.moreoptions.prototype.gameEngine.data.projectileEvents.SplitEvent;

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

    public static void shootDown(Entity e ) {
        Entity proj = new Entity();

        PositionComponent playerPosition = e.getComponent(PositionComponent.class);


        proj.add(new PositionComponent(playerPosition.getX(), playerPosition.getY()));
        proj.add(new VelocityComponent(50, 10));
        proj.add(new CollisionComponent(new SplitEvent()));
        proj.add(new CircleCollisionComponent((proj.getComponent(PositionComponent.class).getX()), (proj.getComponent(PositionComponent.class).getY()), 2));
        proj.add(new DebugColorComponent(Color.CORAL));
        VelocityComponent pv = proj.getComponent(VelocityComponent.class);
        pv.setVelY(-200);
        GameWorld.getInstance().addEntity(proj);
    }

    public static void shootUp(Entity e ) {
        Entity proj = new Entity();

        PositionComponent playerPosition = e.getComponent(PositionComponent.class);


        proj.add(new PositionComponent(playerPosition.getX(), playerPosition.getY()));
        proj.add(new VelocityComponent(50, 10));
        proj.add(new CollisionComponent(new SplitEvent()));
        proj.add(new CircleCollisionComponent((proj.getComponent(PositionComponent.class).getX()), (proj.getComponent(PositionComponent.class).getY()), 2));
        proj.add(new DebugColorComponent(Color.CORAL));
        VelocityComponent pv = proj.getComponent(VelocityComponent.class);
        pv.setVelY(200);
        GameWorld.getInstance().addEntity(proj);
    }

    public static void shootLeft(Entity e ) {
        Entity proj = new Entity();

        PositionComponent playerPosition = e.getComponent(PositionComponent.class);


        proj.add(new PositionComponent(playerPosition.getX(), playerPosition.getY()));
        proj.add(new VelocityComponent(50, 10));
        proj.add(new CollisionComponent(new SplitEvent()));
        proj.add(new CircleCollisionComponent((proj.getComponent(PositionComponent.class).getX()), (proj.getComponent(PositionComponent.class).getY()), 2));
        proj.add(new DebugColorComponent(Color.CORAL));
        VelocityComponent pv = proj.getComponent(VelocityComponent.class);
        pv.setVelX(-200);
        GameWorld.getInstance().addEntity(proj);
    }

    public static void shootRight(Entity e ) {
        Entity proj = new Entity();

        PositionComponent playerPosition = e.getComponent(PositionComponent.class);


        proj.add(new PositionComponent(playerPosition.getX(), playerPosition.getY()));
        proj.add(new VelocityComponent(50, 10));
        proj.add(new CollisionComponent(new SplitEvent()));
        proj.add(new CircleCollisionComponent((proj.getComponent(PositionComponent.class).getX()), (proj.getComponent(PositionComponent.class).getY()), 2));
        proj.add(new DebugColorComponent(Color.CORAL));
        VelocityComponent pv = proj.getComponent(VelocityComponent.class);
        pv.setVelX(200);
        GameWorld.getInstance().addEntity(proj);
    }
}
