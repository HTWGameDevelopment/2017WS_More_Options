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
 * -Resolve Collisions with immovable entities, entities that _do_not_ move. makes us unable to pass bounds, or run over rocks.
 *
 * Maybe implement a spacial map to not iterate x²
 *
 */
public class MovementSystem extends EntitySystem {

    ComponentMapper<CollisionComponent> colMapper   = ComponentMapper.getFor(CollisionComponent.class);
    ComponentMapper<PositionComponent>  posMapper   = ComponentMapper.getFor(PositionComponent.class);
    ComponentMapper<VelocityComponent>  velMapper   = ComponentMapper.getFor(VelocityComponent.class);
    ComponentMapper<CircleCollisionComponent> cMapper = ComponentMapper.getFor(CircleCollisionComponent.class);

    Family posColl = Family.all(PositionComponent.class, CollisionComponent.class, VelocityComponent.class, CircleCollisionComponent.class).exclude(TileComponent.class).get();
    Family blockedTilesFamily = Family.all(BlockedTileComponent.class).get();
    ImmutableArray<Entity> entities;

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
            resolveXCollision(e,col.getOldX(), col.getOldY());


            pos.setY(pos.getY() + vel.getVelY() * deltaTime);
            resolveYCollision(e,col.getOldX(), col.getOldY());

            //Add smooth edge movement

        }
    }

    private void resolveXCollision(Entity e, float x,float y) {
            for(Entity t : getEngine().getEntitiesFor(blockedTilesFamily)) {
                try {
                    float r = CollisionUtil.getXOverlap(EntityTools.getEntityHitbox(e), EntityTools.getTileHitbox(t),x,y);
                    updateEntityXPosition(e,r);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
    }

    private void resolveYCollision(Entity e, float x,float y) {
        for(Entity t : getEngine().getEntitiesFor(blockedTilesFamily)) {
            try {
                float r = CollisionUtil.getYOverlap(EntityTools.getEntityHitbox(e), EntityTools.getTileHitbox(t),x,y);
                updateEntityYPosition(e, r);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    public void updateEntityXPosition(Entity e, float r) {
        PositionComponent p = posMapper.get(e);
        CircleCollisionComponent c = cMapper.get(e);

        p.setX(p.getX() + r);
        c.getHitbox().x += r;
    }

    public void updateEntityYPosition(Entity e, float r) {
        PositionComponent p = posMapper.get(e);
        CircleCollisionComponent c = cMapper.get(e);

        p.setY(p.getY() + r);
        c.getHitbox().y += r;
    }


}
