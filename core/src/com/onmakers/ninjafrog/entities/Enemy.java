package com.onmakers.ninjafrog.entities;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import static com.onmakers.ninjafrog.utils.Constants.PPM;

public class Enemy {

    public Body body;
    String id;

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

        this.body = world.createBody(bodyDef);
        this.body.createFixture(fixtureDef).setUserData(this);

        shape.dispose();
    }

    public void hit(){
        System.out.println(id + ": has been hit");
    }

}
