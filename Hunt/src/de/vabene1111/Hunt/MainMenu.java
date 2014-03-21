package de.vabene1111.Hunt;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

public class MainMenu implements Screen {

	SpriteBatch batch;

	BitmapFont cFont;
	Stage stage;
	Label label, scoreboard;
	LabelStyle style, styleRed;

	TextureAtlas buttonAtlas;
	TextButtonStyle buttonStyle;
	TextButton button;
	Skin skin;

	int score;
	boolean showScore = false;

	Game game;

	public MainMenu(Game game) {
		this.game = game;
	}

	public MainMenu(Game game, int score) {
		this.game = game;
		this.score = score;
		showScore = true;
	}

	@Override
	public void show() {

		batch = new SpriteBatch();

		// STAGE
		stage = new Stage();

		cFont = new BitmapFont(Gdx.files.internal("font.fnt"), false);

		style = new LabelStyle(cFont, Color.GREEN);
		styleRed = new LabelStyle(cFont, Color.RED);

		label = new Label("Hunter-v1.0 by vabene1111 ", style);
		label.setPosition(50, Gdx.graphics.getHeight() - 50);

		stage.addActor(label);

		if (showScore) {
			scoreboard = new Label("Score: " + score, styleRed);
		} else{
			scoreboard = new Label("Score: ", styleRed);
		}
		
		scoreboard.setPosition(
				Gdx.graphics.getWidth() - (Gdx.graphics.getWidth() / 3),
				Gdx.graphics.getHeight() - (Gdx.graphics.getHeight() / 2.5f));
		stage.addActor(scoreboard);
		
		skin = new Skin();

		buttonAtlas = new TextureAtlas(
				Gdx.files.internal("Buttons/button.pack"));
		skin.addRegions(buttonAtlas);

		buttonStyle = new TextButtonStyle();
		buttonStyle.up = skin.getDrawable("button");
		buttonStyle.over = skin.getDrawable("buttonpressed");
		buttonStyle.down = skin.getDrawable("buttonpressed");
		buttonStyle.font = cFont;

		button = new TextButton("vabene is awesome", buttonStyle);

		stage.addActor(button);
		Gdx.input.setInputProcessor(stage);

		button.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				game.setScreen(new PlayScreen(game));
				return true;
			}
		});

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		stage.act();

		batch.begin();
		stage.draw();
		batch.end();

	}

	@Override
	public void resize(int width, int height) {

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

	@Override
	public void dispose() {
		// TODO dispose evrything
	}

}