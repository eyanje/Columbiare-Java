package com.glowingpigeon.columbiare.world;

import com.glowingpigeon.columbiare.graphics.Sprite;

import javafx.scene.canvas.GraphicsContext;

public class BackgroundObject implements Renderable {
    private Sprite sprite;
    private int x;
    private int y;
    private int width;
    private int height;

    public BackgroundObject(String spritePath, int x, int y, int width, int height) {
        sprite = new Sprite(spritePath);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void update() {
        sprite.tick();
    }

    @Override
    public void render(GraphicsContext gc) {
        sprite.render(gc, x, y, width, height);
    }
    
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}