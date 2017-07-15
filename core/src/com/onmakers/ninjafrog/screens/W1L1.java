package com.onmakers.ninjafrog.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.onmakers.ninjafrog.NinjaFrog;
import com.onmakers.ninjafrog.entities.Coin;
import com.onmakers.ninjafrog.entities.Key;
import com.onmakers.ninjafrog.entities.Player;
import com.onmakers.ninjafrog.entities.PlayerFoot;
import com.onmakers.ninjafrog.handler.PlayerContactListener;
import com.onmakers.ninjafrog.utils.JointBuilder;
import com.onmakers.ninjafrog.utils.TiledObjectUtil;
import com.onmakers.ninjafrog.utils.UtilityMethods;

import java.util.ArrayList;

import static com.onmakers.ninjafrog.NinjaFrog.V_Height;
import static com.onmakers.ninjafrog.NinjaFrog.V_WIDTH;
import static com.onmakers.ninjafrog.utils.Constants.FROG_BODY_HEIGHT;
import static com.onmakers.ninjafrog.utils.Constants.FROG_BODY_WIDTH;
import static com.onmakers.ninjafrog.utils.Constants.FROG_HEIGHT;
import static com.onmakers.ninjafrog.utils.Constants.FROG_WIDTH;
import static com.onmakers.ninjafrog.utils.Constants.PPM;
import static com.onmakers.ninjafrog.utils.Constants.elapsedTime;
import static com.onmakers.ninjafrog.utils.Constants.frogDirection;
import static com.onmakers.ninjafrog.utils.Constants.frogStatus;
import static com.onmakers.ninjafrog.utils.Constants.mapPixelHeight;
import static com.onmakers.ninjafrog.utils.Constants.mapPixelWidth;
import static com.onmakers.ninjafrog.utils.UtilityMethods.buttonAttack;
import static com.onmakers.ninjafrog.utils.UtilityMethods.buttonJump;
import static com.onmakers.ninjafrog.utils.UtilityMethods.buttonLeft;
import static com.onmakers.ninjafrog.utils.UtilityMethods.buttonRight;
import static com.onmakers.ninjafrog.utils.UtilityMethods.cameraUpdate;
import static com.onmakers.ninjafrog.utils.UtilityMethods.updateFrogStatus;

public class W1L1 implements Screen {

    private static final float SCALE = 0.8f;
    private OrthographicCamera camera;
    private Viewport viewport;

    private OrthogonalTiledMapRenderer tmr;
    private TiledMap map;

    private Box2DDebugRenderer b2dr;
    private World world;


    //private Body frog;
    private Player frog;
    private PlayerFoot frogFoot;

    //body of keys and coins
    private ArrayList<Coin> coins;
    private ArrayList<Key> keys;

    private float w, h;

    //batch
    private SpriteBatch batch;
    private TextureAtlas.AtlasRegion tex;
    private Texture texCoin;


    //stage
    private Stage stage;
    private Skin skin;
    private Table table;

    private Label heading;

    public NinjaFrog game;

    //Animation
    private TextureAtlas atlasWalkingFrog,atlasStandingFrog, atlasJumpingFrog, atlasAttackingFrog, atlasFallingFrog;
    private Animation<TextureAtlas.AtlasRegion> animStanding ,animWalking, animAttacking , animJumping, animFalling;

    //Game Design
    private Texture topbar;
    private Sprite bottombar;

    public W1L1(NinjaFrog game) {
        this.game = game;
    }

    @Override
    public void show() {
        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, V_WIDTH / SCALE, V_Height / SCALE);
        camera.update();
        viewport = new FillViewport(V_WIDTH / SCALE, V_Height / SCALE, camera);
        //viewport = new ScreenViewport(camera);
        viewport.apply();

        world = new World(new Vector2(0f, -25f), true);
        this.world.setContactListener(new PlayerContactListener());
        b2dr = new Box2DDebugRenderer();

        //tile
        //map = new TmxMapLoader().load("maps/World1Level1.tmx");
        map = game.manager.get("maps/World1Level1.tmx", TiledMap.class);
        tmr = new OrthogonalTiledMapRenderer(map);
        MapProperties prop = map.getProperties();

