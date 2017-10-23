package com.moreoptions.prototype;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import javafx.geometry.Pos;

public class MoreOptions extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Engine e;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");

		e = new Engine();

		Entity playerEntity = new Entity();
		playerEntity.add(new InputComponent());
		playerEntity.add(new PositionComponent());
		playerEntity.add(new VelocityComponent());


		e.addEntity(playerEntity);

		e.addSystem(new InputSystem());
		e.addSystem(new MovementSystem());

	}

	@Override
	public void render () {

		e.update(Gdx.graphics.getDeltaTime());
		Family f = Family.all(PositionComponent.class).get();

		ImmutableArray<Entity> entities = e.getEntitiesFor(f);



		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();

		for(Entity e: entities) {
			PositionComponent p = e.getComponent(PositionComponent.class);
			batch.draw(img, p.x, p.y);
		}

		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
