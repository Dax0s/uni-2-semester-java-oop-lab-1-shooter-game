package com.klimavicius.shooter_game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.klimavicius.shooter_game.Constants;
import com.klimavicius.shooter_game.ShooterGame;
import com.klimavicius.shooter_game.utils.CustomStage;

import java.awt.*;

public class GameScreen implements Screen {
    private ShooterGame game;

    CustomStage customStage;
    Texture treeImage;
    Rectangle tree;

    public GameScreen(ShooterGame game) {
        this.game = game;

        customStage = new CustomStage(false, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        Gdx.input.setInputProcessor(customStage.getStage());

        treeImage = new Texture(Gdx.files.internal("tree.png"));

        tree = new Rectangle();
        tree.x = Constants.SCREEN_WIDTH / 2 - 64 / 2;
        tree.y = Constants.SCREEN_HEIGHT / 2 - 64 / 2;
        tree.width = 64;
        tree.height = 64;

        customStage.getStage().addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.A) {
                    tree.x -= (int) (1000 * Gdx.graphics.getDeltaTime());
                } else if (keycode == Input.Keys.D) {
                    tree.x += (int) (1000 * Gdx.graphics.getDeltaTime());
                } else if (keycode == Input.Keys.S) {
                    tree.y -= (int) (1000 * Gdx.graphics.getDeltaTime());
                } else if (keycode == Input.Keys.W) {
                    tree.y += (int) (1000 * Gdx.graphics.getDeltaTime());
                }

                return true;
            }
        });
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 1, 1, 1);

//        customStage.getCamera().update();

        if (tree.x < 0)
            tree.x = 0;
        if (tree.x > Constants.SCREEN_WIDTH - 64)
            tree.x = Constants.SCREEN_WIDTH - 64;

//        customStage.getStage().getBatch().setProjectionMatrix(customStage.getCamera().combined);
        customStage.getStage().getBatch().begin();
        customStage.getStage().getBatch().draw(treeImage, tree.x, tree.y);
        customStage.getStage().getBatch().end();

//        customStage.getStage().act(delta);
//        customStage.getStage().draw();
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
