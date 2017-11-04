package com.moreoptions.prototype.gameEngine.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.*;
import com.moreoptions.prototype.gameEngine.components.*;
import com.moreoptions.prototype.gameEngine.data.Consts;
import com.moreoptions.prototype.gameEngine.util.CollisionUtil;
import com.moreoptions.prototype.gameEngine.util.EntityTools;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;

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

    Family posColl = Family.all(PositionComponent.class, CollisionComponent.class, VelocityComponent.class).exclude(TileComponent.class).get();
    Family tilesFamily = Family.all(TileComponent.class).get();
    ImmutableArray<Entity> entities;
    private float TILE_SIZE = Consts.TILE_SIZE;

    @Override
    public void update(float deltaTime) {
        moveAllEntities(deltaTime);
    }

    /**
     * Fixes collision between an Entity and Entity with a Tile Component on the Y-Axis by moving the entity away. The distance the entity is moved is decided in 6 ways
     *
     * If Entity center is between bounds of Tile it is moved away the entity radius from the bounds
     *
     * If Entity center is outside bounds of Tile it is moved away by the distance to the furthest away point of intersection of the nearest bound
     * @param e
     */
    /*private void fixYCollision(Entity e, Entity tile) {

        Rectangle r = EntityTools.getTileHitbox(tile);
        Circle c = EntityTools.getEntityHitbox(e);

        PositionComponent entityPosition = e.getComponent(PositionComponent.class);

        if (Intersector.overlaps(c, r)) {                                                                               //Check if overlap exists. If it does, fix.
            boolean top = (c.y > r.y) ? true : false;                                                                   //Are we approaching from TOP or BOT?
            if (top) {
                if (c.x > r.x && c.x < r.x + TILE_SIZE) {
                    float overlap = r.y + TILE_SIZE - c.y + c.radius;
                    entityPosition.setY(entityPosition.getY() + overlap);
                } else {
                    //Get Point of Collision
                    boolean right = (c.x > r.x) ? true : false;
                    if (right) {
                        //COming from top right
                        //get right boundary
                        Vector2 a = new Vector2(r.x + TILE_SIZE, r.y + TILE_SIZE);
                        Vector2 b = new Vector2(r.x + TILE_SIZE, r.y);
                        Vector2 d = new Vector2(c.x, c.y);

                        float radius = c.radius;

                        //Need the one with the lowest Y value
                        List<Vector2> tr = CollisionUtil.getCircleLineIntersectionPoint(a, b, d, radius);

                        Vector2 re = tr.get(0);
                        for (Vector2 v : CollisionUtil.getCircleLineIntersectionPoint(a, b, d, radius)) {
                            if (re.y > v.y) re = v;
                        }

                        entityPosition.setY(c.y + (r.y + 32 - re.y));
                        entityPosition.setX(c.x + 0.5f);

                    } else {
                        Vector2 a = new Vector2(tile.getX(), tile.getY() + 32);
                        Vector2 b = new Vector2(tile.getX(), tile.getY());
                        Vector2 d = new Vector2(entityPos.getX(), entityPos.getY());

                        float radius = c.radius;

                        //Need the one with the lowest Y value
                        List<Vector2> tr = CollisionUtil.getCircleLineIntersectionPoint(a, b, d, radius);

                        Vector2 re = tr.get(0);
                        for (Vector2 v : CollisionUtil.getCircleLineIntersectionPoint(a, b, d, radius)) {
                            if (re.y > v.y) re = v;
                        }

                        entityPos.setY(entityPos.getY() + (tile.getY() + 32 - re.y));
                        entityPos.setX(entityPos.getX() - 0.5f);
                        return true;
                    }

                }

            } else {
                if (c.x > tile.getX() && c.x < tile.getX() + TILE_SIZE) {
                    float overlap = tile.getY() - entityPos.getY() - c.radius;
                    entityPos.setY(entityPos.getY() + overlap);
                    return true;
                } else {
                    //Get Point of Collision
                    boolean right = (c.x > tile.getX()) ? true : false;
                    if (right) {
                        //COming from top right
                        //get right boundary
                        Vector2 a = new Vector2(tile.getX() + 32, tile.getY() + 32);
                        Vector2 b = new Vector2(tile.getX() + 32, tile.getY());
                        Vector2 d = new Vector2(entityPos.getX(), entityPos.getY());

                        float radius = c.radius;

                        //Need the one with the lowest Y value
                        List<Vector2> tr = CollisionUtil.getCircleLineIntersectionPoint(a, b, d, radius);

                        Vector2 re = tr.get(0);
                        for (Vector2 v : CollisionUtil.getCircleLineIntersectionPoint(a, b, d, radius)) {
                            if (re.y < v.y) re = v;
                        }

                        entityPos.setY(entityPos.getY() - (re.y - tile.getY()));
                        entityPos.setX(entityPos.getX() + 0.5f);
                        return true;

                    } else {
                        Vector2 a = new Vector2(tile.getX(), tile.getY() + 32);
                        Vector2 b = new Vector2(tile.getX(), tile.getY());
                        Vector2 d = new Vector2(entityPos.getX(), entityPos.getY());

                        float radius = c.radius;

                        //Need the one with the highest Y value
                        List<Vector2> tr = CollisionUtil.getCircleLineIntersectionPoint(a, b, d, radius);

                        Vector2 re = tr.get(0);
                        for (Vector2 v : CollisionUtil.getCircleLineIntersectionPoint(a, b, d, radius)) {
                            if (re.y < v.y) re = v;
                        }

                        entityPos.setY(entityPos.getY() - (re.y - tile.getY()));
                        entityPos.setX(entityPos.getX() - 0.5f);
                        return true;
                    }
                }
            }
        }


    }*/


    private void fixXCollision(Entity e) {
        ImmutableArray<Entity> tiles = getEngine().getEntitiesFor(tilesFamily);

        for (Entity t : tiles) {
            try {

                Rectangle r = EntityTools.getTileHitbox(t);
                Circle c = EntityTools.getEntityHitbox(e);

                if (Intersector.overlaps(c, r)) { //THEY OVERLAP? WE NEED TO INVESTIGATE THIS FURTHER
                    PositionComponent tile = posMapper.get(t);
                    PositionComponent entityPos = posMapper.get(e);
                    boolean right = (c.x > tile.getX()+16) ? true : false;
                    if (right) {
                        System.out.print("OVERLAPPING FROM RIGHT");
                        if (c.y > tile.getY() && c.y < (tile.getY() + TILE_SIZE)) {
                            float overlap = tile.getX() + TILE_SIZE - entityPos.getX() + c.radius;
                            entityPos.setX(entityPos.getX() + overlap);
                        } else {
                            //Check top, bot, do collisionstuff
                            boolean top = (c.y > tile.getY()) ? true : false;
                            if (top) {
                                System.out.print("TOP\n");
                                Vector2 a = new Vector2(tile.getX() + 32, tile.getY() + 32);
                                Vector2 b = new Vector2(tile.getX(), tile.getY()+32);

                                Vector2 center = new Vector2(entityPos.getX(), entityPos.getY());
                                float radius = c.radius;

                                //Get List of Intersectionvectors

                                List<Vector2> tr = CollisionUtil.getCircleLineIntersectionPoint(a, b, center, radius);

                                createDebugLine(a,b,5);
                                Vector2 re = (tr.isEmpty()) ? null : tr.get(0);

                                for (Vector2 v : CollisionUtil.getCircleLineIntersectionPoint(a, b, center, radius)) {
                                    if (re.x > v.x) re = v;
                                }



                                entityPos.setX(entityPos.getX() + (tile.getX() + 32 - re.x));
                                entityPos.setY(entityPos.getY() + 0.5f);
                            } else {
                                System.out.print("BOT\n");
                                Vector2 a = new Vector2(tile.getX()+32, tile.getY());
                                Vector2 b = new Vector2(tile.getX(), tile.getY());


                                Vector2 center = new Vector2(entityPos.getX(), entityPos.getY());
                                float radius = c.radius;

                                //Get List of Intersectionvectors

                                List<Vector2> tr = CollisionUtil.getCircleLineIntersectionPoint(a, b, center, radius);

                                Vector2 re = (tr.isEmpty()) ? null : tr.get(0);

                                for (Vector2 v : CollisionUtil.getCircleLineIntersectionPoint(a, b, center, radius)) {
                                    if (re.x > v.x) re = v;
                                }
                                System.out.println(re.x-tile.getX()+32 + "|" + re.x + "|" + tile.getX()+32);

                                createDebugLine(a,b,5);

                                System.out.println((re.x-tile.getX()));

                                entityPos.setX(entityPos.getX() - (re.x-tile.getX()-32));
                                entityPos.setY(entityPos.getY() - 0.5f);
                            }
                        }
                    } else {
                        System.out.print("OVERLAPPING FROM LEFT");
                        if (c.y > tile.getY() && c.y < tile.getY() + TILE_SIZE) {
                            float overlap = tile.getX() - entityPos.getX() - c.radius;
                            entityPos.setX(entityPos.getX() + overlap);
                        } else {
                            boolean top = (c.y-c.radius > tile.getY()) ? true : false;
                            if (top) {
                                System.out.print("TOP\n");
                                Vector2 a = new Vector2(tile.getX(), tile.getY() + 32);
                                Vector2 b = new Vector2(tile.getX()+32, tile.getY()+32);

                                Vector2 center = new Vector2(entityPos.getX(), entityPos.getY());
                                float radius = c.radius;

                                //Get List of Intersectionvectors

                                List<Vector2> tr = CollisionUtil.getCircleLineIntersectionPoint(a, b, center, radius);

                                Vector2 re = (tr.isEmpty()) ? null : tr.get(0);
                                for (Vector2 v : CollisionUtil.getCircleLineIntersectionPoint(a, b, center, radius)) {
                                    if (re.x < v.x) re = v;
                                }
                                createDebugLine(a,b,5);

                                System.out.println(re.x -tile.getX());
                                entityPos.setX(entityPos.getX() - (re.x - tile.getX()));
                                entityPos.setY(entityPos.getY() + 0.5f);

                            } else {

                                System.out.print("BOT\n");
                                Vector2 a = new Vector2(tile.getX()+32, tile.getY());
                                Vector2 b = new Vector2(tile.getX(), tile.getY());

                                Vector2 center = new Vector2(entityPos.getX(), entityPos.getY());
                                float radius = c.radius;

                                createDebugLine(a,b,5);

                                //Get List of Intersectionvectors

                                List<Vector2> tr = CollisionUtil.getCircleLineIntersectionPoint(a, b, center, radius);

                                Vector2 re = (tr.isEmpty()) ? null : tr.get(0);

                                for (Vector2 v : CollisionUtil.getCircleLineIntersectionPoint(a, b, center, radius)) {
                                    if (re.x < v.x) re = v;
                                }

                                entityPos.setX(entityPos.getX() - (re.x - tile.getX()));
                                entityPos.setY(entityPos.getY() - 0.5f);

                            }

                        }
                    }
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }


    /**
     * Moves entities per axis and checks collision. If collision occurs, moves the entity outside
     * of the collisionzone by resolving the overlap.
     *
     * @param deltaTime The deltaTime between frames
     */
    private void moveAllEntities(float deltaTime) {

        entities = getEngine().getEntitiesFor(posColl);

        for(Entity e : entities) {
            VelocityComponent vel = velMapper.get(e);
            PositionComponent pos = posMapper.get(e);
            CollisionComponent col= colMapper.get(e);

            //Record previous position for later collisionresolving
            col.setOldX(pos.getX());
            col.setOldY(pos.getY());

            pos.setX(pos.getX() + vel.getVelX() * deltaTime);
            test(e,col.getOldX(), col.getOldY());
            pos.setY(pos.getY() + vel.getVelY() * deltaTime);
            test2(e,col.getOldX(), col.getOldY());

            //Add smooth edge movement

        }
    }

    private void test(Entity e, float x,float y) {
            for(Entity t : getEngine().getEntitiesFor(tilesFamily)) {
                try {
                    float r = CollisionUtil.getXOverlap(EntityTools.getEntityHitbox(e), EntityTools.getTileHitbox(t),x,y);
                    e.getComponent(PositionComponent.class).setX(e.getComponent(PositionComponent.class).getX() + r);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
    }

    private void test2(Entity e, float x,float y) {
        for(Entity t : getEngine().getEntitiesFor(tilesFamily)) {
            try {
                float r = CollisionUtil.getYOverlap(EntityTools.getEntityHitbox(e), EntityTools.getTileHitbox(t),x,y);
                e.getComponent(PositionComponent.class).setY(e.getComponent(PositionComponent.class).getY() + r);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }


    private void createDebugLine(Vector2 start, Vector2 end, float x) {
        Entity e = new Entity();
        e.add(new DebugLineComponent(start,end));
        e.add(new TimedComponent(x));
        getEngine().addEntity(e);
    }

    private void createDebugRing(Vector2 center, float radius, float x) {
        Entity e = new Entity();
        e.add(new DebugCircleComponent(center,radius));
        e.add(new TimedComponent(x));
        getEngine().addEntity(e);

    }

}
