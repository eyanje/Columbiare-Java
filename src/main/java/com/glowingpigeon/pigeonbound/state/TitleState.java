package com.glowingpigeon.pigeonbound.state;

import javafx.scene.canvas.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import com.glowingpigeon.pigeonbound.graphics.*;

public class TitleState extends GameState {
    Sprite background;
    boolean transition;

    public TitleState() {
        background = new Sprite("sprites/title/title.spr");
        transition = false;
    }

    @Override
    public void mouseEvent(MouseEvent e) {
        if (e.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {
            System.out.println("Transition");
            transition = true;
        }
    }

    @Override
    public void keyEvent(KeyEvent e) {
        if (e.getEventType().equals(KeyEvent.KEY_RELEASED)) {
            System.out.println("Transition");
            transition = true;
        }
    }
    
    @Override
    public GameState getNextState() {
        if (transition) {
            return new MenuState();
        }
        return null;
    }
    
    @Override
    public void update() {
        
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());

        gc.fillText("text", 10, 10);
        background.render(gc, 0, 0);
    }
}