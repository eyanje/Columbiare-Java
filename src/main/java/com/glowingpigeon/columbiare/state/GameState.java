package com.glowingpigeon.columbiare.state;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.*;
import javafx.stage.*;

public abstract class GameState {
    private GraphicsContext graphicsContext;

    public GameState(GraphicsContext graphicsContext) {
        this.graphicsContext = graphicsContext;
    }

    public void windowEvent(WindowEvent e) {}
    public void mouseEvent(MouseEvent e) {}
    public void keyEvent(KeyEvent e) {}

    public GameState getNextState() { return this; }

    public void update() {}
    public void render() {}

    public GraphicsContext getGraphicsContext() { return graphicsContext; }
    public int getWidth() { return (int) graphicsContext.getCanvas().getWidth(); }
    public int getHeight() { return (int) graphicsContext.getCanvas().getHeight(); }
}