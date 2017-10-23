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
	private InputManager inputManager = new InputManager();
	SpriteBatch batch;
	Texture img;
	Engine e;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");

		e = new Engine();
		Gdx.input.setInputProcessor(inputManager);

		Entity playerEntity = new Entity();
		playerEntity.add(new InputComponent());
		playerEntity.add(new PositionComponent());
		playerEntity.add(new VelocityComponent());


		e.addEntity(playerEntity);

		e.addSystem(InputSystem.getInstance());
		e.addSystem(new MovementSystem());

	}

	@Override
	public void render () {

		e.update(Gdx.graphics.getDeltaTime());
		Family f = Family.all(PositionComponent.class).get();

		ImmutableArray<Entity> entities = e.getEntitiesFor(f);

		InputSystem p = InputSystem.getInstance();

		// gitSystem.out.println( "" + p.up + "|" + p.down + "|" + p.left + "|" + p.right);

		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();

		for(Entity e: entities) {
			PositionComponent po = e.getComponent(PositionComponent.class);
			batch.draw(img, po.x, po.y);
		}

		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
