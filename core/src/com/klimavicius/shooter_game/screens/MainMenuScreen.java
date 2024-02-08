package com.klimavicius.shooter_game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.klimavicius.shooter_game.Constants;
import com.klimavicius.shooter_game.ShooterGame;
import sun.tools.jconsole.JConsole;

public class MainMenuScreen implements Screen {
    private final ShooterGame game;

    OrthographicCamera camera;
    ScreenViewport viewport;
    Stage stage;
    Button button;

    public MainMenuScreen(ShooterGame game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

        viewport = new ScreenViewport(camera);

        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        button = new TextButton("Start Game", new Skin(Gdx.files.internal("button_skins/uiskin.json")));
        button.setX(Constants.SCREEN_WIDTH / 2 - button.getPrefWidth() / 2);
        button.setY(Constants.SCREEN_HEIGHT / 2 - button.getPrefHeight() / 2);

        button.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Button pressed");

                return true;
            }
        });

        stage.addActor(button);
    }



    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 0);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void show() {

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
        stage.dispose();
    }
}
