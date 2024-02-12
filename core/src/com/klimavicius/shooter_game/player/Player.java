package com.klimavicius.shooter_game.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.klimavicius.shooter_game.utils.Constants;

public class Player extends Actor {
    private final Texture playerImage;
    private final Rectangle playerRectangle;

    private final double speed;

    private boolean moveLeft = false;
    private boolean moveRight = false;
    private boolean moveUp = false;
    private boolean moveDown = false;

    public Player(double speed) {
        this.speed = speed;

        playerImage = new Texture(Gdx.files.internal("tree.png"));

        playerRectangle = new Rectangle(
                (float) Constants.SCREEN_WIDTH / 2 - (float) 64 / 2,
                (float) Constants.SCREEN_HEIGHT / 2 - (float) 64 / 2,
                64,
                64
        );

        this.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (event.getKeyCode() == Input.Keys.A)
                    moveLeft = true;
                if (keycode == Input.Keys.D)
                    moveRight = true;
                if (keycode == Input.Keys.S)
                    moveDown = true;
                if (keycode == Input.Keys.W)
                    moveUp = true;

                return true;
            }

            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                if (event.getKeyCode() == Input.Keys.A)
                    moveLeft = false;
                if (keycode == Input.Keys.D)
                    moveRight = false;
                if (keycode == Input.Keys.S)
                    moveDown = false;
                if (keycode == Input.Keys.W)
                    moveUp = false;

                return true;
            }
        });
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(playerImage, playerRectangle.x, playerRectangle.y);
    }

    private int countMovementBooleans() {
        int counter = 0;
        if (moveLeft)
            counter++;
        if (moveRight)
            counter++;
        if (moveDown)
            counter++;
        if (moveUp)
            counter++;

        return counter;
    }

    @Override
    public void act(float delta) {
        double speed = this.speed;

        if (countMovementBooleans() == 2)
            speed *= 0.7;

        if (moveLeft)
            playerRectangle.x -= (int) (speed * delta);
         if (moveRight)
            playerRectangle.x += (int) (speed * delta);
         if (moveDown)
            playerRectangle.y -= (int) (speed * delta);
        if (moveUp)
            playerRectangle.y += (int) (speed * delta);
    }
}
