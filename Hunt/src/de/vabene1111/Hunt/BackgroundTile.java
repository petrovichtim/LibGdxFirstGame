package de.vabene1111.Hunt;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class BackgroundTile {
	
	float x,y,width,height;
	Texture texture;

	public BackgroundTile(Texture texture, float x, float y, float width, float height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.texture = texture;
	}

	public void render(SpriteBatch batch){
		batch.draw(texture, x, y, width, height);
	}
}
