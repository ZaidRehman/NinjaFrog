package com.onmakers.ninjafrog.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import static com.onmakers.ninjafrog.utils.Constants.PPM;
import static com.onmakers.ninjafrog.utils.Constants.coinCounter;

public class Coin {
    public World world;
    public Body body;
    String id;
    public boolean touched = false;

    public Coin(World world, String id, float x, float y, float width, float height){
        this.world = world;
        this.id = id;
        createBoxBody(world,x,y,width,height);
        // body.setLinearDamping(1f);
    }

    public void createBoxBody(World world,float x,float y, float width, float hieght){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.fixedRotation = true;
        bodyDef.position.set(x / PPM, y / PPM);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / PPM,hieght / PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density =1.0f;
        fixtureDef.isSensor = true;

        this.body = world.createBody(bodyDef);
        this.body.createFixture(fixtureDef).setUserData(this);

        shape.dispose();
    }

    public void hit(){
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run () {
                world.destroyBody(body);
            }
        });
        touched =true;
        coinCounter ++ ;
        System.out.println(id + ": has been hit");
    }
    public boolean isTouched (){
        return touched;
    }

}
