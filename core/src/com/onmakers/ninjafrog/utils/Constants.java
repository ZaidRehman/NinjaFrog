package com.onmakers.ninjafrog.utils;

import com.badlogic.gdx.Gdx;
import com.onmakers.ninjafrog.entities.Coin;
import com.onmakers.ninjafrog.entities.Enemy;

import java.util.ArrayList;

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

    public static float MAX_H_VELOCITY = 25.0f;
    public static float MAX_V_VELOCITY = 45.0f;

    public static final float WIDTH = Gdx.graphics.getWidth();
    public static final float HEIGHT = Gdx.graphics.getHeight();

    // Animation
    public static boolean isGrounded = true;
    public static boolean isKillingEnemy = false;

    public static boolean frogDirection = false;
    public static String frogStatus = "standing";
    public static float elapsedTime = 0;
    public static float attackCounter = 0;
    public static float mapPixelWidth = 0;
    public static float mapPixelHeight = 0;


    //Dead
    public static boolean isDead = false;

    public static int coinCounter = 0;
    public static ArrayList<Coin> coins = new ArrayList<Coin>();
    public static ArrayList<Enemy> flyingOwls = new ArrayList<Enemy>();


}
