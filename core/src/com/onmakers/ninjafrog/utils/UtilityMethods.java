package com.onmakers.ninjafrog.utils;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.onmakers.ninjafrog.entities.Player;

import static com.onmakers.ninjafrog.utils.Constants.MAX_H_VELOCITY;
import static com.onmakers.ninjafrog.utils.Constants.MAX_V_VELOCITY;
import static com.onmakers.ninjafrog.utils.Constants.PPM;
import static com.onmakers.ninjafrog.utils.Constants.action;
import static com.onmakers.ninjafrog.utils.Constants.attackCounter;
import static com.onmakers.ninjafrog.utils.Constants.elapsedTime;
import static com.onmakers.ninjafrog.utils.Constants.isGrounded;
import static com.onmakers.ninjafrog.utils.Constants.jump;
import static com.onmakers.ninjafrog.utils.Constants.left;
import static com.onmakers.ninjafrog.utils.Constants.mapPixelHeight;
import static com.onmakers.ninjafrog.utils.Constants.mapPixelWidth;
import static com.onmakers.ninjafrog.utils.Constants.right;
import static com.onmakers.ninjafrog.utils.Constants.frogDirection;
import static com.onmakers.ninjafrog.utils.Constants.frogStatus;

public class UtilityMethods {

    public static ImageButton buttonLeft, buttonRight, buttonJump, buttonAttack;
    public static void initButtons(Object obj){

        Drawable leftup, rightup, leftdown, rightdown, jumpup,jumpdown , attackup, attackdown;

        //Left Button
        leftup = new TextureRegionDrawable(new TextureRegion(new Texture("images/button/left.up.png")));
        leftdown = new TextureRegionDrawable(new TextureRegion(new Texture("images/button/left.down.png")));
        buttonLeft = new ImageButton(leftup,leftdown);
        buttonLeft.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                left = false;
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                left = true;
                frogDirection = false;
                System.out.println("left");
                return true;
            }
        });

        //Right Button
        rightup = new TextureRegionDrawable(new TextureRegion(new Texture("images/button/right.up.png")));
        rightdown = new TextureRegionDrawable(new TextureRegion(new Texture("images/button/right.down.png")));
        buttonRight = new ImageButton(rightup,rightdown);
        buttonRight.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                right = false;
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                //inputTouchUpdate("right",x,y, Gdx.graphics.getDeltaTime(),true);
                right = true;
                frogDirection = true;
                System.out.println("right");
                return true;
            }
        });

        jumpup = new TextureRegionDrawable(new TextureRegion(new Texture("images/button/jump.up.png")));
        jumpdown = new TextureRegionDrawable(new TextureRegion(new Texture("images/button/jump.down.png")));
        buttonJump = new ImageButton(jumpup,jumpdown);
        buttonJump.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                jump = false;
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                jump = true;
                frogStatus = "jumping";
                elapsedTime = 0;
                System.out.println("jump");
                return true;
            }
        });

        attackup = new TextureRegionDrawable(new TextureRegion(new Texture("images/button/attack.up.png")));
        attackdown = new TextureRegionDrawable(new TextureRegion(new Texture("images/button/attack.down.png")));
        buttonAttack = new ImageButton(attackup,attackdown);
        buttonAttack.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                action = false;
                System.out.println("attack");
                //game.setScreen(new Settings(game));
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                action = true;
                frogStatus = "attacking";
                return true;
            }
        });

    }


    public static void inputUpdate(Player frog){

        Vector2 vel = frog.body.getLinearVelocity();
        Vector2 pos = frog.body.getPosition();

        if (left && right && jump  && vel.y < MAX_V_VELOCITY){
            frog.body.applyForceToCenter( 0, 10.80F * PPM,true);
            System.out.println(" l r j ");
        }
        else if(left &&  right){
            //frog.body.applyForceToCenter(0,0);
            System.out.println(" l r");
        }
        else if(left && jump  && vel.y < MAX_V_VELOCITY && vel.x > -MAX_H_VELOCITY){
            frog.body.applyForceToCenter(-10.80f * PPM, 0, true);
            if(isGrounded){
                frog.body.applyLinearImpulse(new Vector2(0, 20 *  PPM),frog.body.getPosition(),true);
            }
            System.out.println(" l j ");
        }
        else if(right && jump  && vel.y < MAX_V_VELOCITY && vel.x < MAX_H_VELOCITY){
            frog.body.applyForceToCenter(10.80f * PPM, 0, true);
            if(isGrounded){
                frog.body.applyLinearImpulse(new Vector2(0, 20 *  PPM),frog.body.getPosition(),true);
            }
            System.out.println(" r j ");
        }
        else if(left && vel.x > -MAX_H_VELOCITY){
            frog.body.applyForceToCenter(-10.80f * PPM, 0, true);
        }
        else if (right && vel.x < MAX_H_VELOCITY){
            frog.body.applyForceToCenter(10.80f * PPM, 0,true);
        }
        else if (jump && vel.y < MAX_V_VELOCITY){
            if(isGrounded){
                frog.body.applyLinearImpulse(new Vector2(0, 20 *  PPM),frog.body.getPosition(),true);
            }
        }
    }

    public static void updateFrogStatus(float delta, Player frog) {


        if(frogStatus == "falling"){
            if(frog.body.getLinearVelocity().y >= 0)
                frogStatus = "standing";
        }else if(frogStatus == "jumping"){
            if(frog.body.getLinearVelocity().y <= 0){
                elapsedTime = 0;
                frogStatus = "falling";
            }
        }else if(frogStatus == "attacking"){
            attackCounter += delta;
            if(attackCounter >= (1f/15f * 17f)){
                System.out.println("attack logic");
                frogStatus = "standing";
                attackCounter = 0;
            }
        }else if(frog.body.getLinearVelocity().x <4 && frog.body.getLinearVelocity().x > -4 && frogStatus!= "jumping" && frogStatus!= "falling"){
            frogStatus = "standing";
        }else if(frogStatus != "jumping" && frogStatus != "falling"){
            frogStatus = "walking";
        }

    }

    public static void cameraUpdate(float delta, Player frog, OrthographicCamera camera) {
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



}
