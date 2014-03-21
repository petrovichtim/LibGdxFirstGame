package de.vabene1111.Hunt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Torch {

	private static final int col = 6;
	private static final int row = 1;

	Animation animation;
	Texture objTexture;
	TextureRegion[] frames;
	TextureRegion curFrame, test;

	float stateTime;

	public Torch() {

		// ANIMATION
		objTexture = new Texture(Gdx.files.internal("fackel.png"));
		TextureRegion[][] tmp = TextureRegion.split(objTexture,
				objTexture.getWidth() / col, objTexture.getHeight() / row);
		frames = new TextureRegion[col * row];

		int index = 0;
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				frames[index++] = tmp[i][j];
			}
		}

		animation = new Animation(1f, frames);
		stateTime = 0f;
		curFrame = animation.getKeyFrame(0);
	}

	public void update() {

		if (stateTime < 6) {
			stateTime += Gdx.graphics.getDeltaTime();
			System.out.println("plus delta time" +  stateTime);
		} else {
			stateTime = 0;
			System.out.println("set to 0");
		}

		curFrame = animation.getKeyFrame(stateTime);
	}

	public void draw(SpriteBatch batch) {

		batch.draw(curFrame, 300, 300, curFrame.getRegionHeight() * 2, curFrame.getRegionWidth() * 2);
	}

}
