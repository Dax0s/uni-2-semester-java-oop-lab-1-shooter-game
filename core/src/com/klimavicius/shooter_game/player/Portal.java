package com.klimavicius.shooter_game.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Portal extends Actor {
    private boolean portalVisible;

    private final Texture texture;
    private final Rectangle rectangle;

    public Portal(boolean portalVisible, float x, float y) {
        this.portalVisible = portalVisible;

        this.texture = new Texture(Gdx.files.internal("portal.png"));
        this.rectangle = new Rectangle(x, y, 64, 64);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (portalVisible) {
            batch.draw(texture, rectangle.x, rectangle.y, 64, 64);
        }
    }

    public void setPortalVisible(boolean portalVisible) {
        this.portalVisible = portalVisible;
    }

    public boolean getPortalVisible() {
        return this.portalVisible;
    }

    public Rectangle getRectangle() {
        return this.rectangle;
    }
}
