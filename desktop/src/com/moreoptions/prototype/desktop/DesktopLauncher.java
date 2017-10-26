package com.moreoptions.prototype.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.moreoptions.prototype.MoreOptions;
import com.moreoptions.prototype.level.Map;

public class DesktopLauncher {
	public static void main (String[] arg) {
		new Map();
		// LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		// new LwjglApplication(new MoreOptions(), config);
	}
}
