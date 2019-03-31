package com.glowingpigeon.pigeonbound.state;

import com.glowingpigeon.pigeonbound.graphics.*;

import javafx.scene.canvas.*;
import javafx.scene.input.*;
import javafx.scene.text.*;

public class MenuState extends GameState {
    private Sprite background;
    private int menuOption;
    private boolean transition;

    public MenuState() {
        background = new Sprite("/sprites/menu/menu.spr");
        menuOption = 0;
        transition = false;
    }

    public GameState getNextState() {
        if (transition) {
            switch (menuOption) {
                case 0: {
                    return new PlayState();
                }
                case 1: {
                    return new PlayState("/save.sv");
                }
                case 2: {
                    return new SettingsState();
                }
            }
        }
        return null;
    }

    @Override
    public void mouseEvent(MouseEvent e) {
        
    }

    @Override
    public void keyEvent(KeyEvent e) {
        if (e.getEventType() == KeyEvent.KEY_PRESSED) {
            switch (e.getCode()) {
                case UP: {
                    // Subtract 1
                    menuOption = (menuOption + 2) % 3;
                }
                break;
                case DOWN: {
                    menuOption = (menuOption + 1) % 3;
                }
                break;
                default:
                // Do nothing
                break;
            }
        } else if (e.getEventType().equals(KeyEvent.KEY_RELEASED)) {
            if (e.getCode() == KeyCode.Z) {
                transition = true;
            }
        }
    }

    @Override
    public void update() {
        background.tick();
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());

        gc.setFont(Font.font(24));

        gc.fillOval(20, 280 + (80 * menuOption), 20, 20);
        gc.fillText("New Game", 60, 280 + 12);
        gc.fillText("Continue", 60, 360 + 12);
        gc.fillText("Settings", 60, 440 + 12);
    }
}