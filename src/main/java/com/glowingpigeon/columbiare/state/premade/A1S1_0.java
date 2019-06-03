package com.glowingpigeon.columbiare.state.premade;

import com.glowingpigeon.columbiare.audio.AudioManager;
import com.glowingpigeon.columbiare.data.*;
import com.glowingpigeon.columbiare.state.*;

import javafx.scene.canvas.*;

public class A1S1_0 extends SetSection {
    public A1S1_0(Save save, GraphicsContext graphicsContext) {
        super(save, "sections/a1s1-0.set", graphicsContext);

        AudioManager.playMusic("audio/act1.au");

        save.recomputeSolids();
    }

}