package com.onmakers.ninjafrog.screens;


// New Version (V.1)

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.onmakers.ninjafrog.NinjaFrog;
import com.onmakers.ninjafrog.actors.CoinCell;
import com.onmakers.ninjafrog.actors.FrogLivesCell;
import com.onmakers.ninjafrog.entities.Coin;
import com.onmakers.ninjafrog.entities.Enemy;
import com.onmakers.ninjafrog.entities.Player;
import com.onmakers.ninjafrog.entities.PlayerFoot;
import com.onmakers.ninjafrog.entities.PlayerSword;
import com.onmakers.ninjafrog.handler.PlayerContactListener;
import com.onmakers.ninjafrog.utils.Constants;
import com.onmakers.ninjafrog.utils.JointBuilder;
import com.onmakers.ninjafrog.utils.TiledObjectUtil;
import com.onmakers.ninjafrog.utils.UtilityMethods;

import java.util.ArrayList;
import java.util.List;

import static com.onmakers.ninjafrog.NinjaFrog.V_Height;
import static com.onmakers.ninjafrog.NinjaFrog.V_WIDTH;
import static com.onmakers.ninjafrog.utils.Constants.FROG_BODY_HEIGHT;
import static com.onmakers.ninjafrog.utils.Constants.FROG_BODY_WIDTH;
import static com.onmakers.ninjafrog.utils.Constants.FROG_HEIGHT;
import static com.onmakers.ninjafrog.utils.Constants.FROG_WIDTH;
import static com.onmakers.ninjafrog.utils.Constants.PPM;
import static com.onmakers.ninjafrog.utils.Constants.coinCounter;
import static com.onmakers.ninjafrog.utils.Constants.coins;
import static com.onmakers.ninjafrog.utils.Constants.deadFrogCounter;
import static com.onmakers.ninjafrog.utils.Constants.elapsedTime;
import static com.onmakers.ninjafrog.utils.Constants.flyingOwls;
import static com.onmakers.ninjafrog.utils.Constants.frogDirection;
import static com.onmakers.ninjafrog.utils.Constants.frogStatus;
import static com.onmakers.ninjafrog.utils.Constants.isDead;
import static com.onmakers.ninjafrog.utils.Constants.isKillingEnemy;
import static com.onmakers.ninjafrog.utils.Constants.mapPixelHeight;
import static com.onmakers.ninjafrog.utils.Constants.mapPixelWidth;
import static com.onmakers.ninjafrog.utils.UtilityMethods.buttonAttack;
import static com.onmakers.ninjafrog.utils.UtilityMethods.buttonJump;
import static com.onmakers.ninjafrog.utils.UtilityMethods.buttonLeft;
import static com.onmakers.ninjafrog.utils.UtilityMethods.buttonRight;
import static com.onmakers.ninjafrog.utils.UtilityMethods.cameraUpdate;
import static com.onmakers.ninjafrog.utils.UtilityMethods.updateFrogStatus;

public class Level implements Screen {

    private static final float SCALE = 0.8f;
    public static Animation<TextureAtlas.AtlasRegion> animFalling;
    //Sound
    public static Sound frogDead, sword, frogJump, frogHurt;
    private static Animation<TextureAtlas.AtlasRegion> animStanding, animWalking, animAttacking, animJumping, animDeadFrog;
    public final NinjaFrog game;
    public boolean deadPlayed = false;
    float allowChangeScreen;
    float dc;
    private OrthographicCamera camera;
    private Viewport viewport;
    private OrthogonalTiledMapRenderer tmr;
    private TiledMap map;
    private Box2DDebugRenderer b2dr;
    private World world;
    //private Body frog;
    private Player frog;
    //batch
    private SpriteBatch batch;
    private TextureAtlas.AtlasRegion tex;
    //stage
    private Stage stage;
    private Skin skin;
    private Table table;
    private int levelScreenNo = 0;
    private boolean startedWithLevelScreen = false;
    //Animation
    private TextureAtlas atlasWalkingFrog,atlasStandingFrog, atlasJumpingFrog, atlasAttackingFrog,atlasFallingFrog,atlasDeadFrog;
    //Game Design
    private TextureAtlas gs;
    //Particle effect
    private List<ParticleEffect> peList;

