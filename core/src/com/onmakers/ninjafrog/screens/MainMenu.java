package com.onmakers.ninjafrog.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.onmakers.ninjafrog.NinjaFrog;
import com.onmakers.ninjafrog.tween.ActorAccessor;
import com.onmakers.ninjafrog.tween.SpriteAccessor;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenAccessor;
import aurelienribon.tweenengine.TweenManager;

import static com.onmakers.ninjafrog.utils.Constants.HEIGHT;
import static com.onmakers.ninjafrog.utils.Constants.WIDTH;


public class MainMenu implements Screen{



    private  final NinjaFrog game;

    private  Stage stage;
    private  Skin skin;
    private TweenManager tweenManager;
    private  Table table;
    private Image playImg;
    private TextButton buttonPlay,buttonjSetting, buttonWorld, buttonExit;
    private ShapeRenderer shapeRenderer;
    private BitmapFont showcardFont;
    private Label label;

    int i=1;

    public MainMenu(final  NinjaFrog game) {
        this.game= game;

        this.stage = new Stage(new FitViewport(NinjaFrog.V_WIDTH,NinjaFrog.V_Height, game.camera));
        this.shapeRenderer = new ShapeRenderer();
    }

    public  void update(float delta){
        stage.act(delta);
        i+=10;
    }

    @Override
    public void show() {
        System.out.println("Main Menu");
        Gdx.input.setInputProcessor(stage);
        stage.clear();

        showcardFont = new BitmapFont(Gdx.files.internal("font/showcardFont/showcard.fnt"));

        Texture playTex = new Texture(Gdx.files.internal("images/teddybear0.png"));
        playImg = new Image(playTex);
        playImg.setPosition(stage.getWidth()/2, stage.getHeight()/2);
        playImg.setOrigin(playImg.getWidth()/2,playImg.getHeight()/2);
        stage.addActor(playImg);



        this.skin=new Skin();
        //this.skin.addRegions(game.manager.get("skin/glassy-ui.atlas", TextureAtlas.class));
        game.manager.load("skin/glassy-ui.atlas", TextureAtlas.class);
        game.manager.finishLoading();
        this.skin.addRegions(game.manager.get("skin/glassy-ui.atlas", TextureAtlas.class));

        //  this.skin.load(Gdx.files.internal("skin/glassy-ui.atlas"));
        //this.skin.addRegions(game.manager.load(););
        game.manager.load("skin/glassy-ui.atlas", TextureAtlas.class);
        //this.skin.add("font", game.font24);
        this.skin.add("font", showcardFont);
        this.skin.load(Gdx.files.internal("skin/glassy-ui.json"));

        tweenManager = new TweenManager();
        Tween.registerAccessor(Actor.class, new ActorAccessor());
        Tween.set(playImg,ActorAccessor.ALPHA).target(0).start(tweenManager);
        Tween.to(playImg,ActorAccessor.ALPHA,2).target(1).start(tweenManager);

        initButtons();

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,165,114,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);
        stage.draw();

        tweenManager.update(delta);


        game.batch.begin();
        showcardFont.draw(game.batch, "Main Menu", WIDTH /2 -15, HEIGHT);
        game.batch.end();


        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        //shapeRenderer.setColor(Color.RED);
       // shapeRenderer.rect(WIDTH/2,HEIGHT* 0.75f,WIDTH/2,HEIGHT/4);

        shapeRenderer.setColor(Color.BLUE);
        if(i<WIDTH / 2)
            shapeRenderer.rect(WIDTH - i,HEIGHT* 0.75f,WIDTH/2,HEIGHT/4);
        else
            shapeRenderer.rect(WIDTH/2,HEIGHT* 0.75f,WIDTH/2,HEIGHT/4);

        shapeRenderer.end();
    }

    @Override
    public void resize(int width, int height) {

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
        shapeRenderer.dispose();
        stage.dispose();
        skin.dispose();

    }
    private void  initButtons(){
        buttonPlay = new TextButton("Play", skin, "default");
        //buttonPlay.setText("Play");
        buttonPlay.setPosition(600,400);
        buttonPlay.setSize(280,60);

        buttonExit= new TextButton("Exit", skin, "default");
        buttonExit.setPosition(600,100);
        buttonExit.setSize(240,100);
        buttonExit.addListener(new ClickListener(){
            @Override
            public  void clicked (InputEvent event, float x, float y){
                Gdx.app.exit();
    }
});

        stage.addActor(buttonPlay);
        stage.addActor(buttonExit);
    }
}
