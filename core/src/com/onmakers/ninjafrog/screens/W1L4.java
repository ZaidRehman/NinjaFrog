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
import com.onmakers.ninjafrog.entities.Enemy;
import com.onmakers.ninjafrog.entities.Key;
import com.onmakers.ninjafrog.entities.Player;
import com.onmakers.ninjafrog.entities.PlayerFoot;
import com.onmakers.ninjafrog.entities.PlayerSword;
import com.onmakers.ninjafrog.handler.PlayerContactListener;
import com.onmakers.ninjafrog.utils.Constants;
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
import static com.onmakers.ninjafrog.utils.Constants.isKillingEnemy;
import static com.onmakers.ninjafrog.utils.Constants.mapPixelHeight;
import static com.onmakers.ninjafrog.utils.Constants.mapPixelWidth;
import static com.onmakers.ninjafrog.utils.UtilityMethods.buttonAttack;
import static com.onmakers.ninjafrog.utils.UtilityMethods.buttonJump;
import static com.onmakers.ninjafrog.utils.UtilityMethods.buttonLeft;
import static com.onmakers.ninjafrog.utils.UtilityMethods.buttonRight;
import static com.onmakers.ninjafrog.utils.UtilityMethods.cameraUpdate;
import static com.onmakers.ninjafrog.utils.UtilityMethods.updateFrogStatus;

public class W1L4 implements Screen {

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
    private PlayerSword frogSwordR;
    private PlayerSword frogSwordL;

    //body of keys and coins
    private ArrayList<Coin> coins;
    private ArrayList<Key> keys;
    private ArrayList<Enemy> flyingOwls;

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
    private TextureAtlas atlasWalkingFrog,atlasStandingFrog, atlasJumpingFrog, atlasAttackingFrog,atlasFallingFrog;
    private Animation<TextureAtlas.AtlasRegion> animStanding ,animWalking, animAttacking , animJumping, animFalling;


    //Game Design
    private Texture topbar;
    private Sprite bottombar;

    public W1L4(NinjaFrog game) {
        this.game = game;
    }

    @Override
    public void show() {
        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();
        Constants.isGrounded = false;

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
        map = game.manager.get("maps/World1Level4.tmx", TiledMap.class);
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
        frogFoot = new PlayerFoot(world,"FROG_FOOT",100,300, FROG_BODY_WIDTH, 10);
        frogSwordR = new PlayerSword(world,"FROG_SWORD_RIGHT",100 + 300,300, FROG_BODY_WIDTH, FROG_BODY_HEIGHT);
        frogSwordL = new PlayerSword(world,"FROG_SWORD_LEFT",100 + 300,300, FROG_BODY_WIDTH, FROG_BODY_HEIGHT);
        frogSwordR.joint = JointBuilder.createRJointDef(world,frog.body, frogSwordR.body, FROG_BODY_WIDTH * 2,0, false);
        frogSwordL.joint = JointBuilder.createRJointDef(world,frog.body, frogSwordL.body, -FROG_BODY_WIDTH * 2,0, false);
        frogFoot.joint = JointBuilder.createRJointDef(world,frog.body,frogFoot.body, 0, -FROG_BODY_HEIGHT -10, false);


        flyingOwls=new ArrayList<Enemy>();
        flyingOwls.add(new Enemy(world, "flyingOwl"+1, mapPixelWidth * 0.1f * 1, 1000, 30, 30));
        flyingOwls.add(new Enemy(world, "flyingOwl"+2, mapPixelWidth * 0.1f * 2, 1200, 30, 30));
        flyingOwls.add(new Enemy(world, "flyingOwl"+3, mapPixelWidth * 0.1f * 3, 900, 30, 30));
        flyingOwls.add(new Enemy(world, "flyingOwl"+4, mapPixelWidth * 0.1f * 4, 1100, 30, 30));
        flyingOwls.add(new Enemy(world, "flyingOwl"+5, mapPixelWidth * 0.1f * 5, 900, 30, 30));
        flyingOwls.add(new Enemy(world, "flyingOwl"+6, mapPixelWidth * 0.08f * 6, 900, 30, 30));
        flyingOwls.add(new Enemy(world, "flyingOwl"+7, mapPixelWidth * 0.1f * 7, 950, 30, 30));
        flyingOwls.add(new Enemy(world, "flyingOwl"+8, mapPixelWidth * 0.1f * 8, 500, 30, 30));
        flyingOwls.add(new Enemy(world, "flyingOwl"+9, mapPixelWidth * 0.1f * 9, 500, 30, 30));
        //flyingOwls.add(new Enemy(world, "flyingOwl"+10, mapPixelWidth * 0.1f * 10, 900, 30, 30));


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

        for (Enemy flyingOwl :
                flyingOwls) {
            flyingOwl.elapsedOwlTime = 0;
            flyingOwl.elapsedDeadCounter = 0;
            flyingOwl.atlasFlyingOwl = game.manager.get("owlAnim/owlFlying/owlFlying.atlas", TextureAtlas.class);
            flyingOwl.animFlyingOwl = new Animation<TextureAtlas.AtlasRegion>(1f / 30f, flyingOwl.atlasFlyingOwl.getRegions());

            flyingOwl.atlasDead1Owl = game.manager.get("owlAnim/owlDead/owlDead1.atlas", TextureAtlas.class);
            flyingOwl.animDead1Owl = new Animation<TextureAtlas.AtlasRegion>(1 / 15f, flyingOwl.atlasDead1Owl.getRegions());
            flyingOwl.atlasDead2Owl = game.manager.get("owlAnim/owlDead/owlDead2.atlas", TextureAtlas.class);
            flyingOwl.animDead2Owl = new Animation<TextureAtlas.AtlasRegion>(1 / 15f, flyingOwl.atlasDead2Owl.getRegions());
            flyingOwl.atlasDead3Owl = game.manager.get("owlAnim/owlDead/owlDead3.atlas", TextureAtlas.class);
            flyingOwl.animDead3Owl = new Animation<TextureAtlas.AtlasRegion>(1 / 15f, flyingOwl.atlasDead3Owl.getRegions());

            flyingOwl.atlasAttack1Owl = game.manager.get("owlAnim/owlAttack/owlAttack1.atlas", TextureAtlas.class);
            flyingOwl.animAttack1Owl = new Animation<TextureAtlas.AtlasRegion>(1 / 30f, flyingOwl.atlasAttack1Owl.getRegions());
            flyingOwl.atlasAttack2Owl = game.manager.get("owlAnim/owlAttack/owlAttack2.atlas", TextureAtlas.class);
            flyingOwl.animAttack2Owl = new Animation<TextureAtlas.AtlasRegion>(1 / 30f, flyingOwl.atlasAttack2Owl.getRegions());
        }


/*        //particles
        pe = new ParticleEffect();
        pe.load(Gdx.files.internal("particles/fire.p"),Gdx.files.internal(""));
        pe.getEmitters().first().setPosition(w / 2, h / 2);
        pe.start();*/

    }

