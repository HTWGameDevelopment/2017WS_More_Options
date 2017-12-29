package com.moreoptions.prototype.gameEngine.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.moreoptions.prototype.gameEngine.components.CircleCollisionComponent;
import com.moreoptions.prototype.gameEngine.components.SquareCollisionComponent;
import com.moreoptions.prototype.gameEngine.data.Consts;
import com.moreoptions.prototype.gameEngine.util.AssetLoader;
import com.moreoptions.prototype.gameEngine.util.QuadTree;

/**
 * System designed to debug QuadTree. This System adds all entities with Components relevant to Collision to a system and renders the resulting tree on the screen.
 * Created by denwe on 24.12.2017.
 */
public class QuadTreeDebug extends EntitySystem{

    QuadTree tree = new QuadTree(0, new Rectangle(0,0, Consts.GAME_WIDTH, Consts.GAME_HEIGHT));
    Family f = Family.one(SquareCollisionComponent.class, CircleCollisionComponent.class).get();
    ShapeRenderer renderer;
    BitmapFont font;
    SpriteBatch batch;
    Texture debugLineWhite;
    Texture debugLineYellow;


    public QuadTreeDebug(SpriteBatch batch, ShapeRenderer renderer) {
        this.renderer = renderer;
        this.font = AssetLoader.getInstance().getAssetManager().get("fonts/RobotoSlab-Bold.ttf", BitmapFont.class);
        this.batch = batch;
        font.setColor(Color.WHITE);

        Pixmap pix = new Pixmap(3,3, Pixmap.Format.RGB888);
        pix.setColor(Color.WHITE);
        pix.fillRectangle(0,0,3,3);
        debugLineWhite = new Texture(pix);
        pix.setColor(Color.YELLOW);
        debugLineYellow = new Texture(pix);
        pix.dispose();
    }

    @Override
    public void update(float deltaTime) {
        tree.clear();
        ImmutableArray<Entity> entities = getEngine().getEntitiesFor(f);

        for(Entity e : entities) {
            tree.insert(e);
        }
        Gdx.gl.glLineWidth(4);

        renderer.begin(ShapeRenderer.ShapeType.Line);

        //tree.visualize(renderer);
        renderer.end();
        batch.begin();
        tree.drawNumbers(batch, font);
        batch.end();
        Gdx.gl.glLineWidth(1);
    }


}
