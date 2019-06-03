package com.glowingpigeon.columbiare;

import javafx.application.*;
import javafx.event.*;
import javafx.animation.*;
import javafx.scene.*;
import javafx.scene.input.*;
import javafx.stage.*;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import com.glowingpigeon.columbiare.audio.AudioManager;
import com.glowingpigeon.columbiare.state.*;

public class Columbiare extends Application {
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

        state = new TitleState(gc);

        // Add event handlers

        EventHandler<WindowEvent> windowEventHandler = (event) -> {
            if (event.getEventType() == WindowEvent.WINDOW_CLOSE_REQUEST) {
                AudioManager.stopMusic();
            }

            state.windowEvent(event);
        };
        stage.setOnCloseRequest(windowEventHandler);
        stage.setOnHidden(windowEventHandler);
        stage.setOnHiding(windowEventHandler);
        stage.setOnShown(windowEventHandler);
        EventHandler<MouseEvent> mouseEventHandler = (event) -> {
            state.mouseEvent(event);
        };
        root.setOnMouseMoved(mouseEventHandler);
        root.setOnMousePressed(mouseEventHandler);
        root.setOnMouseReleased(mouseEventHandler);
        root.setOnMouseClicked(mouseEventHandler);
        root.setOnMouseDragged(mouseEventHandler);
        EventHandler<KeyEvent> keyEventHandler = (event) -> {
            state.keyEvent(event);
        };
        scene.setOnKeyPressed(keyEventHandler);
        scene.setOnKeyReleased(keyEventHandler);
        scene.setOnKeyTyped(keyEventHandler);

        // Start timer

        timer = new AnimationTimer() {
        
            @Override
            public void handle(long now) {
                if (now - lastFrame > FRAME_LENGTH) {
                    state.update();
                    state.render();
                    GameState next = state.getNextState();
                    if (next != null) {
                        state = next;
                    }
                    lastFrame = now;
                }
            }
        };

        root.getChildren().add(canvas);

        stage.setTitle("Columbiare");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.sizeToScene();
        stage.show();

        timer.start();

    }

    public static void main(String[] args) {
        launch(args);
    }
}