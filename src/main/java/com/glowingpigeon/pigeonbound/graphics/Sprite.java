package com.glowingpigeon.pigeonbound.graphics;

import java.io.*;
import java.util.*;

import javafx.scene.canvas.*;

public class Sprite {
    private HashMap<String, Animation> animations;
    private String current;

    public Sprite() {
        animations = new HashMap<>();
        current = null;
    }

    public Sprite(String path) {
        this();
        // TOOD parse a sprite
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            Animation current = null;
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                String[] tokens = line.split(" ");
                
                switch (tokens[0]) {
                    case "a": {
                        String name = line.substring(line.indexOf(' ') + 1);
                        current = new Animation();
                        addAnimation(name, current);
                        // Add a new animation
                    }
                    break;
                    case "i": {
                        // Add a new frame
                        int frameLength = Integer.parseInt(tokens[1]);
                        String imgPath = tokens[0];
                        Image frame = null;

                        if (tokens.length <= 3 /* data only*/) {
                            frame = new Image(imgPath);
                        } else {
                            int t0 = Integer.parseInt(tokens[2]);
                            int t1 = Integer.parseInt(tokens[3]);
                            if (tokens.length <= 3 /* data */ + 2 /* width and height */) {
                                frame = new Image(imgPath, t0, t1); // Width and height
                            } else {
                                int t2 = Integer.parseInt(tokens[2]);
                                int t3 = Integer.parseInt(tokens[3]);
                                frame = new Image(imgPath, t0, t1, t2, t3); // x y width height
                            }
                            if (current == null) {
                                System.err.println("In " + path + " frame loaded before animation");
                            } else {
                                current.addFrame(frameLength, frame);
                            }
                        }
                    }
                    break;
                }
            }
            reader.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void addAnimation(String name, Animation animation) {
        if (name != null && animation != null) {
            animations.put(name, animation);
            if (current == null) {
                current = name;
            }
        }
    }

    public String getCurrentAnimationName() {
        return current;
    }

    public Animation getCurrentAnimation() {
        return animations.get(current);
    }

    public void tick() {
        animations.get(current).tick();
    }

    public void render(GraphicsContext gc, int x, int y) {
        if (animations != null && current != null && animations.get(current) != null) {
            animations.get(current).render(gc, x, y);
        }
    }

    public void render(GraphicsContext gc, int x, int y, int width, int height) {
        if (animations != null && current != null && animations.get(current) != null) {
            animations.get(current).render(gc, x, y, width, height);
        }
    }
}