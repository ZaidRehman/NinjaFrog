package com.onmakers.ninjafrog;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

import java.util.HashMap;
import java.util.Map;


public class Demo  extends ApplicationAdapter implements InputProcessor{
    private OrthographicCamera camera;
    private Stage stage;
    private SpriteBatch batch;
    private Touchpad touchpad;
    private Touchpad.TouchpadStyle touchpadStyle;
    private Skin touchpadSkin;
    private Drawable touchBackground;
    private Drawable touchKnob;
    private Texture blockTexture;
    private Sprite blockSprite;
    private float blockSpeed;

    //input
    private SpriteBatch batchInput;
    private BitmapFont font;
    private String message = "Touch something already!";
    private int w,h;

    //tile
    Texture img;
    TiledMapRenderer tiledMapRenderer;
    TiledMap tiledMap;

    class TouchInfo {
        public float touchX = 0;
        public float touchY = 0;
        public boolean touched = false;
    }
    private Map<Integer,Demo.TouchInfo> touches = new HashMap<Integer,Demo.TouchInfo>();



    @Override
    public void create() {
        batch = new SpriteBatch();
        //Create camera
        float aspectRatio = (float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight();
        camera = new OrthographicCamera();
        camera.setToOrtho(false,  Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();


        //Create frog sprite
        blockTexture = new Texture(Gdx.files.internal("frog_1.png"));
        blockSprite = new Sprite(blockTexture);
        blockSprite.setScale(0.2f);
        //Set position to centre of the screen
        blockSprite.setPosition(0, Gdx.graphics.getHeight() / 2 - blockSprite.getHeight() / 2);

        blockSpeed = 5;

        //tile
        tiledMap = new TmxMapLoader().load("lvl1.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        //input
        batchInput = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.BLACK);
        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();
        Gdx.input.setInputProcessor((InputProcessor) this);
        touches.put(0, new Demo.TouchInfo());
    }

    @Override
    public void dispose() {
        batchInput.dispose();
        font.dispose();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();

        //tile
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();

        //Draw
        batch.begin();
        blockSprite.draw(batch);
        batch.end();

        batchInput.begin();
        float middle=Gdx.graphics.getWidth()/2;
        if (touches.get(0).touched) {
            if (touches.get(0).touchX >= middle) {
                camera.translate(5,0);
                //blockSprite.setX(blockSprite.getX() + blockSpeed);
            }else if(touches.get(0).touchX < middle){
                blockSprite.setX(blockSprite.getX() - blockSpeed);
            }
        }
        message = "";
        for(int i = 0; i < 2; i++){
            if(touches.get(i)==null ) continue;
            if(touches.get(i).touched)
                message += "Finger:" + Integer.toString(i) + "touch at:" +
                        Float.toString(touches.get(i).touchX) +
                        "," +
                        Float.toString(touches.get(i).touchY) +
                        "middle "+
                        middle +
                        "\n";

        }
        GlyphLayout glyphLayout =new GlyphLayout();
        glyphLayout.setText(font,message);
        float x = w/2 - glyphLayout.width/2;
        float y = h/2 + glyphLayout.height/2;
        font.draw(batchInput, message, x, y);
        batchInput.end();

    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(pointer < 5){
            touches.get(pointer).touchX = screenX;
            touches.get(pointer).touchY = screenY;
            touches.get(pointer).touched = true;
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if(pointer < 5){
            touches.get(pointer).touchX = 0;
            touches.get(pointer).touchY = 0;
            touches.get(pointer).touched = false;
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }


    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void resize(int width, int height) {
    }
}