    @Override
    public void render(float delta) {
        update(Gdx.graphics.getDeltaTime());
        //  pe.update(delta);

        //Render
        Gdx.gl.glClearColor(1, 1, 1, 1);
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
        //pe.draw(batch);
        //owl
        for (Enemy flyingOwl :
                flyingOwls) {
            if(flyingOwl.isBodyDestroyed)
                continue;

            if(flyingOwl.isAlive){
                batch.draw(
                        flyingOwl.animFlyingOwl.getKeyFrame(flyingOwl.elapsedOwlTime,true),
                        flyingOwl.body.getPosition().x * PPM - 30 * 2.5f + flyingOwl.animFlyingOwl.getKeyFrame(flyingOwl.elapsedOwlTime,true).getTexture().getWidth() / 6,
                        flyingOwl.body.getPosition().y * PPM - 30 * 1.5f,
                        - flyingOwl.animFlyingOwl.getKeyFrame(flyingOwl.elapsedOwlTime,true).getTexture().getWidth() / 6,
                        flyingOwl.animFlyingOwl.getKeyFrame(flyingOwl.elapsedOwlTime,true).getTexture().getHeight() / 6);
            }else{
                if (flyingOwl.flyingOwlStatus == "dead2") {
                    batch.draw(
                            flyingOwl.animDead2Owl.getKeyFrame(flyingOwl.elapsedDeadCounter,true),
                            flyingOwl.body.getPosition().x * PPM - 30 * 2.5f + flyingOwl.animDead2Owl.getKeyFrame(flyingOwl.elapsedDeadCounter,false).getTexture().getWidth() / 12,
                            flyingOwl.body.getPosition().y * PPM - 30 * 1.5f,
                            - flyingOwl.animDead2Owl.getKeyFrame(flyingOwl.elapsedDeadCounter,false).getTexture().getWidth() / 12,
                            flyingOwl.animDead2Owl.getKeyFrame(flyingOwl.elapsedDeadCounter,false).getTexture().getHeight() / 3);
                }else if(flyingOwl.flyingOwlStatus == "dead1"){
                    batch.draw(
                            flyingOwl.animDead1Owl.getKeyFrame(flyingOwl.elapsedDeadCounter,false),
                            flyingOwl.body.getPosition().x * PPM - 30 * 2.5f + 10+ flyingOwl.animDead1Owl.getKeyFrame(flyingOwl.elapsedDeadCounter,false).getTexture().getWidth() / 7,
                            flyingOwl.body.getPosition().y * PPM - 30 * 1.5f,
                            - flyingOwl.animDead1Owl.getKeyFrame(flyingOwl.elapsedDeadCounter,false).getTexture().getWidth() / 7,
                            flyingOwl.animDead1Owl.getKeyFrame(flyingOwl.elapsedDeadCounter,false).getTexture().getHeight()/ 3);
                }else if(flyingOwl.flyingOwlStatus == "dead3"){
                    batch.draw(
                            flyingOwl.animDead3Owl.getKeyFrame(flyingOwl.elapsedDeadCounter,false),
                            flyingOwl.body.getPosition().x * PPM - 30 * 2.5f + flyingOwl.animDead3Owl.getKeyFrame(flyingOwl.elapsedDeadCounter,false).getTexture().getWidth()/8,
                            flyingOwl.body.getPosition().y * PPM - 30 * 1.5f,
                            - flyingOwl.animDead3Owl.getKeyFrame(flyingOwl.elapsedDeadCounter,false).getTexture().getWidth()/8,
                            flyingOwl.animDead3Owl.getKeyFrame(flyingOwl.elapsedDeadCounter,false).getTexture().getHeight()/1.5f);
                }
            }
        }
        //frog
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

      /*  if(pe.isComplete()){
            pe.reset();
        }*/

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
        checkAttacking(delta);
        updateEnemies(delta);

        tmr.setView(camera);
        batch.setProjectionMatrix(camera.combined);



        //flyingOwl.body.setLinearVelocity(flyingOwl.body.getLinearVelocity().x, delta * 12);
        //flyingOwl.body.applyLinearImpulse(0,1.5f,0,0,true);

        if((mapPixelWidth - 200) <= frog.body.getPosition().x * PPM){
            game.gm.setPrefLevel(4);
            game.setScreen(game.levelLoading);
        }
    }
    public void updateEnemies(float delta){
        for (final Enemy flyingOwl :
                flyingOwls) {
            if(!flyingOwl.isBodyDestroyed){
                if(flyingOwl.body.getLinearVelocity().y <= 0 && flyingOwl.isAlive)
                    flyingOwl.body.applyForceToCenter(0,5.5f * PPM,true);

                flyingOwl.elapsedOwlTime += delta;
                if(!flyingOwl.isAlive){
                    flyingOwl.elapsedDeadCounter += delta;
                    if(flyingOwl.animDead1Owl.isAnimationFinished(flyingOwl.elapsedDeadCounter))
                        flyingOwl.flyingOwlStatus = "dead2";
                    if(flyingOwl.body.getLinearVelocity().y == 0)
                        flyingOwl.flyingOwlStatus = "dead3";
                    if(flyingOwl.flyingOwlStatus == "dead3" && flyingOwl.elapsedDeadCounter >= 1/15f * 34){
                        world.destroyBody(flyingOwl.body);
                        flyingOwl.isBodyDestroyed= true;

                    }
                }
            }


            /*if(flyingOwl.elapsedDeadCounter >= 1/15f * 34){
//                flyingOwl.atlasAttack1Owl.dispose();
//                flyingOwl.atlasAttack2Owl.dispose();
//                flyingOwl.atlasDead1Owl.dispose();
//                flyingOwl.atlasDead2Owl.dispose();
//                flyingOwl.atlasDead3Owl.dispose();
//                flyingOwl.atlasFlyingOwl.dispose();
                //world.destroyBody(flyingOwl.body);
            }*/
        }

    }
    public void checkAttacking(float delta){
        if(frogStatus == "attacking" && isKillingEnemy){
            if(frogDirection){
                for (Enemy flyingOwl :
                        flyingOwls) {
                    if (flyingOwl.isTouchingRSword && flyingOwl.isAlive) {
                        flyingOwl.isAlive = false;
                        flyingOwl.flyingOwlStatus="dead1";

                    }
                }
            }else{
                for (Enemy flyingOwl :
                        flyingOwls) {
                    if (flyingOwl.isTouchingLSword && flyingOwl.isAlive) {
                        flyingOwl.isAlive = false;
                        flyingOwl.flyingOwlStatus = "dead1";
                    }
                }
            }


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
        //pe.dispose();
        for (Enemy flyingOwl : flyingOwls) {
            flyingOwl.dispose();
        }
        game.manager.unload("maps/World1Level4.tmx");
    }



    private boolean canJump() {
        return ((frog.body.getLinearVelocity().y < 2 || frog.body.getLinearVelocity().y > -2) && frogStatus!= "jumping" && frogStatus != "falling");
    }
}
