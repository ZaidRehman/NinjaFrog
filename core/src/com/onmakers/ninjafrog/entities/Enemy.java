package com.onmakers.ninjafrog.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import static com.onmakers.ninjafrog.utils.Constants.BIT_OWL;
import static com.onmakers.ninjafrog.utils.Constants.BIT_WALL;
import static com.onmakers.ninjafrog.utils.Constants.PPM;

public class Enemy {

    public Body body;
    public String id;
    public boolean isAlive = true;
    public boolean isTouchingRSword = false;
    public boolean isTouchingLSword = false;
    public boolean flyingOwlDirection = false;
    public TextureAtlas atlasFlyingOwl,atlasDead1Owl,atlasDead2Owl,atlasDead3Owl,atlasAttack1Owl,atlasAttack2Owl;
    public   Animation<TextureAtlas.AtlasRegion> animFlyingOwl,animDead1Owl,animDead2Owl,animDead3Owl,animAttack1Owl,animAttack2Owl;
    public float elapsedOwlTime = 0;
    public float elapsedDeadCounter = 0;
    public String flyingOwlStatus = "flying";
    public boolean isBodyDestroyed= false;

    public Enemy(World world, String id, float x, float y, float width, float height){
        this.id = id;
        createBoxBody(world,x,y,width,height);
       // body.setLinearDamping(1f);
    }

    public void createBoxBody(World world,float x,float y, float width, float hieght){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = true;
        bodyDef.position.set(x / PPM, y / PPM);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / PPM,hieght / PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density =1.0f;
        fixtureDef.filter.categoryBits = BIT_OWL;
        fixtureDef.filter.maskBits = BIT_WALL;

        this.body = world.createBody(bodyDef);
        this.body.createFixture(fixtureDef).setUserData(this);

        shape.dispose();
    }

    public void hit(boolean x){
        System.out.println(id + ": has been hit");
    }
    public void dispose(){
        atlasFlyingOwl.dispose();
        atlasDead1Owl.dispose();
        atlasDead2Owl.dispose();
        atlasDead3Owl.dispose();
        atlasAttack1Owl.dispose();
        atlasAttack2Owl.dispose();
    }

}
