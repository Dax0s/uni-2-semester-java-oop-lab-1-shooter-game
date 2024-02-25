package com.klimavicius.shooter_game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.*;
import com.klimavicius.shooter_game.utils.Constants;
import com.klimavicius.shooter_game.player.Player;
import com.klimavicius.shooter_game.ShooterGame;
import com.klimavicius.shooter_game.utils.CustomStage;

import java.util.GregorianCalendar;

public class GameScreen implements Screen {
    private ShooterGame game;

    CustomStage customStage;
    Player player;

    Texture backgroundTexture;
    Texture wallTexture;

    Array<Rectangle> walls;

    public GameScreen(ShooterGame game) {
        walls = new Array<Rectangle>();

        JsonReader jsonReader = new JsonReader();
        JsonValue base = jsonReader.parse(Gdx.files.internal("level1.json"));

        JsonValue jsonWalls = base.get("walls");

        if (jsonWalls != null && jsonWalls.isArray())
        {
            for (JsonValue wall : jsonWalls)
            {
                walls.add(new Rectangle(
                        wall.getInt("x") * 64,
                        wall.getInt("Y") * 64,
                        64,
                        64
                ));
            }
        }

        customStage = new CustomStage(false, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        this.game = game;
        this.player = new Player(
                customStage.getCamera(),
                customStage.getStage(),
                walls,
                base.get("player").getInt("x") * 64,
                base.get("player").getInt("y") * 64,
                Constants.PLAYER_SPEED,
                Constants.CAMERA_SPEED
        );

        Gdx.input.setInputProcessor(customStage.getStage());

        customStage.getStage().addActor(this.player);
        customStage.getStage().setKeyboardFocus(this.player);

        customStage.getStage().addListener(new InputListener() {
            @Override
            public boolean mouseMoved(InputEvent event, float x, float y) {
                if (event.getTarget() == event.getListenerActor())
                    player.fire(event);

                return true;
            }

            @Override
            public void touchDragged (InputEvent event, float x, float y, int pointer) {
                if (event.getTarget() == event.getListenerActor())
                    player.fire(event);
            }


            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                if (event.getTarget() == event.getListenerActor())
                    player.fire(event);

                return true;
            }
        });

        backgroundTexture = new Texture(Gdx.files.internal("background.jpeg"));
        wallTexture = new Texture(Gdx.files.internal("wall.png"));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 1, 1, 1);

        customStage.getStage().getBatch().begin();

        for (Rectangle rectangle : walls)
            customStage.getStage().getBatch().draw(wallTexture, rectangle.x, rectangle.y, 64, 64);

        customStage.getStage().getBatch().end();

        customStage.getStage().act(delta);
        customStage.getStage().draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
