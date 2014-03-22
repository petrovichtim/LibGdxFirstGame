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
		System.out.println(stateTime);
		move(determineDirection(tx, ty));

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
		switch (d) {// TODO 2* for side move
		case 11:
			player.x -= 1f;
			player.y -= 1f;
			return;
		case 9:
			player.x -= 1f;
			return;
		case 7:
			player.x -= 1f;
			player.y += 1f;
			return;
		case 6:
			player.y += 1f;
			return;
		case 5:
			player.x += 1f;
			player.y += 1f;
			return;
		case 3:
			player.x += 1f;
			return;
		case 1:
			player.x += 1f;
			player.y -= 1f;
			return;
		case 0:
			player.y -= 1f;
		}
	}

	public int determineDirection(int tx, int ty) {
		Vector2 p, q;
		p = new Vector2(0, 0);
		q = new Vector2(0, 0);

		// if west of player
		if (tx < player.x) {
			
			p.y = player.y - (player.x - tx);
			q.y = player.y + (player.x - tx);

			if (ty < p.y && tx < player.x - (player.width/2)) {
				curFrame = animation.getKeyFrame(3 + stateTime);
				return 11;
			} else if (ty > p.y && ty < q.y) {
				curFrame = animation.getKeyFrame(1 + stateTime);
				return 9;// TODO split in 3 regions
			} else if (ty > q.y && tx < player.x - (player.width/2)) {
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
				curFrame = animation.getKeyFrame(2 + stateTime);
				return 3;// TODO split in 3 regions
			} else if (ty > q.y && tx > player.x + (player.width / 2)) {
				curFrame = animation.getKeyFrame(0 + stateTime);
				return 5;
			}
		}
		
		//if south or north
		if(ty > player.y){
			curFrame = animation.getKeyFrame(0 + stateTime);
			return 6;
		} else if(ty < player.y){
			curFrame = animation.getKeyFrame(3 + stateTime);
			return 0;
		}

		return 12;
	}
	
	
	//!!!!!DEPRECATED!!!!!
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
