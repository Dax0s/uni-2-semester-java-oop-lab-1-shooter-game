package com.klimavicius.shooter_game.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.klimavicius.shooter_game.utils.Constants;


public class Player extends Actor {
    private final OrthographicCamera camera;

    private final Gun gun;
    private final Vector2 gunAngleVector = new Vector2(0, 0);

    private final Texture texture;
    private final Rectangle rectangle;

    private final float speed;
    private final float cameraSpeed;

    private boolean moveLeft = false;
    private boolean moveRight = false;
    private boolean moveUp = false;
    private boolean moveDown = false;

    public Rectangle getRectangle() {
        return rectangle;
    }

    public Player(OrthographicCamera camera, Stage stage, float speed, float cameraSpeed) {
        this.speed = speed;
        this.cameraSpeed = cameraSpeed;
        this.camera = camera;

        texture = new Texture(Gdx.files.internal("player.png"));

        rectangle = new Rectangle(
                Constants.SCREEN_WIDTH / 2.0f - 64 / 2.0f,
                Constants.SCREEN_HEIGHT / 2.0f - 64 / 2.0f,
                64,
                64
        );

        gun = new Gun();
        stage.addActor(gun);

        this.addListener(new InputListener() {
            // MOUSE EVENTS
            @Override
            public boolean mouseMoved(InputEvent event, float x, float y) {
                gunAngleVector.x = x - gun.getRectangle().x;
                gunAngleVector.y = y - gun.getRectangle().y;

                return true;
            }

            @Override
            public void touchDragged (InputEvent event, float x, float y, int pointer) {
                gunAngleVector.x = x - gun.getRectangle().x;
                gunAngleVector.y = y - gun.getRectangle().y;
            }

            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }

            // KEYBOARD EVENTS
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
        batch.draw(texture, rectangle.x, rectangle.y, 64, 64);
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
        float speed = this.speed;
        float cameraSpeed = this.cameraSpeed;

        if (countMovementBooleans() == 2)
            speed *= 0.7f;
        if (countMovementBooleans() == 2)
            cameraSpeed *= 0.7f;

        if (moveLeft)
            rectangle.x -= (speed * delta);
         if (moveRight)
            rectangle.x += (speed * delta);
         if (moveDown)
            rectangle.y -= (speed * delta);
        if (moveUp)
            rectangle.y += (speed * delta);

        double distance = Math.sqrt(Math.pow(Math.abs(rectangle.x - camera.position.x), 2) +
                        Math.pow(Math.abs(rectangle.y - camera.position.y), 2));

        if (distance >= 100)
            cameraSpeed = speed;


        if (camera.position.x < rectangle.x)
            camera.translate(cameraSpeed * delta, 0);
        if (camera.position.x > rectangle.x)
            camera.translate(-cameraSpeed * delta, 0);
        if (camera.position.y < rectangle.y)
            camera.translate(0, cameraSpeed * delta);
        if (camera.position.y > rectangle.y)
            camera.translate(0, -cameraSpeed * delta);

        gun.getRectangle().x = rectangle.x + 75;
        gun.getRectangle().y = rectangle.y + 10;

        gun.setRotation((float) (Math.atan2(gunAngleVector.y, gunAngleVector.x) * 180 / Math.PI));
    }
}
