package com.onmakers.ninjafrog.screens;

/**
 * Created by qureshi on 01/10/17.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.onmakers.ninjafrog.NinjaFrog;
import com.onmakers.ninjafrog.actors.LevelCell;

import java.util.ArrayList;

import static com.onmakers.ninjafrog.NinjaFrog.V_Height;
import static com.onmakers.ninjafrog.NinjaFrog.V_WIDTH;


public class LevelScreen implements Screen {


    private  final NinjaFrog game;

    private  Stage stage;
    OrthographicCamera camera;
    Viewport viewport;
    private static final float SCALE = 1f;
    float w;
    float h;
    private Skin skin;


    private OrthogonalTiledMapRenderer tmr;
    private TiledMap map;
    private Table table;

    //Game Design
    private TextureAtlas gs;

    ArrayList<LevelCell> ls;

    public LevelScreen(NinjaFrog game) {
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
        gs = new TextureAtlas(Gdx.files.internal("images/gs/gs.atlas"));
        skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));

        TextButton back = new TextButton("Back",skin);
        back.addListener(new ChangeListener() {
            public void changed (ChangeListener.ChangeEvent event, Actor actor) {
                game.setScreen(game.mainMenu);
            }
        });

        ls = new ArrayList<LevelCell>();
        ls.add(new LevelCell(gs.findRegion("icon empty"),0,game.showcard));
        ls.add(new LevelCell(gs.findRegion("icon empty"),1,game.showcard));
        ls.add(new LevelCell(gs.findRegion("icon empty"),2,game.showcard));
        ls.add(new LevelCell(gs.findRegion("icon empty"),3,game.showcard));
        ls.add(new LevelCell(gs.findRegion("icon empty"),4,game.showcard));
        ls.add(new LevelCell(gs.findRegion("icon empty"),5,game.showcard));
        ls.add(new LevelCell(gs.findRegion("icon empty"),6,game.showcard));
        ls.add(new LevelCell(gs.findRegion("icon empty"),7,game.showcard));
        ls.add(new LevelCell(gs.findRegion("icon empty"),8,game.showcard));
        ls.add(new LevelCell(gs.findRegion("icon empty"),9,game.showcard));
        ls.add(new LevelCell(gs.findRegion("icon empty"),10,game.showcard));
        ls.add(new LevelCell(gs.findRegion("icon empty"),11,game.showcard));
        ls.add(new LevelCell(gs.findRegion("icon empty"),12,game.showcard));
        ls.add(new LevelCell(gs.findRegion("icon empty"),13,game.showcard));
        ls.add(new LevelCell(gs.findRegion("icon empty"),14,game.showcard));
        ls.add(new LevelCell(gs.findRegion("icon empty"),15,game.showcard));
        ls.add(new LevelCell(gs.findRegion("icon empty"),16,game.showcard));
        ls.add(new LevelCell(gs.findRegion("icon empty"),17,game.showcard));
        ls.add(new LevelCell(gs.findRegion("icon empty"),18,game.showcard));
        ls.add(new LevelCell(gs.findRegion("icon empty"),19,game.showcard));

        table = new Table();


        table.add(ls.get(0)).width(w * 0.18f).height(h * 0.20f).pad(10);
        table.add(ls.get(1)).width(w * 0.18f).height(h * 0.20f).pad(10);
        table.add(ls.get(2)).width(w * 0.18f).height(h * 0.20f).pad(10);
        table.add(ls.get(3)).width(w * 0.18f).height(h * 0.20f).pad(10);
        table.add(ls.get(4)).width(w * 0.18f).height(h * 0.20f).pad(10);
        table.row();
        table.add(ls.get(5)).width(w * 0.18f).height(h * 0.20f).pad(10);
        table.add(ls.get(6)).width(w * 0.18f).height(h * 0.20f).pad(10);
        table.add(ls.get(7)).width(w * 0.18f).height(h * 0.20f).pad(10);
        table.add(ls.get(8)).width(w * 0.18f).height(h * 0.20f).pad(10);
        table.add(ls.get(9)).width(w * 0.18f).height(h * 0.20f).pad(10);
        table.row();
        table.add(ls.get(10)).width(w * 0.18f).height(h * 0.20f).pad(10);
        table.add(ls.get(11)).width(w * 0.18f).height(h * 0.20f).pad(10);
        table.add(ls.get(12)).width(w * 0.18f).height(h * 0.20f).pad(10);
        table.add(ls.get(13)).width(w * 0.18f).height(h * 0.20f).pad(10);
        table.add(ls.get(14)).width(w * 0.18f).height(h * 0.20f).pad(10);
        table.row();
        table.add(ls.get(15)).width(w * 0.18f).height(h * 0.20f).pad(10);
        table.add(ls.get(16)).width(w * 0.18f).height(h * 0.20f).pad(10);
        table.add(ls.get(17)).width(w * 0.18f).height(h * 0.20f).pad(10);
        table.add(ls.get(18)).width(w * 0.18f).height(h * 0.20f).pad(10);
        table.add(ls.get(19)).width(w * 0.18f).height(h * 0.20f).pad(10);
        table.row();
        table.add(back).colspan(5);



        //table.debug();

        table.setFillParent(true);

        stage.addActor(table.align(Align.center));

        map = new TmxMapLoader().load("maps/levelScreen.tmx");
        tmr = new OrthogonalTiledMapRenderer(map);

        for (LevelCell lc :
                ls) {
            final int assign = lc.text;
            lc.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    game.setScreen(new LevelLoading(game,assign));
                }
            });
        }


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
