package com.glowingpigeon.columbiare.world;

import java.util.*;

import com.glowingpigeon.columbiare.data.ResourceManager;

import javafx.scene.canvas.GraphicsContext;

/**
 * Purely environemntal things
 */
public class World {
    private ArrayList<Obstacle> solids;
    private ArrayList<Renderable> nonsolids;
    private ArrayList<Teleport> teleports;
    private String path;

    public World() {
        this("world/world.wld");
    }

    public World(String path) {
        this.path = path;
        solids = new ArrayList<>();
        nonsolids = new ArrayList<>();
        teleports = new ArrayList<>();
        for (String line : ResourceManager.getLines(path)) {
            if (!line.isEmpty()) {
                String type = line.substring(0, line.indexOf(' '));
                if (!"#".equals(type)) {

                    line = line.substring(line.indexOf(' ') + 1);
                    String x = line.substring(0, line.indexOf(' '));
                    line = line.substring(line.indexOf(' ') + 1);
                    String y = line.substring(0, line.indexOf(' '));
                    line = line.substring(line.indexOf(' ') + 1);
                    String width = line.substring(0, line.indexOf(' '));
                    line = line.substring(line.indexOf(' ') + 1);
                    String height = line.substring(0, line.indexOf(' '));
                    line = line.substring(line.indexOf(' ') + 1);
                    switch (type) {
                        case "solid": {
                            solids.add(new Obstacle(line,
                            Integer.parseInt(x), Integer.parseInt(y),
                            Integer.parseInt(width), Integer.parseInt(height)));
                        }
                        break;
                        case "nonsolid": {
                            nonsolids.add(new BackgroundObject(line,
                            Integer.parseInt(x), Integer.parseInt(y),
                            Integer.parseInt(width), Integer.parseInt(height)));
                        }
                        break;
                        case "teleport": {
                            String destX = line.substring(0, line.indexOf(' '));
                            line = line.substring(line.indexOf(' ') + 1);
                            teleports.add(new Teleport(Integer.parseInt(x), Integer.parseInt(y),
                            Integer.parseInt(width), Integer.parseInt(height),
                            Integer.parseInt(destX), Integer.parseInt(line)));
                        }
                        break;
                    }
                }
            }
        }
    }

    public ArrayList<Obstacle> getSolids() {
        return solids;
    }

    public ArrayList<Renderable> getNonSolids() {
        return nonsolids;
    }

    public ArrayList<Teleport> getTeleports() {
        return teleports;
    }

    public void update() {
        for (Renderable r : nonsolids) {
            if (r instanceof BackgroundObject) {
                ((BackgroundObject) r).update();
            }
        }
        for (Obstacle o : solids) {
            o.update();
        }
    }

    public void render(GraphicsContext gc) {
        for (Renderable r : nonsolids) {
            r.render(gc);
        }
        for (Obstacle so : solids) {
            so.render(gc);
        }
    }

    public String getPath() {
        return path;
    }
}