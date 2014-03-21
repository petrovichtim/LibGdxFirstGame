package de.vabene1111.Hunt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Player {

	private static final int col = 4;
	private static final int row = 4;

	Animation animation;
	Texture playerTexture;
	TextureRegion[] frames;
	TextureRegion curFrame, test;

	float stateTime;

	Rectangle player;
	float speed;

	public Player(Rectangle r) {

		player = r;
		System.out.println(player.x);
		System.out.println(player.y);

		// ANIMATION
		playerTexture = new Texture(Gdx.files.internal("CSS.png"));
		TextureRegion[][] tmp = TextureRegion
				.split(playerTexture, playerTexture.getWidth() / col,
						playerTexture.getHeight() / row);
		frames = new TextureRegion[col * row];

		int index = 0;
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				frames[index++] = tmp[i][j];
			}
		}

		animation = new Animation(0.25f, frames);
		stateTime = 0f;
		curFrame = animation.getKeyFrame(0);
	}

	public void update(int tx, int ty) {

		if (stateTime < 4) {
			stateTime += Gdx.graphics.getDeltaTime();
		} else {
			stateTime = 0;
		}

		determinePos(tx, ty);

	}

	public void draw(SpriteBatch batch) {
		if (!curFrame.isFlipY()) {
			curFrame.flip(false, true);
		}
		batch.draw(curFrame, player.x, player.y);
	}

	public void determinePos(int tx, int ty) {

		player.height = 64;
		float speed;

		// Default speed
		speed = 2;
		// 2. speed level
		if (tx > player.x + player.height * 2
				|| ty > player.y + player.height * 2
				|| tx < player.x - player.height * 2
				|| ty < player.y - player.height * 2) {
			speed = 3;
		}
		// 3. speed level
		if (tx > player.x + player.height * 3
				|| ty > player.y + player.height * 3
				|| tx < player.x - player.height * 3
				|| ty < player.y - player.height * 3) {
			speed = 4;
		}
		// 4. speed level
		if (tx > player.x + player.height * 4
				|| ty > player.y + player.height * 4
				|| tx < player.x - player.height * 4
				|| ty < player.y - player.height * 4) {
			speed = 5;
		}

		// IF outside texture
		if (tx < player.x + player.height && tx > player.x
				&& ty < player.y + player.height && ty > player.y) {
			return;
		}

		// EAST-WEST
		if (ty <= player.y + player.height + player.height / 2
				&& ty >= player.y - player.height / 2) {
			if (tx < player.x + 1) {
				curFrame = animation.getKeyFrame(4 + stateTime);
				player.x -= speed;
				return;
			}
			if (tx > player.x + 1) {
				curFrame = animation.getKeyFrame(8 + stateTime);
				player.x += speed;
				return;
			}
		}

		// NORTH
		if (ty < player.y) {
			curFrame = animation.getKeyFrame(12 + stateTime);
			// NORTH
			if (tx <= player.x + player.height + player.height / 2
					&& tx >= player.x - player.height / 2) {
				player.y -= speed;
				return;
			}
			// NORTH-EAST
			if (tx > player.x + player.height) {
				player.x += speed;
				player.y -= speed;
				return;
			}
			// NORTH-WEST
			if (tx < player.x) {
				player.x -= speed;
				player.y -= speed;
				return;
			}
		}
		// SOUTH
		if (ty > player.y) {
			curFrame = animation.getKeyFrame(0 + stateTime);
			// SOUTH
			if (tx <= player.x + player.height + player.height / 2
					&& tx >= player.x - player.height / 2) {
				player.y += speed;
				return;
			}
			// SOUTH-EAST
			if (tx > player.x + player.height) {
				player.x += speed;
				player.y += speed;
				return;
			}
			// SOUTH-WEST
			if (tx < player.x) {
				player.x -= speed;
				player.y += speed;
				return;
			}
		}

		System.out.println("Position not changed");
		return;
	}

	public Rectangle getPlayer() {
		return player;
	}

	public void setPlayer(Rectangle player) {
		this.player = player;
	}
}
