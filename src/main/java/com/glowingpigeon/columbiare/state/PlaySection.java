package com.glowingpigeon.columbiare.state;

import java.util.*;

import com.glowingpigeon.columbiare.data.*;
import com.glowingpigeon.columbiare.world.*;
import com.glowingpigeon.columbiare.world.entity.*;

import javafx.scene.canvas.*;
import javafx.scene.input.*;

public class PlaySection extends Section {
    Player player;
    // A collection to hold existing solids
    Collection<SolidObject> solids;
    Deque<String> trigger;
    Section nextSection;
    boolean[] moves;
    boolean interact;

    public PlaySection(Save save, GraphicsContext graphicsContext) {
        super(save, graphicsContext);

        solids = new ArrayList<>();
        for (NPC npc : save.getNPCs()) {
            solids.add((SolidObject) npc);
        }

        for (Obstacle obstacle : save.getWorld().getSolids()) {
            solids.add((SolidObject) obstacle);
        }

        moves = new boolean[4];
        this.player = save.getPlayer();

        nextSection = this;
    }

    public void mouseEvent(MouseEvent e) {
        
    }

    public void keyEvent(KeyEvent e) {
        if (e.getEventType() == KeyEvent.KEY_PRESSED || e.getEventType() == KeyEvent.KEY_RELEASED) {

            switch (e.getCode()) {
                case UP: {
                    moves[0] = e.getEventType() == KeyEvent.KEY_PRESSED;
                }
                break;
                case DOWN: {
                    moves[1] = e.getEventType() == KeyEvent.KEY_PRESSED;
                }
                break;
                case LEFT: {
                    moves[2] = e.getEventType() == KeyEvent.KEY_PRESSED;
                }
                break;
                case RIGHT: {
                    moves[3] = e.getEventType() == KeyEvent.KEY_PRESSED;
                }
                break;
                default:
                // Do nothing
                break;
            }
            if (e.getEventType().equals(KeyEvent.KEY_PRESSED)) {
                if (e.getCode().equals(KeyCode.Z)) {
                    interact = true;
                }
            }
        }
    }

    @Override
    public void update(Save save) {
        if (!getUI().isOpen()) {
            player.move(getSave(), moves[0], moves[1], moves[2], moves[3]);
            player.getSprite().setCurrentAnimation("stand");
        }
        super.update(save);
        
        getUI().update();

        if (interact) {
            if (getUI().isOpen()) {
                getUI().interact();
            } else {
                // Add new lines
                int interactX = player.getInteractX();
                int interactY = player.getInteractY();
    
                for (NPC npc : save.getNPCs()) {
                    if (npc.contains(interactX, interactY)) {
                        getUI().getTextBox().addNPCLines(npc, save.getProgress());
                        // Only add lines for the first NPC you find
                        break;
                    }
                }
            }
        }

        interact = false;
    }

    @Override
    public void render() {
        GraphicsContext gc = getGraphicsContext();

        gc.setTransform(1, 0, 0, 1,
            gc.getCanvas().getWidth() / 2 - player.getX() - player.getWidth() / 2,
            gc.getCanvas().getHeight() / 2 - player.getY() - player.getHeight() / 2
        );
        super.render();
    }
}