package com.onmakers.ninjafrog.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by qureshi on 01/10/17.
 */

public class LevelCell extends Actor {
    public int text = 0; // item text
    private TextureRegion image; // image to show
    private BitmapFont font; // font used for text


    public LevelCell(TextureRegion reg, int itemText, BitmapFont fnt){
        image = reg;
        text = itemText;
        font = fnt;
    }
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        // draw image in center of actor
        batch.draw(image, getX(), getY(), getOriginX(), getOriginY(),
                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        // draw text below image
        font.draw(batch, (text+1) + "", getX() + getWidth() * 0.45f, getY() + getHeight() * 0.7f);
    }
    public void setText(int x){
        text = x;
    }
}
