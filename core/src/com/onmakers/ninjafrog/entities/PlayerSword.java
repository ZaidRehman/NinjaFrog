package com.onmakers.ninjafrog.entities;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;

import static com.onmakers.ninjafrog.utils.Constants.PPM;
import static com.onmakers.ninjafrog.utils.Constants.isKillingEnemy;

public class PlayerSword {
    public RevoluteJoint joint;
    public Body body;
    public String id;

    public PlayerSword(World world, String id, float x, float y, float width, float height){
        this.id = id;
        createSwordSensor(world,x,y,width,height);
        body.setLinearDamping(2f);
    }
    public void createSwordSensor(World world, float x, float y, float width, float hieght){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = true;
        bodyDef.position.set(x / PPM, y / PPM);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / PPM,hieght / PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        //fixtureDef.restitution = 0.0f;
        fixtureDef.density = 0;
        fixtureDef.isSensor = true;

        this.body = world.createBody(bodyDef);
        this.body.createFixture(fixtureDef).setUserData(this);

        shape.dispose();
    }
    public void hit(boolean x){
        isKillingEnemy = x;
        //System.out.println("isKillingEnemy " + x);
    }
}
