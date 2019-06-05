package com.glowingpigeon.columbiare.state.premade;

import java.util.Deque;

import com.glowingpigeon.columbiare.audio.AudioManager;
import com.glowingpigeon.columbiare.data.Save;
import com.glowingpigeon.columbiare.state.PlaySection;
import com.glowingpigeon.columbiare.state.Section;

import javafx.scene.canvas.GraphicsContext;

public class A1S2 extends PlaySection {
    private boolean transition;

    public A1S2(Save save, GraphicsContext graphicsContext) {
        super(save, graphicsContext);
        
        save.getNPCByName("Amia").setPosition(5024, 3264);

        save.recomputeSolids();
        AudioManager.playMusic("audio/act1.au");

        if (save.getPlayer().getY() > 1024) {
            // Play act 2 later
        }
        transition = false;
    }

    @Override
    public void update(Save save) {
        super.update(save);

        Deque<String> data = save.getNPCByName("Fara").getDataContainer();
        //System.out.println(data);
        if (!data.isEmpty() && "received".equals(data.peekFirst())) {
            transition = true;
        }
    }

    public Section getNextSection() {
        if (transition) {
            return new A2_0(getSave(), getGraphicsContext());
        }
        return this;
    }

}