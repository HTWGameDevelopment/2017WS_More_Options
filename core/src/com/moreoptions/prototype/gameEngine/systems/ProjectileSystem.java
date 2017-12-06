package com.moreoptions.prototype.gameEngine.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Color;
import com.moreoptions.prototype.gameEngine.GameWorld;
import com.moreoptions.prototype.gameEngine.components.*;
import com.moreoptions.prototype.gameEngine.data.InputState;
import com.moreoptions.prototype.gameEngine.data.Player;
import com.moreoptions.prototype.gameEngine.data.PlayerStatistics;
import com.moreoptions.prototype.gameEngine.data.callback.CollisionEvent;
import com.moreoptions.prototype.gameEngine.data.projectileEvents.SplitEvent;

/**
 * Created by User on 11/20/2017.
 */
public class ProjectileSystem extends EntitySystem {
    private Family family = Family.all(PlayerComponent.class).get();

    private Family projFamily = Family.all(ProjectileComponent.class).get();

    @Override
    public void update(float deltatime){
        ImmutableArray<Entity> entities = getEngine().getEntitiesFor(projFamily);

        for (Entity e : entities){

            ProjectileComponent p = e.getComponent(ProjectileComponent.class);
            CollisionComponent c = e.getComponent(CollisionComponent.class);
            PositionComponent pc = e.getComponent(PositionComponent.class);

            float oldX = c.getOldX();
            float oldY = c.getOldY();
            float currentX = pc.getX();
            float currentY = pc.getY();

            p.setDistanceTravelled((float) ( p.getDistanceTravelled() + (Math.hypot(oldX-currentX, oldY - currentY))));

            if(p.getDistanceTravelled() > p.getRange()) getEngine().removeEntity(e);

        }


    }

    public static void shoot(Direction direction, Entity e) {

        PlayerComponent p = e.getComponent(PlayerComponent.class);

        PlayerStatistics stats = p.getPlayer().getStats();

        if(stats.getCurrentShotCooldown() > stats.getFireRate()) {
            stats.setCurrentShotCooldown(0);
            switch (direction) {

                case UP:
                    shootUp(e);
                    break;
                case DOWN:
                    shootDown(e);
                    break;
                case LEFT:
                    shootLeft(e);
                    break;
                case RIGHT:
                    shootRight(e);
                    break;
                default:
                    throw new NullPointerException("Direction was null");

            }

        }

    }



    public static void shootDown(Entity e ) {
        Entity proj = new Entity();

        PlayerStatistics stats = e.getComponent(PlayerComponent.class).getPlayer().getStats();

        System.out.println("Shooting down");
        PositionComponent playerPosition = e.getComponent(PositionComponent.class);


        proj.add(new PositionComponent(playerPosition.getX(), playerPosition.getY()));
        proj.add(new VelocityComponent(stats.getProjectileSpeed(), 10));
        proj.add(new CollisionComponent(new CollisionEvent.DefaultProjectileCollisionEvent()));
        proj.add(new CircleCollisionComponent((proj.getComponent(PositionComponent.class).getX()), (proj.getComponent(PositionComponent.class).getY()), 2));
        proj.add(new DebugColorComponent(Color.CORAL));
        proj.add(new ProjectileComponent(stats.getRange()));
        VelocityComponent pv = proj.getComponent(VelocityComponent.class);
        pv.setVelY(-pv.getSpeed());
        GameWorld.getInstance().addEntity(proj);
    }

    public static void shootUp(Entity e ) {
        Entity proj = new Entity();

        PositionComponent playerPosition = e.getComponent(PositionComponent.class);

        PlayerStatistics stats = e.getComponent(PlayerComponent.class).getPlayer().getStats();


        proj.add(new PositionComponent(playerPosition.getX(), playerPosition.getY()));
        proj.add(new VelocityComponent(stats.getProjectileSpeed(), 10));
        proj.add(new CollisionComponent(new CollisionEvent.DefaultProjectileCollisionEvent()));
        proj.add(new CircleCollisionComponent((proj.getComponent(PositionComponent.class).getX()), (proj.getComponent(PositionComponent.class).getY()), 2));
        proj.add(new DebugColorComponent(Color.CORAL));
        proj.add(new ProjectileComponent(stats.getRange()));
        VelocityComponent pv = proj.getComponent(VelocityComponent.class);
        pv.setVelY(pv.getSpeed());
        GameWorld.getInstance().addEntity(proj);
    }

    public static void shootLeft(Entity e ) {
        Entity proj = new Entity();

        PositionComponent playerPosition = e.getComponent(PositionComponent.class);

        PlayerStatistics stats = e.getComponent(PlayerComponent.class).getPlayer().getStats();


        proj.add(new PositionComponent(playerPosition.getX(), playerPosition.getY()));
        proj.add(new VelocityComponent(stats.getProjectileSpeed(), 10));
        proj.add(new CollisionComponent(new CollisionEvent.DefaultProjectileCollisionEvent()));
        proj.add(new CircleCollisionComponent((proj.getComponent(PositionComponent.class).getX()), (proj.getComponent(PositionComponent.class).getY()), 2));
        proj.add(new DebugColorComponent(Color.CORAL));

        proj.add(new ProjectileComponent(stats.getRange()));
        VelocityComponent pv = proj.getComponent(VelocityComponent.class);
        pv.setVelX(-pv.getSpeed());
        GameWorld.getInstance().addEntity(proj);
    }

    public static void shootRight(Entity e ) {
        Entity proj = new Entity();

        PositionComponent playerPosition = e.getComponent(PositionComponent.class);

        PlayerStatistics stats = e.getComponent(PlayerComponent.class).getPlayer().getStats();


        proj.add(new PositionComponent(playerPosition.getX(), playerPosition.getY()));
        proj.add(new VelocityComponent(stats.getProjectileSpeed(), 10));
        proj.add(new CollisionComponent(new CollisionEvent.DefaultProjectileCollisionEvent()));
        proj.add(new CircleCollisionComponent((proj.getComponent(PositionComponent.class).getX()), (proj.getComponent(PositionComponent.class).getY()), 2));
        proj.add(new DebugColorComponent(Color.CORAL));
        proj.add(new ProjectileComponent(stats.getRange()));
        VelocityComponent pv = proj.getComponent(VelocityComponent.class);
        pv.setVelX(pv.getSpeed());
        GameWorld.getInstance().addEntity(proj);
    }


    public enum Direction {

        UP,DOWN,LEFT,RIGHT;

    }
}
