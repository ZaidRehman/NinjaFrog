package com.onmakers.ninjafrog.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class GamePreferences {

    private static final String PREF_LEVEL = "current_level";
    private static final String PREF_TOTAL_COINS = "coins";
    private static final String PREF_TOTAL_ENEMY_KILLS = "kills";
    private static final String PREF_LIVES = "lives";

    private static final String PREFS_NAME = "game";

    protected Preferences getPrefs() {
        return Gdx.app.getPreferences(PREFS_NAME);
    }

    public int getLevel() {
        return getPrefs().getInteger(PREF_LEVEL,0);
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
    public  void setPrefLevel(int value){
        getPrefs().putInteger(PREF_LEVEL,value).flush();
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
