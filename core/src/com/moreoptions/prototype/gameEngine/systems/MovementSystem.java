package com.moreoptions.prototype.gameEngine.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.moreoptions.prototype.gameEngine.CollisionUtil;
import com.moreoptions.prototype.gameEngine.GameEngine;
import com.moreoptions.prototype.gameEngine.components.CollisionComponent;
import com.moreoptions.prototype.gameEngine.components.PositionComponent;
import com.moreoptions.prototype.gameEngine.components.TileComponent;
import com.moreoptions.prototype.gameEngine.components.VelocityComponent;
import com.moreoptions.prototype.gameEngine.util.EntityTools;

import java.awt.geom.Point2D;

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
    private float TILE_SIZE = 32;

    @Override
    public void update(float deltaTime) {
        moveAllEntities(deltaTime);
    }

    private void fixYCollision(Entity e) {

        ImmutableArray<Entity> tiles = getEngine().getEntitiesFor(tilesFamily);

        for (Entity t : tiles) {
            try {
                Rectangle r = EntityTools.getTileHitbox(t);
                Circle c = EntityTools.getEntityHitbox(e);

                if (Intersector.overlaps(c, r)) { //THEY OVERLAP? WE NEED TO INVESTIGATE THIS FURTHER
                    PositionComponent tile = posMapper.get(t);
                    PositionComponent entityPos = posMapper.get(e);
                    boolean top = (c.y > tile.getY()) ? true : false;
                    if (top) {
                        if (c.x > tile.getX() && c.x < tile.getX() + TILE_SIZE) {
                            float overlap = tile.getY() + TILE_SIZE - entityPos.getY() + c.radius;
                            entityPos.setY(entityPos.getY() + overlap);
                        } else {
                            if (c.y < tile.getY() + TILE_SIZE) {
                                entityPos.setY(tile.getY() + TILE_SIZE + c.radius);
                            } else {

                                while (Intersector.overlaps(c, r)) {
                                    entityPos.setY(entityPos.getY() + 0.1f);
                                    c = EntityTools.getEntityHitbox(e);

                                }
                            }
                        }
                    } else {
                        if (c.x > tile.getX() && c.x < tile.getX() + TILE_SIZE) {
                            float overlap = tile.getY() - entityPos.getY() - c.radius;
                            entityPos.setY(entityPos.getY() + overlap);
                        } else {
                            if (c.y > tile.getY()) {
                                entityPos.setY(tile.getY() - c.radius);
                            } else {
                                while (Intersector.overlaps(c, r)) {
                                    entityPos.setY(entityPos.getY() - 0.1f);
                                    c = EntityTools.getEntityHitbox(e);
                                }
                            }
                        }
                    }
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    private void fixXCollision(Entity e) {
        ImmutableArray<Entity> tiles = getEngine().getEntitiesFor(tilesFamily);

        for (Entity t : tiles) {
            try {
                Rectangle r = EntityTools.getTileHitbox(t);
                Circle c = EntityTools.getEntityHitbox(e);

                if (Intersector.overlaps(c, r)) { //THEY OVERLAP? WE NEED TO INVESTIGATE THIS FURTHER
                    PositionComponent tile = posMapper.get(t);
                    PositionComponent entityPos = posMapper.get(e);
                    boolean right = (c.x > tile.getX()) ? true : false;
                    if (right) {
                        if (c.y > tile.getY() && c.y < (tile.getY() + TILE_SIZE)) {
                            float overlap = tile.getX() + TILE_SIZE - entityPos.getX() + c.radius;
                            entityPos.setX(entityPos.getX() + overlap);
                        } /*else {
                            if (c.x < tile.getX() + TILE_SIZE) {
                                entityPos.setX(tile.getX() + TILE_SIZE + c.radius);
                            } else {

                                while (Intersector.overlaps(c, r)) {
                                    entityPos.setX(entityPos.getX() + 0.1f);
                                    c = EntityTools.getEntityHitbox(e);

                                }
                            }
                        }*/
                    } else {
                        if (c.y > tile.getY() && c.y < tile.getY() + TILE_SIZE) {
                            float overlap = tile.getX() - entityPos.getX() - c.radius;
                            entityPos.setX(entityPos.getX() + overlap);
                        } /*else {
                            if (c.x < tile.getX()) {
                                entityPos.setX(tile.getX() - c.radius);
                            } else {
                                while (Intersector.overlaps(c, r)) {
                                    entityPos.setX(entityPos.getX() - 0.1f);
                                    c = EntityTools.getEntityHitbox(e);
                                }
                            }
                        }*/
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

        ImmutableArray<Entity> entities = getEngine().getEntitiesFor(posColl);

        for(Entity e : entities) {
            VelocityComponent vel = velMapper.get(e);
            PositionComponent pos = posMapper.get(e);
            CollisionComponent col= colMapper.get(e);

            //Record previous position for later collisionresolving
            col.setOldX(pos.getX());
            col.setOldY(pos.getY());

            //Step Y
            pos.setY(pos.getY() + vel.getVelY() * deltaTime);
            fixYCollision(e);

            //Step X
            pos.setX(pos.getX() + vel.getVelX() * deltaTime);
            fixXCollision(e);
        }
    }


}
