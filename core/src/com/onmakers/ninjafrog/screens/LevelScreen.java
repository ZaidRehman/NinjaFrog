package com.onmakers.ninjafrog.screens;

/**
 * Created by qureshi on 01/10/17.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.onmakers.ninjafrog.NinjaFrog;
import com.onmakers.ninjafrog.actors.levelCell;


import java.util.ArrayList;

import static com.onmakers.ninjafrog.NinjaFrog.V_Height;
import static com.onmakers.ninjafrog.NinjaFrog.V_WIDTH;


public class LevelScreen implements Screen {


    private  final NinjaFrog game;

    private  Stage stage;
    private  Skin skin;
    private Batch batch;
    OrthographicCamera camera;
    Viewport viewport;
    private static final float SCALE = 1f;
    float w;
    float h;


    private OrthogonalTiledMapRenderer tmr;
    private TiledMap map;

    //Game Design
    private TextureAtlas gs;

    ArrayList<levelCell> ls;

    public LevelScreen(NinjaFrog game) {
        this.game = game;
        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();
        this.stage = new Stage(new FitViewport(w,h));
        this.batch = new SpriteBatch();
        //this.skin=new Skin();
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
//
//        game.manager.load("skin/glassy-ui.json", TextureAtlas.class);
//        game.manager.finishLoading();
//        this.skin.addRegions(game.manager.get("skin/glassy-ui.json", TextureAtlas.class));

        stage.getViewport().apply();
        skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));


        //Label Style
        Label.LabelStyle headingStyle = new Label.LabelStyle(game.showcard, Color.BLACK);

        gs = new TextureAtlas(Gdx.files.internal("images/gs/gs.atlas"));

        ls = new ArrayList<levelCell>();
        ls.add(new levelCell(gs.findRegion("icon empty"),"1",game.showcard));
        ls.add(new levelCell(gs.findRegion("icon empty"),"2",game.showcard));
        ls.add(new levelCell(gs.findRegion("icon empty"),"3",game.showcard));
        ls.add(new levelCell(gs.findRegion("icon empty"),"4",game.showcard));
        ls.add(new levelCell(gs.findRegion("icon empty"),"5",game.showcard));
        ls.add(new levelCell(gs.findRegion("icon empty"),"6",game.showcard));
        ls.add(new levelCell(gs.findRegion("icon empty"),"7",game.showcard));
        ls.add(new levelCell(gs.findRegion("icon empty"),"8",game.showcard));
        ls.add(new levelCell(gs.findRegion("icon empty"),"9",game.showcard));
        ls.add(new levelCell(gs.findRegion("icon empty"),"10",game.showcard));
        ls.add(new levelCell(gs.findRegion("icon empty"),"11",game.showcard));
        ls.add(new levelCell(gs.findRegion("icon empty"),"12",game.showcard));
        ls.add(new levelCell(gs.findRegion("icon empty"),"13",game.showcard));
        ls.add(new levelCell(gs.findRegion("icon empty"),"14",game.showcard));
        ls.add(new levelCell(gs.findRegion("icon empty"),"15",game.showcard));
        ls.add(new levelCell(gs.findRegion("icon empty"),"16",game.showcard));
        ls.add(new levelCell(gs.findRegion("icon empty"),"17",game.showcard));
        ls.add(new levelCell(gs.findRegion("icon empty"),"18",game.showcard));
        ls.add(new levelCell(gs.findRegion("icon empty"),"19",game.showcard));
        ls.add(new levelCell(gs.findRegion("icon empty"),"20",game.showcard));

        Table table = new Table(skin);


        table.add(ls.get(0)).width(w * 0.18f).height(h * 0.23f).pad(10);
        table.add(ls.get(1)).width(w * 0.18f).height(h * 0.23f).pad(10);
        table.add(ls.get(2)).width(w * 0.18f).height(h * 0.23f).pad(10);
        table.add(ls.get(3)).width(w * 0.18f).height(h * 0.23f).pad(10);
        table.add(ls.get(4)).width(w * 0.18f).height(h * 0.23f).pad(10);
        table.row();
        table.add(ls.get(5)).width(w * 0.18f).height(h * 0.23f).pad(10);
        table.add(ls.get(6)).width(w * 0.18f).height(h * 0.23f).pad(10);
        table.add(ls.get(7)).width(w * 0.18f).height(h * 0.23f).pad(10);
        table.add(ls.get(8)).width(w * 0.18f).height(h * 0.23f).pad(10);
        table.add(ls.get(9)).width(w * 0.18f).height(h * 0.23f).pad(10);
        table.row();
        table.add(ls.get(10)).width(w * 0.18f).height(h * 0.23f).pad(10);
        table.add(ls.get(11)).width(w * 0.18f).height(h * 0.23f).pad(10);
        table.add(ls.get(12)).width(w * 0.18f).height(h * 0.23f).pad(10);
        table.add(ls.get(13)).width(w * 0.18f).height(h * 0.23f).pad(10);
        table.add(ls.get(14)).width(w * 0.18f).height(h * 0.23f).pad(10);
        table.row();
        table.add(ls.get(15)).width(w * 0.18f).height(h * 0.23f).pad(10);
        table.add(ls.get(16)).width(w * 0.18f).height(h * 0.23f).pad(10);
        table.add(ls.get(17)).width(w * 0.18f).height(h * 0.23f).pad(10);
        table.add(ls.get(18)).width(w * 0.18f).height(h * 0.23f).pad(10);
        table.add(ls.get(19)).width(w * 0.18f).height(h * 0.23f).pad(10);



        table.debug();

        table.setFillParent(true);

        stage.addActor(table.align(Align.center));

        map = new TmxMapLoader().load("maps/levelScreen.tmx");
        tmr = new OrthogonalTiledMapRenderer(map);


    }

    public void update(float delta){
        camera.update();
        game.manager.update();
        stage.act(delta);
        batch.setProjectionMatrix(camera.combined);
        tmr.setView(camera);
    }

    @Override
    public void render(float delta) {
        update(delta);

        //Render
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        int i = 1;
        for (levelCell lc :
                ls) {
            lc.setText(i++ + "");
        }
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