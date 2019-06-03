package com.glowingpigeon.columbiare.data;

import java.io.*;
import java.util.*;

import com.glowingpigeon.columbiare.Columbiare;

import javafx.scene.image.*;
import javafx.scene.text.Font;

import java.nio.charset.StandardCharsets;

public final class ResourceManager {
    static HashMap<String, Image> images = new HashMap<>();
    static HashMap<String, Font> fonts = new HashMap<>();
    
    private ResourceManager() {}

    public static Image getImage(String path) {
        if (path == null) {
            System.err.println("Null path passed to getImage");
            return null;
        }
        InputStream stream = Columbiare.class.getClassLoader().getResourceAsStream(path);
        Image image = null;

        if (stream == null) {
            System.err.println("No image found at " + path);
            System.err.println("Base Path: " + Columbiare.class.getClassLoader().getResource("."));
        } else {
            image = new javafx.scene.image.Image(stream);
        }

        images.put(path, image);

        return image;
    }

    public static Font getFont(String name, int size) {
        if (fonts.containsKey(name + size)) {
            return fonts.get(name + size);
        }

        InputStream stream = Columbiare.class.getClassLoader().getResourceAsStream(name);

        if (stream == null) {
            System.err.println("Font " + name + " not found!");
            return null;
        } else {
            Font font = Font.loadFont(stream, size);
            fonts.put(name + size, font);
            return font;
        }
    }

    public static BufferedReader getReader(String path) {
        if (path == null) {
            System.err.println("null path passed to getReader");
            return null;
        }
        InputStream in = Columbiare.class.getClassLoader().getResourceAsStream(path);
        if (in == null) {
            System.err.println("No file found at " + path);
            return null;
        } else {
            return new BufferedReader(
                new InputStreamReader(
                    Columbiare.class.getClassLoader().getResourceAsStream(path)
                )
            );
        }
    }

    public static BufferedReader getExternalReader(String path) {
        if (path == null) {
            System.err.println("null path passed to getExternalReader");
            return null;
        }
        try {
            return new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static String[] getLines(String path) {
        BufferedReader reader = getReader(path);
        if (reader == null) {
            return new String[0];
        } else {
            return getReader(path)
                .lines()
                .map((str) -> new String(str.getBytes(), StandardCharsets.UTF_8))
                .toArray(String[]::new);
        }
    }

    public static String[] getExternalLines(String path) {
        BufferedReader reader = getExternalReader(path);
        if (reader == null) {
            return new String[0];
        } else {
            return reader.lines()
                .map((str) -> new String(str.getBytes(), StandardCharsets.UTF_8))
                .toArray(String[]::new);
        }
    }
}
