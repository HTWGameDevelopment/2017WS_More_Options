package com.moreoptions.prototype.gameEngine.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.moreoptions.prototype.gameEngine.components.CollisionComponent;
import com.moreoptions.prototype.gameEngine.components.PositionComponent;
import com.moreoptions.prototype.gameEngine.components.TileComponent;
import com.moreoptions.prototype.gameEngine.components.VelocityComponent;
import com.moreoptions.prototype.gameEngine.util.EntityTools;

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

    @Override
    public void update(float deltaTime) {
        moveAllEntities(deltaTime);
    }

    private void fixYCollision(Entity e) {

        ImmutableArray<Entity> tiles = getEngine().getEntitiesFor(tilesFamily);

        for(Entity t : tiles) {
            try {
                Rectangle r = EntityTools.getTileHitbox(t);
                Circle c = EntityTools.getEntityHitbox(e);

                if(Intersector.overlaps(c,r)) {
                    PositionComponent pp = posMapper.get(e);
                    CollisionComponent cp = colMapper.get(e);

                    PositionComponent tilepp = posMapper.get(t);

                    if(cp.getOldY() > tilepp.getY() + 32) {
                        float tileY = tilepp.getY() + 32;

                        //Calc distance:

                        float distance = tileY - pp.getY() - cp.getSize();



                        // WENN EIN VERTEX IM DREIECK IST

                        float radius = cp.getSize();

                        float a = pp.getY() - tileY;

                        float b = (float) Math.sqrt(a * a + radius * radius);


                        float test = tilepp.getX() - pp.getX();
                        System.out.println(tilepp.getX() + "- " + pp.getX() + " = " + test);



                        System.out.println("a: "+a + " radius: "+radius+ " b: " + b + " |" + test);

                        System.out.println(pp.getY() + (b-test) - pp.getY() );


                        pp.setY(pp.getY() + (b-test-radius));





                    } else {
                        float tileY = tilepp.getY();
                        float overlap = pp.getY()- tileY + cp.getSize();
                        pp.setY(pp.getY()-overlap);
                    }
                } else {
                    //Check if we passed trough a tile between frames
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    private void fixXCollision(Entity e) {

        ImmutableArray<Entity> tiles = getEngine().getEntitiesFor(tilesFamily);

        for(Entity t : tiles) {
            try {
                Rectangle r = EntityTools.getTileHitbox(t);
                Circle c = EntityTools.getEntityHitbox(e);
                if(Intersector.overlaps(c,r)) {

                    PositionComponent pp = posMapper.get(e);
                    CollisionComponent cp = colMapper.get(e);

                    PositionComponent tilepp = posMapper.get(t);

                    if(cp.getOldX() > tilepp.getX() + 32) {
                        float tileX = tilepp.getX() + 32;
                        float overlap = tileX - pp.getX() + cp.getSize();
                        pp.setX(pp.getX()+overlap);

                    } else {
                        float tileX = tilepp.getX();
                        float overlap = pp.getX()- tileX + cp.getSize();
                        pp.setX(pp.getX()-overlap);

                    }
                } else  {
                    //Check if we passed trough a tile between frames
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
