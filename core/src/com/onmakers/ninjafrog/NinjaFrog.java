package com.onmakers.ninjafrog;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.onmakers.ninjafrog.screens.LevelLoading;
import com.onmakers.ninjafrog.screens.LoadingScreen;
import com.onmakers.ninjafrog.screens.MainMenu;
import com.onmakers.ninjafrog.screens.Splash;
import com.onmakers.ninjafrog.screens.W1L1;
import com.onmakers.ninjafrog.screens.W1L2;
import com.onmakers.ninjafrog.screens.W1L3;
import com.onmakers.ninjafrog.screens.W1L4;
import com.onmakers.ninjafrog.screens.W1L5;
import com.onmakers.ninjafrog.utils.GamePreferences;

public class NinjaFrog extends Game {


    public static final String TITTLE = "String";
    public static final float VERSION = 0.1f;
    public static final int V_WIDTH = 1920;
    public static final int V_Height = 1200;

    public OrthographicCamera camera;
    public SpriteBatch batch;

    public BitmapFont showcard;

    public GamePreferences gm;
    public  AssetManager manager;

    public LoadingScreen loadingScreen;
    public LevelLoading levelLoading;
    public Splash splash;
    public MainMenu mainMenu;

    @Override
    public void create() {
        manager = new AssetManager();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() );
        batch = new SpriteBatch();

        initFonts();

        gm = new GamePreferences();


        levelLoading = new LevelLoading(this);
        splash = new Splash(this);
        loadingScreen = new LoadingScreen(this);
        mainMenu = new MainMenu(this);
        setScreen(splash);
        /*switch(gm.getLevel()){
            case 0 : setScreen( new W1L1(this));
                break;
            case 1 : setScreen( new W1L2(this));
                break;
            case 2 : setScreen( new W1L3(this));
                break;
            case 3 : setScreen( new W1L4(this));
                break;
            case 4 : setScreen( new W1L5(this));
                break;
        }*/

    }


    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
        showcard.dispose();
        manager.dispose();
        loadingScreen.dispose();
        levelLoading.dispose();
        splash.dispose();
        //mainMenu.dispose();
    }

    private void initFonts(){
        FreeTypeFontGenerator generator =new FreeTypeFontGenerator(Gdx.files.internal("font/ShowcardFont/showcard.TTF"));
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();

        params.size = 48;
        params.color = Color.WHITE;
        showcard = generator.generateFont(params);
    }

    public OrthographicCamera getCamera (){
        return camera;
    }

    public SpriteBatch getBatch(){
        return batch;
    }
}