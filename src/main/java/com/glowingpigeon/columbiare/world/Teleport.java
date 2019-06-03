package com.glowingpigeon.columbiare.world;

import javafx.scene.canvas.GraphicsContext;

public class Teleport extends BackgroundObject {
    int destX, destY;

    public Teleport(int x, int y, int width, int height, int destX, int destY) {
        super(null, x, y, width, height);
    }
    
    public void update() {
        // Do nothing
    }

    @Override
    public void render(GraphicsContext gc) {
        // Do nothing
    }

    public int getDestX() {
        return destX;
    }

    public int getDestY() {
        return destY;
    }
}