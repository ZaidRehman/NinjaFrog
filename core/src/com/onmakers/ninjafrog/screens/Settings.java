package com.onmakers.ninjafrog.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.onmakers.ninjafrog.NinjaFrog;
import com.onmakers.ninjafrog.utils.AppPreferences;

import static com.onmakers.ninjafrog.NinjaFrog.V_Height;
import static com.onmakers.ninjafrog.NinjaFrog.V_WIDTH;


public class Settings implements Screen {

    private static final float SCALE = 0.8f;
    private OrthographicCamera camera;
    private Viewport viewport;

    private NinjaFrog game;
    private Stage stage;
    private Skin skin;

    private Label titleLabel;
    private Label volumeMusicLabel;
    private Label volumeSoundLabel;
    private Label musicOnOffLabel;
    private Label soundOnOffLabel;

    private Table table;

    public Settings(NinjaFrog game){
        this.game = game;
    }

    @Override
    public void show() {

        camera = new OrthographicCamera();
        camera.setToOrtho(false, V_WIDTH / SCALE, V_Height / SCALE);
        camera.update();
        viewport = new FillViewport(V_WIDTH / SCALE, V_Height / SCALE, camera);
        //viewport = new ScreenViewport(camera);
        viewport.apply();



        final AppPreferences prefs = new AppPreferences();
        stage = new Stage(new ScreenViewport());
        stage.getViewport().apply();
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
        // return to main screen button
        // the extra argument here "small" is used to set the button to the smaller version instead of the big default version
        final TextButton backButton = new TextButton("Back", skin, "small");
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new W1L1(game));
            }
        });
        //music
        final CheckBox musicCheckbox = new CheckBox(null, skin);
        musicCheckbox.setChecked( prefs.isMusicEnabled() );
        musicCheckbox.addListener( new EventListener() {
            @Override
            public boolean handle(Event event) {
                boolean enabled = musicCheckbox.isChecked();
                prefs.setMusicEnabled( enabled );
                return false;
            }
        });
        final Slider soundMusicSlider=new Slider( 0f, 1f, 1.0f, false, skin);
        soundMusicSlider.setValue( prefs.getSoundVolume() );
        soundMusicSlider.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                prefs.setSoundVolume( soundMusicSlider.getValue());
                return false;
            }
        });


        //volume
        final CheckBox soundEffectsCheckbox = new CheckBox(null, skin);
        soundEffectsCheckbox.setChecked( prefs.isSoundEffectsEnabled());
        soundEffectsCheckbox.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                prefs.setSoundEffectsEnabled( soundEffectsCheckbox.isChecked());
                return false;
            }
        });
        final Slider volumeMusicSlider = new Slider( 0f, 1f, 0.1f,false, skin );
        volumeMusicSlider.setValue( prefs.getMusicVolume() );
        volumeMusicSlider.addListener( new EventListener() {
            @Override
            public boolean handle(Event event) {
                prefs.setMusicVolume( volumeMusicSlider.getValue() );
                return false;
            }
        });

        titleLabel = new Label( "Preferences", skin );
        volumeMusicLabel = new Label( "Music Volume", skin );
        volumeSoundLabel = new Label( "Music", skin );
        musicOnOffLabel = new Label( "Sound Volume", skin );
        soundOnOffLabel = new Label( "Sound Effects", skin );


        table= new Table(skin);

        table.add(titleLabel).colspan(2).expandX().uniformX();
        table.row().pad(10).expandY().uniform();
        table.add(volumeMusicLabel);
        table.add(volumeMusicSlider);
        table.row();
        table.add(musicOnOffLabel);
        table.add(musicCheckbox);
        table.row();
        table.add(volumeSoundLabel);
        table.add(soundMusicSlider);
        table.row();
        table.add(soundOnOffLabel);
        table.add(soundEffectsCheckbox);
        table.row();
        table.add(backButton).colspan(2);

        table.debug();

        table.setFillParent(true);
        stage.addActor(table.align(Align.center));


    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f,0f,0f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportHeight = height;
        camera.viewportWidth = width;
        viewport.update(width, height);
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
