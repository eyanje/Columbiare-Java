package com.glowingpigeon.columbiare.state.premade;

import java.util.Deque;

import com.glowingpigeon.columbiare.audio.AudioManager;
import com.glowingpigeon.columbiare.data.Save;
import com.glowingpigeon.columbiare.state.PlaySection;
import com.glowingpigeon.columbiare.state.Section;

import javafx.scene.canvas.GraphicsContext;

public class A3 extends PlaySection {

    private boolean transition;

    public A3(Save save, GraphicsContext graphicsContext) {
        super(save, graphicsContext);

        AudioManager.stopMusic();
        
        getSave().setProgress("a3s1");

        save.getNPCByName("Fara").getDataContainer().clear();
    }

    public void update(Save save) {
        super.update(save);

        Deque<String> data = save.getNPCByName("Fara").getDataContainer();
        //System.out.println(data);
        if (!data.isEmpty() && "reveal".equals(data.peekFirst())) {
            transition = true;
        }
    }


    public Section getNextSection() {
        if (transition) {
            return new End(getSave(), getGraphicsContext());
        }
        return this;
    }
}