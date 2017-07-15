package com.onmakers.ninjafrog.utils;

import com.badlogic.gdx.Gdx;

public class Constants {
    public static final float PPM = 32;
    public static final float FROG_RATIO = 0.77f;
    public static final float FROG_WIDTH = 32 * 6f;
    public static final float FROG_HEIGHT = 48 * 6f;
    public static final float FROG_BODY_WIDTH = 32 * 1.5f;
    public static final float FROG_BODY_HEIGHT = 48 * 2f;

    // Filters
    public static final short BIT_WALL = 1;
    public static final short BIT_FROG = 2;
    public static final short BIT_OWL = 4;
    public static final short BIT_COIN = 8;
    public static final short BIT_KEY = 16;
    public static final short BIT_DEATH_WALL = 32;

    public static boolean left = false;
    public static boolean right = false;
    public static boolean jump = false;
    public static boolean action = false;

    public static float MAX_H_VELOCITY = 10.0f;
    public static float MAX_V_VELOCITY = 20.0f;

    public static final float WIDTH = Gdx.graphics.getWidth();
    public static final float HEIGHT = Gdx.graphics.getHeight();

    // Animation
    public static boolean isGrounded = true;
    public static boolean frogDirection = false;
    public static String frogStatus = "standing";
    public static float elapsedTime = 0;
    public static float attackCounter = 0;
    public static float mapPixelWidth = 0;
    public static float mapPixelHeight = 0;

}
