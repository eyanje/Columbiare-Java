package com.glowingpigeon.columbiare.state;

import java.util.*;

import com.glowingpigeon.columbiare.data.ResourceManager;
import com.glowingpigeon.columbiare.data.Save;
import com.glowingpigeon.columbiare.world.entity.NPC;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class SetSection extends Section {
    private Deque<String> actions;
    private Section nextSection;
    private int cameraX;
    private int cameraY;
    private boolean interact = false;

    public SetSection(Save save, String path, GraphicsContext graphicsContext) {
        super(save, graphicsContext);

        actions = new ArrayDeque<>();

        for (String line : ResourceManager.getLines(path)) {
            if (line.contains(" ")) {
                actions.add(line);
            }
        }

        nextSection = this;

        cameraX = save.getPlayer().getX() + save.getPlayer().getWidth() / 2;
        cameraY = save.getPlayer().getY() + save.getPlayer().getHeight() / 2;
    }

    @Override
    public void keyEvent(KeyEvent e) {
        if (e.getEventType() == KeyEvent.KEY_PRESSED && e.getCode().equals(KeyCode.Z)) {
            interact = true;
        }
    }

    @Override
    public Section getNextSection() {
        return nextSection;
    }

    @Override
    public void update(Save save) {

        if (actions.isEmpty()) {
            nextSection = new PlaySection(save, getGraphicsContext());
        } else {
            String action = actions.pop();
            
            String type;
            String data;

            if (action.contains(" ")) {
                type = action.substring(0, action.indexOf(' '));
                data = action.substring(action.indexOf(' ') + 1).trim();
            } else {
                type = action;
                data = "";
            }

            switch (type) {
                case "section": {
                    nextSection = Section.getByName(data, save, getGraphicsContext());
                    if (nextSection == null) {
                        nextSection = this;
                    }
                }
                break;
                case "wait": {
                    int len = Integer.parseInt(data);
                    if (len > 0) {
                        actions.push("wait " + (Integer.parseInt(data) - 1));
                    }
                }
                break;
                case "pan": {
                    String[] tokens = data.split(" ");
                    int panX = Integer.parseInt(tokens[0]);
                    int panY = Integer.parseInt(tokens[1]);
                    int speed = 1;
                    // Load a new speed
                    if (tokens.length >= 3) {
                        speed = Integer.parseInt(tokens[2]);
                    }

                    if (panX < 0) {
                        cameraX -= speed;
                        panX += speed;
                        // Check for overshooting
                        if (panX > 0) {
                            cameraX += panX;
                            panX = 0;
                        }
                    } else if (panX > 0) {
                        cameraX += speed;
                        panX -= speed;
                        // Check for overshooting
                        if (panX < 0) {
                            cameraX += panX;
                            panX = 0;
                        }
                    }
                    if (panY < 0) {
                        cameraY -= speed;
                        panY += speed;
                        // Check for overshooting
                        if (panY > 0) {
                            cameraY += panY;
                            panY = 0;
                        }
                    } else if (panY > 0) {
                        cameraY += speed;
                        panY -= speed;
                        // Check for overshooting
                        if (panY < 0) {
                            cameraY += panY;
                            panY = 0;
                        }
                    }
                    if ((panX | panY) != 0) {
                        actions.push("pan " + panX + " " + panY + " " + speed);
                    }
                }
                break;
                case "panto": {
                    String[] tokens = data.split(" ");
                    int panX = Integer.parseInt(tokens[0]);
                    int panY = Integer.parseInt(tokens[1]);
                    int speed = 1;
                    // Load a new speed
                    if (tokens.length >= 3) {
                        speed = Integer.parseInt(tokens[2]);
                    }
                    actions.push("pan " + (panX - cameraX) + " " + (panY - cameraY) + " " + speed);
                }
                break;
                case "tp": {
                    String[] tokens = data.split(" ");
                    int tpX = Integer.parseInt(tokens[0]);
                    int tpY = Integer.parseInt(tokens[1]);
                    cameraX = tpX;
                    cameraY = tpY;
                }
                break;
                case "npc": {
                    String name = data.substring(0, data.indexOf(' '));
                    data = data.substring(data.indexOf(' ') + 1);

                    NPC npc = getSave().getNPCByName(name);

                    String subAction = data.substring(0, data.indexOf(' '));
                    data = data.substring(data.indexOf(' ') + 1);

                    switch (subAction) {
                        case "move": {
                            String[] tokens = data.split(" ");
                            int moveX = Integer.parseInt(tokens[0]);
                            int moveY = Integer.parseInt(tokens[1]);
                            int speed = 1;
                            if (tokens.length >= 3) {
                                speed = Integer.parseInt(tokens[2]);
                            }

                            // Load a new speed
                            if (tokens.length >= 3) {
                                speed = Integer.parseInt(tokens[2]);
                            }

                            if (moveX < 0) {
                                npc.translate(getWorld().getSolids(), -speed, 0);
                                moveX += speed;
                                // Check for overshooting
                                if (moveX > 0) {
                                    npc.translate(getWorld().getSolids(), moveX, 0);
                                    moveX = 0;
                                }
                            } else if (moveX > 0) {
                                npc.translate(getWorld().getSolids(), speed, 0);
                                moveX -= speed;
                                // Check for overshooting
                                if (moveX < 0) {
                                    npc.translate(getWorld().getSolids(), moveX, 0);
                                    moveX = 0;
                                }
                            }
                            if (moveY < 0) {
                                npc.translate(getWorld().getSolids(), 0, -speed);
                                moveY += speed;
                                // Check for overshooting
                                if (moveY > 0) {
                                    npc.translate(getWorld().getSolids(), moveY, 0);
                                    moveY = 0;
                                }
                            } else if (moveY > 0) {
                                npc.translate(getWorld().getSolids(), 0, speed);
                                moveY -= speed;
                                // Check for overshooting
                                if (moveY < 0) {
                                    npc.translate(getWorld().getSolids(), moveY, 0);
                                    moveY = 0;
                                }
                            }
                            if ((moveX | moveY) != 0) {
                                actions.push("npc move " + moveX + " " + moveY + " " + speed);
                            }
                        }
                    }
                }
                break;
                case "text": {
                    if (data.strip().isEmpty()) {
                        // Keep going through old text
                        if (interact) {
                            getUI().interact();
                        }
                        if (getUI().isOpen()) {
                            actions.push("text");
                        }
                    } else {
                        // Add new text
                        String name = data.substring(0, data.indexOf(' '));
                        String text = data.substring(data.indexOf(' ') + 1);
                        getUI().getTextBox().addAdjustedLine(name, text);
                        actions.push("text");
                    }
                }
                break;
                case "#":
                break;
                default: {
                    System.out.println("Unrecognized action " + action);
                }
                break;
            }
        }
        
        interact = false;
        super.update(save);
    }

    @Override
    public void render() {
        GraphicsContext gc = getGraphicsContext();

        gc.setTransform(1, 0, 0, 1, gc.getCanvas().getWidth() / 2 - cameraX, gc.getCanvas().getHeight() / 2 - cameraY);
        super.render();
    }
}