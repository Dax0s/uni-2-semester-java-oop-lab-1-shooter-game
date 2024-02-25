package com.klimavicius.shooter_game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.ScreenUtils;
import com.klimavicius.shooter_game.utils.Constants;
import com.klimavicius.shooter_game.ShooterGame;
import com.klimavicius.shooter_game.utils.CustomStage;

public class MainMenuScreen implements Screen {
    private final ShooterGame game;

    private final CustomStage customStage;
    private final Button button;
    private final Button editorButton;

    public MainMenuScreen(ShooterGame game) {
        this.game = game;

        customStage = new CustomStage(false, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

        Gdx.input.setInputProcessor(customStage.getStage());

        button = new TextButton("Start Game", new Skin(Gdx.files.internal("button_skins/uiskin.json")));
        button.setX((float) Constants.SCREEN_WIDTH / 2 - button.getPrefWidth() / 2);
        button.setY((float) Constants.SCREEN_HEIGHT / 2 - button.getPrefHeight() / 2);

        button.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new GameScreen(game));
                dispose();

                return true;
            }
        });

        editorButton = new TextButton("Editor", new Skin(Gdx.files.internal("button_skins/uiskin.json")));
        editorButton.setX((float) Constants.SCREEN_WIDTH / 2 - editorButton.getPrefWidth() / 2);
        editorButton.setY((float) Constants.SCREEN_HEIGHT / 2 - editorButton.getPrefHeight() / 2 - 50);

        editorButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new EditorScreen(game));
                dispose();

                return true;
            }
        });

        customStage.getStage().addActor(button);
        customStage.getStage().addActor(editorButton);
    }



    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 0);

        customStage.getStage().act(delta);
        customStage.getStage().draw();
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
        customStage.getStage().dispose();
    }
}
