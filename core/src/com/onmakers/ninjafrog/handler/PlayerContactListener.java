package com.onmakers.ninjafrog.handler;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.onmakers.ninjafrog.entities.Coin;
import com.onmakers.ninjafrog.entities.Enemy;
import com.onmakers.ninjafrog.entities.Key;
import com.onmakers.ninjafrog.entities.Player;
import com.onmakers.ninjafrog.entities.PlayerFoot;

public class PlayerContactListener implements ContactListener{
    @Override
    public void beginContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if(fa == null || fb == null) return;
        if(fa.getUserData() == null || fb.getUserData() == null) return;

        // check contact of wall and foot
        if(isCollisionInFootAndWall(fa, fb)){
            if (fa.getUserData() instanceof PlayerFoot) {
                PlayerFoot playerFoot = (PlayerFoot) fa.getUserData();
                playerFoot.hit(true);
            } else {
                PlayerFoot playerFoot = (PlayerFoot) fb.getUserData();
                playerFoot.hit(true);
            }
        }

        //Check Enemy Contact
        if (isEnemyContact(fa, fb)) {
            if (fa.getUserData() instanceof Player) {
                Enemy enemy = (Enemy) fb.getUserData();
                enemy.hit();
            } else {
                Enemy enemy = (Enemy) fa.getUserData();
                enemy.hit();
            }
        }

        // check key Contact
        if (isKeyContact(fa, fb)) {
            if (fa.getUserData() instanceof Player) {
                Key key = (Key) fb.getUserData();
                key.hit();
            } else {
                Key key = (Key) fa.getUserData();
                key.hit();
            }
        }

        // check coin contact
        if (isCoinContact(fa, fb)) {
            if (fa.getUserData() instanceof Player) {
                Coin coin = (Coin) fb.getUserData();
                coin.hit();
            } else {
                Coin coin = (Coin) fa.getUserData();
                coin.hit();
            }
        }
    }

    private boolean isEnemyContact(Fixture fa, Fixture fb) {
        if(fa.getUserData() instanceof Player || fb.getUserData() instanceof Player){
            if(fa.getUserData() instanceof Enemy || fb.getUserData() instanceof Enemy){
                return true;
            }
        }
        return false;
    }
    private boolean isKeyContact(Fixture fa, Fixture fb){
        if(fa.getUserData() instanceof Player || fb.getUserData() instanceof Player){
            if(fa.getUserData() instanceof Key || fb.getUserData() instanceof Key){
                return true;
            }
        }
        return false;
    }
    private boolean isCoinContact(Fixture fa, Fixture fb){
        if(fa.getUserData() instanceof Player || fb.getUserData() instanceof Player){
            if(fa.getUserData() instanceof Coin || fb.getUserData() instanceof Coin){
                return true;
            }
        }
        return false;
    }
    private  boolean isCollisionInFootAndWall(Fixture fa, Fixture fb){
        if(fa.getUserData() instanceof String || fb.getUserData() instanceof String){
            if(fa.getUserData() == "WALL" || fb.getUserData() == "WALL")
                if(fa.getUserData() instanceof PlayerFoot || fb.getUserData() instanceof PlayerFoot)
                    return true;
        }
        return false;
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if(fa == null || fb == null) return;
        if(fa.getUserData() == null || fb.getUserData() == null) return;


        //System.out.println("A Collision Ended");

        if(isCollisionInFootAndWall(fa, fb)){
            if (fa.getUserData() instanceof PlayerFoot) {
                PlayerFoot playerFoot = (PlayerFoot) fa.getUserData();
                playerFoot.hit(false);
            } else {
                PlayerFoot playerFoot = (PlayerFoot) fb.getUserData();
                playerFoot.hit(false);
            }
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }
}
