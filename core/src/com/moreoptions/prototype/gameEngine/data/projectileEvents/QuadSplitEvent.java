package com.moreoptions.prototype.gameEngine.data.projectileEvents;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.moreoptions.prototype.gameEngine.GameWorld;
import com.moreoptions.prototype.gameEngine.components.*;
import com.moreoptions.prototype.gameEngine.data.callback.CollisionEvent;

/**
 * Created by Andreas on 02.01.2018.
 */
public class QuadSplitEvent implements CollisionEvent {
    @Override
    public boolean onCollision(Entity us, Entity them) {
        Entity northBullet = addNorthBullet(us, them);

        GameWorld.getInstance().addEntity(northBullet);

        Entity westBullet = new Entity();

        Entity southBullet = new Entity();

        Entity eastBullet = new Entity();



        Entity test2 = new Entity();

        CollisionComponent cuc = us.getComponent(CollisionComponent.class);
        PositionComponent usp = us.getComponent(PositionComponent.class);
        test2.add(new PositionComponent(cuc.getOldX(),cuc.getOldY()));
        VelocityComponent vcus = us.getComponent(VelocityComponent.class);
        VelocityComponent vc = new VelocityComponent(300,10);
        vc.setVelY(- vcus.getVelY() );
        vc.setVelX(- vcus.getVelX());

        test2.add(vc);
        test2.add(new CollisionComponent(new CollisionEvent() {
            @Override
            public boolean onCollision(Entity us, Entity them) {
                GameWorld.getInstance().removeEntity(us);
                return false;
            }
        }));
        test2.add(new CircleCollisionComponent((test2.getComponent(PositionComponent.class).getX()),(test2.getComponent(PositionComponent.class).getY()),2));
        test2.add(new DebugColorComponent(Color.CORAL));
        GameWorld.getInstance().addEntity(test2);

        Entity test3 = new Entity();
        cuc = us.getComponent(CollisionComponent.class);
        test3.add(new PositionComponent(cuc.getOldX(),cuc.getOldY()));
        vcus = us.getComponent(VelocityComponent.class);
        vc = new VelocityComponent(300,10);
        vc.setVelY(- vcus.getVelY() );
        vc.setVelX(- vcus.getVelX());
        test3.add(vc);
        test3.add(new CollisionComponent(new CollisionEvent() {
            @Override
            public boolean onCollision(Entity us, Entity them) {
                GameWorld.getInstance().removeEntity(us);
                return false;
            }
        }));
        test3.add(new CircleCollisionComponent((test3.getComponent(PositionComponent.class).getX()),(test3.getComponent(PositionComponent.class).getY()),2));
        test3.add(new DebugColorComponent(Color.CORAL));
        GameWorld.getInstance().addEntity(test3);
        GameWorld.getInstance().removeEntity(us);
        return false;
    }

    private Entity addNorthBullet(Entity us, Entity them) {
        Entity northBullet = new Entity();

        CollisionComponent cc = us.getComponent(CollisionComponent.class);
        PositionComponent pc = us.getComponent(PositionComponent.class);
        northBullet.add(new PositionComponent(cc.getOldX(), cc.getOldY()));

        VelocityComponent vcus = us.getComponent(VelocityComponent.class);
        VelocityComponent vc = new VelocityComponent(300, 10);
        vc.setVelY(-vcus.getVelY());
        vc.setVelX(-vcus.getVelX());

        northBullet.add(vc);
        northBullet.add(new CollisionComponent(new CollisionEvent() {
            @Override
            public boolean onCollision(Entity us, Entity them) {
                GameWorld.getInstance().removeEntity(us);
                return false;
            }
        }));
        northBullet.add(new CircleCollisionComponent((northBullet.getComponent(PositionComponent.class).getX()),
                (northBullet.getComponent(PositionComponent.class).getY()), 2));

        northBullet.add(new DebugColorComponent(Color.CORAL));
        return northBullet;
    }
}
