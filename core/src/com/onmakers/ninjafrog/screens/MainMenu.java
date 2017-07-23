package com.onmakers.ninjafrog.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.onmakers.ninjafrog.NinjaFrog;
import com.onmakers.ninjafrog.tween.ActorAccessor;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

import static com.onmakers.ninjafrog.utils.Constants.HEIGHT;
import static com.onmakers.ninjafrog.utils.Constants.WIDTH;


public class MainMenu implements Screen{



    private  final NinjaFrog game;

    private  Stage stage;
    private  Skin skin;
    private TweenManager tweenManager;
    private Image playImg;
    private Image settingsImg;
    private Image levelsImg;
    private Image profileImg;
    private Image scoreImg;
    private Sprite tsBgImg;
    //private BitmapFont showcardFont;
    private TextureAtlas titleScreenAtlas;
    private Batch batch;
    OrthographicCamera camera;
    Viewport viewport;

    public MainMenu(final  NinjaFrog game) {
        this.game= game;

        this.stage = new Stage(new FitViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()));
    }

    public  void update(float delta){
        stage.act(delta);
    }

    @Override
    public void show() {

        Gdx.input.setInputProcessor(stage);
        stage.clear();

        camera = new OrthographicCamera();
        viewport = new FillViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(),camera);
        viewport.apply();

        camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);

        //showcardFont = new BitmapFont(Gdx.files.internal("font/showcardFont/showcard.ttf"));

        titleScreenAtlas = new TextureAtlas(Gdx.files.internal("titleScreen/titleScreen.atlas"));

        tsBgImg = new Sprite(new Texture("images/titleScreenPainting.png"));
        batch = new SpriteBatch();

        playImg = new Image(titleScreenAtlas.findRegion("PLAY button"));
        profileImg = new Image(titleScreenAtlas.findRegion("PROFILE button"));
        levelsImg = new Image(titleScreenAtlas.findRegion("LEVELS button"));
        scoreImg = new Image(titleScreenAtlas.findRegion("SCORE button"));
        settingsImg = new Image(titleScreenAtlas.findRegion("settings button 2"));

        playImg.setPosition(stage.getWidth() / 4, stage.getHeight() - 200);
        playImg.setOrigin(playImg.getWidth()/4,playImg.getHeight()/2);
        playImg.setScale(1.5f);
        stage.addActor(playImg);

        profileImg.setPosition(stage.getWidth()/4 , stage.getHeight() - 400) ;
        profileImg.setOrigin(profileImg.getWidth()/4,profileImg.getHeight()/2);
        stage.addActor(profileImg);

        levelsImg.setPosition(stage.getWidth()/4 , stage.getHeight() - 600);
        levelsImg.setOrigin(levelsImg.getWidth()/4,levelsImg.getHeight()/2);
        stage.addActor(levelsImg);

        scoreImg.setPosition(stage.getWidth()/4 , stage.getHeight() - 800);
        scoreImg.setOrigin(scoreImg.getWidth()/4,scoreImg.getHeight()/2);
        stage.addActor(scoreImg);

        settingsImg.setPosition(stage.getWidth() - 400, stage.getHeight() - 400);
        settingsImg.setOrigin(settingsImg.getWidth() - 400,settingsImg.getHeight() - 400);
        stage.addActor(settingsImg);

        this.skin=new Skin();
        game.manager.load("skin/glassy-ui.atlas", TextureAtlas.class);
        game.manager.finishLoading();
        this.skin.addRegions(game.manager.get("skin/glassy-ui.atlas", TextureAtlas.class));

        game.manager.load("skin/glassy-ui.atlas", TextureAtlas.class);
        //this.skin.add("font", showcardFont);
        this.skin.load(Gdx.files.internal("skin/glassy-ui.json"));

        tweenManager = new TweenManager();
        Tween.registerAccessor(Actor.class, new ActorAccessor());
        Tween.set(playImg,ActorAccessor.ALPHA).target(0).start(tweenManager);
        Tween.to(playImg,ActorAccessor.ALPHA,2).target(1).start(tweenManager);

        Tween.set(profileImg,ActorAccessor.ALPHA).target(0).start(tweenManager);
        Tween.to(profileImg,ActorAccessor.ALPHA,2).target(1).start(tweenManager);

        Tween.set(levelsImg,ActorAccessor.ALPHA).target(0).start(tweenManager);
        Tween.to(levelsImg,ActorAccessor.ALPHA,2).target(1).start(tweenManager);

        Tween.set(scoreImg,ActorAccessor.ALPHA).target(0).start(tweenManager);
        Tween.to(scoreImg,ActorAccessor.ALPHA,2).target(1).start(tweenManager);

        initButtons();

    }

    @Override
    public void render(float delta) {

        camera.update();
        update(delta);
        Gdx.gl.glClearColor(0,165,114,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        tsBgImg.draw(batch);
        batch.end();

        /*stage.getBatch().begin();
        stage.getBatch().draw(tsBgImg,0,0, Gdx.graphics.getWidth(), game.camera.viewportHeight);
        stage.getBatch().end();*/
        stage.draw();

        tweenManager.update(delta);

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
        camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
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

    }
    private void  initButtons(){

        settingsImg.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new Settings(game));
            }
        });

        playImg.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.levelLoading);
            }
        });

        scoreImg.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        });

        levelsImg.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        });

        profileImg.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        });
    }
}
