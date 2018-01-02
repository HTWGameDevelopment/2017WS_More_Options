package com.moreoptions.prototype.gameEngine.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.moreoptions.prototype.gameEngine.components.*;
import com.moreoptions.prototype.gameEngine.util.CollisionUtil;
import com.moreoptions.prototype.gameEngine.util.EntityTools;
import javafx.util.Pair;

import java.util.ArrayList;


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
    private ComponentMapper<DisplacableComponent> dcMapper = ComponentMapper.getFor(DisplacableComponent.class);
    private ComponentMapper<StatsComponent> statsMapper = ComponentMapper.getFor(StatsComponent.class);

    private Family posColl = Family.all(PositionComponent.class, CollisionComponent.class, VelocityComponent.class,
            CircleCollisionComponent.class).exclude(TileComponent.class).get();

    private Family playColl = Family.one(PlayerComponent.class).get();
    private Family enemyColl = Family.one(EnemyComponent.class).get();

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
            if(dcMapper.has(e)) pos.setX(pos.getX() + dcMapper.get(e).getDir().x);
            if(sqMapper.has(e)) sqMapper.get(e).getHitbox().setPosition(pos.getX(),pos.getY());
            if(cMapper.has(e)) cMapper.get(e).getHitbox().setPosition(pos.getX(),pos.getY());
            boolean xCollision = resolveXCollision(e,col.getOldX(), col.getOldY());

            pos.setY(pos.getY() + vel.getVelY() * deltaTime);
            if(dcMapper.has(e)) {
                pos.setY(pos.getY() + dcMapper.get(e).getDir().y);

            }
            if(sqMapper.has(e)) sqMapper.get(e).getHitbox().setPosition(pos.getX(),pos.getY());
            if(cMapper.has(e)) cMapper.get(e).getHitbox().setPosition(pos.getX(),pos.getY());
            boolean yCollsion = resolveYCollision(e,col.getOldX(), col.getOldY());

            //SMOOTH EDGE MOVEMENT
