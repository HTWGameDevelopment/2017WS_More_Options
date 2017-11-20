package com.moreoptions.prototype.gameEngine.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.*;
import com.moreoptions.prototype.gameEngine.components.*;
import com.moreoptions.prototype.gameEngine.data.Consts;
import com.moreoptions.prototype.gameEngine.util.CollisionUtil;
import com.moreoptions.prototype.gameEngine.util.EntityTools;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import jdk.nashorn.internal.ir.Block;

import javax.swing.text.Position;
import java.util.List;

/**
 * The job of this movement-system is to:
 * -Move Entities
 * -Resolve Collisions with tiles
 * -Resolves Collision with nonmoving entities.
 *
 * Maybe implement a spacial map to not iterate x²
 *
 */
public class MovementSystem extends EntitySystem {

    private ComponentMapper<CollisionComponent> colMapper   = ComponentMapper.getFor(CollisionComponent.class);
    private ComponentMapper<PositionComponent>  posMapper   = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<VelocityComponent>  velMapper   = ComponentMapper.getFor(VelocityComponent.class);
    private ComponentMapper<CircleCollisionComponent> cMapper = ComponentMapper.getFor(CircleCollisionComponent.class);
    private ComponentMapper<SquareCollisionComponent> sqMapper = ComponentMapper.getFor(SquareCollisionComponent.class);

    private Family posColl = Family.all(PositionComponent.class, CollisionComponent.class, VelocityComponent.class, CircleCollisionComponent.class).exclude(TileComponent.class).get();
    private Family blockedTilesFamily = Family.all(BlockedTileComponent.class).get();
    private ImmutableArray<Entity> entities;

    @Override
    public void update(float deltaTime) {
        moveAllEntities(deltaTime);
    }

    /**
     * Moves entities per axis and checks collision. If collision occurs, moves the entity outside
     * of the collision zone by resolving the overlap.
     *
     * @param deltaTime The deltaTime between frames
     */
    private void moveAllEntities(float deltaTime) {
        entities = getEngine().getEntitiesFor(posColl);

        for(Entity e : entities) {
            VelocityComponent vel = velMapper.get(e);
            PositionComponent pos = posMapper.get(e);
            CollisionComponent col= colMapper.get(e);


            //Record previous position for later collision resolving
            col.setOldX(pos.getX());
            col.setOldY(pos.getY());

            pos.setX(pos.getX() + vel.getVelX() * deltaTime);
            if(sqMapper.has(e)) sqMapper.get(e).getHitbox().setPosition(pos.getX(),pos.getY());
            if(cMapper.has(e)) cMapper.get(e).getHitbox().setPosition(pos.getX(),pos.getY());
            resolveXCollision(e,col.getOldX(), col.getOldY());


            pos.setY(pos.getY() + vel.getVelY() * deltaTime);

            if(sqMapper.has(e)) sqMapper.get(e).getHitbox().setPosition(pos.getX(),pos.getY());
            if(cMapper.has(e)) cMapper.get(e).getHitbox().setPosition(pos.getX(),pos.getY());
            resolveYCollision(e,col.getOldX(), col.getOldY());

            //Add smooth edge movement
        }
    }

    private void resolveXCollision(Entity e, float x,float y) {
            for(Entity t : getEngine().getEntitiesFor(blockedTilesFamily)) {
                try {
                    float r = CollisionUtil.getXOverlap(EntityTools.getCircleHitbox(e), EntityTools.getSquareHitbox(t),x,y);
                    updateEntityXPosition(e,r);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
    }

    private void resolveYCollision(Entity e, float x,float y) {
        for(Entity t : getEngine().getEntitiesFor(blockedTilesFamily)) {
            try {
                float r = CollisionUtil.getYOverlap(EntityTools.getCircleHitbox(e), EntityTools.getSquareHitbox(t),x,y);
                updateEntityYPosition(e, r);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    private void updateEntityXPosition(Entity e, float r) {
        PositionComponent p = posMapper.get(e);
        CircleCollisionComponent c = cMapper.get(e);

        p.setX(p.getX() + r);
        c.getHitbox().x += r;
    }

    private void updateEntityYPosition(Entity e, float r) {
        PositionComponent p = posMapper.get(e);
        CircleCollisionComponent c = cMapper.get(e);

        p.setY(p.getY() + r);
        c.getHitbox().y += r;
    }


}