    public Level(NinjaFrog game, int level) {
        this.game = game;
        levelScreenNo = level;
        if(level != -1){
            startedWithLevelScreen = true;
        }
        //Sounds
        frogDead = game.manager.get("sounds/dead.wav",Sound.class);
        frogHurt = game.manager.get("sounds/hurt.wav",Sound.class);
        frogJump = game.manager.get("sounds/jump.wav",Sound.class);
        sword = game.manager.get("sounds/sword.wav",Sound.class);
        //owlDead = game.manager.get("sounds/owldead.wav",Sound.class);

    }

    @Override
    public void show() {

        PlayerFoot frogFoot;
        PlayerSword frogSwordR;
        PlayerSword frogSwordL;

        float w, h;

        //initialize
        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();
        Constants.isGrounded = false;
        isDead = false;
        dc = 0;
        coinCounter = 0;
        deadFrogCounter = 10;
        peList = new ArrayList<ParticleEffect>();    //particles

        camera = new OrthographicCamera();
        camera.setToOrtho(false, V_WIDTH / SCALE, V_Height / SCALE);
        camera.update();
        viewport = new FillViewport(V_WIDTH / SCALE, V_Height / SCALE, camera);
        viewport.apply();

        //Box2d
        world = new World(new Vector2(0f, -40f), true);
        this.world.setContactListener(new PlayerContactListener());
        b2dr = new Box2DDebugRenderer();

        //tile
        int assign;
        if(levelScreenNo == -1){
            assign = game.gm.getLevel();
        }else{
            assign = levelScreenNo;
        }
        switch (assign){

            case 0:
                map = game.manager.get("maps/level1.tmx", TiledMap.class);
                break;
            case 1:
                map = game.manager.get("maps/level2.tmx", TiledMap.class);
                break;
            case 2:
                map = game.manager.get("maps/level3.tmx", TiledMap.class);
                break;
            case 3:
                map = game.manager.get("maps/level4.tmx", TiledMap.class);
                break;
            case 4:
                map = game.manager.get("maps/level5.tmx", TiledMap.class);
                break;
            case 5:
                map = game.manager.get("maps/level6.tmx", TiledMap.class);
                break;
            case 6:
                map = game.manager.get("maps/level7.tmx", TiledMap.class);
                break;
            case 7:
                map = game.manager.get("maps/level8.tmx", TiledMap.class);
                break;
            case 8:
                map = game.manager.get("maps/level9.tmx", TiledMap.class);
                break;
            case 9:
                map = game.manager.get("maps/level10.tmx", TiledMap.class);
                break;
            case 10:
                map = game.manager.get("maps/level11.tmx", TiledMap.class);
                break;
            case 11:
                map = game.manager.get("maps/level12.tmx", TiledMap.class);
                break;
            case 12:
                map = game.manager.get("maps/level13.tmx", TiledMap.class);
                break;
            case 13:
                map = game.manager.get("maps/level14.tmx", TiledMap.class);
                break;
            case 14:
                map = game.manager.get("maps/level15.tmx", TiledMap.class);
                break;
            case 15:
                map = game.manager.get("maps/level16.tmx", TiledMap.class);
                break;
            case 16:
                map = game.manager.get("maps/level17.tmx", TiledMap.class);
                break;
            case 17:
                map = game.manager.get("maps/level18.tmx", TiledMap.class);
                break;
            case 18:
                map = game.manager.get("maps/level19.tmx", TiledMap.class);
                break;
            case 19:
                map = game.manager.get("maps/level20.tmx", TiledMap.class);
                break;

            default:
                game.gm.setPrefLevel(0);
                map = game.manager.get("maps/level1.tmx", TiledMap.class);
                break;

        }
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

        frog = new Player(world, "FROG", 100, 360, FROG_BODY_WIDTH, FROG_BODY_HEIGHT);
        frogFoot = new PlayerFoot(world,"FROG_FOOT",100,300, FROG_BODY_WIDTH, 10);
        frogSwordR = new PlayerSword(world,"FROG_SWORD_RIGHT",100 + 300,300, FROG_BODY_WIDTH + 100, FROG_BODY_HEIGHT);
        frogSwordL = new PlayerSword(world,"FROG_SWORD_LEFT",100 + 300,300, FROG_BODY_WIDTH + 100, FROG_BODY_HEIGHT);
        frogSwordR.joint = JointBuilder.createRJointDef(world,frog.body, frogSwordR.body, FROG_BODY_WIDTH * 2,0, false);
        frogSwordL.joint = JointBuilder.createRJointDef(world,frog.body, frogSwordL.body, -FROG_BODY_WIDTH * 2,0, false);
        frogFoot.joint = JointBuilder.createRJointDef(world,frog.body,frogFoot.body, 0, -FROG_BODY_HEIGHT -10, false);

        coins.clear();
        flyingOwls.clear();
        TiledObjectUtil.parseTiledObjectLayer(game,world, map.getLayers().get("collision-layer").getObjects());


        //stage
        stage = new Stage(new FitViewport(w,h));
        gs = game.manager.get("images/gs/gs.atlas", TextureAtlas.class);

        //atlas = new TextureAtlas("ui/atlas.pack");
        skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));

        //table
        table = new Table(skin);
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        //initialize buttons
        UtilityMethods.initButtons(this,gs,w,h);

