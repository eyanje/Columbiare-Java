package com.glowingpigeon.pigeonbound;

import javafx.application.*;
import javafx.event.*;
import javafx.animation.*;
import javafx.scene.*;
import javafx.scene.input.*;
import javafx.stage.*;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import com.glowingpigeon.pigeonbound.state.*;

public class PigeonBound extends Application {
    public static final long FRAME_LENGTH = 16667;
    long lastFrame = 0;
    AnimationTimer timer;
    GraphicsContext gc;
    GameState state;

    public void start(Stage stage) {
        Group root = new Group();
        Canvas canvas = new Canvas(640, 560);
        Scene scene = new Scene(root);

        gc = canvas.getGraphicsContext2D();

        state = new TitleState();

        // Add event handlers

        EventHandler<WindowEvent> windowEventHandler = new EventHandler<WindowEvent>() {
            public void handle(WindowEvent event) {
                state.windowEvent(event);
            }
        };
        stage.setOnCloseRequest(windowEventHandler);
        stage.setOnHidden(windowEventHandler);
        stage.setOnHiding(windowEventHandler);
        stage.setOnShown(windowEventHandler);
        EventHandler<MouseEvent> mouseEventHandler = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                state.mouseEvent(event);
            }
        };
        root.setOnMouseMoved(mouseEventHandler);
        root.setOnMousePressed(mouseEventHandler);
        root.setOnMouseReleased(mouseEventHandler);
        root.setOnMouseClicked(mouseEventHandler);
        root.setOnMouseDragged(mouseEventHandler);
        EventHandler<KeyEvent> keyEventHandler = new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                state.keyEvent(event);
            }
        };
        root.setOnKeyPressed(keyEventHandler);
        root.setOnKeyReleased(keyEventHandler);
        root.setOnKeyTyped(keyEventHandler);

        // Start timer

        timer = new AnimationTimer(){
        
            @Override
            public void handle(long now) {
                if (now - lastFrame > FRAME_LENGTH) {
                    state.update();
                    state.render(gc);
                    GameState next = state.getNextState();
                    if (next != null) {
                        state = next;
                    }
                    lastFrame = now;
                }
            }
        };

        root.getChildren().add(canvas);

        stage.setTitle("PigeonBound");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.sizeToScene();
        stage.show();

        timer.start();

    }
}