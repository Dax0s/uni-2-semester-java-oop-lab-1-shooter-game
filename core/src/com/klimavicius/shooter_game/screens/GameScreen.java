package com.klimavicius.shooter_game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.klimavicius.shooter_game.utils.Constants;
import com.klimavicius.shooter_game.player.Player;
import com.klimavicius.shooter_game.ShooterGame;
import com.klimavicius.shooter_game.utils.CustomStage;

public class GameScreen implements Screen {
    private ShooterGame game;

    CustomStage customStage;
    Player player;

    Texture backgroundTexture;

    public GameScreen(ShooterGame game) {
        this.game = game;
        this.player = new Player(Constants.PLAYER_SPEED);

        customStage = new CustomStage(false, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

        Gdx.input.setInputProcessor(customStage.getStage());

        customStage.getStage().addActor(this.player);
        customStage.getStage().setKeyboardFocus(this.player);

        backgroundTexture = new Texture(Gdx.files.internal("background.jpeg"));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 1, 1, 1);

        Vector2 playerPos = new Vector2();
        player.getPlayerRectangle().getPosition(playerPos);

        if (customStage.getCamera().position.x < playerPos.x)
            customStage.getCamera().translate(300 * delta, 0);
        if (customStage.getCamera().position.x > playerPos.x)
            customStage.getCamera().translate(-300 * delta, 0);
        if (customStage.getCamera().position.y < playerPos.y)
            customStage.getCamera().translate(0, 300 * delta);
        if (customStage.getCamera().position.y > playerPos.y)
            customStage.getCamera().translate(0, -300 * delta);

        customStage.getStage().getBatch().begin();
        customStage.getStage().getBatch().draw(backgroundTexture, 0, 0);
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
