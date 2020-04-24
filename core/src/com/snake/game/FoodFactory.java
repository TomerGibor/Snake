package com.snake.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

public class FoodFactory {
    private Texture texture;
    private int width, height;
    private float x, y;
    private Random rnd;

    public FoodFactory() {
        this.x = 0;
        this.y = 0;

        if (Game.SCREEN_HEIGHT == 1440) {
            texture = new Texture("apple.png");
        } else if (Game.SCREEN_HEIGHT == 1080 || Game.SCREEN_HEIGHT == 1200) {
            texture = new Texture("small_apple.png");
        }
        width = texture.getWidth();
        height = texture.getHeight();
        rnd = new Random();
        rnd.setSeed(System.currentTimeMillis());
        generatePos();
    }

    public void generatePos() {
        int wBoxes = Game.SCREEN_WIDTH / width;
        int hBoxes = Game.SCREEN_HEIGHT / height;
        this.x = rnd.nextInt(wBoxes) * width * 1.0f;
        this.y = rnd.nextInt(hBoxes) * height * 1.0f;
    }

    public boolean checkCollision(float x, float y) {
        if (this.x == x && this.y == y) {
            generatePos();
            return true;
        }
        return false;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(this.texture, this.x, this.y);
    }

    public void dispose() {
        this.texture.dispose();
    }
}
