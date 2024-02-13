package com.klimavicius.shooter_game.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.klimavicius.shooter_game.utils.Constants;

public class Gun extends Actor {
    private final Texture gunTexture;
    private final Texture bulletTexture;
    private final Rectangle rectangle;

    private final Array<Rectangle> bullets = new Array<>();

    public Rectangle getRectangle() {
        return rectangle;
    }

    public Gun() {
        this.gunTexture = new Texture(Gdx.files.internal("gun2.png"));
        this.bulletTexture = new Texture(Gdx.files.internal("bullet.png"));

        this.rectangle = new Rectangle(
                Constants.SCREEN_WIDTH / 2.0f - 64 / 2.0f,
                Constants.SCREEN_WIDTH / 2.0f - 64 / 2.0f,
                64,
                64
        );
    }

    public void shoot() {
        bullets.add(new Rectangle(
                rectangle.x,
                rectangle.y,
                64,
                64
        ));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(new TextureRegion(gunTexture), rectangle.x, rectangle.y, 32, 32, 64, 64, 1, 1, this.getRotation());

        for (Rectangle bullet: bullets) {
            batch.draw(new TextureRegion(bulletTexture), bullet.x, bullet.y, 32, 32, 64, 64, 1, 1, this.getRotation());
        }
    }
}
