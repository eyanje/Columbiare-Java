package com.glowingpigeon.columbiare.world.entity;

import java.util.*;

import com.glowingpigeon.columbiare.graphics.Sprite;

import com.glowingpigeon.columbiare.world.*;

import javafx.scene.canvas.GraphicsContext;

/**
 * An entity is anything which can move around.
 * Basically anything alive.
 */
public abstract class Entity implements Renderable, SolidObject {
    protected String name;
    protected Sprite sprite;
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected Deque<String> data;

    Entity() {
        this("Error", "sprites/testnums/testnums.spr", 0, 0, 0, 0);
    }

    Entity(String name, String spritePath, int x, int y, int width, int height) {
        this.name = name;
        this.sprite = new Sprite(spritePath);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        data = new ArrayDeque<>();
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null) {
            this.name = name;
        }
    }
    
    @Override
    public int getX() {
        return x;
    }
    @Override
    public void setX(int x) {
        this.x = x;
    }
    @Override
    public int getY() {
        return y;
    }
    @Override
    public void setY(int y) {
        this.y = y;
    }
    @Override
    public int getWidth() {
        return width;
    }
    @Override
    public void setWidth(int width) {
        this.width = width;
    }
    @Override
    public int getHeight() {
        return height;
    }
    @Override
    public void setHeight(int height) {
        this.height = height;
    }

    public boolean contains(int x, int y) {
        return x >= this.x &&
        x <= this.x + this.width &&
        y >= this.y &&
        y <= this.y + this.height;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public int collides(Collection<? extends SolidObject> solids) {
        int collides = 0;

        for (SolidObject s : solids) {
            // First check for collision
            if (s != null && this != s
            && s.getX() <= getX() + getWidth()
            && s.getY() <= getY() + getHeight()
            && s.getX() + s.getWidth() >= getX()
            && s.getY() + s.getHeight() >= getY()
            ) {
                int collideWidth = Math.min(
                    s.getX() + s.getWidth(),
                    getX() + getWidth()
                ) - Math.max(s.getX(), getX());
                int collideHeight = Math.min(
                    s.getY() + s.getHeight(),
                    getY() + getHeight()
                ) - Math.max(s.getY(), getY());

                if ((collideWidth | collideHeight) != 0) {

                    // Move by y axis
                   if (Math.abs(collideWidth) >= Math.abs(collideHeight)) {
                       if (s.getY() < getY()) {
                           collides = collides | 0b0001;
                       } else if (s.getY() > getY()) {
                           collides = collides | 0b0010;
                       }
                   }
    
                   // Move by x axis
                   if (Math.abs(collideWidth) <= Math.abs(collideHeight)) {
                       if (s.getX() < getX()) {
                           collides = collides | 0b0100;
                       } else if (s.getX() > getX()) {
                           collides = collides | 0b1000;
                       }
                   }
                }

            }
        }

        return collides;
    }
    
    public void translate(Collection<? extends SolidObject> solids, int x, int y) {
        // Records possible movement as 0bxy
        int possible = 0b11;
        if (solids == null) {
            this.x += x;
            this.y += y;
        } else {
            do { // Should eventually reach 0
                possible = 0b11;

                if (x > 0 // Move right
                && ((collides(solids) & 0b1000) == 0)) {
                    ++this.x;
                    --x;
                } else if (x < 0 // Move left
                && ((collides(solids) & 0b0100) == 0)) {
                    --this.x;
                    ++x;
                } else {
                    possible = possible & 0b01;
                }
                if (y > 0 // Move down
                && ((collides(solids) & 0b0010) == 0)) {
                    ++this.y;
                    --y;
                } else if (y < 0 // Moving up
                && ((collides(solids) & 0b0001) == 0)) {
                    --this.y;
                    ++y;
                } else {
                    possible = possible & 0b10;
                }
            } while (possible != 0);
        }
    }

    public void translate(int x, int y) {
        this.x += x;
        this.y += y;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void render(GraphicsContext gc) {
        sprite.tick();
        sprite.render(gc, x, y, width, height);
    }

    public Deque<String> getDataContainer() {
        return data;
    }
}