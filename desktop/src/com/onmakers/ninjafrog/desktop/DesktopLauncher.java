package com.onmakers.ninjafrog.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.onmakers.ninjafrog.NinjaFrog;

import static com.onmakers.ninjafrog.NinjaFrog.V_Height;
import static com.onmakers.ninjafrog.NinjaFrog.V_WIDTH;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Ninja Frog";
		//config.useGL20 = false;
		config.width = V_WIDTH;//960;
		config.height = V_Height;//600;
		new LwjglApplication(new NinjaFrog(), config);
	}
}
