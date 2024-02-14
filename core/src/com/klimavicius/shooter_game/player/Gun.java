package com.klimavicius.shooter_game.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.klimavicius.shooter_game.utils.Constants;

public class Gun extends Actor {
    private final Texture gunTexture;
    private final Rectangle rectangle;

    private final Array<Bullet> bullets = new Array<>();

    public Rectangle getRectangle() {
        return rectangle;
    }

    public Gun() {
        this.gunTexture = new Texture(Gdx.files.internal("gun2.png"));

        this.rectangle = new Rectangle(
                Constants.SCREEN_WIDTH / 2.0f - 64 / 2.0f,
                Constants.SCREEN_WIDTH / 2.0f - 64 / 2.0f,
                64,
                64
        );
    }

    public void shoot(Vector2 angleVector) {
        angleVector.nor();
        bullets.add(new Bullet(rectangle.x, rectangle.y, this.getRotation(), Constants.BULLET_LIFETIME, angleVector));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(new TextureRegion(gunTexture), rectangle.x, rectangle.y, 32, 32, 64, 64, 1, 1, this.getRotation());

        for (Bullet bullet: bullets) {
            batch.draw(new TextureRegion(bullet.getTexture()), bullet.getX(), bullet.getY(), 32, 32, 64, 64, 1, 1, bullet.getRotation());
        }
    }

    @Override
    public void act(float delta) {
        for (int i = bullets.size - 1; i >= 0; i--) {
            bullets.get(i).setLifetime(bullets.get(i).getLifetime() - delta);

            bullets.get(i).move(delta, 600);

            if (bullets.get(i).getLifetime() <= 0)
                bullets.removeIndex(i);
        }
    }
}
