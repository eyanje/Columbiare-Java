package com.glowingpigeon.columbiare.ui;

import com.glowingpigeon.columbiare.audio.*;

import com.glowingpigeon.columbiare.world.*;
import com.glowingpigeon.columbiare.world.entity.*;
import com.glowingpigeon.columbiare.world.entity.dialogue.*;

import javafx.scene.canvas.*;
import javafx.scene.paint.*;

import java.util.*;

public class TextBox implements Renderable {
    public final static int PADDING = 14;
    public final static int CHAR_WIDTH = 14;
    GraphicsContext graphicsContext;
    //Deque<String[]> lines;
    Deque<ConversationPart> parts;
    int x, y, width, height;
    int lineRead;
    boolean visible;

    TextBox(GraphicsContext graphicsContext, int x, int y, int width, int height) {
        this.graphicsContext = graphicsContext;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        lineRead = 0;

        parts = new ArrayDeque<>();
    }

    public boolean hasLines() {
        return !parts.isEmpty();
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        if (visible ^ this.visible) {
            lineRead = 0;
            if (visible) {
                AudioManager.playSound("audio/nextline.au");
            }
        }
        this.visible = visible;
    }

    public void addLine(String speaker, String line) {
        parts.add(new LinePair(speaker, line));
        //lines.add(new String[] { speaker, line });
        setVisible(true);
    }

    /**
     * Reads lines from an NPC and adds them to the dialogue box
     */
    public void addNPCLines(NPC npc, String trigger) {
        Conversation conv = npc.getDialogueSet().getConversationSet(trigger).read();
        if (conv != null) {
            for (ConversationPart part : conv) {
                if (part instanceof LineSet) {
                    LineSet lineSet = (LineSet) part;
                    if (lineSet != null) {
                        // Get each fixed line
                        for (String line : lineSet.getLinesyWidth(width - 2 * PADDING, graphicsContext.getFont())) {
                            addLine(lineSet.getSpeaker(), line);
                        }
                    }
                } else {
                    parts.add(part);
                }
            }
        }
        setVisible(true);
    }
    /**
     * Reads lines from an NPC and adds them to the dialogue box
     */
    public void addDialogueSet(DialogueSet set, String trigger) {
        Conversation conv = set.getConversationSet(trigger).read();
        if (conv != null) {
            for (ConversationPart part : conv) {
                if (part instanceof LinePair) {
                    LineSet lineSet = (LineSet) part;
                    if (lineSet != null) {
                        // Get each fixed line
                        for (String line : lineSet.getLinesyWidth(width - 2 * PADDING, graphicsContext.getFont())) {
                            addLine(lineSet.getSpeaker(), line);
                        }
                    }
                } else {
                    // Add an action to the deque of conversation actions
                    parts.add(part);
                }
            }
        }
        setVisible(true);
    }

    public void addAdjustedLine(String speaker, String line) {
        LineSet lineSet = new LineSet(speaker);
        lineSet.add(line);
        for (String adjustedLine : lineSet.getLinesyWidth(width - 2 * PADDING, graphicsContext.getFont())) {
            addLine(lineSet.getSpeaker(), adjustedLine);
        }
        setVisible(true);
    }

    @Override
    public void render(GraphicsContext gc) {
        if (visible) {
            //String[] linePair = lines.peek();
            ConversationPart part = parts.peek();

            if (part != null && part instanceof LinePair) {
                LinePair pair = (LinePair) part;
            
                String speaker = pair.getSpeaker();
                String line = pair.getLine();

                if (speaker == null || line == null) {
                    setVisible(false);
                } else {
                    // Draw the main rectangle
                    gc.setFill(new Color(0, 1, 0, 0.5));
                    gc.fillRect(x, y, width, height);

                    // Draw the name rectangle
                    gc.setFill(new Color(0, 0.5, 0, 0.5));
                    gc.fillRect(x, y - PADDING * 2 - gc.getFont().getSize(), width / 2, PADDING * 2 + gc.getFont().getSize());
                    
                    if (lineRead < line.length()) {
                        line = line.substring(0, lineRead);
                    }
                    gc.setFill(new Color(0, 0, 0, 1));
                    // Draw the speaker name
                    gc.fillText(speaker, x + PADDING, y - PADDING, width / 2);
                    // Draw the text
                    // Cut out the first few lines, if they are too high
                    for (
                        int textHeight = (int) (line.split("\n").length * (gc.getFont().getSize() * 3 / 2));
                        textHeight + 2*PADDING > height;
                        textHeight = (int) (line.split("\n").length * gc.getFont().getSize() * 3 / 2)
                    ) {
                        //System.out.println("Shortening line " + line);
                        line = line.substring(line.indexOf('\n') + 1);
                    }
                    gc.fillText(line, x + PADDING, y + PADDING + gc.getFont().getSize());
                }
            }
        }
    }

    public void advanceChar() {
        if (!parts.isEmpty()) {

            ConversationPart current = parts.peekFirst();
            
            if (current instanceof LinePair) {
                LinePair pair = (LinePair) current;
                
                if (lineRead < pair.getLine().length()) {
                    ++lineRead;
                }
            }
        }

    }

    public void advanceLine() {
        if (parts.peekFirst() == null) {
            setVisible(false);
        } else {
            ConversationPart part = parts.peekFirst();

            // All current lines should be line pairs.
            if (part instanceof LinePair) {
                LinePair pair = (LinePair) part;
                
                if (lineRead < pair.getLine().length()) {
                    // Skip to the end of the line
                    lineRead = pair.getLine().length();
                } else {
                    // Go to the next line
                    parts.poll();
                    part = parts.peek();

                    // Find the next linepair
                    while (part != null && !(part instanceof LinePair)) {
                        if (part instanceof DialogueAction) {
                            // Consume a dialogue action if encountered
                            ((DialogueAction) part).consume();
                        }
                        parts.poll();
                        part = parts.peek();
                    }

                    if (part == null || !(part instanceof LinePair)) {
                        // No LinePair parts exist
                        setVisible(false);
                    } else {
                        // Move to the next line
                        lineRead = 0;
                        AudioManager.playSound("audio/nextline.au");
                    }
                }
            } else {
                setVisible(false);
            }
        }

    }

}