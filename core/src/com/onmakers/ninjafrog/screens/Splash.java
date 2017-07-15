package com.onmakers.ninjafrog.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.onmakers.ninjafrog.NinjaFrog;
import com.onmakers.ninjafrog.manager.ScreenManager;

public class Splash implements Screen{

    private NinjaFrog game;

    private Stage stage;
    private Image splashImg;

    float timer = 0;

    private float w ,h;

   public Splash (final NinjaFrog game){
        this.game=game;
        this.stage = new Stage(new FitViewport(NinjaFrog.V_WIDTH,NinjaFrog.V_Height, game.camera));

    }
   /*public Splash(ScreenManager sm){
       super(sm);
       this.game = sm.ninjaFrog();
       this.stage = new Stage(new ScreenViewport());//(NinjaFrog.V_WIDTH,NinjaFrog.V_Height, game.camera));


       w = Gdx.graphics.getWidth();
       h = Gdx.graphics.getHeight();

       Gdx.input.setInputProcessor(stage);

       Runnable transitionRunnable = new Runnable() {
           @Override
           public void run() {
               // game.setScreen(game.mainMenu);
           }
       };

      Texture splashTex = new Texture(Gdx.files.internal("images/logo.png"));
       splashImg = new Image(splashTex);
       //splashImg.setSize(splashImg.getImageWidth()/2,splashImg.getImageHeight()/2);
       splashImg.setSize(550,200);
       splashImg.setPosition(stage.getWidth()/2 - splashImg.getWidth()/2, stage.getHeight()/2 - splashImg.getHeight()/2);
       //splashImg.setOrigin(w/2 - splashImg.getWidth()/2, h / 2 - splashImg.getHeight()/2);
       //splashImg.setOrigin(splashImg.getWidth()/2,splashImg.getHeight()/2);
R

        *//*splashImg.addAction(sequence(alpha(0),
                scaleTo(.1f,.1f),
                parallel(fadeIn(2f, Interpolation.pow2),
                        scaleTo(2f,2f,2.5f,Interpolation.pow5),
                        moveTo(stage.getWidth()/2-splashImg.getWidth()/2,stage.getHeight()/2 - splashImg.getHeight() / 2,2f, Interpolation.swing),
                        delay(1.5f),
                        fadeOut(1.25f)),
                run(transitionRunnable)));*//*
       stage.addActor(splashImg);
   }*/

    @Override
    public void show() {

        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();

        Gdx.input.setInputProcessor(stage);

        Runnable transitionRunnable = new Runnable() {
            @Override
            public void run() {
                // game.setScreen(game.mainMenu);
            }
        };

        Texture splashTex = new Texture(Gdx.files.internal("images/logo.png"));
        splashImg = new Image(splashTex);
        //splashImg.setSize(stage.getWidth()/2,splashImg.getHeight()/2);

        splashImg.setSize(550,200);
        splashImg.setPosition(stage.getWidth()/2 - splashImg.getWidth()/2, stage.getHeight()/2 - splashImg.getHeight()/2);
        //splashImg.setOrigin(w/2 - splashImg.getWidth()/2, h / 2 - splashImg.getHeight()/2);
        // splashImg.setOrigin(splashImg.getWidth()/2,splashImg.getHeight()/2);


        /*splashImg.addAction(sequence(alpha(0),
                scaleTo(.1f,.1f),
                parallel(fadeIn(2f, Interpolation.pow2),
                        scaleTo(2f,2f,2.5f,Interpolation.pow5),
                        moveTo(stage.getWidth()/2-splashImg.getWidth()/2,stage.getHeight()/2 - splashImg.getHeight() / 2,2f, Interpolation.swing),
                        delay(1.5f),
                        fadeOut(1.25f)),
                run(transitionRunnable)));*/
        stage.addActor(splashImg);
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(1f,1f,1f,1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        update(delta);
        stage.draw();

        game.batch.begin();
        game.showcard.draw(game.batch, "Ninja Frog",w/2 -15, h);
        game.batch.end();

    }

    public void update(float delta){
        timer += delta;
        if(timer >= 3){
           // game.setScreen(new W1L1(sm));
             // sm.setScreenState(ScreenManager.State.W1L1);
        }
        stage.act(delta);

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, false);

    }

    @Override
    public void pause() {
        System.out.println("pause");
    }

    @Override
    public void resume() {
        System.out.println("resume");
    }

    @Override
    public void hide() {
        System.out.println("hide");
    }

    @Override
    public void dispose() {
       stage.dispose();
    }

}
