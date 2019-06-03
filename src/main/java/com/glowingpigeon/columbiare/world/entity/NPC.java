package com.glowingpigeon.columbiare.world.entity;

import java.util.*;

import com.glowingpigeon.columbiare.data.*;
import com.glowingpigeon.columbiare.graphics.*;
import com.glowingpigeon.columbiare.world.entity.dialogue.*;

public class NPC extends Entity {
    private DialogueSet dialogueSet;
    private String status;
    private String dataPath;
    private String state;
    
    public NPC(String status, String dataPath) {
        super("NPC", null, 0, 0, 16, 16);
        this.status = status;
        this.dataPath = dataPath;

        readFile(dataPath);
        
        state = null;
    }

    public NPC(String status, String dataPath, int x, int y) {
        this(status, dataPath);

        this.x = x;
        this.y = y;
    }
    

    public NPC(String status, String dataPath, int x, int y, String[] data) {
        this(status, dataPath, x, y);

        for (String d : data) {
            getDataContainer().add(d);
        }
    }

    private void readFile(String path) {
        for (String line : ResourceManager.getLines(dataPath)) {
            if (line.contains(" ")) {
                String propertyName = line.substring(0, line.indexOf(' '));
                line = line.substring(line.indexOf(' ') + 1);
                switch (propertyName) {
                    case "name": {
                        setName(line);
                    }
                    break;
                    case "bounds": {
                        String[] tokens = line.split(" ");
                        x = Integer.parseInt(tokens[0]);
                        y = Integer.parseInt(tokens[1]);
                        if (tokens.length >= 3) {
                            width = Integer.parseInt(tokens[2]);
                        } else {
                            width = 64;
                        }
                        if (tokens.length >= 4) {
                            height = Integer.parseInt(tokens[3]);
                        } else {
                            height = 64;
                        }
                    }
                    break;
                    case "sprite": {
                        sprite = new Sprite(line);
                    }
                    break;
                    case "dialogue": {
                        dialogueSet = new DialogueSet(line, this);
                    }
                    break;
                    case "#":
                    break;
                    default: {
                        System.out.println("Invaid npc property " + propertyName + " in " + dataPath);
                    }
                }
            }
        }
    }

    public DialogueSet getDialogueSet() {
        return dialogueSet;
    }

    public void update() {
        Deque<String> dataContainer = getDataContainer();
        if (!dataContainer.isEmpty()) {
            String first = dataContainer.pop();

            String[] tokens = first.split(" ");

            String head = tokens[0];
            String body = first.substring(first.indexOf(' ') + 1);

            switch (head) {
                case "move": {
                    int x = Integer.parseInt(tokens[1]);
                    int y = Integer.parseInt(tokens[2]);
                    int speed = 4;
                    if (tokens.length >= 4) {
                        speed = Integer.parseInt(tokens[3]);
                    }

                    int traX = 0;
                    int traY = 0;

                    if (x > 0) {
                        traX = speed;
                        x -= speed;
                        if (x < 0) {
                            traX += x;
                            x = 0;
                        }
                    } else if (x < 0) {
                        traX = -speed;

                        if (x > 0) {
                            traX += x;
                            x = 0;
                        }
                    }
                    if (y > 0) {
                        traY = speed;
                        y -= speed;
                        if (y < 0) {
                            traY += y;
                            y = 0;
                        }
                    } else if (y < 0) {
                        traY = -speed;
                        y += speed;
                        if (y > 0) {
                            traY += y;
                            y = 0;
                        }
                    }

                    translate(traX, traY);

                    if (x != 0 || y != 0) {
                        dataContainer.push("move " + x + " " + y + " " + speed);
                    }
                }
                break;
                case "anim": {
                    getSprite().setCurrentAnimation(body);
                    System.out.println("Set anim to " + body);
                }
                break;
                default:
                System.out.println("Unknown action " + tokens[0]);
                break;
            }
        }

        switch (status) {
            case "stand": {
                // TODO standing
            }
            break;
            case "wander": {
                // TODO wandering
            }
            break;
            case "walk": {
                
            }
        }

        getSprite().tick();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPath() {
        return dataPath;
    }

    public String toString() {
        return "npc " + status + " " + state + " " + dataPath;
    }
}