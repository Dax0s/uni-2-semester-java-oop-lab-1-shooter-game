package com.klimavicius.shooter_game.utils;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class CustomStage {
    OrthographicCamera camera;
    ScreenViewport viewport;
    Stage stage;

    public CustomStage(boolean yDown, float viewportWidth, float viewportHeight) {
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(yDown, viewportWidth, viewportHeight);

        this.viewport = new ScreenViewport(this.camera);

        this.stage = new Stage(this.viewport);
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public ScreenViewport getViewport() {
        return viewport;
    }

    public Stage getStage() {
        return stage;
    }
}
