package com.onmakers.ninjafrog.screens;

import com.badlogic.gdx.Gdx;
import  com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.onmakers.ninjafrog.NinjaFrog;

import static com.onmakers.ninjafrog.NinjaFrog.V_Height;
import static com.onmakers.ninjafrog.NinjaFrog.V_WIDTH;

public class LevelLoading implements Screen {


    private  final  NinjaFrog game;
    private ShapeRenderer shapeRenderer;
    private  float progress ;
    //OrthographicCamera camera;
    Viewport viewport;
    private static final float SCALE = 1f;

    public LevelLoading(final NinjaFrog game) {
        this.game= game;
        this.shapeRenderer= new ShapeRenderer() ;
    }

    private  void update(float delta){
        progress = MathUtils.lerp(progress, game.manager.getProgress(),.1f);
        if(game.manager.update() && (progress >= game.manager.getProgress() - 0.01f)){
            switch (0){

                case 0:
                    game.setScreen(new W1L1(game));
                    break;
                case 1:
                    game.setScreen(new W1L2(game));
                    break;
                case 2:
                    game.setScreen(new W1L3(game));
                    break;
                case 3:
                    game.setScreen(new W1L4(game));
                    break;
                case 4:
                    game.setScreen(new W1L5(game));
                    break;

            }

        }

    }



    @Override
    public void show() {

        this.progress = 0f;
        queueAssets();


        //game.camera = new OrthographicCamera();
        //camera.setToOrtho(false, V_WIDTH / SCALE, V_Height / SCALE);
        game.camera.update();
        viewport = new FillViewport(V_WIDTH / SCALE, V_Height / SCALE, game.camera);
        //viewport = new ScreenViewport(camera);
        viewport.apply();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.1f,.5f,.5f,1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.camera.update();
        update(delta);
        game.batch.setProjectionMatrix(game.camera.combined);
        shapeRenderer.setProjectionMatrix(game.camera.combined);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(32,game.camera.viewportHeight/2 -8,game.camera.viewportWidth -60,16);

        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.rect(32,game.camera.viewportHeight/2 -8,progress*(game.camera.viewportWidth -64),16);
        shapeRenderer.end();

        game.batch.begin();
        game.showcard.draw(game.batch,"Level "+ (game.gm.getLevel() + 1 )+" is loading",game.camera.viewportWidth / 2 - 50,game.camera.viewportHeight * 0.65f);
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {

        game.camera.viewportHeight = height;
        game.camera.viewportWidth = width;
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
        shapeRenderer.dispose();
    }

    private  void  queueAssets(){
        //game.manager.load("images/LevelLoadingScreen.png", Texture.class);
        //game.manager.load("images/topBar.png", Texture.class);
       // game.manager.load("skin/glassy-ui.atlas", TextureAtlas.class);

     /*   game.manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        game.manager.load("maps/lvl3final.tmx", TiledMap.class);
        game.manager.load("images/frog.png", Texture.class);*/
        game.manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        //game.manager.load("images/frog.png", Texture.class);
        game.manager.load("frogAnim/standingFrog/standingFrog.atlas",TextureAtlas.class);
        game.manager.load("frogAnim/walkingFrog/walkingFrog.atlas",TextureAtlas.class);
        game.manager.load("frogAnim/attackingFrog/attackingFrog.atlas",TextureAtlas.class);
        game.manager.load("frogAnim/jumpingFrog/JumpingFrog.atlas",TextureAtlas.class);
        game.manager.load("frogAnim/fallingFrog/fallingFrog.atlas",TextureAtlas.class);
        game.manager.load("frogAnim/deadFrog/deadFrog.atlas",TextureAtlas.class);

        game.manager.load("owlAnim/owlFlying/owlFlying.atlas",TextureAtlas.class);
        game.manager.load("owlAnim/owlAttack/owlAttack1.atlas",TextureAtlas.class);
        game.manager.load("owlAnim/owlAttack/owlAttack2.atlas",TextureAtlas.class);
        game.manager.load("owlAnim/owlDead/owlDead1.atlas",TextureAtlas.class);
        game.manager.load("owlAnim/owlDead/owlDead2.atlas",TextureAtlas.class);
        game.manager.load("owlAnim/owlDead/owlDead3.atlas",TextureAtlas.class);

        game.manager.load("images/gs/gs.atlas", TextureAtlas.class);
        switch (game.gm.getLevel()){

            case 0:
                game.manager.load("maps/World1Level1.tmx", TiledMap.class);
                break;
            case 1:
                game.manager.load("maps/World1Level2.tmx", TiledMap.class);
                break;
            case 2:
                game.manager.load("maps/World1Level3.tmx", TiledMap.class);
                break;
            case 3:
                game.manager.load("maps/World1Level4.tmx", TiledMap.class);
                break;
            case 4:
                game.manager.load("maps/World1Level5.tmx", TiledMap.class);
                break;
            default:
                game.gm.setPrefLevel(0);
                game.manager.load("maps/World1Level1.tmx", TiledMap.class);
                break;
        }
    }
}
