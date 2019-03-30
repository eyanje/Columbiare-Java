package com.glowingpigeon.pigeonbound.graphics;

import java.io.*;
import java.util.*;

import javafx.scene.canvas.*;

public class Sprite {
    private HashMap<String, Animation> animations;
    private String current;

    /**
     * Constructs an empty sprite, with no animations
     */
    public Sprite() {
        animations = new HashMap<>();
        current = null;
    }

    /**
     * Constructs a sprite using a spr file
     * @param path the path to the spr file
     */
    public Sprite(String path) {
        this();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            Animation current = null;

            // Read each line in the file for data
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                String[] tokens = line.split(" ");
                
                switch (tokens[0]) {
                    case "a": {
                        // Add a new animation
                        String name = line.substring(line.indexOf(' ') + 1);
                        current = new Animation();
                        addAnimation(name, current);
                    }
                    break;
                    case "i": {
                        // Add a new frame

                        // Extract basic data
                        int frameLength = Integer.parseInt(tokens[1]);
                        String imgPath = tokens[0];

                        Image frame = null;

                        if (tokens.length <= 3 /* data only*/) {
                            frame = new Image(imgPath);
                        } else {
                            // Parse first two numbers
                            int t0 = Integer.parseInt(tokens[2]);
                            int t1 = Integer.parseInt(tokens[3]);

                            if (tokens.length <= 3 /* data */ + 2 /* width and height */) {
                                frame = new Image(imgPath, t0, t1); // Width and height
                            } else {
                                // Parse next two numbers (width and height)
                                int t2 = Integer.parseInt(tokens[2]);
                                int t3 = Integer.parseInt(tokens[3]);
                                frame = new Image(imgPath, t0, t1, t2, t3); // x y width height
                            }

                            if (current == null) {
                                System.err.println("In " + path + " frame loaded before animation");
                            } else {
                                // Add frame to the last animation created
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

    /**
     * Adds an animation to the list of animations
     * @param name the name of the animation
     * @param animation the animation to be added
     */
    public void addAnimation(String name, Animation animation) {
        if (name != null && animation != null) {
            animations.put(name, animation);
            if (current == null) {
                current = name;
            }
        }
    }

    /**
     * Gets the name of the current animation playing
     * 
     * @return the name of the current animation
     */
    public String getCurrentAnimationName() {
        return current;
    }

    /**
     * Gets the current animation playing
     * @return the current animation playing
     */
    public Animation getCurrentAnimation() {
        return animations.get(current);
    }

    /**
     * Advances the current animation by one frame
     */
    public void tick() {
        animations.get(current).tick();
    }

    /**
     * Renders the sprite at the specified x and y
     * @param gc the GraphicsContext on which the sprite renders
     * @param x the x-coordinate of the top-left corner of the sprite
     * @param y the y-coordinate of the top-left corner of the sprite
     */
    public void render(GraphicsContext gc, int x, int y) {
        if (animations != null && current != null && animations.get(current) != null) {
            animations.get(current).render(gc, x, y);
        }
    }

    /**
     * Renders the sprite at the specified x and y with the specified width and height
     * @param gc the GraphicsContext on which the sprite renders
     * @param x the x-coordinate of the top-left corner of the sprite
     * @param y the y-coordinate of the top-left corner of the sprite
     * @param width the width of the sprite, in pixels
     * @param height the height of the sprite, in pixels
     */
    public void render(GraphicsContext gc, int x, int y, int width, int height) {
        if (animations != null && current != null && animations.get(current) != null) {
            animations.get(current).render(gc, x, y, width, height);
        }
    }
}