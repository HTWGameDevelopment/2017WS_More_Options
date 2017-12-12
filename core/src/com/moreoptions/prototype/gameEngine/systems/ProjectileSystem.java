package com.moreoptions.prototype.gameEngine.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.moreoptions.prototype.gameEngine.GameWorld;
import com.moreoptions.prototype.gameEngine.components.*;
import com.moreoptions.prototype.gameEngine.data.InputState;
import com.moreoptions.prototype.gameEngine.data.Player;
import com.moreoptions.prototype.gameEngine.data.Statistics;
import com.moreoptions.prototype.gameEngine.data.callback.CollisionEvent;
import com.moreoptions.prototype.gameEngine.data.projectileEvents.SplitEvent;
import com.moreoptions.prototype.gameEngine.util.Event;
import com.moreoptions.prototype.gameEngine.util.EventListener;
import com.moreoptions.prototype.gameEngine.util.EventSubscriber;
import com.moreoptions.prototype.gameEngine.util.ProjectileFactory;

/**
 * Created by User on 11/20/2017.
 */
public class ProjectileSystem extends EntitySystem {

    private Family projFamily = Family.all(ProjectileComponent.class).get();
    private Family enemyFamily = Family.all(EnemyHitboxComponent.class).get();

    private EventSubscriber subscriber;

    private ComponentMapper<StatsComponent> scMapper = ComponentMapper.getFor(StatsComponent.class);
    private ComponentMapper<EnemyHitboxComponent> ehcMapper = ComponentMapper.getFor(EnemyHitboxComponent.class);
    private ComponentMapper<PositionComponent> posMapper = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<CollisionComponent> ccMapper = ComponentMapper.getFor(CollisionComponent.class);
    private ComponentMapper<ProjectileComponent> pcMapper = ComponentMapper.getFor(ProjectileComponent.class);
    private ComponentMapper<CircleCollisionComponent> cccMapper = ComponentMapper.getFor(CircleCollisionComponent.class);


    public ProjectileSystem() {

        initListeners();


    }

    private void initListeners() {

        subscriber = new EventSubscriber();
        subscriber.subscribe("shoot", new EventListener() {
            @Override
            public void trigger(Event e) {

                Entity entity = e.getData("entity", Entity.class);
                Vector2 direction = e.getData("direction", Vector2.class);

                float ccd = entity.getComponent(StatsComponent.class).getStats().getCurrentShotCooldown();
                float uppercd = entity.getComponent(StatsComponent.class).getStats().getFireRate();
                if(ccd >= uppercd) {
                    Entity projectile = ProjectileFactory.createProjectile(entity, direction);
                    getEngine().addEntity(projectile);
                    scMapper.get(entity).getStats().setCurrentShotCooldown(0);
                }
            }
        });
    }

    @Override
    public void update(float deltatime){
        ImmutableArray<Entity> entities = getEngine().getEntitiesFor(projFamily);
        ImmutableArray<Entity> enemies = getEngine().getEntitiesFor(enemyFamily);

        for (Entity e : entities){
            ProjectileComponent p = pcMapper.get(e);
            CollisionComponent c = ccMapper.get(e);
            PositionComponent pc = posMapper.get(e);
            CircleCollisionComponent projectileCircleCollisionComponent = cccMapper.get(e);

            float oldX = c.getOldX();
            float oldY = c.getOldY();
            float currentX = pc.getX();
            float currentY = pc.getY();

            p.setDistanceTravelled((float) ( p.getDistanceTravelled() + (Math.hypot(oldX-currentX, oldY - currentY))));

            if(p.getDistanceTravelled() > p.getRange()) {
                getEngine().removeEntity(e);
            }

            for(Entity hit : enemies) {
                EnemyHitboxComponent ehc = ehcMapper.get(hit);
                PositionComponent hitpc = posMapper.get(hit);

                ehc.getCircle().setPosition(hitpc.getX(),hitpc.getY());

                if(ehc.getCircle().overlaps(projectileCircleCollisionComponent.getHitbox())) {
                    if(p.getHitEvent().onHit(e,hit)) {
                        getEngine().removeEntity(e);
                    }
                    break;
                }
            }
        }
    }


    public enum Direction {
        UP,DOWN,LEFT,RIGHT;
    }
}