//        //Label Style
//        Label.LabelStyle headingStyle = new Label.LabelStyle(game.showcard, Color.WHITE);
//        coinLabel = new Label((coinCounter + ""), headingStyle);

        Drawable backButtonUp = new TextureRegionDrawable(new TextureRegion(new Texture("images/button/left.up.png")));
        Drawable backButtonDown = new TextureRegionDrawable(new TextureRegion(new Texture("images/button/left.down.png")));

        ImageButton back = new ImageButton(backButtonUp,backButtonDown);
        back.addListener(new ChangeListener() {
            public void changed (ChangeListener.ChangeEvent event, Actor actor) {
                game.setScreen(game.mainMenu);
            }
        });

        Image lives = new Image(gs.findRegion("lives"));
        Image coins = new Image(gs.findRegion("coin"));

        Image empty = new Image();
        table.add(lives).height(h * 0.15f).width(h * 0.15f);
        table.add(new FrogLivesCell(gs.findRegion("icon empty"),deadFrogCounter+"",game.showcard)).height(h * 0.15f).width(h * 0.15f);
        table.add(coins).height(h * 0.15f).width(h * 0.15f);
        table.add(new CoinCell(gs.findRegion("icon empty"),coinCounter+"",game.showcard)).height(h * 0.15f).width(h * 0.15f).center();
        table.add(back.align(Align.right)).height(h * 0.15f).width(h * 0.15f).expandX().right();


        Table bottomTable = new Table();
        bottomTable.add(empty).expandY().height(h * 0.55f);
        bottomTable.row();
        bottomTable.add(buttonJump).colspan(2).height(h * 0.15f).expandY();
        bottomTable.row();
        bottomTable.add(buttonLeft).height(h * .15f).left().width(h * 0.25f).left();
        bottomTable.add(buttonRight).height(h * .15f).left().width(h * 0.25f).left();
        bottomTable.add(empty).expandX().width(w - (h * 0.75f));
        bottomTable.add(buttonAttack).height(h * .15f).right().width(h * 0.25f).right();

        //bottomTable.debug();

        table.row();
        table.add(bottomTable).colspan(5).expandY().expandX().left();
