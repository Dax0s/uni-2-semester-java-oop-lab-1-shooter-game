package com.klimavicius.shooter_game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.*;
import com.klimavicius.shooter_game.ShooterGame;
import com.klimavicius.shooter_game.enemies.Spawner;
import com.klimavicius.shooter_game.player.Player;
import com.klimavicius.shooter_game.player.Portal;
import com.klimavicius.shooter_game.utils.Constants;
import com.klimavicius.shooter_game.utils.CustomStage;

import java.io.*;

public class EditorScreen implements Screen {
    private final ShooterGame game;

    private final CustomStage customStage;
    private final Player player;

    private final Texture wallTexture;

    private final Array<Rectangle> walls;
    private Array<Spawner> spawners;

    private Portal portal;

    private boolean moveLeft = false;
    private boolean moveRight = false;
    private boolean moveUp = false;
    private boolean moveDown = false;

    enum Tile {
        WALL,
        PLAYER,
        PORTAL,
        GHOST_SPAWNER
    }

    private final Button wallButton;
    private final Button playerButton;
    private final Button portalButton;
    private final Button ghostSpawnerButton;
    private final Button saveButton;

    private Tile tileToPlace = Tile.WALL;

    private int levelToEdit;

    public EditorScreen(ShooterGame game, int levelToEdit) {
        this.levelToEdit = levelToEdit;
        walls = new Array<>();

        JsonReader jsonReader = new JsonReader();
        JsonValue base = jsonReader.parse(Gdx.files.internal("level" + levelToEdit + ".json"));

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
                true,
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

        this.player.getGun().remove();

        for (Spawner spawner : spawners) {
            customStage.getStage().addActor(spawner);
        }

        Gdx.input.setInputProcessor(customStage.getStage());

        customStage.getStage().addActor(this.player);
        customStage.getStage().addActor(this.portal);

        wallButton = new TextButton("Wall", new Skin(Gdx.files.internal("button_skins/uiskin.json")));
        wallButton.setX((float) Constants.SCREEN_WIDTH - 100 - wallButton.getPrefWidth() / 2);
        wallButton.setY((float) Constants.SCREEN_HEIGHT - 100);

        wallButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                tileToPlace = Tile.WALL;

                event.cancel();

                return true;
            }
        });

        portalButton = new TextButton("Portal", new Skin(Gdx.files.internal("button_skins/uiskin.json")));
        portalButton.setX((float) Constants.SCREEN_WIDTH - 100 - portalButton.getPrefWidth() / 2);
        portalButton.setY((float) Constants.SCREEN_HEIGHT - 130);

        portalButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                tileToPlace = Tile.PORTAL;

                event.cancel();

                return true;
            }
        });

        playerButton = new TextButton("Player", new Skin(Gdx.files.internal("button_skins/uiskin.json")));
        playerButton.setX((float) Constants.SCREEN_WIDTH - 100 - playerButton.getPrefWidth() / 2);
        playerButton.setY((float) Constants.SCREEN_HEIGHT - 160);

        playerButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                tileToPlace = Tile.PLAYER;

                event.cancel();

                return true;
            }
        });

        ghostSpawnerButton = new TextButton("Ghost spawner", new Skin(Gdx.files.internal("button_skins/uiskin.json")));
        ghostSpawnerButton.setX((float) Constants.SCREEN_WIDTH - 100 - ghostSpawnerButton.getPrefWidth() / 2);
        ghostSpawnerButton.setY((float) Constants.SCREEN_HEIGHT - 190);

        ghostSpawnerButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                tileToPlace = Tile.GHOST_SPAWNER;

                event.cancel();

                return true;
            }
        });

        saveButton = new TextButton("Save", new Skin(Gdx.files.internal("button_skins/uiskin.json")));
        saveButton.setX((float) Constants.SCREEN_WIDTH - 100 - saveButton.getPrefWidth() / 2);
        saveButton.setY((float) Constants.SCREEN_HEIGHT - 220);

        saveButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Save();

                event.cancel();

                return true;
            }
        });

        customStage.getStage().addActor(playerButton);
        customStage.getStage().addActor(portalButton);
        customStage.getStage().addActor(wallButton);
        customStage.getStage().addActor(ghostSpawnerButton);
        customStage.getStage().addActor(saveButton);

        customStage.getStage().addListener(new InputListener() {
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

            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                Vector3 coords = new Vector3(x, y, 0);
                customStage.getCamera().unproject(coords);
                coords.y = Math.abs(coords.y - Constants.SCREEN_HEIGHT);
                coords.x -= customStage.getCamera().position.x - (float) Constants.SCREEN_WIDTH / 2;
                coords.y += customStage.getCamera().position.y - (float) Constants.SCREEN_HEIGHT / 2;

                coords.x = (float) ((int) coords.x / 64) * 64;
                coords.y = (float) ((int) coords.y / 64) * 64;

                boolean canPlace = true;
                for (Rectangle wall : walls) {
                    if (wall.x == coords.x && wall.y == coords.y) {
                        canPlace = false;
                        break;
                    }
                }

                for (Spawner spawner : spawners) {
                    if (spawner.getSpawnerLocation().x == coords.x && spawner.getSpawnerLocation().y == coords.y) {
                        canPlace = false;
                        break;
                    }
                }

                if (player.getRectangle().x == coords.x && player.getRectangle().y == coords.y) {
                    canPlace = false;
                }

                if (button == 0 && canPlace) {
                    if (tileToPlace == Tile.WALL) {
                        walls.add(new Rectangle(
                                coords.x,
                                coords.y,
                                64,
                                64
                        ));
                    } else if (tileToPlace == Tile.GHOST_SPAWNER) {
                        Spawner spawner = new Spawner(
                                "ghost",
                                1,
                                5,
                                100,
                                coords.x / 64,
                                coords.y / 64,
                                player.getGun(),
                                player
                        );

                        spawners.add(spawner);
                        customStage.getStage().addActor(spawner);
                    } else if (tileToPlace == Tile.PLAYER) {
                        player.getRectangle().x = coords.x;
                        player.getRectangle().y = coords.y;
                    } else if (tileToPlace == Tile.PORTAL) {
                        portal.getRectangle().x = coords.x;
                        portal.getRectangle().y = coords.y;
                    }

                } else if (button == 1) {
                    for (int i = 0; i < walls.size; i++) {
                        if (walls.get(i).x <= coords.x && walls.get(i).x + 32 >= coords.x && walls.get(i).y <= coords.y && walls.get(i).y + 32 >= coords.y) {
                            walls.removeIndex(i);
                            break;
                        }
                    }
                    for (int i = 0; i < spawners.size; i++) {
                        if (spawners.get(i).getSpawnerLocation().x <= coords.x &&
                                spawners.get(i).getSpawnerLocation().x + 32 >= coords.x &&
                                spawners.get(i).getSpawnerLocation().y <= coords.y &&
                                spawners.get(i).getSpawnerLocation().y + 32 >= coords.y) {
                            spawners.get(i).remove();
                            spawners.removeIndex(i);
                            break;
                        }
                    }
                }

                return true;
            }
        });

        wallTexture = new Texture(Gdx.files.internal("wall.png"));
    }

    private void Save() {
        Json json = new Json();
        StringWriter stringWriter = new StringWriter();
        JsonWriter jsonWriter = new JsonWriter(stringWriter);

        json.setOutputType(JsonWriter.OutputType.json);
        json.setWriter(jsonWriter);
        json.writeObjectStart();
        // player
        json.writeObjectStart("player");
        json.writeValue("x", player.getRectangle().x / 64);
        json.writeValue("y", player.getRectangle().y / 64);
        json.writeValue("speed", 300);
        json.writeObjectEnd();
        // portal
        json.writeObjectStart("portal");
        json.writeValue("x", portal.getRectangle().x / 64);
        json.writeValue("y", portal.getRectangle().y / 64);
        json.writeObjectEnd();
        // spawners
        json.writeArrayStart("spawners");
        for (Spawner spawner : spawners) {
            json.writeObjectStart();
            json.writeValue("enemy", spawner.getEnemy());
            json.writeValue("spawnDelay", spawner.getSpawnDelay());
            json.writeValue("enemiesToSpawn", spawner.getEnemiesToSpawn());
            json.writeValue("x", spawner.getSpawnerLocation().x / 64);
            json.writeValue("y", spawner.getSpawnerLocation().y / 64);
            json.writeValue("speed", spawner.getSpeed());
            json.writeObjectEnd();
        }
        json.writeObjectEnd();
        // walls
        json.writeArrayStart("walls");
        for (Rectangle wall : walls) {
            json.writeObjectStart();
            json.writeValue("x", wall.x / 64);
            json.writeValue("y", wall.y / 64);
            json.writeObjectEnd();
        }
        json.writeArrayEnd();
        json.writeObjectEnd();

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("level" + levelToEdit + ".json"));
            writer.write(json.getWriter().getWriter().toString());

            writer.close();
        } catch (IOException e) {

        }
    }

    @Override
    public void show() {

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
    public void render(float delta) {
        float speed = Constants.PLAYER_SPEED;

        if (countMovementBooleans() == 2)
            speed *= 0.7f;

        if (moveLeft)
            customStage.getCamera().position.x -= speed * delta;
        if (moveRight)
            customStage.getCamera().position.x += speed * delta;
        if (moveDown)
            customStage.getCamera().position.y -= speed * delta;
        if (moveUp)
            customStage.getCamera().position.y += speed * delta;

        ScreenUtils.clear(1, 1, 1, 1);

        customStage.getStage().getBatch().begin();
        customStage.getStage().getBatch().setProjectionMatrix(customStage.getCamera().combined);

        for (Rectangle rectangle : walls)
            customStage.getStage().getBatch().draw(wallTexture, rectangle.x, rectangle.y, 64, 64);

        customStage.getStage().getBatch().end();

//        customStage.getStage().act(delta);
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
