package com.glowingpigeon.columbiare.graphics;

import javafx.scene.canvas.GraphicsContext;

public class Pattern extends Image {
    private int offsetX;
    private int offsetY;

    public Pattern(String path) {
        super(path);
        offsetX = 0;
        offsetY = 0;
    }

    public Pattern(String path, int width, int height) {
        super(path, width, height);
    }

    public Pattern(String path, int x, int y, int width, int height) {
        super(path, width, height);
    }

    public int getOffsetX() {
        return offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }

    public void translate(int x, int y) {
        offsetX = (offsetX + x) % getWidth();
        offsetY = (offsetY + y) % getHeight();
    }

    public void setOffset(int x, int y) {
        offsetX = x;
        offsetY = y;
    }

    @Override
    public void render(GraphicsContext gc, int x, int y) {
        // Render one iteration of the pattern
        render(gc, x, y, getWidth(), getHeight());
    }

    /**
     * Renders the pattern in a rectangle of the specified x, y, width, height
     * @param x the x-coordinate of the top-left corner of the rectangle
     * @param y the y-coordinate of the top-left corner of the rectangle
     * @param width the width of the rectangle
     * @param height the height of the rectangle
     */
    @Override
    public void render(GraphicsContext gc, int x, int y, int width, int height) {
        if (getImage() != null) {
            for (int sy = y; sy < y + height; sy += getHeight()) {
                for (int sx = x; sx < x + width; sx += getWidth()) {
                    // Draw it four times: Four quadrants
                    int splitX = offsetX % getWidth();
                    int splitY = offsetY % getHeight();
                    int maxWidth = getWidth();
                    int maxHeight = getHeight();
                    
                    if (sx + getWidth() > width) {
                        maxWidth = width - sx;
                    }
                    if (sy + getHeight() > height) {
                        maxHeight = height - sy;
                    }
                
                // The width of the left column: splitX - 0
                // Remember this becomes the right column, so if
                // getWidth() - splitx > maxWidth, this should be 0
                int preSplitWidth = splitX;
                if (getWidth() - splitX > maxWidth) {
                    preSplitWidth = 0;
                } else if (getWidth() > maxWidth) {
                    preSplitWidth = maxWidth - (getWidth() - splitX);
                }
                // The width of the right column: maxWidth - splitX
                // Remember that this becomes the left column when drawn, so if
                // postSplitWidth > maxWidth, postSplitWidth = maxWidth
                int postSplitWidth = getWidth() - splitX;
                if (postSplitWidth > maxWidth)
                postSplitWidth = maxWidth;
                
                // The height of the top row: splitY
                // This becomes the bottom row, so if getHeight() - splitY > maxHeight, this is 0.
                int preSplitHeight = splitY;
                if (getHeight() - splitY > maxHeight) {
                    preSplitHeight = 0;
                } else if (getHeight() > maxHeight) {
                    preSplitHeight = maxHeight - (getHeight() - splitY);
                }
                // The width of the bottom column, maxHeight - splitY
                // This becomes the top row when drawn, so if
                // plostSplitHeight > maxHeight, = maxHeight
                int postSplitHeight = getHeight() - splitY;
                if (postSplitHeight > maxHeight)
                postSplitHeight = maxHeight;
                
                // Draw the top-left corner of the reassembled image
                gc.drawImage(getImage(),
                getX() + splitX, // sub x
                getY() + splitY, // sub y
                getWidth() - splitX, // sub width
                getHeight() - splitY, // sub height
                sx, // rendered x
                sy, // rendered y
                postSplitWidth, // rendered width
                    postSplitHeight // rendered height
                    );
                    
                    // Draw top-right corner of the reassembled image
                    // Aka the bottom left of the old image
                    gc.drawImage(getImage(),
                    getX(), // sub x
                    getY() + splitY, // sub y
                    splitX, // sub width
                    getHeight() - splitY, // sub height
                    sx + (getWidth() - splitX), // rendered x
                    sy, // rendered y
                    preSplitWidth, // rendered width
                    postSplitHeight // rendered height
                    );
                    
                // Draw the bottom-left corner of the reassembled image
                // Aka the top right corner of the old image
                gc.drawImage(getImage(),
                    getX() + splitX, // sub x
                    getY(), // sub y
                    getWidth() - splitX, // sub width
                    splitY, // sub height
                    sx, // rendered x
                    sy + (getHeight() - splitY), // rendered y
                    postSplitWidth, // rendered width
                    preSplitHeight // rendered height
                    );
                    
                    // Draw the bottom-right corner of the reassembled image
                    // Aka the top left corner of the old image
                    gc.drawImage(getImage(),
                    getX(), // sub x
                    getY(), // sub y
                    splitX, // sub width
                    splitY, // sub height
                    sx + (getWidth() - splitX), // rendered x
                    sy + (getHeight() - splitY), // rendered y
                    preSplitWidth, // rendered width
                    preSplitHeight // rendered height
                    );
                }
            }
        }
    }
}