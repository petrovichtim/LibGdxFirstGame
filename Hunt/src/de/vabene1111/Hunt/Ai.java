package de.vabene1111.Hunt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Ai {
	private Rectangle pos;
	private Vector2 mult;
	TextureRegion texture;

	// ANIMATION
	

	public Ai(Rectangle r) {
		pos = r;
		mult = new Vector2();

		texture = new TextureRegion(new Texture(
				Gdx.files.internal("goomba.png")));
		texture.flip(false, true);
		
	}

	

	public void update() {
		
		Rectangle world = new Rectangle(0, 0, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());

		if (mult.x == 0 || mult.y == 0) {
			mult.x = getRandom();
			mult.y = getRandom();
		}

		if (!world.contains(pos)) {
			// x axis intercept
			if (pos.x >= Gdx.graphics.getWidth() - 64) {
				mult.x = getRandom();
				if (mult.x >= 0) {
					mult.x = mult.x * (-1);
				}
			}
			if (pos.x <= 0) {
				mult.x = getRandom();
				if (mult.x <= 0) {
					mult.x = mult.x - (-1);
				}
			}
			// y axis intercept
			if (pos.y >= Gdx.graphics.getHeight() - 64) {
				mult.y = getRandom();
				if (mult.y >= 0) {
					mult.y = mult.y * (-1);
				}
			}
			if (pos.y <= 0) {
				mult.y = getRandom();
				if (mult.y >= 0) {
					mult.y = mult.y - (-1);
				}
			}

		}

		pos.x += mult.x;
		pos.y += mult.y;
	}
	
	public void draw(SpriteBatch batch) {
		batch.draw(texture, pos.x, pos.y);
	}
	
	public float getRandom() {
		double r = Math.random() * 2;

		if (Math.random() * 2 > 1) {
			r = r - r * 2;
		}

		float rand = (float) r;

		return rand;
	}

	public Vector2 getMult() {
		return mult;
	}

	public void setMult(Vector2 mult) {
		this.mult = mult;
	}

	public Rectangle getPos() {
		return pos;
	}

	public void setPos(Rectangle pos) {
		this.pos = pos;
	}
}
