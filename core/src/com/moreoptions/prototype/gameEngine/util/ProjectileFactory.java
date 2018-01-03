package com.moreoptions.prototype.gameEngine.util;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.moreoptions.prototype.gameEngine.components.*;
import com.moreoptions.prototype.gameEngine.data.Statistics;
import com.moreoptions.prototype.gameEngine.data.callback.CollisionEvent;

public class ProjectileFactory {

    private static ComponentMapper<PlayerComponent> pcMapper = ComponentMapper.getFor(PlayerComponent.class);
    private static ComponentMapper<PositionComponent> poMapper = ComponentMapper.getFor(PositionComponent.class);
    private static ComponentMapper<VelocityComponent> velMapper = ComponentMapper.getFor(VelocityComponent.class);

    public static Entity createProjectile (Entity entity, Vector2 v2){
        if(pcMapper.has(entity)){
            return playerProjectile(entity,v2);
        } else{
            return enemyProjectile(entity,v2);
        }
    }

    public static Entity enemyProjectile (Entity entity, Vector2 v2){
        Statistics eStats = entity.getComponent(StatsComponent.class).getStats();
        Entity proj = new Entity();
        VelocityComponent vc = entity.getComponent(VelocityComponent.class);
        PositionComponent pc = poMapper.get(entity);

        proj.add(new PositionComponent(new Vector2(pc.getPosition())));
        proj.add(new VelocityComponent(vc.getSpeed(),vc.getDeceleration()));
        proj.add(new CollisionComponent(new CollisionEvent.DefaultProjectileCollisionEvent()));
        proj.add(new CircleCollisionComponent((proj.getComponent(PositionComponent.class).getX()), (proj.getComponent(PositionComponent.class).getY()), 2));
        proj.add(new DebugColorComponent(Color.SKY));
        proj.add(new ProjectileComponent(eStats.getDamage(), eStats.getRange(), true));
        VelocityComponent projVelocity = velMapper.get(proj);

        projVelocity.setVelX(v2.x*(eStats.getProjectileSpeed()));
        projVelocity.setVelY(v2.y*(eStats.getProjectileSpeed()));

        return proj;
    }

    public static Entity playerProjectile (Entity entity, Vector2 v2){
        Entity proj = new Entity();

        Statistics pStats = entity.getComponent(StatsComponent.class).getStats();
        VelocityComponent vc = entity.getComponent(VelocityComponent.class);
        PositionComponent pc = entity.getComponent(PositionComponent.class);


        VelocityComponent pvc = velMapper.get(entity);

        proj.add(new PositionComponent(new Vector2(pc.getPosition())));
        proj.add(new VelocityComponent(vc.getSpeed(),vc.getDeceleration()));
        proj.add(new CollisionComponent(new CollisionEvent.DefaultProjectileCollisionEvent()));
        proj.add(new CircleCollisionComponent((proj.getComponent(PositionComponent.class).getX()), (proj.getComponent(PositionComponent.class).getY()), 2));
        proj.add(new DebugColorComponent(Color.SKY));
        proj.add(new ProjectileComponent(pStats.getDamage(),pStats.getRange(), false));

        proj.getComponent(VelocityComponent.class).setVelX(v2.x*(pStats.getProjectileSpeed()));
        proj.getComponent(VelocityComponent.class).setVelY(v2.y*(pStats.getProjectileSpeed()));

        Vector2 playerVelocity = pvc.getVelocity();
        VelocityComponent projVelocity = velMapper.get(proj);


        if((playerVelocity.x > 0 && v2.x >= 0) || (playerVelocity.x < 0 && v2.x <= 0) ) {
            projVelocity.setVelX(playerVelocity.x + projVelocity.getVelX());
            System.out.println("X ACCELERATION");
        } else {
            System.out.println("NOT X");
        }

        if((playerVelocity.y > 0 && v2.y >= 0) || (playerVelocity.y < 0 && v2.y <= 0) ) {
            projVelocity.setVelY(playerVelocity.y + projVelocity.getVelY());
            System.out.println("Y ACCELERATION");
        }



        return proj;
    }
}
