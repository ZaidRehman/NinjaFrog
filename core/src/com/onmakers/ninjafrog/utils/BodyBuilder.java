package com.onmakers.ninjafrog.utils;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import static com.onmakers.ninjafrog.utils.Constants.PPM;

public class BodyBuilder {

    public static Body createBox(World world, float x, float y, int width, int height, boolean isStatic, boolean fixedRotation) {
        Body pBody;
        BodyDef def = new BodyDef();

        if(isStatic)
            def.type = BodyDef.BodyType.StaticBody;
        else
            def.type = BodyDef.BodyType.DynamicBody;

        def.position.set(x / PPM, y / PPM);
        def.fixedRotation = fixedRotation;
        pBody = world.createBody(def);
        pBody.setUserData("wall");

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2 / PPM, height / 2 / PPM);

        FixtureDef fd = new FixtureDef();
        fd.shape = shape;
        fd.density = 1.0f;
        fd.filter.categoryBits = Constants.BIT_WALL;
        fd.filter.maskBits = Constants.BIT_WALL;
        fd.filter.groupIndex = 0;
        pBody.createFixture(fd);
        shape.dispose();
        return pBody;
    }

    public static Body createBox(final World world, float x, float y, float w, float h,
                                 boolean isStatic, boolean canRotate, short cBits, short mBits, short gIndex) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.fixedRotation = canRotate;
        bodyDef.position.set(x / PPM, y / PPM);

        if(isStatic) {
            bodyDef.type = BodyDef.BodyType.StaticBody;
        } else {
            bodyDef.type = BodyDef.BodyType.DynamicBody;
        }

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(w / PPM / 2, h / PPM / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.filter.categoryBits = cBits; // Is a
        fixtureDef.filter.maskBits = mBits; // Collides with
        fixtureDef.filter.groupIndex = gIndex;

        Body ret = world.createBody(bodyDef).createFixture(fixtureDef).getBody();
        shape.dispose();

        return ret;
    }

    public static Body createCircle(final World world, float x, float y, float r,
                                    boolean isStatic, boolean canRotate, short cBits, short mBits, short gIndex) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.fixedRotation = canRotate;
        bodyDef.position.set(x / PPM, y / PPM);

        if(isStatic) {
            bodyDef.type = BodyDef.BodyType.StaticBody;
        } else {
            bodyDef.type = BodyDef.BodyType.DynamicBody;
        }

        CircleShape shape = new CircleShape();
        shape.setRadius(r / PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.filter.categoryBits = cBits; // Is a
        fixtureDef.filter.maskBits = mBits; // Collides with
        fixtureDef.filter.groupIndex = gIndex;

        //shape.dispose();

        return world.createBody(bodyDef).createFixture(fixtureDef).getBody();
    }

}
