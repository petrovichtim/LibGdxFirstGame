package de.vabene1111.Hunt;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Hunt";
		cfg.useGL20 = false;
		cfg.width = 1080;
		cfg.height = 720;
		
		new LwjglApplication(new MyGdxGame(), cfg);
	}
}
