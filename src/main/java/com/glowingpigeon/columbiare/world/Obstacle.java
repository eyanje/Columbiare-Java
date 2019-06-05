package com.glowingpigeon.columbiare.world;

import com.glowingpigeon.columbiare.graphics.*;

import javafx.scene.canvas.*;

/**
 * A rectangular object which just gets in the Player's way
 */
public class Obstacle implements SolidObject, Renderable {
    private Sprite sprite;
    private int x;
    private int y;
    private int width;
    private int height;
    private String description;

    public Obstacle(String spritePath, int x, int y, int width, int height, String description) {
        sprite = new Sprite(spritePath);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.description = description;
    }

    public void update() {
        sprite.tick();
    }

    @Override
    public void render(GraphicsContext gc) {
        //gc.setFill(new Color(0, 1, 0, 0.2));
        //gc.fillRect(x, y, width, height);
        sprite.render(gc, x, y, width, height);
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public void setWidth(int width) {
        if (width >= 0) {
            this.width = width;
        }
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void setHeight(int height) {
        if (height >= 0) {
            this.height = height;
        }
    }

    public boolean contains(int x, int y) {
        return x >= getX() && x <= getX() + getWidth() &&
        y >= getY() && y <= getY() + getHeight();
    }

    public String getDescription() {
        return description;
    }
}