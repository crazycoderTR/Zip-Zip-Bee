package com.crazycoder.io.bee;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ZipZipBee extends ApplicationAdapter {

	SpriteBatch batch; // cizim icin kullanacagim arac
	Texture background;
	
	@Override
	public void create () {
		// oncreate metodu ile ayni
		batch = new SpriteBatch();
		background = new Texture("background.png");
	}

	@Override
	public void render () {
		// oyunun render edildigi kisim, oyun alani
		batch.begin(); // begin ve end arasina oyunu cizecegiz, neler olacagini

		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		batch.end(); // begin ve end arasina oyunu cizecegiz, neler olacagini
	}
	
	@Override
	public void dispose () {
	}
}
