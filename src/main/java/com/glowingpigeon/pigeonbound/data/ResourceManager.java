package com.glowingpigeon.pigeonbound.data;

import java.io.InputStream;
import java.util.*;

import com.glowingpigeon.pigeonbound.PigeonBound;

import javafx.scene.image.*;
import javafx.scene.text.Font;

public final class ResourceManager {
    static HashMap<String, Image> images = new HashMap<>();
    static HashMap<String, Font> fonts = new HashMap<>();
    
    private ResourceManager() {}

    public static Image getImage(String path) {
        InputStream stream = PigeonBound.class.getClassLoader().getResourceAsStream(path);
        Image image = null;

        if (stream == null) {
            System.err.println("No image found at " + path);
            System.err.println("Base Path: " + PigeonBound.class.getClassLoader().getResource("."));
        } else {
            image = new javafx.scene.image.Image(stream);
        }

        images.put(path, image);

        return image;
    }
}