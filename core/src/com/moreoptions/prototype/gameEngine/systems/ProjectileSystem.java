package com.moreoptions.prototype.gameEngine.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import com.moreoptions.prototype.gameEngine.components.*;
import com.moreoptions.prototype.gameEngine.data.Consts;
import com.moreoptions.prototype.gameEngine.data.GameState;
import com.moreoptions.prototype.gameEngine.data.Statistics;
import com.moreoptions.prototype.gameEngine.util.EventFactory;
import com.moreoptions.prototype.gameEngine.util.eventBus.Event;
import com.moreoptions.prototype.gameEngine.util.eventBus.EventListener;
import com.moreoptions.prototype.gameEngine.util.eventBus.EventSubscriber;
import com.moreoptions.prototype.gameEngine.util.ProjectileFactory;

/**
 * Created by User on 11/20/2017.
 */
public class ProjectileSystem extends EntitySystem {

    private Family projFamily = Family.all(ProjectileComponent.class).get();
    private Family enemyFamily = Family.all(EnemyHitboxComponent.class).get();
    private Family playerFamily = Family.all(PlayerComponent.class).get();

    private EventSubscriber subscriber;
    private EventSubscriber subscriber2;
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
        subscriber.subscribe(Consts.SHOOT_EVENT, new EventListener() {
            @Override
            public boolean trigger(Event e) {

                Entity entity = e.getData(Consts.ENTITY, Entity.class);
                Vector2 direction = e.getData(Consts.DIRECTION, Vector2.class);
                Entity projectile = ProjectileFactory.createProjectile(entity, direction);
                getEngine().addEntity(projectile);
                return true;

            }
        });
        subscriber2 = new EventSubscriber();
        subscriber2.subscribe(Consts.DAMAGE_EVENT_PROJECTILE, new EventListener() {
            @Override
            public boolean trigger(Event e) {
                Entity projectile = e.getData(Consts.PROJECTILE, Entity.class);
                Entity hit = e.getData(Consts.HIT, Entity.class);

                ProjectileComponent pc = pcMapper.get(projectile);
                Statistics statistics = scMapper.get(hit).getStats();
                if(statistics.getImmunityTimer() < statistics.getTimeSinceLastHit()) {
                    statistics.setCurrentHealth(statistics.getCurrentHealth() - pc.getDmg());
                    System.out.println("DamageEvent");

                    EventFactory.createDamageText(hit, pc.getDmg());

                    statistics.setTimeSinceLastHit(0);
                }

                return true;
            }
        });
    }

    @Override
    public void update(float deltatime){
        ImmutableArray<Entity> entities = getEngine().getEntitiesFor(projFamily);
        ImmutableArray<Entity> enemies = getEngine().getEntitiesFor(enemyFamily);
        ImmutableArray<Entity>  players = getEngine().getEntitiesFor(playerFamily);

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

            if(p.isEnemy()) {
                for (Entity hit : players) {
                    CircleCollisionComponent ccc = cccMapper.get(hit);
                    PositionComponent  hitpc = posMapper.get(hit);

                    if (ccc.getHitbox().overlaps(projectileCircleCollisionComponent.getHitbox())) {
                        if (p.getHitEvent().onHit(e, hit)) {
                            getEngine().removeEntity(e);
                        }
                    }
                }

            } else {
                for (Entity hit : enemies) {
                    EnemyHitboxComponent ehc = ehcMapper.get(hit);
                    PositionComponent hitpc = posMapper.get(hit);

                    ehc.getCircle().setPosition(hitpc.getX(), hitpc.getY());

                    if (ehc.getCircle().overlaps(projectileCircleCollisionComponent.getHitbox())) {
                        if (p.getHitEvent().onHit(e, hit)) {
                            getEngine().removeEntity(e);
                        }
                        break;
                    }
                }
            }
        }
    }
}
