package com.klimavicius.shooter_game.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.klimavicius.shooter_game.utils.Constants;

public class Gun extends Actor {
    private final Texture texture;
    private final Rectangle rectangle;

    public Rectangle getRectangle() {
        return rectangle;
    }

    public Gun() {
        this.texture = new Texture(Gdx.files.internal("gun2.png"));
        this.rectangle = new Rectangle(
                (float) Constants.SCREEN_WIDTH / 2 - 64 / 2.0f,
                (float) Constants.SCREEN_WIDTH / 2 - 64 / 2.0f,
                64,
                64
        );
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(new TextureRegion(texture), rectangle.x, rectangle.y, 32, 32, 64, 64, 1, 1, this.getRotation());
    }
}
