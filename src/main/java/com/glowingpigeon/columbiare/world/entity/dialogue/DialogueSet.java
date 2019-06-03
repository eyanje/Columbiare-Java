package com.glowingpigeon.columbiare.world.entity.dialogue;

import java.util.*;

import com.glowingpigeon.columbiare.data.*;
import com.glowingpigeon.columbiare.world.entity.NPC;

public class DialogueSet extends HashMap<String, ConversationSet> {
    private static final long serialVersionUID = 2689180337188889437L;

    public DialogueSet(String path, NPC parent) {
        String currentTrigger = null;
        String speaker = parent.getName();

        if (ResourceManager.getLines(path).length == 0) {
            System.out.println("Dialogue set at " + path + " has no lines!");
        }

        for (String line : ResourceManager.getLines(path)) {
            if (!line.trim().isEmpty()) {
                line = line.trim();
                String key = line;
                if (line.contains(" ")) {
                    key = line.substring(0, line.indexOf(' '));
                    line = line.substring(line.indexOf(' ') + 1);
                }
                switch (key) {
                    // Load a new trigger
                    case "t": {
                        currentTrigger = line;
                        putIfAbsent(line, new ConversationSet());
                    }
                    break;
                    case "c": {
                        // Add a new conversation to the current deque
                        get(currentTrigger).add(new Conversation());
                    }
                    case "s": {
                        speaker = line;
                        if (currentTrigger == null || !containsKey(currentTrigger)) {
                            System.err.println("Invalid current trigger on line " + line);
                        } else {
                            if (get(currentTrigger).isEmpty()) {
                                System.out.println("No conversation at " + line);
                                System.out.println("Adding default conversation");
                                get(currentTrigger).add(new Conversation());
                            }
                            // Add a new LineSet
                            get(currentTrigger).peekLast().add(new LineSet(speaker));
                        }
                    }
                    break;
                    case "l": {
                        // Add a line to the current lineSet
                        if (currentTrigger == null || !containsKey(currentTrigger)) {
                            System.err.println("Invalid current trigger on line " + line);
                        } else if (speaker == null) {
                            System.err.println("Current speaker is null on line " + line);
                        } else {
                            addLine(currentTrigger, line);
                        }
                    }
                    break;
                    case "d": {
                        // Add some data to run
                        get(currentTrigger).peekLast().add(new DialogueAction(parent.getDataContainer(), line));
                    }
                    break;
                    case "#":
                    break;
                    default: {
                        // Add the line to the last line
                        line = key + " " + line;
                        addToLine(currentTrigger, " " + line);
                    }
                    // TODO add dialogue options
                }
            }
        }
    }

    public void addLine(String trigger, String line) {
        if (trigger == null) {
            System.err.println("Trigger is null when adding line " + line);
        } else if (!containsKey(trigger) || get(trigger) == null) {
            System.err.println("Trigger " + trigger + " is not a valid trigger when adding line " + line);
        } else if (line == null) {
            System.err.println("Line is null");
        } else {
            Conversation conv = get(trigger).peekLast();
            // Add the line to the end of the list
            ConversationPart part = conv.peekLast();

            if (part instanceof LineSet) {
                ((LineSet) part).add(line);
            } else {
                System.out.println("Adding line " + line + " in trigger " + trigger + " to a non-lineset");
            }
        }
    }

    public void addToLine(String trigger, String text) {
        if (trigger == null) {
            System.err.println("Trigger is null when adding text " + text);
        } else if (!containsKey(trigger) || get(trigger) == null) {
            System.err.println("Trigger " + trigger + " is not a valid trigger when adding text " + text);
        } else if (text == null) {
            System.err.println("Text is null");
        } else {
            ConversationPart last = peekLastConversation(trigger) // Conversation
            .peekLast(); // ConversationPart
            if (last instanceof LineSet) {
                ((LineSet) last).addToLine(text);
            } else {
                System.out.println("Adding line " + text + " in trigger " + trigger + " to a non-lineset");
            }
        }
    }

    public ConversationSet getConversationSet(String trigger) {
        if (containsKey(trigger)) {
            return get(trigger);
        } else {
            System.out.println(trigger + " is not a valid dialogue trigger!");
            System.out.println("Instead try " + keySet());
            return null;
        }
    }

    /**
     * Reads the next conversation
     * @return the last unread conversation
     */
    public Conversation readConversation(String trigger) {
        return getConversationSet(trigger).read();
    }

    /**
     * Peeks the last conversation
     * @return the last unread conversation
     */
    public Conversation peekLastConversation(String trigger) {
        return getConversationSet(trigger).peekLast();
    }
}