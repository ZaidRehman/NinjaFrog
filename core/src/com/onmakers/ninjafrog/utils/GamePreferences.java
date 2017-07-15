package com.onmakers.ninjafrog.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.onmakers.ninjafrog.NinjaFrog;

public class GamePreferences {

    private static final String PREF_CURRENT_WORLD = "current_world";
    private static final String PREF_PREVIOUS_WORLD = "previous_world";
    private static final String PREF_LEVEL = "current_level";
    private static final String PREF_TOTAL_KEYS = "keys";
    private static final String PREF_TOTAL_COINS = "coins";
    private static final String PREF_TOTAL_ENEMY_KILLS = "kills";
    private static final String PREF_LIVES = "lives";

    private static final String PREFS_NAME = "game";

    protected Preferences getPrefs() {
        return Gdx.app.getPreferences(PREFS_NAME);
    }

    public String getCurrentWorld() {
        return getPrefs().getString(PREF_CURRENT_WORLD,"start");
    }

    public String getPreviousWorld() {
        return getPrefs().getString(PREF_PREVIOUS_WORLD,"start");
    }

    public int getLevel() {
        return getPrefs().getInteger(PREF_LEVEL,0);
    }

    public int getTotalKeys() {
        return getPrefs().getInteger(PREF_TOTAL_KEYS,0);
    }

    public int getTotalCoins() {
        return getPrefs().getInteger(PREF_TOTAL_COINS,0);
    }

    public int getTotalEnemyKills() {
        return getPrefs().getInteger(PREF_TOTAL_ENEMY_KILLS,0);
    }

    public int getLives() {
        return getPrefs().getInteger(PREF_LIVES,5);
    }

    public void setPrefCurrentWorld(String value){
        getPrefs().putString(PREF_CURRENT_WORLD,value).flush();
    }

    public void setPrefPreviousWorld(String value){
        getPrefs().putString(PREF_PREVIOUS_WORLD,value).flush();
    }

    public  void setPrefLevel(int value){
        getPrefs().putInteger(PREF_LEVEL,value).flush();
    }

    public void setPrefTotalKeys(int value){
        getPrefs().putInteger(PREF_TOTAL_KEYS,value).flush();
    }

    public void setPrefTotalCoins(int value){
        getPrefs().putInteger(PREF_TOTAL_COINS,value).flush();
    }

    public void setPrefTotalEnemyKills(int value){
        getPrefs().putInteger(PREF_TOTAL_ENEMY_KILLS,0).flush();
    }

    public void setPrefLives(){
        getPrefs().putInteger(PREF_LIVES,0).flush();
    }

}
