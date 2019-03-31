package com.glowingpigeon.pigeonbound.state;

import com.glowingpigeon.pigeonbound.data.*;

import javafx.scene.canvas.GraphicsContext;

public class PlayState extends GameState {
    Save save;
    
    public PlayState() {
        save = new Save();
    }

    public PlayState(String savePath) {
        save = new Save(savePath);
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
    }
}