package com.onmakers.ninjafrog.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.onmakers.ninjafrog.NinjaFrog;

import static com.onmakers.ninjafrog.utils.Constants.PPM;
import static com.onmakers.ninjafrog.utils.Constants.coinCounter;

public class Coin {
    private final NinjaFrog game;
    public World world;
    public Body body;
    public boolean touched = false;
    String id;

    public Coin(NinjaFrog game,World world, String id, float x, float y, float width, float height){
        this.game = game;
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
        System.out.println(id + ": has been hit " + game.gm.getTotalCoins());
        game.gm.setPrefTotalCoins(game.gm.getTotalCoins() + 1);
    }
    public boolean isTouched (){
        return touched;
    }

}
