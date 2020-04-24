package com.snake.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import sun.rmi.runtime.Log;

public class Game extends ApplicationAdapter {
    SpriteBatch batch;
    Snake snake;
    static FoodFactory factory;
    static int tick = 0;
    public static int SCREEN_WIDTH, SCREEN_HEIGHT;
    public static int score, highScore;
    private BitmapFont scoreFont, highScoreFont;
    private Preferences prefs;

    @Override
    public void create() {
        prefs = Gdx.app.getPreferences("high_score");
        highScore = prefs.getInteger("high_score");
        batch = new SpriteBatch();
        SCREEN_WIDTH = Gdx.graphics.getWidth();
        SCREEN_HEIGHT = Gdx.graphics.getHeight();
        if (Game.SCREEN_HEIGHT == 1440)
            snake = new Snake(SCREEN_WIDTH / 2 - ((SCREEN_WIDTH / 2) % 100),
                    SCREEN_HEIGHT / 2 - ((SCREEN_HEIGHT / 2) % 100));
        else
            snake = new Snake(SCREEN_WIDTH / 2 - ((SCREEN_WIDTH / 2) % 80),
                    SCREEN_HEIGHT / 2 - ((SCREEN_HEIGHT / 2) % 80));
        factory = new FoodFactory();
        score = 0;
        this.scoreFont = new BitmapFont();
        this.highScoreFont = new BitmapFont();
        if (SCREEN_HEIGHT == 1440) {
            scoreFont.getData().setScale(5, 5);
            highScoreFont.getData().setScale(5, 5);
        }
        else if (SCREEN_HEIGHT == 1080 || SCREEN_HEIGHT == 1200){
            scoreFont.getData().setScale(2.75f, 2.75f);
            highScoreFont.getData().setScale(2.75f, 2.75f);
        }
        initGesture();
    }

    public void initGesture() {
        Gdx.input.setInputProcessor(new SimpleGestureDetector(new SimpleGestureDetector.DirectionListener() {
            @Override
            public void onLeft() {
                snake.move(180);
            }

            @Override
            public void onRight() {
                snake.move(0);
            }

            @Override
            public void onUp() {
                snake.move(90);
            }

            @Override
            public void onDown() {
                snake.move(270);
            }
        }));
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (tick % 10 == 0) {
            snake.update();
            if (factory.checkCollision(snake.getX(), snake.getY())) {
                snake.addBP();
                score++;
                if (score > highScore) {
                    highScore = score;
                    prefs.putInteger("high_score", highScore);
                    prefs.flush();
                }
            }
        }
        batch.begin();
        factory.draw(batch);
        snake.draw(batch);
        if (SCREEN_HEIGHT == 1440) {
            scoreFont.draw(batch, "Score: " + score, 80, SCREEN_HEIGHT - 20);
            highScoreFont.draw(batch, "High Score: " + highScore, SCREEN_WIDTH - 550, SCREEN_HEIGHT - 20);
        } else if (SCREEN_HEIGHT == 1080 || SCREEN_HEIGHT == 1200) {
            scoreFont.draw(batch, "Score: " + score, 50, SCREEN_HEIGHT - 20);
            highScoreFont.draw(batch, "High Score: " + highScore, SCREEN_WIDTH - 340, SCREEN_HEIGHT - 20);
        }
        batch.end();
        tick++;
    }

    @Override
    public void dispose() {
        batch.dispose();
        snake.dispose();
        factory.dispose();
        scoreFont.dispose();
        highScoreFont.dispose();
    }
}
