package com.onmakers.ninjafrog.manager;

import com.onmakers.ninjafrog.NinjaFrog;
import com.onmakers.ninjafrog.screens.AbstractScreen;
import com.onmakers.ninjafrog.screens.MainMenu;
import com.onmakers.ninjafrog.screens.Splash;
import com.onmakers.ninjafrog.screens.W1L1;

import java.util.Stack;
import java.util.jar.Pack200;

public class ScreenManager {

    private final NinjaFrog game;
    private Stack<AbstractScreen> states;

    public enum State {
        SPLASH,
        MAINMENU,
        W1L1,
        W1L2,
        W1L3,
        W1L4
    }

    public ScreenManager(final NinjaFrog game){
        this.game = game;
        this.states = new Stack<AbstractScreen>();
        this.setScreenState(State.SPLASH);
    }

    public NinjaFrog ninjaFrog(){
        return game;
    }

    public void update (float delta){
        //states.peek().show();
        states.peek().update(delta);
    }

    public void render (float delta){
        states.peek().render(delta);
    }

    public void dispose(){
        for (AbstractScreen abs:states) {
            abs.dispose();
        }
        states.clear();
    }

    public void resize (int w, int h){
        states.peek().resize(w,h);
    }

    public void  setScreenState(State state){
        if(states.size() >= 1)
            states.pop().dispose();
        //states.push(getState(state));
    }

    /*public AbstractScreen getState(State state){
        switch (state){
            case SPLASH:
                return new Splash(this);
            case MAINMENU:
                return null;
            case W1L1:
                return new W1L1(this);

        }
        return null;
    }*/
}