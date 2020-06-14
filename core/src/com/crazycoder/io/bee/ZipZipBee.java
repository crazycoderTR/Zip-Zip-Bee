package com.crazycoder.io.bee;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;

import java.util.Random;

public class ZipZipBee extends ApplicationAdapter {

	SpriteBatch batch; // cizim icin kullanacagim arac
	Texture background;
	Texture bird;
	Texture bee;
	float
		bird_X,
		bird_Y,
		velocity = 0,
		bee_velocity = 3,
		gravity = 0.3f,
		bee_width,
		bee_height;
	int game_state = 0; // oyun basladi/baslamadi/bitti
	Random random;

	Circle bird_circle;
	Circle[][] bee_circle_set;

	ShapeRenderer shapeRenderer;


	int bee_set = 4;
	float [] bee_set_X = new float[bee_set];
	float [][] bee_off_set = new float[bee_set][bee_set - 1];
	float distance_of_bee_set_X = 0;
	
	@Override
	public void create () {
		// oncreate metodu ile ayni
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();

		background = new Texture("background.png");
		bird = new Texture("bird-1.png");
		bee = new Texture("bee-1.png");

		bird_X = Gdx.graphics.getWidth()/5;
		bird_Y = Gdx.graphics.getHeight()/2;

		bird_circle = new Circle();

		distance_of_bee_set_X = Gdx.graphics.getWidth()/2;
		random = new Random();

		bee_width = Gdx.graphics.getWidth()/10;
		bee_height = Gdx.graphics.getHeight()/8;

		bee_circle_set = new Circle[bee_set][bee_set];

		for (int i = 0; i < bee_set; i++) {
			bee_set_X[i] = Gdx.graphics.getWidth() - bee_width + (i * distance_of_bee_set_X);
			for (int k = 0; k < bee_set - 1; k++) {
				bee_circle_set[i][k] = new Circle();
				bee_off_set[i][k] = ((random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200));
			}
		}
	}

	@Override
	public void render () {
		// oyunun render edildigi kisim, oyun alani
		batch.begin(); // begin ve end arasina oyunu cizecegiz, neler olacagini
		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); // arkaplan

		if (game_state == 1) {

			if (Gdx.input.justTouched()) {
				// tiklanma metodu

				velocity = -(Gdx.graphics.getHeight()/180); // yukari sicrama
			}

			for (int i = 0; i < bee_set; i++) {

				if (bee_set_X[i] < 15) {

					bee_set_X[i] = bee_set_X[i] + bee_set * distance_of_bee_set_X;
					for (int k = 0; k < bee_set - 1; k++) {
						bee_off_set[i][k] = ((random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200));
						System.out.println("ybee " + bee_off_set[i][k]);
					}
				} else {
					bee_set_X[i] = bee_set_X[i] - bee_velocity;
				}

				for (int k = 0; k < bee_set - 1; k++) {
					batch.draw(bee, bee_set_X[i], Gdx.graphics.getHeight()/2 + bee_off_set[i][k], bee_width, bee_height);
					bee_circle_set[i][k] = new Circle(bee_set_X[i] + bee_width/2, Gdx.graphics.getHeight()/2 + bee_off_set[i][k] + bee_height/2, bee_width/2);
				}
			}

			if (bird_Y > 0) {
				velocity += gravity;
				bird_Y = bird_Y - velocity;
			} else {
				game_state = 2;
			}

		} else if (game_state == 0) {
			if (Gdx.input.justTouched()) {
				// tiklanma metodu
				game_state = 1;
			}

		} else if (game_state == 2) {
			if (Gdx.input.justTouched()) {
				// tiklanma metodu
				game_state = 1;

				bird_Y = Gdx.graphics.getHeight()/2;

				for (int i = 0; i < bee_set; i++) {
					bee_set_X[i] = Gdx.graphics.getWidth() - bee_width + (i * distance_of_bee_set_X);
					for (int k = 0; k < bee_set - 1; k++) {
						bee_circle_set[i][k] = new Circle();
						bee_off_set[i][k] = ((random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200));
					}
				}

				velocity = 0;
			}
		}

		batch.draw(bird, bird_X, bird_Y, Gdx.graphics.getWidth()/12, Gdx.graphics.getHeight()/10);

		batch.end(); // begin ve end arasina oyunu cizecegiz, neler olacagini

		bird_circle.set(bird_X + Gdx.graphics.getWidth()/24, bird_Y + Gdx.graphics.getHeight()/20, Gdx.graphics.getWidth()/24);

//		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//		shapeRenderer.setColor(Color.BLACK);
//		shapeRenderer.circle(bird_circle.x, bird_circle.y, bird_circle.radius);

		for (int i = 0; i < bee_set; i++) {
			for (int k = 0; k < bee_set - 1; k++) {
//				shapeRenderer.circle(bee_circle_set[i][k].x, bee_circle_set[i][k].y, bee_circle_set[i][k].radius);

				if (Intersector.overlaps(bird_circle, bee_circle_set[i][k])) { // carpisma
					System.out.println("Collision detection");
					game_state = 2;
				}
			}
		}
//		shapeRenderer.end();
	}
	
	@Override
	public void dispose () {
	}
}
