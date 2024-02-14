package com.klimavicius.shooter_game.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Bullet {
    private final Rectangle rectangle;
    private final Texture texture;
    private final float rotation;
    private final Vector2 angleVector;

    private float lifetime;

    public Bullet(float x, float y, float rotation, float lifetime, Vector2 angleVector) {
        this.texture = new Texture(Gdx.files.internal("bullet.png"));

        this.rectangle = new Rectangle(x, y, 64, 64);
        this.rotation = rotation;
        this.lifetime = lifetime;
        this.angleVector = angleVector.nor();
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public Texture getTexture() {
        return texture;
    }

    public Vector2 getAngleVector() {
        return angleVector;
    }

    public float getX() {
        return rectangle.x;
    }

    public float getY() {
        return rectangle.y;
    }

    public void setX(float x) {
        rectangle.x = x;
    }

    public void setY(float y) {
        rectangle.y = y;
    }

    public float getRotation() {
        return rotation;
    }

    public float getLifetime() {
        return lifetime;
    }

    public void setLifetime(float lifetime) {
        this.lifetime = lifetime;
    }
}
