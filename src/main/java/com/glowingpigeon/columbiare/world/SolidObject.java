package com.glowingpigeon.columbiare.world;

public interface SolidObject {
    public int getX();
    public void setX(int x);
    public int getY();
    public void setY(int y);
    public int getWidth();
    public void setWidth(int width);
    public int getHeight();
    public void setHeight(int height);
    public boolean contains(int x, int y);
}