        //tile map variables
        int mapWidth = prop.get("width", Integer.class);
        int mapHeight = prop.get("height", Integer.class);
        int tilePixelWidth = prop.get("tilewidth", Integer.class);
        int tilePixelHeight = prop.get("tileheight", Integer.class);
        mapPixelWidth = mapWidth * tilePixelWidth;
        mapPixelHeight = mapHeight * tilePixelHeight;

        //batch
        batch = new SpriteBatch();
        //tex = game.manager.get("images/frog.png", Texture.class);

        //Keys
        //texKey = new Texture("images/key.png");
        keys = new ArrayList<Key>();
        keys.add(new Key(world, "KEY0", mapPixelWidth * 0.02f, mapPixelHeight * 0.75f, 16, 16));
        keys.add(new Key(world, "KEY1", 325, 475, 16, 16));
        keys.add(new Key(world, "KEY2", 325, 475, 16, 16));
        keys.add(new Key(world, "KEY3", 325, 475, 16, 16));
        keys.add(new Key(world, "KEY4", 325, 475, 16, 16));

        //Coins
        texCoin = new Texture("images/coin.png");
        coins = new ArrayList<Coin>();
        for (int i = 0, j = 0; i < 10; i++) {
            coins.add(new Coin(world, "COIN" + i, mapPixelWidth * 0.02f + 100 * i, mapPixelHeight * 0.75f + 100, 32, 32));
        }

        frog = new Player(world, "FROG", 100, 360, FROG_BODY_WIDTH, FROG_BODY_HEIGHT);
        frogFoot = new PlayerFoot(world,"FROG_FOOT",100,300, FROG_BODY_WIDTH, 5);
        JointBuilder.createDJointDef(world,frog.body,frogFoot.body, FROG_BODY_HEIGHT);

        TiledObjectUtil.parseTiledObjetLayer(world, map.getLayers().get("collision-layer").getObjects());


        //stage
        stage = new Stage(new FitViewport(w,h));

        //atlas = new TextureAtlas("ui/atlas.pack");
        skin = new Skin();

        //table
        table = new Table(skin);
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        //initialize buttons
        UtilityMethods.initButtons(this);

        //Label Style
        Label.LabelStyle headingStyle = new Label.LabelStyle(game.showcard, Color.WHITE);
        heading = new Label("Ninja Frog ", headingStyle);
        heading.setFontScale(3);

        table.add(heading).colspan(4).center().height(h * 0.03f).expandX();
        table.row().expandY().padBottom(h * 0.05f);
        table.add(buttonLeft).bottom().left().width(w * 0.15f).height(h * 0.2f);
        table.add(buttonRight).bottom().left().width(w * 0.15f).height(h * 0.2f).padRight(w * 0.13f);
        table.add(buttonJump).bottom().right().width(w * 0.15f).height(h * 0.2f).padLeft(w * 0.13f);
        table.add(buttonAttack).bottom().right().width(w * 0.15f).height(h * 0.2f);


        //Game Design
        topbar = game.manager.get("images/topBar.png", Texture.class);
        bottombar = new Sprite(topbar);
        bottombar.flip(false,true);

        //table.debug();// TODO remove later
        table.setFillParent(true);
        //table.debug();
        stage.addActor(table.align(Align.center));

        Gdx.input.setInputProcessor(stage);

