package com.klimavicius.shooter_game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.klimavicius.shooter_game.screens.MainMenuScreen;

public class ShooterGame extends Game {
//	private Stage stage;
//
//	public Stage getStage() {
//		return stage;
//	}

	@Override
	public void create () {
//		stage = new Stage();
		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
	}
}
