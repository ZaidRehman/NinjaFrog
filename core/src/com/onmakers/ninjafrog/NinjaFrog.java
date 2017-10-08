package com.onmakers.ninjafrog;

// New Version (V.1)


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.onmakers.ninjafrog.screens.LevelLoading;
import com.onmakers.ninjafrog.screens.LevelScreen;
import com.onmakers.ninjafrog.screens.MainMenu;
import com.onmakers.ninjafrog.screens.Splash;
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

    public LevelScreen levelScreen;
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


        levelScreen = new LevelScreen(this);
        levelLoading = new LevelLoading(this,-1);
        splash = new Splash(this);
        mainMenu = new MainMenu(this);
        setScreen(splash);

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
        levelLoading.dispose();
        splash.dispose();
        mainMenu.dispose();
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