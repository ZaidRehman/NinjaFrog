package com.onmakers.ninjafrog.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.onmakers.ninjafrog.NinjaFrog;
import com.onmakers.ninjafrog.manager.ScreenManager;

public abstract class AbstractScreen implements Screen{

    protected NinjaFrog game;
    protected ScreenManager sm;
    protected SpriteBatch batch;
    protected OrthographicCamera camera;

    public AbstractScreen(ScreenManager sm) {
        this.sm = sm;
        this.game = sm.ninjaFrog();
        camera = game.getCamera();
        batch = game.getBatch();
    }

    public abstract void update(float delta);

    //@Override
    public void resize(int width, int height) {
        camera.setToOrtho(false,width,height);
    }
}
