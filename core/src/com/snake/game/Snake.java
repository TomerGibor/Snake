package com.snake.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class Snake {
    private float x, y, dx, dy;
    private int width, height;
    private Texture texture;
    Directions dir;

    private ArrayList<BodyPart> bodyParts;

    public Snake(float x, float y) {
        this.x = x;
        this.y = y;
        this.dx = 0;
        this.dy = 0;
        this.dir = Directions.STOP;
        if (Game.SCREEN_HEIGHT == 1440)
            texture = new Texture("green_square.jpg");

        else if (Game.SCREEN_HEIGHT == 1080 || Game.SCREEN_HEIGHT == 1200)
            texture = new Texture("green_square_small.jpg");
        width = texture.getWidth();
        height = texture.getHeight();

        bodyParts = new ArrayList<BodyPart>();
    }

    public void move(int heading) {
        switch (heading) {
            case 0:
                if (this.dir != Directions.LEFT) {
                    this.dy = 0;
                    this.dx = width;
                    this.dir = Directions.RIGHT;
                }
                break;
            case 180:
                if (this.dir != Directions.RIGHT) {
                    this.dy = 0;
                    this.dx = -width;
                    this.dir = Directions.LEFT;
                }
                break;
            case 90:
                if (this.dir != Directions.DOWN) {
                    this.dx = 0;
                    this.dy = height;
                    this.dir = Directions.UP;
                }
                break;
            case 270:
                if (this.dir != Directions.UP) {
                    this.dx = 0;
                    this.dy = -height;
                    this.dir = Directions.DOWN;
                }
                break;
        }
    }

    public void update() {
        this.x += this.dx;
        this.y += this.dy;
        if (this.x + width > Game.SCREEN_WIDTH || this.x < 0 || this.y + height > Game.SCREEN_HEIGHT
                || this.y < 0) {
            resetSnake();
        }

        for (int i = bodyParts.size() - 1; i > 0; i--) {
            bodyParts.get(i).setX(bodyParts.get(i - 1).getX());
            bodyParts.get(i).setY(bodyParts.get(i - 1).getY());

            if (bodyParts.get(i).getX() == this.x && bodyParts.get(i).getY() == this.y) {
                resetSnake();
                return;
            }
        }
        if (bodyParts.size() > 0) {
            switch (this.dir) {
                case UP:
                    bodyParts.get(0).setX(this.x);
                    bodyParts.get(0).setY(this.y - height);
                    break;
                case DOWN:
                    bodyParts.get(0).setX(this.x);
                    bodyParts.get(0).setY(this.y + height);
                    break;
                case RIGHT:
                    bodyParts.get(0).setX(this.x - width);
                    bodyParts.get(0).setY(this.y);
                    break;
                case LEFT:
                    bodyParts.get(0).setX(this.x + width);
                    bodyParts.get(0).setY(this.y);
                    break;
            }
        }
    }

    public void draw(SpriteBatch batch) {
        batch.draw(this.texture, this.x, this.y);
        for (BodyPart b : bodyParts) {
            batch.draw(this.texture, b.getX(), b.getY());
        }
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void addBP() {
        this.bodyParts.add(new BodyPart(this.x, this.y));
    }

    public void dispose() {
        this.texture.dispose();
    }

    public void resetSnake() {
        this.x = Game.SCREEN_WIDTH / 2 - ((Game.SCREEN_WIDTH / 2) % width);
        this.y = Game.SCREEN_HEIGHT / 2 - ((Game.SCREEN_HEIGHT / 2) % height);
        this.dx = 0;
        this.dy = 0;
        this.dir = Directions.STOP;
        this.bodyParts.clear();
        Game.factory.generatePos();
        Game.score = 0;
    }

    public class BodyPart {
        private float x, y;

        public BodyPart(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public float getX() {
            return x;
        }

        public void setX(float x) {
            this.x = x;
        }

        public float getY() {
            return y;
        }

        public void setY(float y) {
            this.y = y;
        }
    }
}
