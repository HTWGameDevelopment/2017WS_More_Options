package com.moreoptions.prototype.gameEngine.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.moreoptions.prototype.gameEngine.components.*;
import com.moreoptions.prototype.gameEngine.util.CollisionUtil;
import com.moreoptions.prototype.gameEngine.util.EntityTools;


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
    private ComponentMapper<BlockedTileComponent> btcMapper = ComponentMapper.getFor(BlockedTileComponent.class);
    private ComponentMapper<PositionComponent>  posMapper   = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<VelocityComponent>  velMapper   = ComponentMapper.getFor(VelocityComponent.class);
    private ComponentMapper<CircleCollisionComponent> cMapper = ComponentMapper.getFor(CircleCollisionComponent.class);
    private ComponentMapper<SquareCollisionComponent> sqMapper = ComponentMapper.getFor(SquareCollisionComponent.class);

    private Family posColl = Family.all(PositionComponent.class, CollisionComponent.class, VelocityComponent.class,
            CircleCollisionComponent.class).exclude(TileComponent.class).get();
    private Family circlColl = Family.one(PlayerComponent.class, EnemyComponent.class).get();

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

            resolveCollision(e);


        }
    }

    private void resolveCollision(Entity e) {

        ImmutableArray<Entity> circles = getEngine().getEntitiesFor(circlColl);
        for(Entity ex : circles) {
            if(ex.equals(e)) continue;
            Circle a = cMapper.get(e).getHitbox();
            Circle b = cMapper.get(ex).getHitbox();

            PositionComponent apos = posMapper.get(e);
            PositionComponent bpos = posMapper.get(ex);

            if(a.overlaps(b)) {
                DebugColorComponent d = e.getComponent(DebugColorComponent.class);
                d.setColor(Color.RED);

                //GET OVERLAP DISTANCE
                //FIRST DISTANCE BETWEEN CENTERS:

                float dst = apos.getPosition().cpy().dst(bpos.getPosition());
                float overlap = Math.abs(dst-a.radius-b.radius);

                //GET DIRECTIONAL VECTOR BETWEEN OLD POSITION AND ENEMY POSITION

                System.out.println("Overlap:" + overlap);

                Vector2 directional = bpos.getPosition().cpy().sub(apos.getPosition());
                directional = directional.nor();
                directional.scl(overlap*1.9f);

                System.out.println("Correcting by:" + directional.len());
                System.out.println("Precorrection:" + apos.getPosition());

                apos.getPosition().sub(directional);
                a.setPosition(apos.getX(), apos.getY());

                System.out.println(a.overlaps(b));



            } else {
                DebugColorComponent d = e.getComponent(DebugColorComponent.class);
                d.setColor(Color.YELLOW);
            }

        }


    }

    private void resolveXCollision(Entity e, float x,float y) {
            for(Entity t : getEngine().getEntitiesFor(blockedTilesFamily)) {

                BlockedTileComponent blockedTileComponent = btcMapper.get(t);
                if(!blockedTileComponent.isBlocked()) continue;

                try {
                    float r = CollisionUtil.getXOverlap(EntityTools.getCircleHitbox(e), EntityTools.getSquareHitbox(t),x,y);

                    if(r != 0) {
                        CollisionComponent cc = colMapper.get(e);
                        cc.getOnCollision().onCollision(e,t);
                    }
                    updateEntityXPosition(e,r);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
    }

    private void resolveYCollision(Entity e, float x,float y) {
        for(Entity t : getEngine().getEntitiesFor(blockedTilesFamily)) {

            BlockedTileComponent blockedTileComponent = btcMapper.get(t);
            if(!blockedTileComponent.isBlocked()) continue;

            try {
                float r = CollisionUtil.getYOverlap(EntityTools.getCircleHitbox(e), EntityTools.getSquareHitbox(t),x,y);
                if(r != 0) {
                    colMapper.get(e).getOnCollision().onCollision(e,t);
                }
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
