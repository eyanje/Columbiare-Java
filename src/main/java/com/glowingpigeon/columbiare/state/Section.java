package com.glowingpigeon.columbiare.state;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

import com.glowingpigeon.columbiare.data.Save;
import com.glowingpigeon.columbiare.ui.*;
import com.glowingpigeon.columbiare.world.*;
import com.glowingpigeon.columbiare.world.entity.*;

import javafx.scene.canvas.*;
import javafx.scene.input.KeyEvent;

public abstract class Section {
    private GraphicsContext graphicsContext;
    private Save save;
    private Player player;
    private HashSet<NPC> npcs;
    private World world;
    private UI ui;
    private Section nextSection;

    public Section(Save save, GraphicsContext graphicsContext) {
        this.graphicsContext = graphicsContext;
        this.save = save;
        player = save.getPlayer();
        npcs = save.getNPCs();
        world = save.getWorld();
        ui = new UI(graphicsContext);
        nextSection = this;
    }

    public static Section getByName(String name, Save save, GraphicsContext graphicsContext) {
        try {
            return (Section) Class.forName(name).getDeclaredConstructor(Save.class, GraphicsContext.class)
                    .newInstance(save, graphicsContext);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public abstract void keyEvent(KeyEvent e);

    public void update(Save save) {
        world.update();
        ui.update();
    }

    public Section getNextSection() { return nextSection; };

    public void setNextSection(Section next) {
        this.nextSection = next;
    }

    public Save getSave() {
        return save;
    }

    public Player getPlayer() {
        return player;
    }

    public HashSet<NPC> getNPCs() {
        return npcs;
    }

    public World getWorld() {
        return world;
    }

    public UI getUI() {
        return ui;
    }

    public GraphicsContext getGraphicsContext() {
        return graphicsContext;
    }

    public int getWidth() {
        return (int) graphicsContext.getCanvas().getWidth();
    }

    public int getHeight() {
        return (int) graphicsContext.getCanvas().getHeight();
    }

    public void render() {
        world.render(graphicsContext);

        for (Renderable r : npcs) {
            r.render(graphicsContext);
        }
        player.render(graphicsContext);

        ui.render();
    }
}