/*

            if(vel.getVelX() > 0 && xCollision) {

                if(!checkSensor(Sensor.TOP_RIGHT, e)) {

                    vel.setVelY(vel.getVelY() + 3);

                } else if(!checkSensor(Sensor.BOT_RIGHT, e)){

                    vel.setVelY(vel.getVelY() - 3);
                }

                resolveYCollision(e,col.getOldX(), col.getOldY());

            } else if(vel.getVelX() < 0 && xCollision) {
                if(!checkSensor(Sensor.TOP_LEFT, e)) {
                    vel.setVelY(vel.getVelY() + 3);

                } else if(!checkSensor(Sensor.BOT_LEFT, e)){

                    vel.setVelY(vel.getVelY() - 3);
                }

                resolveYCollision(e,col.getOldX(), col.getOldY());
            }




            if(vel.getVelY() > 0 && yCollsion) {

                if(!checkSensor(Sensor.TOP_LEFT, e)) {

                    vel.setVelX(vel.getVelX() - 3);

                } else if(!checkSensor(Sensor.TOP_RIGHT, e)){
                    vel.setVelX(vel.getVelX() + 3);
                }

            } else if(vel.getVelY() < 0 && yCollsion) {
                if(!checkSensor(Sensor.BOT_LEFT, e)) {
                    vel.setVelX(vel.getVelX() - 3);
                } else if(!checkSensor(Sensor.BOT_RIGHT, e)){
                    vel.setVelX(vel.getVelX() + 3);
                }
            }

            resolveXCollision(e,col.getOldX(), col.getOldY());
*/


            if(dcMapper.has(e)) {
                dcMapper.get(e).getDir().scl(0.9f);
            }
        }

        ArrayList<Pair<Entity,Entity>> collisionList = getCollisions();

        for(Pair<Entity, Entity> collision : collisionList) {

            Entity player = collision.getKey();
            Entity enemy = collision.getValue();

            Circle a = cMapper.get(player).getHitbox();
            Circle b = cMapper.get(enemy).getHitbox();

            PositionComponent apos = posMapper.get(player);
            PositionComponent bpos = posMapper.get(enemy);

            if(statsMapper.has(player)){
                StatsComponent stats = statsMapper.get(player);
                applyKnockback(player, stats.getStats().getPushability(), enemy);
                applyKnockback(enemy, stats.getStats().getPushability(), player);
            } else {

                applyKnockback(player, 0.5f, enemy);
                applyKnockback(enemy, 0.5f, player);
            }

            CollisionComponent cc = colMapper.get(player);
            CollisionComponent cd = colMapper.get(enemy);

        }
    }

    private boolean checkSensor(Sensor sensor, Entity e) {

        float posX = 0;
        float posY = 0;

        PositionComponent p = posMapper.get(e);
        CircleCollisionComponent cp = cMapper.get(e);

        switch (sensor) {
            case TOP_RIGHT:
                posX = p.getX() + cp.getHitbox().radius;
                posY = p.getY() + cp.getHitbox().radius;
                break;
            case BOT_LEFT:
                posX = p.getX() - cp.getHitbox().radius;
                posY = p.getY() - cp.getHitbox().radius;
                break;
            case BOT_RIGHT:
                posX = p.getX() + cp.getHitbox().radius;
                posY = p.getY() - cp.getHitbox().radius;
                break;
            case TOP_LEFT:
                posX = p.getX() - cp.getHitbox().radius;
                posY = p.getY() + cp.getHitbox().radius;
                break;
        }

        ImmutableArray<Entity> entities = getEngine().getEntitiesFor(blockedTilesFamily);
        for(Entity ex : entities) {
            SquareCollisionComponent sqcc = sqMapper.get(ex);
            if(sqcc.getHitbox().contains(posX,posY)) {
                return true;
            }
        }
        return false;

    }

    private void applyKnockback(Entity player, float f, Entity enemy) {

        DisplacableComponent dc = dcMapper.get(player);

        if(!dc.isImmune()) {
            Vector2 norm = getDirectionVector(enemy, player);
            dc.applyForce(norm, f);
        }
    }

    private ArrayList<Pair<Entity,Entity>> getCollisions() {

        ImmutableArray<Entity> players = getEngine().getEntitiesFor(playColl);
        ImmutableArray<Entity> enemies = getEngine().getEntitiesFor(enemyColl);

        ArrayList<Pair<Entity,Entity>> collisionList = new ArrayList<Pair<Entity, Entity>>();

        for(Entity player : players) {
            for(Entity enemy : enemies) {

                Circle a = cMapper.get(player).getHitbox();
                Circle b = cMapper.get(enemy).getHitbox();

                if(a.overlaps(b)) {
                    collisionList.add(new Pair<Entity, Entity>(player, enemy));
                }
            }
        }

        for(int i = 0; i < enemies.size(); i++) {
            for(int k = 0; k < enemies.size(); k++) {
                if(i==k) continue;

                Entity enemy1 = enemies.get(i);
                Entity enemy2 = enemies.get(k);

                Circle a = cMapper.get(enemy1).getHitbox();
                Circle b = cMapper.get(enemy2).getHitbox();

                if(a.overlaps(b)) {
                    collisionList.add(new Pair<Entity, Entity>(enemy1, enemy2));
                }
            }
        }

        return collisionList;
        /*
        for(Entity ex : circles) {
            if(ex.equals(e)) continue;
            Circle a = cMapper.get(e).getHitbox();
            Circle b = cMapper.get(ex).getHitbox();

            PositionComponent apos = posMapper.get(e);
            PositionComponent bpos = posMapper.get(ex);

            CollisionComponent cc = colMapper.get(e);
            CollisionComponent cd = colMapper.get(ex);


            if(a.overlaps(b)) {

                a.setX(cc.getOldX());
                b.setX(cd.getOldX());

                if(a.overlaps(b)) {

                    a.setY(cc.getOldY());
                    b.setY(cd.getOldY());
                }

                //X
                apos.getPosition().x = cc.getOldX();

                apos.getPosition().set(cc.getOldX(),cc.getOldY());


                bpos.getPosition().set(cd.getOldX(),cd.getOldY());

                a.setPosition(apos.getX(),apos.getY());
                b.setPosition(bpos.getX(),bpos.getY());

                if(a.overlaps(b)) {
                    System.out.println("NEIN");
                }

                //applyForce(e,ex);



                *//*

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
                directional.scl(overlap*1.0000001f);
                System.out.println("Correcting by:" + directional.len());
                System.out.println("Precorrection:" + apos.getPosition());

                apos.getPosition().sub(directional);
                a.setPosition(apos.getX(), apos.getY());

                System.out.println(a.overlaps(b));*//*



            } else {
                DebugColorComponent d = e.getComponent(DebugColorComponent.class);
                d.setColor(Color.YELLOW);
            }*/



    }

    private boolean resolveXCollision(Entity e, float x,float y) {
        boolean corrected = false;
        for (Entity t : getEngine().getEntitiesFor(blockedTilesFamily)) {

            BlockedTileComponent blockedTileComponent = btcMapper.get(t);
            if (!blockedTileComponent.isBlocked()) continue;

            try {
                float r = CollisionUtil.getXOverlap(EntityTools.getCircleHitbox(e), EntityTools.getSquareHitbox(t), x, y);

                if (r != 0) {
                    CollisionComponent cc = colMapper.get(e);
                    cc.getOnCollision().onCollision(e, t);
                    corrected = true;
                }
                updateEntityXPosition(e, r);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return corrected;
    }

    private boolean resolveYCollision(Entity e, float x,float y) {
        boolean corrected = false;
        for(Entity t : getEngine().getEntitiesFor(blockedTilesFamily)) {

            BlockedTileComponent blockedTileComponent = btcMapper.get(t);
            if(!blockedTileComponent.isBlocked()) continue;

            try {
                float r = CollisionUtil.getYOverlap(EntityTools.getCircleHitbox(e), EntityTools.getSquareHitbox(t),x,y);
                if(r != 0) {
                    colMapper.get(e).getOnCollision().onCollision(e,t);
                    corrected = true;
                }
                updateEntityYPosition(e, r);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return corrected;
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


    public Vector2 getDirectionVector(Entity e, Entity e2) {
        PositionComponent pe1 = posMapper.get(e);
        PositionComponent pe2 = posMapper.get(e2);

        return pe2.getPosition().cpy().sub(pe1.getPosition()).nor();

    }
}
