package com.glowingpigeon.columbiare.state.premade;

import java.awt.image.RescaleOp;

import com.glowingpigeon.columbiare.audio.AudioManager;
import com.glowingpigeon.columbiare.data.ResourceManager;
import com.glowingpigeon.columbiare.data.Save;
import com.glowingpigeon.columbiare.state.Section;
import javafx.scene.paint.Color;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;

public class End extends Section {

    public End(Save save, GraphicsContext graphicsContext) {
        super(save, graphicsContext);
        AudioManager.playMusic("audio/meme.au");
    }

    @Override
    public void keyEvent(KeyEvent e) {

    }

    @Override
    public void render() {
        GraphicsContext gc = getGraphicsContext();

        gc.setTransform(1, 0, 0, 1, 0, 0);

        gc.setFill(Color.WHITE);
        
        gc.setFont(ResourceManager.getFont("Gabriola.ttf", 48));
        gc.fillText("You're an idiot.", gc.getCanvas().getWidth() / 2, gc.getCanvas().getHeight() / 2);
    }
}