package com.glowingpigeon.columbiare.world.entity;

import com.glowingpigeon.columbiare.audio.AudioManager;
import com.glowingpigeon.columbiare.data.ResourceManager;
import com.glowingpigeon.columbiare.data.Save;
import com.glowingpigeon.columbiare.graphics.*;
import com.glowingpigeon.columbiare.world.*;

public class Player extends Entity {
    private String name;
    private String dataPath;
    private int stepTimer;

    // Directions work as follows
    // X is major: 0 is none, 3 is left, 6 is right
    // Y is minor: -1 is to top, +1 is to bottom
    // -1 is corrected to 0
    // 7 6 5 4 3 2 1 0
    private int direction; // In terms of radians

    public Player(String dataPath, int x, int y) {
        super("", null, x, y, 64, 64);

        this.dataPath = dataPath;
        direction = 1; // forward

        for (String line : ResourceManager.getLines(dataPath)) {
            if (line.contains(" ")) {
                String key = line.substring(0, line.indexOf(' '));
                line = line.substring(line.indexOf(' ') + 1);
                switch (key) {
                    case "name": {
                        setName(name);
                    }
                    break;
                    case "sprite": {
                        this.sprite = new Sprite(line);
                    }
                }
            }
        }
        stepTimer = 0;
    }

    public void move(Save save, boolean up, boolean down, boolean left, boolean right) {
        int moveX = 0;
        int moveY = 0;
        
        if (left == right) {
            if (up != down) {
                if (up) {
                    direction = 0;
                    moveY = -5;
                } else {
                    moveY = 5;
                    direction = 1;
                }
            }
        } else if (left) {
            moveX = -5;
            direction = 2;
        } else {
            moveX = 5;
            direction = 3;
        }
        
        super.translate(save.getSolids(), moveX, moveY);

        for (Teleport teleport : save.getWorld().getTeleports()) {
            if (
                getX() < teleport.getX() + teleport.getWidth() &&
                getX() + getWidth() > teleport.getX() &&
                getY() < teleport.getY() &&
                getY() + getHeight() > teleport.getY()
            ) {
                setPosition(teleport.getDestX(), teleport.getDestY());
            }
        }

        if ((moveX | moveY) == 0) {
            sprite.setCurrentAnimation("stand" + direction);
            stepTimer = 0;
        } else {
            sprite.setCurrentAnimation("walk" + direction);

            // Play stepping sound
            --stepTimer;
            if (stepTimer < 0) {
                AudioManager.playSound("audio/step.au");
                stepTimer = 16;
            }
        }
    }

    public String getPath() {
        return dataPath;
    }

    public int getDirection() {
        return direction;
    }

    /**
     * Returns -1, 0, or 1, depending on the x-direction the player faces
     */
    public int getFacingX() {
        switch (direction) {
            case 2:
            return -1;
            case 3:
            return 1;
            default:
            return 0;
        }
    }

    /**
     * Returns -1, 0, or 1, depending on the y-direction the player faces.
     */
    public int getFacingY() {
        switch (direction) {
            case 0:
            return -1;
            case 1:
            return 1;
            default:
            return 0;
        }
    }

    /**
     * Returns the x coordinate of the entity interacted with
     */
    public int getInteractX() {
        return getX() + getFacingX() * getWidth() + getWidth() / 2;
    }

    public int getInteractY() {
        return getY() + getFacingY() * getHeight() + getHeight() / 2;
    }
}