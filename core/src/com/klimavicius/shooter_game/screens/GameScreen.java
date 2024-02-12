package com.klimavicius.shooter_game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;
import com.klimavicius.shooter_game.utils.Constants;
import com.klimavicius.shooter_game.player.Player;
import com.klimavicius.shooter_game.ShooterGame;
import com.klimavicius.shooter_game.utils.CustomStage;

public class GameScreen implements Screen {
    private ShooterGame game;

    CustomStage customStage;
    Player player;

    public GameScreen(ShooterGame game) {
        this.game = game;
        this.player = new Player(Constants.PLAYER_SPEED);

        customStage = new CustomStage(false, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

        Gdx.input.setInputProcessor(customStage.getStage());

        customStage.getStage().addActor(this.player);
        customStage.getStage().setKeyboardFocus(this.player);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 1, 1, 1);

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
