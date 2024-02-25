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
import com.badlogic.gdx.utils.Array;
import com.klimavicius.shooter_game.utils.Constants;


public class Player extends Actor {
    private final OrthographicCamera camera;

    private final Gun gun;
    private final Vector2 gunAngleVector = new Vector2(0, 0);

    private final Texture texture;
    private final Rectangle rectangle;

    Array<Rectangle> walls;

    private final float speed;
    private final float cameraSpeed;

    private boolean moveLeft = false;
    private boolean moveRight = false;
    private boolean moveUp = false;
    private boolean moveDown = false;

    public Rectangle getRectangle() {
        return rectangle;
    }

    public Player(OrthographicCamera camera, Stage stage, Array<Rectangle> walls, float speed, float cameraSpeed) {
        this.speed = speed;
        this.cameraSpeed = cameraSpeed;
        this.camera = camera;
        this.walls = walls;

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
                // 32 is half the size of gun sprite
                gunAngleVector.x = x - gun.getRectangle().x - 32;
                gunAngleVector.y = y - gun.getRectangle().y - 32;

                return true;
            }

            @Override
            public void touchDragged (InputEvent event, float x, float y, int pointer) {
                // 32 is half the size of gun sprite
                gunAngleVector.x = x - gun.getRectangle().x - 32;
                gunAngleVector.y = y - gun.getRectangle().y - 32;
            }

            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                // left mouse button
                if (button == 0)
                    gun.shoot(gunAngleVector.cpy());

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
        {
            boolean canMove = true;
            for (int i = 0; i < (int) (speed * delta); i++)
            {
                for (Rectangle wall : walls)
                {
                    if ((int) rectangle.x == (int) wall.x + 64 && Math.abs(rectangle.y - wall.y) < 64) {
                        canMove = false;
                        break;
                    }
                }

                if (canMove)
                    rectangle.x -= 1;
            }
        }
         if (moveRight)
         {
             boolean canMove = true;
             for (int i = 0; i < (int) (speed * delta); i++)
             {
                 for (Rectangle wall : walls)
                 {
                     if ((int) rectangle.x + 64 == (int) wall.x && Math.abs(rectangle.y - wall.y) < 64) {
                         canMove = false;
                         break;
                     }
                 }

                 if (canMove)
                     rectangle.x += 1;
             }
         }
         if (moveDown)
         {
             boolean canMove = true;
             for (int i = 0; i < (int) (speed * delta); i++)
             {
                 for (Rectangle wall : walls)
                 {
                     if ((int) rectangle.y == (int) wall.y + 64 && Math.abs(rectangle.x - wall.x) < 64) {
                         canMove = false;
                         break;
                     }
                 }

                 if (canMove)
                     rectangle.y -= 1;
             }
         }
        if (moveUp)
        {
            boolean canMove = true;
            for (int i = 0; i < (int) (speed * delta); i++)
            {
                for (Rectangle wall : walls)
                {
                    if ((int) rectangle.y + 64 == (int) wall.y && Math.abs(rectangle.x - wall.x) < 64) {
                        canMove = false;
                        break;
                    }
                }

                if (canMove)
                    rectangle.y += 1;
            }
        }

//        double distance = Math.sqrt(Math.pow(Math.abs(rectangle.x - camera.position.x), 2) +
//                        Math.pow(Math.abs(rectangle.y - camera.position.y), 2));

//        if (distance >= 100)
//            cameraSpeed = speed;

        int moveBy = (int) ((int) Math.abs(rectangle.x - camera.position.x) * cameraSpeed * delta);
        System.out.println(moveBy);
        for (int i = 0; i < moveBy; i++)
        {
            if ((int) camera.position.x != (int) rectangle.x)
            {
                if (rectangle.x > camera.position.x)
                    camera.position.x += 1;
                else
                    camera.position.x -= 1;
            }
        }

        moveBy = (int) ((int) Math.abs(rectangle.y - camera.position.y) * cameraSpeed * delta);
        for (int i = 0; i < moveBy; i++)
        {
            if ((int) camera.position.y != (int) rectangle.y)
            {
                if (rectangle.y > camera.position.y)
                    camera.position.y += 1;
                else
                    camera.position.y -= 1;
            }
        }

        gunAngleVector.nor();

        gun.getRectangle().x = rectangle.x + 75;
        gun.getRectangle().y = rectangle.y + 10;

        gun.setRotation((float) (Math.atan2(gunAngleVector.y, gunAngleVector.x) * 180 / Math.PI));
    }
}
