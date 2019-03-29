package com.glowingpigeon.pigeonbound.state;

import javafx.scene.canvas.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import com.glowingpigeon.pigeonbound.graphics.*;

public class TitleState extends GameState {
    Sprite background;

    public TitleState() {
        background = new Sprite();
    }

    @Override
    public void mouseEvent(MouseEvent e) {
        if (e.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {
            System.out.println("Transition");
        }
    }

    @Override
    public void keyEvent(KeyEvent e) {
        if (e.getEventType().equals(KeyEvent.KEY_RELEASED)) {
            System.out.println("Transition");
        }
    }
    
    @Override
    public void update() {
        
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.fillText("text", 10, 10);
        background.render(gc, 0, 0);
    }
}