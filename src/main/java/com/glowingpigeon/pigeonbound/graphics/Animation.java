package com.glowingpigeon.pigeonbound.graphics;

import java.util.*;

import javafx.scene.canvas.*;

public class Animation {
    ArrayList<Image> frames;
    ArrayList<Integer> frameLengths;
    int frame;
    int subFrame;

    Animation() {
        frames = new ArrayList<>();
        frameLengths = new ArrayList<>();
        frame = 0;
        subFrame = 0;
    }

    public void addFrame(int frameLength, Image frame) {
        if (frame != null) {
            frameLengths.add(frameLength);
            frames.add(frame);
        }
    }

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
    
    public void render(GraphicsContext gc, int x, int y) {
        frames.get(frame).render(gc, x, y);
    }

    public void render(GraphicsContext gc, int x, int y, int width, int height) {
        frames.get(frame).render(gc, x, y, width, height);
    }
}