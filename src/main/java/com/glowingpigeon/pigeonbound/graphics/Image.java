package com.glowingpigeon.pigeonbound.graphics;

import javafx.scene.canvas.*;

import com.glowingpigeon.pigeonbound.data.ResourceManager;

public class Image {
    private javafx.scene.image.Image img;
    private int x;
    private int y;
    private int width;
    private int height;

    /**
     * Constructs and image from a file
     * @param path The path to the image
     */
    public Image(String path) {
        x = 0;
        y = 0;
        
        img = ResourceManager.getImage(path);
        if (img == null) {
            width = 0;
            height = 0;
        } else {
            while (img.getProgress() < 1);
            this.width = (int) img.getWidth();
            this.height = (int) img.getHeight();
        }
    }
    
    /**
     * Constructs an image as a subimage of a larger file from (0, 0)
     * @param path The path to the image
     * @param width The width of the subimage, in pixels
     * @param height The height of the subimage, in pixels
     */
    public Image(String path, int width, int height) {
        img = ResourceManager.getImage(path);
        x = 0;
        y = 0;
        this.width = width;
        this.height = height;
        // Wait until loaded
        while (img.getProgress() < 1);
    }
    
    /**
     * Constructs an image as a subimage of a larger file
     * @param path The path to the image
     * @param x The x of the top left of the subimage, in pixels
     * @param y The y of the top left of the subimage, in pixels
     * @param width The width of the subimage, in pixels
     * @param height The height of the subimage, in pixels
     */
    public Image(String path, int x, int y, int width, int height) {
        img = ResourceManager.getImage(path);;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        while (img.getProgress() < 1);
    }
    
    /**
     * Renders the image at the specified x and y
     * @param gc the GraphicsContext on which the image renders
     * @param x the x-coordinate of the top-left corner of the image
     * @param y the y-coordinate of the top-left corner of the image
     */
    public void render(GraphicsContext gc, int x, int y) {
        render(gc, x, y, width, height);
    }
    
    /**
     * Renders the image at the specified x and y with the specified width and height
     * @param gc the GraphicsContext on which the image renders
     * @param x the x-coordinate of the top-left corner of the image
     * @param y the y-coordinate of the top-left corner of the image
     * @param width the width of the image, in pixels
     * @param height the height of the image, in pixels
     */
    public void render(GraphicsContext gc, int x, int y, int width, int height) {
        gc.drawImage(img, this.x, this.y, this.width, this.height, x, y, width, height);
    }
}