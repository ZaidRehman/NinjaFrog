package com.onmakers.ninjafrog.utils;


import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.DistanceJoint;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;

import static com.onmakers.ninjafrog.utils.Constants.PPM;

public class JointBuilder {

    public static DistanceJoint createDJointDef(World world, Body A, Body B, float height){
        DistanceJointDef distanceJointDef = new DistanceJointDef();
        distanceJointDef.bodyA = A;
        distanceJointDef.bodyB = B;
        distanceJointDef.length= height / PPM;
        distanceJointDef.frequencyHz = 0;
        distanceJointDef.dampingRatio = 1.0f;
        distanceJointDef.collideConnected = true;
        return (DistanceJoint) world.createJoint(distanceJointDef);
    }
}
