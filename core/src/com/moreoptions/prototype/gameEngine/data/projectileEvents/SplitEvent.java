package com.moreoptions.prototype.gameEngine.data.projectileEvents;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.moreoptions.prototype.gameEngine.GameWorld;
import com.moreoptions.prototype.gameEngine.components.*;
import com.moreoptions.prototype.gameEngine.data.callback.CollisionEvent;

/**
 * Created by User on 11/20/2017.
 */
public class SplitEvent implements CollisionEvent {
    @Override
    public boolean onCollision(Entity us, Entity them) {
        Entity test2 = new Entity();

        CollisionComponent cuc = us.getComponent(CollisionComponent.class);


        PositionComponent usp = us.getComponent(PositionComponent.class);


        test2.add(new PositionComponent(cuc.getOldX(),cuc.getOldY()));
        VelocityComponent vcus = us.getComponent(VelocityComponent.class);
        VelocityComponent vc = new VelocityComponent(300,10);
        vc.setVelY(- vcus.getVelY() );
        vc.setVelX(5);


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


        usp = us.getComponent(PositionComponent.class);


        test3.add(new PositionComponent(cuc.getOldX(),cuc.getOldY()));
        vcus = us.getComponent(VelocityComponent.class);
        vc = new VelocityComponent(300,10);
        vc.setVelY(- vcus.getVelY() );
        vc.setVelX( (vcus.getVelY())/2);


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
}
