package com.klimavicius.shooter_game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.*;
import com.klimavicius.shooter_game.enemies.Spawner;
import com.klimavicius.shooter_game.player.Portal;
import com.klimavicius.shooter_game.utils.Constants;
import com.klimavicius.shooter_game.player.Player;
import com.klimavicius.shooter_game.ShooterGame;
import com.klimavicius.shooter_game.utils.CustomStage;

import java.util.GregorianCalendar;

public class GameScreen implements Screen {
    private ShooterGame game;

    private final CustomStage customStage;
    private final Player player;

    private final Texture wallTexture;

    private final Array<Rectangle> walls;
    private Array<Spawner> spawners;

    private Portal portal;

    public GameScreen(ShooterGame game) {
        walls = new Array<>();

        JsonReader jsonReader = new JsonReader();
        JsonValue base = jsonReader.parse(Gdx.files.internal("level1.json"));

        JsonValue jsonWalls = base.get("walls");

        if (jsonWalls != null && jsonWalls.isArray()) {
            for (JsonValue wall : jsonWalls) {
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
                base.get("player").getFloat("speed"),
                Constants.CAMERA_SPEED
        );

        this.portal = new Portal(
                false,
                base.get("portal").getInt("x") * 64,
                base.get("portal").getInt("y") * 64
        );

        JsonValue jsonSpawners = base.get("spawners");

        if (jsonSpawners != null && jsonSpawners.isArray()) {
            spawners = new Array<>();
            for (JsonValue spawner : jsonSpawners) {
                spawners.add(new Spawner(
                        spawner.getString("enemy"),
                        spawner.getFloat("spawnDelay"),
                        spawner.getInt("enemiesToSpawn"),
                        spawner.getFloat("speed"),
                        spawner.getFloat("x"),
                        spawner.getFloat("y"),
                        this.player.getGun(),
                        this.player
                ));
            }
        }

        for (Spawner spawner : spawners) {
            customStage.getStage().addActor(spawner);
        }

        Gdx.input.setInputProcessor(customStage.getStage());

        customStage.getStage().addActor(this.player);
        customStage.getStage().addActor(this.portal);
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

        wallTexture = new Texture(Gdx.files.internal("wall.png"));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 1, 1, 1);

        customStage.getStage().getBatch().begin();
        customStage.getStage().getBatch().setProjectionMatrix(customStage.getCamera().combined);

        for (Rectangle rectangle : walls)
            customStage.getStage().getBatch().draw(wallTexture, rectangle.x, rectangle.y, 64, 64);

        boolean portalShouldBeVisible = true;
        for (Spawner spawner : spawners) {
            if (spawner.getEnemiesToSpawn() > 0 || spawner.getEnemies().size > 0) {
                portalShouldBeVisible = false;
                break;
            }
        }

        if (portalShouldBeVisible) {
            portal.setPortalVisible(true);
        }

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
