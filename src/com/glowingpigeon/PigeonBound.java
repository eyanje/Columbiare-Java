package com.glowingpigeon;

import javafx.application.*;
import javafx.scene.*;
import javafx.stage.*;

import com.glowingpigeon.state.*;

public class PigeonBound extends Application {
    GameState current;

    public void start(Stage stage) {
        Group root = new Group();
        Scene scene = new Scene();
        current = new TitleState();

        stage.setTitle("PigeonBound");
        stage.setScene(scene);
        stage.show();
    }
}