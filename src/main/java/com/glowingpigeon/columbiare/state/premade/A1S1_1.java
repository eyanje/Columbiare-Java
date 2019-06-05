package com.glowingpigeon.columbiare.state.premade;

import com.glowingpigeon.columbiare.audio.AudioManager;
import com.glowingpigeon.columbiare.data.*;
import com.glowingpigeon.columbiare.state.*;

import javafx.scene.canvas.GraphicsContext;


public class A1S1_1 extends PlaySection {
    private boolean transition;

    public A1S1_1(Save save, GraphicsContext graphicsContext) {
        super(save, graphicsContext);
        
        save.recomputeSolids();

        transition = false;
        AudioManager.playMusic("audio/act1.au");

    }

    @Override
    public void update(Save save) {
        super.update(save);
        if (save.getPlayer().getX() > 4096 && save.getPlayer().getY() <= 468) {
            transition = true;
            save.setProgress("a1s2");
        }
    }

    public Section getNextSection() {
        if (transition) {
            return new A1S2(getSave(), getGraphicsContext());
        }
        return this;
    }
}