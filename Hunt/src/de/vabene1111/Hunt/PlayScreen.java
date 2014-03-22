package de.vabene1111.Hunt;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

public class PlayScreen implements Screen {

	SpriteBatch batch;
	SpriteBatch mBatch;
	BitmapFont font;
	TextureRegion goomba_1Texture, coinTexture;
	Rectangle hunter, coin;
	OrthographicCamera camera;
	Player player;

	ArrayList<Ai> enemy;
	Iterator<Ai> enemyIterator;

	ArrayList<BackgroundTile> tiles;
	Iterator<BackgroundTile> tileIterator;

	int i;
	int score;

	// MENU OVERLAY
	BitmapFont cFont;
	Stage stage;
	Label label;
	LabelStyle style;

	TextureAtlas buttonAtlas;
	TextButtonStyle buttonStyle;
	TextButton button;
	Skin skin;

	Game game;

	public PlayScreen(Game game) {
		this.game = game;
	}

	@Override
	public void show() {
		batch = new SpriteBatch();
		mBatch = new SpriteBatch();

		camera = new OrthographicCamera();
		camera.setToOrtho(true, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());

		// FONTS
		font = new BitmapFont(true);
		font.setColor(Color.BLACK);

		// COIN
		coinTexture = new TextureRegion(new Texture(
				Gdx.files.internal("coin.png")));
		coinTexture.flip(false, true);

		// RECTANGLES
		hunter = new Rectangle(100, 100, 64, 64);
		player = new Player(hunter);

		enemy = new ArrayList<Ai>();
		enemy.add(new Ai(new Rectangle(300, 300, 64, 64)));

		coin = new Rectangle(0, 0, 32, 32);
		newCoin();

		// STAGE
		stage = new Stage();

		cFont = new BitmapFont(Gdx.files.internal("font.fnt"), false);

		style = new LabelStyle(cFont, Color.GREEN);

		// label = new Label("Hunter-v1.0 by vabene1111 ", style);
		// label.setPosition(50, Gdx.graphics.getHeight() - 50);

		// stage.addActor(label);

		skin = new Skin();

		buttonAtlas = new TextureAtlas(
				Gdx.files.internal("Buttons/button.pack"));
		skin.addRegions(buttonAtlas);

		buttonStyle = new TextButtonStyle();
		buttonStyle.up = skin.getDrawable("button");
		buttonStyle.over = skin.getDrawable("buttonpressed");
		buttonStyle.down = skin.getDrawable("buttonpressed");
		buttonStyle.font = cFont;

		button = new TextButton("Quit", buttonStyle);

		button.setWidth(Gdx.graphics.getWidth() / 9);
		button.setHeight(Gdx.graphics.getHeight() / 12);

		stage.addActor(button);
		Gdx.input.setInputProcessor(stage);

		button.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				game.setScreen(new MainMenu(game, score));
				return true;
			}
		});

		tiles = new ArrayList<BackgroundTile>();
		int tileSizeX = Gdx.graphics.getWidth() / 11;
		int tileSizeY = Gdx.graphics.getHeight() / 8;
		for (int i = 0; i < Gdx.graphics.getWidth()/tileSizeX; i++) {
			for (int j = 0; j < Gdx.graphics.getHeight()/tileSizeY; j++) {
				int R = (int) ((Math.random() * (2 - 0) + 0));
				if (R == 0) {
					tiles.add(new BackgroundTile(new Texture("grass.png"), i * tileSizeX,
							j * tileSizeY, tileSizeX, tileSizeY));
				}
				if (R == 1) {
					tiles.add(new BackgroundTile(new Texture("gravel.png"), i * tileSizeX, j * tileSizeY,
							tileSizeX, tileSizeY));
				}
			}
		}

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		camera.update();
		batch.setProjectionMatrix(camera.combined);
		stage.act();

		if (Gdx.input.isTouched()) {
			player.update(Gdx.input.getX(), Gdx.input.getY());
		}

		batch.begin();
		
		tileIterator = tiles.iterator();
		while (tileIterator.hasNext()) {
			BackgroundTile cur = tileIterator.next();
			cur.render(batch);
		}


		// TITLE
		font.draw(batch, "Coin Hunter - v0.1", 1, 15);
		font.draw(batch, "SCORE:" + score, Gdx.graphics.getWidth() - 100, 1);

		// COIN
		if (hunter.overlaps(coin)) {
			newCoin();
			i++;
			// new enemy every 3 coins
			if (i == 3) {
				enemy.add(new Ai(new Rectangle(300, 300, 64, 64)));
				i = 0;
			}
			score++;
		}

		// DRAW TEXTURES
		batch.draw(coinTexture, coin.x, coin.y);

		enemyIterator = enemy.iterator();
		while (enemyIterator.hasNext()) {
			Ai cur = enemyIterator.next();

			cur.draw(batch);
			cur.update();
			// COLISON
			if (player.getPlayer().overlaps(cur.getPos())) {
				game.setScreen(new MainMenu(game, score));
			}

		}

		
		player.draw(batch);

		batch.end();

		mBatch.begin();
		stage.draw();
		mBatch.end();
	}

	@Override
	public void resize(int width, int height) {
		camera.setToOrtho(true, width, height);
	}

	@Override
	public void hide() {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	public void newCoin() {
		coin.x = getRandomXPos();
		coin.y = getRandomYPos();
		if (coin.x > Gdx.graphics.getWidth() - 32) {
			coin.x = coin.x + 32;
		}
		if (coin.y > Gdx.graphics.getWidth() - 32) {
			coin.y = coin.y + 32;
		}
	}

	public int getRandomXPos() {
		int max = Gdx.graphics.getWidth();
		double x = Math.random() * max;
		int r = (int) x;
		return r;
	}

	public int getRandomYPos() {
		int max = Gdx.graphics.getHeight();
		double x = Math.random() * max;
		int r = (int) x;
		return r;
	}

	@Override
	public void dispose() {
		// TODO dispose everything
		batch.dispose();
		mBatch.dispose();
		font.dispose();
		coinTexture.getTexture().dispose();
	}

}
