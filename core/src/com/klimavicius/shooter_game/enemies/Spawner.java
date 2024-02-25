package com.klimavicius.shooter_game.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.klimavicius.shooter_game.player.Bullet;
import com.klimavicius.shooter_game.player.Gun;


public class Spawner extends Actor {
    private final String enemy;
    private final float spawnDelay;
    private int enemiesToSpawn;
    private final Vector2 spawnerLocation;

    private final Texture spawnerTexture;
    private final Texture enemyTexture;

    private Array<Rectangle> enemies = new Array<Rectangle>();
    private final Gun gun;

    private float timeSinceLastSpawn;

    public Spawner(String enemy, float spawnDelay, int enemiesToSpawn, float x, float y, Gun gun) {
        this.enemy = enemy;
        this.spawnDelay = spawnDelay;
        this.enemiesToSpawn = enemiesToSpawn;
        this.spawnerLocation = new Vector2(x * 64, y * 64);

        this.gun = gun;

        this.spawnerTexture = new Texture(Gdx.files.internal(enemy + "_spawner.png"));
        this.enemyTexture = new Texture(Gdx.files.internal(enemy + ".png"));
    }

    private void spawnEnemy() {

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(spawnerTexture, spawnerLocation.x, spawnerLocation.y, 64, 64);

        for (Rectangle enemy: enemies) {
            batch.draw(enemyTexture, spawnerLocation.x, spawnerLocation.y, 64, 64);
        }
    }

    @Override
    public void act(float delta) {
        if (timeSinceLastSpawn > 0)
            timeSinceLastSpawn -= delta;

        if (enemiesToSpawn > 0 && timeSinceLastSpawn <= 0) {
            timeSinceLastSpawn = spawnDelay;
            enemies.add(new Rectangle(spawnerLocation.x, spawnerLocation.y, 64, 64));
            enemiesToSpawn--;
        }

        for (int i = this.gun.getBullets().size - 1; i >= 0; i--) {
            for (int j = enemies.size - 1; j >= 0; j--) {
                if (enemies.get(j).overlaps(this.gun.getBullets().get(i).getRectangle())) {
                    enemies.removeIndex(j);
                    this.gun.getBullets().removeIndex(i);
                    break;
                }
            }
        }
    }
}
