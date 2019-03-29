package com.glowingpigeon.pigeonbound;

import javafx.application.*;
import javafx.scene.*;
import javafx.stage.*;

import com.glowingpigeon.pigeonbound.state.*;

public class PigeonBound extends Application {
    GameState current;

    public void start(Stage stage) {
        Group root = new Group();
        Scene scene = new Scene(root);
        current = new TitleState();

        stage.setTitle("PigeonBound");
        stage.setScene(scene);
        stage.show();
    }
}