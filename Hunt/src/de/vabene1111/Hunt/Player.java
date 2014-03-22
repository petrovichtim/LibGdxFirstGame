package de.vabene1111.Hunt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player {

	private static final int col = 4;
	private static final int row = 4;

	Animation animation;
	Texture playerTexture;
	TextureRegion[] frames;
	TextureRegion curFrame, test;

	int ty, tx;

	float stateTime;

	Rectangle player;
	float s;

	public Player(Rectangle r) {

		player = r;

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

		this.tx = tx;
		this.ty = ty;

		// sets position to center of texture
		player.x = player.x + player.getWidth() / 2;
		player.y = player.y + player.getHeight() / 2;

		// Determine's state time for animation
		if (stateTime < 0.9f) {
			stateTime += Gdx.graphics.getDeltaTime();
		} else {
			stateTime = 0f;
		}

		// sets new Position

		move(determineDirection());

		// sets position back to top left hand corner
		player.x = player.x - player.getWidth() / 2;
		player.y = player.y - player.getHeight() / 2;

	}

	public void draw(SpriteBatch batch) {
		if (!curFrame.isFlipY()) {
			curFrame.flip(false, true);
		}
		batch.draw(curFrame, player.x, player.y);
	}

	public void move(int d) {
		
		detSpeed();
		float mutl = 1f * s;

		switch (d) {
		case 11:
			player.x -= mutl;
			player.y -= mutl;
			return;
		case 10:
			player.x -= mutl;
			player.y -= mutl;
			return;
		case 9:
			player.x -= mutl;
			return;
		case 8:
			player.x -= mutl;
			player.y += mutl;
			return;
		case 7:
			player.x -= mutl;
			player.y += mutl;
			return;
		case 6:
			player.y += mutl;
			return;
		case 5:
			player.x += mutl;
			player.y += mutl;
			return;
		case 4:
			player.x += mutl;
			player.y += mutl;
			return;
		case 3:
			player.x += mutl;
			return;
		case 2:
			player.x += mutl;
			player.y -= mutl;
			return;
		case 1:
			player.x += mutl;
			player.y -= mutl;
			return;
		case 0:
			player.y -= mutl;
		}
	}

	public int determineDirection() {
		Vector2 p, q;
		p = new Vector2(0, 0);
		q = new Vector2(0, 0);

		// if west of player
		if (tx < player.x) {

			p.y = player.y - (player.x - tx);
			q.y = player.y + (player.x - tx);

			if (ty < p.y && tx < player.x - (player.width / 2)) {
				curFrame = animation.getKeyFrame(3 + stateTime);
				return 11;
			} else if (ty > p.y && ty < q.y) {
				if (ty < player.y - (player.height / 2)) {
					curFrame = animation.getKeyFrame(1 + stateTime);
					return 10;
				} else if (ty > player.y + (player.height / 2)) {
					curFrame = animation.getKeyFrame(1 + stateTime);
					return 8;
				} else {
					curFrame = animation.getKeyFrame(1 + stateTime);
					return 9;
				}
			} else if (ty > q.y && tx < player.x - (player.width / 2)) {
				curFrame = animation.getKeyFrame(0 + stateTime);
				return 7;
			}
		}

		// if east of player
		if (tx > player.x) {
			p.y = player.y - (tx - player.x);
			q.y = player.y + (tx - player.x);

			if (ty < p.y && tx > player.x + (player.width / 2)) {
				curFrame = animation.getKeyFrame(3 + stateTime);
				return 1;
			} else if (ty > p.y && ty < q.y) {
				if (ty < player.y - (player.height / 2)) {
					curFrame = animation.getKeyFrame(2 + stateTime);
					return 2;
				} else if (ty > player.y + (player.height / 2)) {
					curFrame = animation.getKeyFrame(2 + stateTime);
					return 4;
				} else {
					curFrame = animation.getKeyFrame(2 + stateTime);
					return 3;
				}
			} else if (ty > q.y && tx > player.x + (player.width / 2)) {
				curFrame = animation.getKeyFrame(0 + stateTime);
				return 5;
			}
		}

		// if south or north
		if (ty > player.y) {
			curFrame = animation.getKeyFrame(0 + stateTime);
			return 6;
		} else if (ty < player.y) {
			curFrame = animation.getKeyFrame(3 + stateTime);
			return 0;
		}

		return 12;
	}

	public void detSpeed() {
		// Default speed
		s = 2f;
		// 2. speed level
		if (tx > player.x + player.height * 2
				|| ty > player.y + player.height * 2
				|| tx < player.x - player.height * 2
				|| ty < player.y - player.height * 2) {
			s = 3f;
		}
		// 3. speed level
		if (tx > player.x + player.height * 3
				|| ty > player.y + player.height * 3
				|| tx < player.x - player.height * 3
				|| ty < player.y - player.height * 3) {
			s = 4f;
		}
		// 4. speed level
		if (tx > player.x + player.height * 6
				|| ty > player.y + player.height * 6
				|| tx < player.x - player.height * 6
				|| ty < player.y - player.height * 6) {
			s = 5f;
		}
		
	}

	public Rectangle getPlayer() {
		return player;
	}

	public void setPlayer(Rectangle player) {
		this.player = player;
	}
}
