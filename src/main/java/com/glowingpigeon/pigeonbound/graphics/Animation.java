package com.glowingpigeon.pigeonbound.graphics;

import java.util.*;

import javafx.scene.canvas.*;

public class Animation {
    ArrayList<Image> frames;
    ArrayList<Integer> frameLengths;
    int frame;
    int subFrame;

    /**
     * Constructs and empty animation
     */
    Animation() {
        frames = new ArrayList<>();
        frameLengths = new ArrayList<>();
        frame = 0;
        subFrame = 0;
    }

    /**
     * Adds a frame to the animation
     * @param frameLength the amount of frames to repeat the image
     * @param frame the frame to be added
     */
    public void addFrame(int frameLength, Image frame) {
        if (frame != null) {
            frameLengths.add(frameLength);
            frames.add(frame);
        }
    }

    /**
     * Advances the animation by one frame
     */
    public void tick() {
        subFrame += 1;
        if (subFrame == frameLengths.get(frame)) {
            ++frame;
            subFrame = 0;
            if (frame == frames.size()) {
                frame = 0;
            }
        }
    }
    
    /**
     * Renders the animation at the specified x and y
     * @param gc the GraphicsContext on which the animation renders
     * @param x the x-coordinate of the top-left corner of the animation
     * @param y the y-coordinate of the top-left corner of the animation
     */
    public void render(GraphicsContext gc, int x, int y) {
        frames.get(frame).render(gc, x, y);
    }

    /**
     * Renders the animation at the specified x and y with the specified width and height
     * @param gc the GraphicsContext on which the animation renders
     * @param x the x-coordinate of the top-left corner of the animation
     * @param y the y-coordinate of the top-left corner of the animation
     * @param width the width of the animation, in pixels
     * @param height the height of the animation, in pixels
     */
    public void render(GraphicsContext gc, int x, int y, int width, int height) {
        frames.get(frame).render(gc, x, y, width, height);
    }
}