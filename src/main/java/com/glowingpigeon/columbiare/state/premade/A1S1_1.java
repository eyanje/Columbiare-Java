package com.glowingpigeon.columbiare.state.premade;

import com.glowingpigeon.columbiare.data.*;
import com.glowingpigeon.columbiare.state.*;

import javafx.scene.canvas.*;

public class A1S1_1 extends PlaySection {

    public A1S1_1(Save save, GraphicsContext graphicsContext) {
        super(save, graphicsContext);
        
        save.recomputeSolids();
    }

    @Override
    public void update(Save save) {
        super.update(save);
    }

    public Section getNextSection() {
        return this;
    }
}