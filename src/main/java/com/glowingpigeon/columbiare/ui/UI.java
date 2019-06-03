package com.glowingpigeon.columbiare.ui;

import javafx.scene.canvas.*;

public class UI {
    private TextBox textBox;
    private GraphicsContext graphicsContext;

    public UI(GraphicsContext graphicsContext) {
        this.graphicsContext = graphicsContext;
        int width = (int) graphicsContext.getCanvas().getWidth();
        int height = (int) graphicsContext.getCanvas().getHeight();
        textBox = new TextBox(graphicsContext, 0, height * 3 / 4, width, height / 4);
    }

    public TextBox getTextBox() {
        return textBox;
    }

    public void update() {
        textBox.advanceChar();
    }

    public void interact() {
        if (textBox.hasLines()) {
            textBox.advanceLine();
        } else {
            textBox.setVisible(false);
        }
    }

    public boolean isOpen() {
        return textBox.isVisible();
    }

    public void render() {
        graphicsContext.setTransform(1, 0, 0, 1, 0, 0);

        textBox.render(graphicsContext);
    }
}