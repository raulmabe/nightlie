package com.nameless.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.nameless.game.Constants;
import com.nameless.game.MainGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = Constants.VIEWPORT_HEIGHT;
		config.width = Constants.VIEWPORT_WIDTH;
		config.useHDPI = true;
//		config.vSyncEnabled = false;
//		config.foregroundFPS = 0;
//		config.backgroundFPS = 0;
		config.title = Constants.TITLE + " " + Constants.VERSION;
		new LwjglApplication(new MainGame(), config);
	}
}
