package com.onmakers.ninjafrog.utils;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.DistanceJoint;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;

import static com.onmakers.ninjafrog.utils.Constants.PPM;

public class JointBuilder {

    public static DistanceJoint createDJointDef(World world, Body A, Body B, float height, boolean isCollidable){
        DistanceJointDef distanceJointDef = new DistanceJointDef();
        distanceJointDef.bodyA = A;
        distanceJointDef.bodyB = B;
        distanceJointDef.length= height / PPM;
        distanceJointDef.frequencyHz = 0;
        distanceJointDef.dampingRatio = 1.0f;
        distanceJointDef.collideConnected = isCollidable;
        return (DistanceJoint) world.createJoint(distanceJointDef);
    }

    public static RevoluteJoint createRJointDef(World world, Body A, Body B, float x, float y, boolean isCollidable){

        RevoluteJointDef revoluteJointDef = new RevoluteJointDef();
        revoluteJointDef.bodyA = A;
        revoluteJointDef.bodyB = B;
        revoluteJointDef.localAnchorA.add(x / PPM,y / PPM);
        return (RevoluteJoint) world.createJoint(revoluteJointDef);
    }

}