//
//        table.row();
//        table.add(buttonLeft).bottom().left().height(h * 0.2f).padBottom(-80);
//        table.add(buttonRight).bottom().left().height(h * 0.2f).padBottom(-80);
//        table.add(empty);
//        table.add(empty);
//        table.add(buttonAttack).bottom().right().height(h * 0.2f).expandX();

        System.out.println(buttonLeft.getX());
        System.out.println(buttonLeft.getImage().getX());

        System.out.println(buttonRight.getX());
        System.out.println(buttonRight.getImage().getX());

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
        atlasDeadFrog = game.manager.get("frogAnim/deadFrog/deadFrog.atlas",TextureAtlas.class);
        animDeadFrog = new Animation<TextureAtlas.AtlasRegion>(1/15f, atlasDeadFrog.getRegions());

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




    }


    @Override
    public void render(float delta) {
        update(Gdx.graphics.getDeltaTime());
        //for (ParticleEffect pe:
        //     peList) {
        //    if(pe != null)
        //    pe.update(delta);
        //}

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

        if(isDead){
            tex = animDeadFrog.getKeyFrame(dc,false);
            if(frogDirection){
                x += 40;
                width -= 40;
            }else{
                x -= 40;
                width += 40;
            }
        }else if(frogStatus == "standing"){
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

            height += 30;
            y -= 90;
            if(frogDirection){
                x += 30;
                width -= 40;
            }else{
                x -= 30;
                width += 40;
            }
        }else if(frogStatus == "falling"){
            //System.out.println("falling animation");

            y-=90;
            height += 30;
            if(frogDirection){
                x += 30;
                width -= 40;
            }else{
                x -= 30;
                width += 40;
            }
            tex = animFalling.getKeyFrame(elapsedTime,false);
        }




        batch.begin();

        //owl
        for (Enemy flyingOwl :
                flyingOwls) {
            if(flyingOwl.isBodyDestroyed)
                continue;


            float owlX=0,owlY=0,owlWidth=0,owlHeight=0;
            TextureAtlas.AtlasRegion owlTex = gs.findRegion("splash icon");

            if(flyingOwl.isAlive){
                if(flyingOwl.flyingOwlStatus.equals("attacking")) {
                    //owlTex = flyingOwl.animAttack1Owl.getKeyFrame(flyingOwl.elapsedDeadCounter, false);
                    owlTex = flyingOwl.atlasAttack1Owl.findRegion("Owl enemy attack 10015");
                    owlX = flyingOwl.body.getPosition().x * PPM - 30 * 2.5f + flyingOwl.animAttack1Owl.getKeyFrame(flyingOwl.elapsedDeadCounter, false).getTexture().getWidth() / 7;
                    owlY = flyingOwl.body.getPosition().y * PPM - 30 * 1.5f;
                    owlWidth = -flyingOwl.animAttack1Owl.getKeyFrame(flyingOwl.elapsedDeadCounter, false).getTexture().getWidth() / 7;
                    owlHeight = flyingOwl.animAttack1Owl.getKeyFrame(flyingOwl.elapsedDeadCounter, false).getTexture().getHeight() / 3f;
                }else{
                    owlTex = flyingOwl.animFlyingOwl.getKeyFrame(flyingOwl.elapsedOwlTime,true);
                    owlX = flyingOwl.body.getPosition().x * PPM - 30 * 2.5f + flyingOwl.animFlyingOwl.getKeyFrame(flyingOwl.elapsedOwlTime,true).getTexture().getWidth() / 6;
                    owlY = flyingOwl.body.getPosition().y * PPM - 30 * 1.5f;
                    owlWidth = - flyingOwl.animFlyingOwl.getKeyFrame(flyingOwl.elapsedOwlTime,true).getTexture().getWidth() / 6;
                    owlHeight = flyingOwl.animFlyingOwl.getKeyFrame(flyingOwl.elapsedOwlTime,true).getTexture().getHeight() / 6;
                }

            }else{
                if (flyingOwl.flyingOwlStatus == "dead2") {

                    owlTex = flyingOwl.animDead2Owl.getKeyFrame(flyingOwl.elapsedDeadCounter,true);
                    owlX = flyingOwl.body.getPosition().x * PPM - 30 * 2.5f + flyingOwl.animDead2Owl.getKeyFrame(flyingOwl.elapsedDeadCounter,false).getTexture().getWidth() / 12;
                    owlY = flyingOwl.body.getPosition().y * PPM - 30 * 1.5f;
                    owlWidth = - flyingOwl.animDead2Owl.getKeyFrame(flyingOwl.elapsedDeadCounter,false).getTexture().getWidth() / 12;
                    owlHeight = flyingOwl.animDead2Owl.getKeyFrame(flyingOwl.elapsedDeadCounter,false).getTexture().getHeight() / 3;

                }else if(flyingOwl.flyingOwlStatus == "dead1"){

                    owlTex = flyingOwl.animDead1Owl.getKeyFrame(flyingOwl.elapsedDeadCounter,false);
                    owlX = flyingOwl.body.getPosition().x * PPM - 30 * 2.5f + 10+ flyingOwl.animDead1Owl.getKeyFrame(flyingOwl.elapsedDeadCounter,false).getTexture().getWidth() / 7;
                    owlY = flyingOwl.body.getPosition().y * PPM - 30 * 1.5f;
                    owlWidth = - flyingOwl.animDead1Owl.getKeyFrame(flyingOwl.elapsedDeadCounter,false).getTexture().getWidth() / 7;
                    owlHeight = flyingOwl.animDead1Owl.getKeyFrame(flyingOwl.elapsedDeadCounter,false).getTexture().getHeight()/ 3;

                }else if(flyingOwl.flyingOwlStatus == "dead3"){

                    owlTex = flyingOwl.animDead3Owl.getKeyFrame(flyingOwl.elapsedDeadCounter,false);
                    owlX = flyingOwl.body.getPosition().x * PPM - 30 * 2.5f + flyingOwl.animDead3Owl.getKeyFrame(flyingOwl.elapsedDeadCounter,false).getTexture().getWidth()/8;
                    owlY = flyingOwl.body.getPosition().y * PPM - 30 * 1.5f;
                    owlWidth = - flyingOwl.animDead3Owl.getKeyFrame(flyingOwl.elapsedDeadCounter,false).getTexture().getWidth()/8;
                    owlHeight = flyingOwl.animDead3Owl.getKeyFrame(flyingOwl.elapsedDeadCounter,false).getTexture().getHeight()/1.5f;

                }

            }
            if(flyingOwl.direction == 1){
                owlX = owlX + owlWidth;
                owlWidth = -owlWidth;
            }
            batch.draw(owlTex,owlX,owlY,owlWidth,owlHeight);

            //if(flyingOwl.isStartedPE){
             //   renderPE(pe,batch);
            //}

        }

        for (ParticleEffect pe :
                peList) {
            pe.draw(batch,delta);
        }

        //render frog
        batch.draw(tex,x,y,width,height);

        //render coin
        for (Coin coin :
                coins) {
            if (!coin.isTouched())
                batch.draw(gs.findRegion("coin"), coin.body.getPosition().x * PPM - 68, coin.body.getPosition().y * PPM - 68, 128, 128);
        }


        //particle
        //pe.draw(batch);

        batch.end();


        //b2dr.render(world, camera.combined.scl(PPM));

        //draw stage
        stage.act();
        stage.draw();

        //if(pe.isComplete()){
        //    pe.reset();
        //}

    }

    public void update(float delta) {

        //increments
        world.step(1 / 60f, 6, 2);

        //updates
        elapsedTime += delta;
        game.manager.update();
        if (isDead)
            dc+= delta;
        CoinCell.setText(coinCounter + "");
        FrogLivesCell.setText(deadFrogCounter + "");

        cameraUpdate(delta,frog,camera);
        UtilityMethods.inputUpdate(frog);
        updateFrogStatus(delta,frog);
        checkAttacking(delta);
        checkIsFrogAlive();
        updateEnemies(delta);

        tmr.setView(camera);
        batch.setProjectionMatrix(camera.combined);



        //flyingOwl.body.setLinearVelocity(flyingOwl.body.getLinearVelocity().x, delta * 12);
        //flyingOwl.body.applyLinearImpulse(0,1.5f,0,0,true);

        //if(animDeadFrog.isAnimationFinished(elapsedTime) && isDead && isGrounded && frogStatus=="dead"){
        if(isDead)
            allowChangeScreen += delta;
        if(deadFrogCounter <= 0 && allowChangeScreen >= 3){
            if(levelScreenNo == -1){
                game.setScreen(new LevelLoading(game,-1));
            }else{
                game.setScreen(new LevelLoading(game,levelScreenNo));
            }

        }
        if((mapPixelWidth - 200) <= frog.body.getPosition().x * PPM){
            if(game.gm.getLevel() == 19){
                game.gm.setPrefLevel(0);
            }else{
                game.gm.setPrefLevel(game.gm.getLevel() + 1);
//                if(startedWithLevelScreen){
//                    game.gm.setPrefLevel(game.gm.getLevel());
//                }else{
//                    game.gm.setPrefLevel(game.gm.getLevel() + 1);
//                }
            }
            game.setScreen(game.levelLoading);
        }
    }
    public void checkIsFrogAlive(){
        if(deadFrogCounter <= 0){
            isDead = true;
            if(!deadPlayed){
                frogDead.play(0.5f);
                deadPlayed = true;
            }
        }
    }
    public void updateEnemies(float delta){
        for (final Enemy flyingOwl :
                flyingOwls) {
            if(!flyingOwl.isBodyDestroyed){

                flyingOwl.elapsedOwlTime += delta;

                if(!flyingOwl.isAlive){
                    flyingOwl.elapsedDeadCounter += delta;

                    if(flyingOwl.flyingOwlStatus == "dead3" && flyingOwl.elapsedDeadCounter >= 1/15f * 25){
                        world.destroyBody(flyingOwl.body);
                        flyingOwl.isBodyDestroyed= true;
                        //owlDead.play();

                    }

                    if(flyingOwl.body.getLinearVelocity().y == 0 && flyingOwl.flyingOwlStatus == "dead2"){
                        flyingOwl.flyingOwlStatus = "dead3";

                    }

                    if(flyingOwl.animDead1Owl.isAnimationFinished(flyingOwl.elapsedDeadCounter) && flyingOwl.flyingOwlStatus == "dead1")
                        flyingOwl.flyingOwlStatus = "dead2";


                }else if(flyingOwl.body.getLinearVelocity().y <= 0 && flyingOwl.isAlive){

                    //Owl direction
                    if(flyingOwl.body.getPosition().x < frog.body.getPosition().x){
                        flyingOwl.direction = 1;
                    }else{
                        flyingOwl.direction = -1;
                    }

                    //Forces applied to owl
                    if (!frogInRangeOfOwl(flyingOwl)) {
                        flyingOwl.body.applyForceToCenter(0,9f * PPM,true);
                    }else{
                        flyingOwl.flyingOwlStatus = "attacking";
                        flyingOwl.body.applyForceToCenter(2 * PPM * flyingOwl.direction,3f * PPM,true);
                    }

                    //Enemy attacking frog
                    if(flyingOwl.isCollidingFrog){
                        deadFrogCounter--;
                        world.destroyBody(flyingOwl.body);
                        flyingOwl.isAlive = false;
                        flyingOwl.isBodyDestroyed= true;
                        ParticleEffect pe = new ParticleEffect();
                        pe.load(Gdx.files.internal("particles/fire2.p"),Gdx.files.internal(""));
                        pe.getEmitters().first().setPosition(flyingOwl.body.getPosition().x * PPM,flyingOwl.body.getPosition().y * PPM);
                        pe.start();
                        peList.add(pe);
                        frogHurt.play();
                    }

                }
            }
        }

    }

    public boolean frogInRangeOfOwl(Enemy flyingOwl){
        float posFX = frog.body.getPosition().x * PPM;
        float posOX = flyingOwl.body.getPosition().x * PPM;
        float posFY = frog.body.getPosition().y* PPM;
        float posOY = flyingOwl.body.getPosition().y * PPM;

        if(posFY < posOY + 300 && posFY > posOY - 300){
            if(posFX < (posOX + 500) && posFX > (posOX - 500) ){
                return  true;
            }
        }
        return false;
    }
    public void checkAttacking(float delta){
        if(frogStatus == "attacking" && isKillingEnemy){
            if(frogDirection){
                for (Enemy flyingOwl :
                        flyingOwls) {
                    if (flyingOwl.isTouchingRSword && flyingOwl.isAlive) {
                        flyingOwl.isAlive = false;
                        flyingOwl.flyingOwlStatus="dead1";
                        game.gm.setPrefTotalEnemyKills(game.gm.getTotalEnemyKills() + 1);

                    }
                }
            }else{
                for (Enemy flyingOwl :
                        flyingOwls) {
                    if (flyingOwl.isTouchingLSword && flyingOwl.isAlive) {
                        flyingOwl.isAlive = false;
                        flyingOwl.flyingOwlStatus = "dead1";
                        game.gm.setPrefTotalEnemyKills(game.gm.getTotalEnemyKills() + 1);
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
        stage.dispose();
        skin.dispose();
        atlasStandingFrog.dispose();
        atlasWalkingFrog.dispose();
        atlasAttackingFrog.dispose();
        atlasJumpingFrog.dispose();
        atlasDeadFrog.dispose();
        gs.dispose();
        atlasFallingFrog.dispose();

        //sound
        frogHurt.dispose();
        sword.dispose();
        frogDead.dispose();
        frogJump.dispose();


        for (ParticleEffect pe :
                peList) {
            pe.dispose();
        }
        for (Enemy flyingOwl : flyingOwls) {
            flyingOwl.dispose();
        }
        int assign;
        if(levelScreenNo == -1){
            assign = game.gm.getLevel();
        }else{
            assign = levelScreenNo;
        }
        switch (assign){

            case 0:
               game.manager.unload("maps/level1.tmx");
                break;
            case 1:
               game.manager.unload("maps/level2.tmx");
                break;
            case 2:
                game.manager.unload("maps/level3.tmx");
                break;
            case 3:
                game.manager.unload("maps/level4.tmx");
                break;
            case 4:
                game.manager.unload("maps/level5.tmx");
                break;
            case 5:
                game.manager.unload("maps/level6.tmx");
                break;
            case 6:
                game.manager.unload("maps/level7.tmx");
                break;
            case 7:
                game.manager.unload("maps/level8.tmx");
                break;
            case 8:
                game.manager.unload("maps/level9.tmx");
                break;
            case 9:
                game.manager.unload("maps/level10.tmx");
                break;
            case 10:
                game.manager.unload("maps/level11.tmx");
                break;
            case 11:
                game.manager.unload("maps/level12.tmx");
                break;
            case 12:
                game.manager.unload("maps/level13.tmx");
                break;
            case 13:
                game.manager.unload("maps/level14.tmx");
                break;
            case 14:
                game.manager.unload("maps/level15.tmx");
                break;
            case 15:
                game.manager.unload("maps/level16.tmx");
                break;
            case 16:
                game.manager.unload("maps/level17.tmx");
                break;
            case 17:
                game.manager.unload("maps/level18.tmx");
                break;
            case 18:
                game.manager.unload("maps/level19.tmx");
                break;
            case 19:
                game.manager.unload("maps/level20.tmx");
                break;
        }

    }

}