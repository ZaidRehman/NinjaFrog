package com.onmakers.ninjafrog.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.onmakers.ninjafrog.NinjaFrog;
import com.onmakers.ninjafrog.entities.Coin;
import com.onmakers.ninjafrog.entities.Enemy;
import com.onmakers.ninjafrog.entities.Key;
import com.onmakers.ninjafrog.entities.Player;
import com.onmakers.ninjafrog.handler.PlayerContactListener;

import static com.onmakers.ninjafrog.utils.Constants.PPM;

public class Level3 implements Screen{

    private boolean DEBUG = false;
    private static final float SCALE = 1.0f;
    private OrthographicCamera camera;
    private Viewport viewport;

    private OrthogonalTiledMapRenderer tmr;
    private TiledMap map;
    int mapPixelHeight;
    int mapPixelWidth;

    private Box2DDebugRenderer b2dr;
    private World world;
    //private Body frog;
    private Coin coin1;
    private Key key1;
    private Enemy enemy1,enemy2;
    private Player frog;

    private float w, h;

    private SpriteBatch batch;
    private Texture tex;


    private NinjaFrog game;

    public Level3(NinjaFrog game) {
        //super(game);
        this.game = game;
    }

    @Override
    public void show() {
        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, w / SCALE, h / SCALE);
        camera.update();
        viewport = new StretchViewport(w / SCALE, h / SCALE, camera);
        viewport.apply();

        world = new World(new Vector2(0f, -98f), false);
        this.world.setContactListener(new PlayerContactListener());
        b2dr = new Box2DDebugRenderer();

       // Gdx.input.setInputProcessor(this);

        map = game.manager.get("maps/lvl3final.tmx", TiledMap.class);
        tmr = new OrthogonalTiledMapRenderer(map);
        MapProperties prop = map.getProperties();

        int mapWidth = prop.get("width", Integer.class);
        int mapHeight = prop.get("height", Integer.class);
        int tilePixelWidth = prop.get("tilewidth", Integer.class);
        int tilePixelHeight = prop.get("tileheight", Integer.class);

        mapPixelWidth = mapWidth * tilePixelWidth;
        mapPixelHeight = mapHeight * tilePixelHeight;

        batch = new SpriteBatch();
        tex = game.manager.get("images/frog.png", Texture.class);

       /* frog = createBox(world, 100, 300, getFrogWidth(w), getFrogHeight(w) * 0.85f, false, true,
                BIT_FROG, (short) (BIT_WALL | BIT_OWL), (short) 0);*/

        frog = new Player(world, "FROG", 100, 300, 128 / 2, 192 * 0.85f / 2);
        //enemy1 = new Enemy(world, "ENEMY1", 475, 475, 16, 16);
        //enemy2 = new Enemy(world, "ENEMY2", 325, 475, 16, 16);
        key1 = new Key(world, "KEY1", 325, 475, 16, 16);

        //TiledObjectUtil.parseTiledObjectLayer(world, map.getLayers().get(1).getObjects());
    }

    @Override
    public void render(float delta) {
        update(Gdx.graphics.getDeltaTime());

        //Render
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        tmr.render();

        batch.begin();
        batch.draw(tex, frog.body.getPosition().x * PPM - 128 / 2, frog.body.getPosition().y * PPM - 192 * 0.85f / 2, 128, 192);
        batch.end();

        b2dr.render(world, camera.combined.scl(PPM));
    }

    public void update(float delta) {

        world.step(1 / 60f, 6, 2);
        cameraUpdate(delta);
        inputUpdate(delta);
        tmr.setView(camera);
        batch.setProjectionMatrix(camera.combined);
    }

    public void inputUpdate(float delta){// ,int direction, int horizontalForce) {

        /*if (horizontalForce != 0) {
            if (direction == 4) {
                horizontalForce = -2;
            } else if (direction == 6) {
                horizontalForce += 1;
            }
        }*/
        float x = 0, y = 0;
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            y += 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            y -= 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            x -= 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            x += 1;
        }

        // Dampening check
        if (x != 0) {
            frog.body.setLinearVelocity(x * 1500 * delta, frog.body.getLinearVelocity().y);
        }
        if (y != 0) {
            frog.body.setLinearVelocity(frog.body.getLinearVelocity().x, y * 1500 * delta);
        }

       // frog.setLinearVelocity(horizontalForce * 10, frog.getLinearVelocity().y);
    }

    public void cameraUpdate(float delta) {
        Vector3 position = camera.position;
        position.x = frog.body.getPosition().x * PPM;
        position.y = frog.body.getPosition().y * PPM;

        float halfWidth = frog.body.getPosition().x * PPM;// - getFrogWidth(w);
        float halfHeight = frog.body.getPosition().y * PPM;// - getFrogHeight(w);

        //for x axis
        if ((camera.viewportWidth / 2) > halfWidth)
            position.x = camera.viewportWidth / 2;
        else if ((mapPixelWidth - camera.viewportWidth / 2) < halfWidth)
            position.x = mapPixelWidth - camera.viewportWidth / 2;
        else
            position.x = halfWidth;

        //for y axis
        if (camera.viewportHeight / 2 > halfHeight)
            position.y = camera.viewportHeight / 2;
        else if ((mapPixelHeight - camera.viewportHeight / 2) < halfHeight)
            position.y = mapPixelHeight - camera.viewportHeight / 2;
        else
            position.y = halfHeight;

        camera.position.set(position);
        camera.update();

    }

    @Override
    public void resize(int width, int height) {
        camera.viewportHeight = height;
        camera.viewportWidth = width;

        viewport.update(width, height);
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
        tex.dispose();
        tmr.dispose();
        map.dispose();
        game.manager.unload("maps/lvl3final.tmx");
        game.manager.unload("images/frog.png");
    }
/*

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
        frog.setLinearVelocity(frog.getLinearVelocity().x, frog.getLinearVelocity().y += 5);
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (screenX < w / 2) {
            inputUpdate(Gdx.graphics.getDeltaTime(), 4, 1);
        } else {
            inputUpdate(Gdx.graphics.getDeltaTime(), 6, 1);
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (screenX < w / 2) {
            inputUpdate(Gdx.graphics.getDeltaTime(), 4, 0);
        } else {
            inputUpdate(Gdx.graphics.getDeltaTime(), 6, 0);
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
*/
}