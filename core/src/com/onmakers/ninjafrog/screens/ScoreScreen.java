package com.onmakers.ninjafrog.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.onmakers.ninjafrog.NinjaFrog;
import com.onmakers.ninjafrog.actors.LevelCell;

import static com.onmakers.ninjafrog.NinjaFrog.V_Height;
import static com.onmakers.ninjafrog.NinjaFrog.V_WIDTH;

/**
 * Created by qureshi on 02/10/17.
 */

public class ScoreScreen implements Screen {

    private static final float SCALE = 1f;
    private final NinjaFrog game;
    OrthographicCamera camera;
    Viewport viewport;
    float w;
    float h;
    private Stage stage;
    private Skin skin;
    private OrthogonalTiledMapRenderer tmr;
    private TiledMap map;
    private Table table;

    //Game Design
    private TextureAtlas gs;


    public ScoreScreen(NinjaFrog game) {
        this.game = game;
        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();
        this.stage = new Stage(new FitViewport(w,h));
    }

    @Override
    public void show() {


        Gdx.input.setInputProcessor(stage);
        stage.clear();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, V_WIDTH / SCALE, V_Height / SCALE);
        camera.update();
        viewport = new FillViewport(V_WIDTH / SCALE, V_Height / SCALE, camera);
        viewport.apply();
        stage.getViewport().apply();
        skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));

        gs = new TextureAtlas(Gdx.files.internal("images/gs/gs.atlas"));

        table = new Table(skin);

        TextButton back = new TextButton("Back",skin);
        back.addListener(new ChangeListener() {
            public void changed (ChangeListener.ChangeEvent event, Actor actor) {
                game.setScreen(game.mainMenu);
            }
        });

        Label.LabelStyle headingStyle = new Label.LabelStyle(game.showcard, Color.BLACK);
        Label score = new Label("Coins",headingStyle);
        Label kills = new Label("Kills",headingStyle);

        table.add(score).width(w * 0.25f).height(h * 0.25f).pad(10);
        table.add(new LevelCell(gs.findRegion("icon empty"), game.gm.getTotalCoins(), game.showcard)).width(w * 0.25f).height(h * 0.25f).pad(10);
        table.row();
        table.add(kills).width(w * 0.25f).height(h * 0.25f).pad(10);
        table.add(new LevelCell(gs.findRegion("icon empty"), game.gm.getTotalEnemyKills(), game.showcard)).width(w * 0.25f).height(h * 0.25f).pad(10);
        table.row();
        table.add(back).colspan(2);



        //table.debug();

        table.setFillParent(true);

        stage.addActor(table.align(Align.center));

        map = new TmxMapLoader().load("maps/levelScreen.tmx");
        tmr = new OrthogonalTiledMapRenderer(map);
    }


    public void update(float delta){
        camera.update();
        game.manager.update();
        stage.act(delta);
        tmr.setView(camera);
    }

    @Override
    public void render(float delta) {

        update(delta);

        //Render
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        tmr.render();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

        camera.viewportHeight = height;
        camera.viewportWidth = width;
        viewport.update(width, height);
        stage.getViewport().update(width, height);

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

        stage.dispose();
        skin.dispose();
        tmr.dispose();
        map.dispose();
    }
}
