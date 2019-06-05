package com.glowingpigeon.columbiare.state.premade;

import java.util.Deque;

import com.glowingpigeon.columbiare.audio.AudioManager;
import com.glowingpigeon.columbiare.data.Save;
import com.glowingpigeon.columbiare.state.PlaySection;
import com.glowingpigeon.columbiare.state.Section;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;

public class A2_0 extends PlaySection {
    private boolean transition;

    public A2_0(Save save, GraphicsContext graphicsContext) {
        super(save, graphicsContext);
        // TODO Auto-generated constructor stub

        save.getNPCByName("Amia").setPosition(6114, 464);
        save.getNPCByName("Amia").getSprite().setCurrentAnimation("dead");
        save.getNPCByName("Amia").getDataContainer().clear();        
        
        AudioManager.playMusic("audio/act1.au");
        
        getSave().setProgress("a2s1");
        transition = false;
    }

    @Override
    public void update(Save save) {
        super.update(save);
        
        
        Deque<String> data = save.getNPCByName("Amia").getDataContainer();
        //System.out.println(data);
        if (!data.isEmpty() && "dead".equals(data.peekFirst())) {
            transition = true;
        }
    }

    public Section getNextSection() {
        if (transition) {
            return new A3(getSave(), getGraphicsContext());
        }
        return this;
    }

}