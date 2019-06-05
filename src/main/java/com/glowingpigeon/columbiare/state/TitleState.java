package com.glowingpigeon.columbiare.state;

import javafx.scene.canvas.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import com.glowingpigeon.columbiare.graphics.*;
import com.glowingpigeon.columbiare.audio.*;

public class TitleState extends GameState {
    Sprite background;
    boolean transition;

    public TitleState(GraphicsContext graphicsContext) {
        super(graphicsContext);

        background = new Sprite("sprites/title/title.spr");
        transition = false;

        AudioManager.playMusic("audio/title.au");
    }

    @Override
    public void keyEvent(KeyEvent e) {
        if (e.getEventType().equals(KeyEvent.KEY_RELEASED) && e.getCode() == KeyCode.Z) {
            transition = true;
        }
    }
    
    @Override
    public GameState getNextState() {
        if (transition) {
            return new MenuState(getGraphicsContext());
        }
        return this;
    }
    
    @Override
    public void update() {
        background.tick();
    }

    @Override
    public void render() {
        GraphicsContext gc = getGraphicsContext();

        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());

        background.render(gc, 0, 0, (int) gc.getCanvas().getWidth(), (int) gc.getCanvas().getHeight());
    }
}