        //Animation
        elapsedTime = 0;
        atlasStandingFrog = game.manager.get("frogAnim/standingFrog/standingFrog.atlas", TextureAtlas.class);
        animStanding = new Animation<TextureAtlas.AtlasRegion>(1f/30f,atlasStandingFrog.getRegions());
        atlasWalkingFrog = game.manager.get("frogAnim/walkingFrog/walkingFrog.atlas", TextureAtlas.class);
        animWalking = new Animation<TextureAtlas.AtlasRegion>(1f/30f,atlasWalkingFrog.getRegions());
        atlasAttackingFrog = game.manager.get("frogAnim/attackingFrog/attackingFrog.atlas", TextureAtlas.class);
        animAttacking = new Animation<TextureAtlas.AtlasRegion>(1f/30f,atlasAttackingFrog.getRegions());
        atlasJumpingFrog = game.manager.get("frogAnim/jumpingFrog/JumpingFrog.atlas", TextureAtlas.class);
        animJumping = new Animation<TextureAtlas.AtlasRegion>(1f/30f,atlasJumpingFrog.getRegions());
        atlasFallingFrog = game.manager.get("frogAnim/fallingFrog/fallingFrog.atlas", TextureAtlas.class);
        animFalling = new Animation<TextureAtlas.AtlasRegion>(1f/15f, atlasFallingFrog.getRegions());

    }

    @Override
    public void render(float delta) {
        update(Gdx.graphics.getDeltaTime());

        //Render
        Gdx.gl.glClearColor(184f, 134f, 11f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        tmr.render();

        float x,y,width,height;
        height = FROG_HEIGHT;
        y = frog.body.getPosition().y * PPM - FROG_BODY_HEIGHT - 10;
        if(frogDirection){
            x = (frog.body.getPosition().x * PPM - FROG_BODY_WIDTH * 2.5f) + (FROG_WIDTH );
            width = -FROG_WIDTH;
        }else{
            x = (frog.body.getPosition().x * PPM - FROG_BODY_WIDTH * 1.5f);
            width = FROG_WIDTH;
        }

        if(frogStatus == "standing"){
           tex = animStanding.getKeyFrame(elapsedTime,true);
        }else if(frogStatus == "walking") {
            tex = animWalking.getKeyFrame(elapsedTime,true);
            y -= 20;
        }else if(frogStatus == "attacking"){
            if(frogDirection){
                x += 100;
                width -= 100;
            }else{
                x -= 100;
                width += 100;
            }
            tex = animAttacking.getKeyFrame(elapsedTime,true);
        }else if(frogStatus == "jumping"){
            tex = animJumping.getKeyFrame(elapsedTime,false);
            x += 10;
            height += 30;
            if(frogDirection){
                width -= 10;
            }else{
                width += 10;
            }
        }else if(frogStatus == "falling"){
            //System.out.println("falling animation");
            x += 10;
            height += 30;
            if(frogDirection){
                width -= 10;
            }else{
                width += 10;
            }
            tex = animFalling.getKeyFrame(elapsedTime,false);
        }

        batch.begin();
        batch.draw(tex,x,y,width,height);
        for (Coin coin :
                coins) {
            if (!coin.isTouched())
                batch.draw(texCoin, coin.body.getPosition().x * PPM - 68, coin.body.getPosition().y * PPM - 68, 128, 128);
        }
        batch.end();

        stage.getBatch().begin();
        stage.getBatch().draw(topbar,-10,h * 0.91f,w + 10f,h * 0.1f );
        stage.getBatch().draw(bottombar,-10,-10,w + 10f,h * 0.1f);
        stage.getBatch().end();

        //b2dr.render(world, camera.combined.scl(PPM));

        //draw stage
        stage.act();
        stage.draw();

    }

    public void update(float delta) {

        //increments
        world.step(1 / 60f, 6, 2);

        //updates
        elapsedTime += delta;
        game.manager.update();
        cameraUpdate(delta,frog,camera);
        UtilityMethods.inputUpdate(frog);
        updateFrogStatus(delta,frog);

        tmr.setView(camera);
        batch.setProjectionMatrix(camera.combined);

        if((mapPixelWidth - 200) <= frog.body.getPosition().x * PPM){
            game.gm.setPrefLevel(0);
            game.setScreen(game.levelLoading);
        }
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
        b2dr.dispose();
        world.dispose();
        batch.dispose();
        tex.getTexture().dispose();
        tmr.dispose();
        map.dispose();
        texCoin.dispose();
        stage.dispose();
        skin.dispose();
        atlasStandingFrog.dispose();
        atlasWalkingFrog.dispose();
        atlasAttackingFrog.dispose();
        atlasJumpingFrog.dispose();
        topbar.dispose();
        game.manager.unload("maps/World1Level1.tmx");
    }



    private boolean canJump() {
        return ((frog.body.getLinearVelocity().y < 2 || frog.body.getLinearVelocity().y > -2) && frogStatus!= "jumping" && frogStatus != "falling");
    }
}
