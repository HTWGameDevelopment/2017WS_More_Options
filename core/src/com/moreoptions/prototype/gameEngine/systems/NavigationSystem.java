package com.moreoptions.prototype.gameEngine.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.moreoptions.prototype.gameEngine.GameWorld;
import com.moreoptions.prototype.gameEngine.components.*;
import com.moreoptions.prototype.gameEngine.util.navgraph.NavGraph;
import com.moreoptions.prototype.gameEngine.util.navgraph.StandardNavGraph;

/**
 * Created by denwe on 17.12.2017.
 */
public class NavigationSystem extends EntitySystem {

    //This system recalculates the graph everytime a change has occured. it does so by either removing a node and adding it again or by recalculating the whole graph.

    Family f = Family.all(PositionComponent.class, CollisionComponent.class).get();
    Family fa = Family.all(ObstacleComponent.class).get();

    NavGraph navGraph = new StandardNavGraph();
    ShapeRenderer renderer;
    SpriteBatch batch;
    BitmapFont font;


    public NavigationSystem(ShapeRenderer renderer, GameWorld world, SpriteBatch batch, BitmapFont font) {
        this.renderer = renderer;
        this.batch = batch;
        this.font = font;
        world.addEntityListener(f, new EntityListener() {
            @Override
            public void entityAdded(Entity entity) {
                entity.add(new NavigationComponent(entity));
                navGraph.addEntity(entity);
            }

            @Override
            public void entityRemoved(Entity entity) {
                navGraph.removeEntity(entity);
            }
        });

        world.addEntityListener(fa, new EntityListener() {
            @Override
            public void entityAdded(Entity entity) {
                entity.add(new NavigationComponent(entity));
                navGraph.addEntity(entity);
            }

            @Override
            public void entityRemoved(Entity entity) {
                navGraph.removeEntity(entity);
                System.out.println("Entity + QWEWQEQWEEW");
            }
        });

    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        navGraph.draw(renderer, batch, font );

    }
}
