package com.glowingpigeon.pigeonbound.state;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.*;
import javafx.stage.*;

public abstract class GameState {
    public void windowEvent(WindowEvent e) {}
    public void mouseEvent(MouseEvent e) {}
    public void keyEvent(KeyEvent e) {}

    public GameState getNextState() { return null; }

    public void update() {}
    public void render(GraphicsContext gc) {}
}