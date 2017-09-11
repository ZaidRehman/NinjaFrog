package com.onmakers.ninjafrog.entities;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.DistanceJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;

import static com.onmakers.ninjafrog.utils.Constants.PPM;
import static com.onmakers.ninjafrog.utils.Constants.isGrounded;

public class PlayerFoot {

    public RevoluteJoint joint;
    public Body body;
    String id;

    public PlayerFoot(World world, String id, float x, float y, float width, float height){
        this.id = id;
        createFootSensor(world,x,y,width,height);
        body.setLinearDamping(1f);
    }
    public void createFootSensor(World world, float x, float y, float width, float hieght){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = true;
        bodyDef.position.set(x / PPM, y / PPM);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / PPM,hieght / PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density =0.5f;
        fixtureDef.restitution = 0.0f;
        //fixtureDef.isSensor = true;

        this.body = world.createBody(bodyDef);
        this.body.createFixture(fixtureDef).setUserData(this);

        shape.dispose();
    }
    public void hit(boolean x){
        isGrounded = x;
    }
}


