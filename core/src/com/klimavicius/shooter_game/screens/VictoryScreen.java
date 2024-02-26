package com.klimavicius.shooter_game.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Bits;
import com.badlogic.gdx.utils.ScreenUtils;
import com.klimavicius.shooter_game.utils.Constants;
import com.klimavicius.shooter_game.utils.CustomStage;

public class VictoryScreen implements Screen {

    private final CustomStage customStage;
    private final BitmapFont font;

    public VictoryScreen() {
        customStage = new CustomStage(false, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

        font = new BitmapFont();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        customStage.getStage().getBatch().begin();
        customStage.getStage().getBatch().setProjectionMatrix(customStage.getCamera().combined);

        font.draw(customStage.getStage().getBatch(), "You've won!", (float) Constants.SCREEN_WIDTH / 2, (float) Constants.SCREEN_HEIGHT / 2);

        customStage.getStage().getBatch().end();
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
