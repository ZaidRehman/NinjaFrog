package com.onmakers.ninjafrog.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by qureshi on 10/09/17.
 */

public class LeftArrorButton extends Actor {

    private TextureRegion image;

    public LeftArrorButton(TextureRegion textureRegion) {
        image = textureRegion;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.draw(image, getX(), getY(), getOriginX(), getOriginY(),
                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }
}
