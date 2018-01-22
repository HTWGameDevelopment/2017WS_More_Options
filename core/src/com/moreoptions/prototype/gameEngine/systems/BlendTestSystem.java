package com.moreoptions.prototype.gameEngine.systems;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import com.moreoptions.prototype.gameEngine.data.Consts;

/**
 * Created by denwe on 11.01.2018.
 */
public class BlendTestSystem extends EntitySystem {

    ShapeRenderer renderer;
    OrthographicCamera camera;
    float test = 50;

    public BlendTestSystem(ShapeRenderer renderer,OrthographicCamera camera) {
        this.renderer = renderer;
        this.camera = camera;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        test+= 1;
        if(test > 200) {
            test =1;
        }
        renderer.setColor(Color.WHITE);
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.circle(50,50,50);
        renderer.end();

        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(new Color(1, 0, 0, 1f));
        renderer.circle(50, 50, 50);
        Rectangle r = new Rectangle(0,0,200, test);
        Rectangle area = new Rectangle(0,0, Consts.GAME_WIDTH, Consts.GAME_HEIGHT);
        ScissorStack.calculateScissors(camera, renderer.getTransformMatrix(), r, area);
        ScissorStack.pushScissors(r);
        renderer.end();

        ScissorStack.popScissors();

    }
}
