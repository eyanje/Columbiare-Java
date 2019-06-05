package com.glowingpigeon.columbiare.state;

import com.glowingpigeon.columbiare.audio.AudioManager;
import com.glowingpigeon.columbiare.data.*;
import com.glowingpigeon.columbiare.world.entity.NPC;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import javafx.stage.WindowEvent;

public class PlayState extends GameState {
    Section section;
    Save save;
    
    public PlayState(GraphicsContext graphicsContext) {
        super(graphicsContext);

        save = new Save();

        section = save.getSection(graphicsContext); //= new SetSection(save, "setsections/a1s1.set", graphicsContext);
    }

    public PlayState(String savePath, GraphicsContext graphicsContext) {
        super(graphicsContext);

        save = new Save(savePath);
        section = save.getSection(graphicsContext); //new PlaySection(save, graphicsContext);
        AudioManager.stopMusic();
    }

    public void mouseEvent(MouseEvent e) {
        if (section instanceof PlaySection) {
            ((PlaySection) section).mouseEvent(e);
        }
    }
    public void keyEvent(KeyEvent e) {
        section.keyEvent(e);
    }

    public void windowEvent(WindowEvent event) {
        save.setSection(section);
        save.save();
    }

    @Override
    public void update() {
        section.update(save);
        
        for (NPC npc : save.getNPCs()) {
            npc.update();
        }

        section = section.getNextSection();

    }

    @Override
    public void render() {
        GraphicsContext gc = getGraphicsContext();

        gc.setTransform(1, 0, 0, 1, 0, 0);
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        gc.setFill(Color.rgb(6, 0, 12));
        gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        
        section.render();
    }
}