package com.glowingpigeon.columbiare.state;

import com.glowingpigeon.columbiare.data.ResourceManager;
import com.glowingpigeon.columbiare.graphics.*;

import javafx.scene.canvas.*;
import javafx.scene.input.*;
import javafx.scene.paint.Color;

public class MenuState extends GameState {
    private Sprite background;
    private int menuOption;
    private boolean transition;
    private Pattern bgPattern;

    public MenuState(GraphicsContext graphicsContext) {
        super(graphicsContext);

        background = new Sprite("sprites/menu/menu.spr");
        menuOption = 0;
        transition = false;
        bgPattern = new Pattern("sprites/ppattern.png");
    }

    public GameState getNextState() {
        if (transition) {
            switch (menuOption) {
                case 0: {
                    return new PlayState(getGraphicsContext());
                }
                case 1: {
                    return new PlayState("save.sv", getGraphicsContext());
                }
            }
        }
        return this;
    }

    @Override
    public void mouseEvent(MouseEvent e) {
        
    }

    @Override
    public void keyEvent(KeyEvent e) {
        if (e.getEventType() == KeyEvent.KEY_PRESSED) {
            switch (e.getCode()) {
                case UP:
                case DOWN: {
                    menuOption = (menuOption + 1) % 2;
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
    public void render() {
        GraphicsContext gc = getGraphicsContext();

        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());

        bgPattern.render(gc, 0, 0, (int) gc.getCanvas().getWidth(), (int) gc.getCanvas().getHeight());

        gc.setFont(ResourceManager.getFont("Gabriola.ttf", 108));
        gc.fillText("Main Menu", 60, 140);

        gc.setFont(ResourceManager.getFont("default.ttf", 18));

        gc.setFill(Color.rgb(208, 145, 240));
        gc.fillOval(20, 280 + (80 * menuOption), 20, 20);
        gc.strokeOval(20, 280 + (80 * menuOption), 20, 20);
        gc.setFill(Color.BLACK);
        gc.fillText("New Game", 60, 280 + 12 + 4.5);
        gc.fillText("Continue", 60, 360 + 12 + 4.5);
    }
}