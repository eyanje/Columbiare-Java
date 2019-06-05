package com.glowingpigeon.columbiare.data;

import java.io.*;
import java.util.*;

import com.glowingpigeon.columbiare.world.*;
import com.glowingpigeon.columbiare.world.entity.*;

import javafx.scene.canvas.GraphicsContext;

import com.glowingpigeon.columbiare.state.*;

public class Save {
    private String progress;
    private String sectionName;

    private Player player;
    private ArrayList<SolidObject> solids;
    private World world;
    private HashSet<NPC> npcs;

    private String path;

    /**
     * Constructs a new, empty save
     */
    public Save() {
        this.path = "save.sv";

        npcs = new HashSet<>();

        for (String line : ResourceManager.getLines("save.sv")) {
            parseLine(line);
        }

        solids = new ArrayList<>();
        
        fixData();
    }

    /**
     * Loads a save from path
     * 
     * @param path the path to the save file
     */
    public Save(String path) {
        System.out.println("Loading save");

        this.path = path;

        npcs = new HashSet<>();

        if (path != null) {
            for (String line : ResourceManager.getExternalLines(path)) {
                parseLine(line);
            }
        }
        
        solids = new ArrayList<>();
        solids.addAll(world.getSolids());
        solids.addAll(npcs);
        
        fixData();

    }

    private void parseLine(String line) {
        if (line.contains(" ")) {
            String token = line.substring(0, line.indexOf(' '));
            line = line.substring(line.indexOf(' ') + 1);
            String[] pieces = line.split(" ");
            switch (token) {
                case "player": {
                    player = new Player(pieces[0], Integer.parseInt(pieces[1]), Integer.parseInt(pieces[2]));
                }
                break;
                case "world": {
                    world = new World(line);
                }
                break;
                case "npc": {
                    
                    if (pieces.length >= 3) {
                        // status, x, y, dataPath
                        line = line.substring(line.indexOf(' ') + 1); // crop out status
                        line = line.substring(line.indexOf(' ') + 1); // crop out x
                        line = line.substring(line.indexOf(' ') + 1); // crop out y
                        if (line.indexOf(' ') == -1) {
                            line = "";
                        } else {
                            line = line.substring(line.indexOf(' ') + 1); // crop out path
                        }
                        
                        npcs.add(new NPC(
                            pieces[0],
                            pieces[3],
                            Integer.parseInt(pieces[1]),
                            Integer.parseInt(pieces[2]),
                            line.split(" ")));
                    } else {
                        npcs.add(new NPC(
                            pieces[0],
                            line.substring(line.indexOf(' ') + 1)));
                    }
                }
                break;
                case "progress": {
                    progress = line;
                }
                break;
                case "section": {
                    System.out.println("Section");
                    sectionName = line;
                }
                break;
            }
        }
    }

    private void fixData() {
        if (player == null) {
            System.err.println("Player is null");
            player = new Player("entities/columbiare", 0, 0);
        }
        if (world == null) {
            System.err.println("World is null");
            world = new World("world/world.wld");
        }
        if (progress == null) {
            progress = "a1s1";
        }
        if (sectionName == null) {
            System.err.println("No section specified in save");
            sectionName = "com.glowingpigeon.columbiare.state.premade.A1S1_0";
        }
    }

    public Player getPlayer() {
        return player;
    }

    public HashSet<NPC> getNPCs() {
        return npcs;
    }

    public NPC getNPCByName(String name) {
        for (NPC npc : npcs) {
            if (name != null && name.equals(npc.getName())) {
                return npc;
            }
        }
        return null;
    }

    public World getWorld() {
        return world;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getSectionName() {
        return sectionName;
    }

    public Section getSection(GraphicsContext context) {
        return Section.getByName(sectionName, this, context);
    }

    public void setSection(Section section) {
        sectionName = section.getClass().getName();
    }

    /**
     * Saves the game to the file
     */
    public void save() {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(path));
            writer.println("progress " + progress);
            writer.println("player " + player.getPath() + " " + player.getX() + " " + player.getY());
            writer.println("world " + world.getPath());
            writer.println("section " + sectionName);
            for (NPC npc : npcs) {
                writer.print("npc " + npc.getStatus() + " " + npc.getX() + " " + npc.getY() + " " + npc.getPath());
                for (String data : npc.getDataContainer()) {
                    writer.print(" " + data);
                }
                writer.println();
            }
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public ArrayList<SolidObject> getSolids() {
        return solids;
    }

    public void recomputeSolids() {
        solids.clear();
        solids.addAll(world.getSolids());
        solids.addAll(npcs);
    }
}