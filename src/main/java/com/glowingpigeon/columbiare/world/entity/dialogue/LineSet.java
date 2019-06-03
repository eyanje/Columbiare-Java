package com.glowingpigeon.columbiare.world.entity.dialogue;

import java.util.*;

import javafx.scene.text.*;

/**
 * An ArrayList of lines
 */
public class LineSet extends ArrayList<String> implements ConversationPart {
    private static final long serialVersionUID = 3911370939621088255L;
    String speaker;

    public LineSet(String speaker) {
        this.speaker = speaker;
    }

    public String getSpeaker() {
        return speaker;
    }

    /**
     * Fixes the spacing on lines
     * Only break on another space
     */
    private static LineSet fixLinesByLength(LineSet lines, int maxLength) {
        LineSet fixed = new LineSet(lines.speaker);
        if (!lines.isEmpty()) {
            for (String line : lines) {
                String fixedLine = "";
                while (line.length() > maxLength) {
                    int loc = line.lastIndexOf(' ',  maxLength);
                    if (!fixedLine.isEmpty()) {
                        fixedLine += '\n';
                    }
                    fixedLine += line.substring(0, loc);
                    line = line.substring(loc + 1);
                }
                if (!line.isEmpty()) {
                    if (!fixedLine.isEmpty()) {
                    fixedLine += '\n';
                    }
                    fixedLine += line;
                }
                fixed.add(fixedLine);
            }
        }
        return fixed;
    }

    private static int lineWidth(String text, Font font) {
        if (text.isEmpty()) {
            return 0;
        }

        Text textNode = new Text(text);
        textNode.setFont(font);
        return (int) textNode.getBoundsInLocal().getWidth();
    }

    /**
     * Fixes the spacing on lines
     * Only break on another space
     */
    private static LineSet fixLinesByWidth(LineSet lines, int maxWidth, Font font) {
        LineSet fixed = new LineSet(lines.speaker);
        if (!lines.isEmpty()) {
            for (String line : lines) {
                String fixedLine = "";
                while (lineWidth(line, font) > maxWidth) {
                    // Count the width
                    int maxI = 0;
                    int width = lineWidth(String.valueOf(line.charAt(0)), font);
                    for (int i = 1; i < line.length(); ++i) {
                        // Accumulation is O(1) instead of O(n)
                        width += lineWidth(String.valueOf(line.charAt(i)), font);
                        if (width > maxWidth) {
                            maxI = i - 1;
                            break;
                        }
                    }
                    // Find the last space before or at the max
                    int loc = line.lastIndexOf(' ',  maxI - 1);
                    if (loc == -1) {
                        loc = maxI;
                    }
                    // Add a newline to the end of the last line
                    if (!fixedLine.isEmpty()) {
                        fixedLine += '\n';
                    }
                    // Add the cut text
                    fixedLine += line.substring(0, loc);
                    line = line.substring(loc + 1);
                }
                if (!line.isEmpty()) {
                    if (!fixedLine.isEmpty()) {
                    fixedLine += '\n';
                    }
                    fixedLine += line;
                }
                fixed.add(fixedLine);
            }
        }
        return fixed;
    }

    public void addToLine(String text) {
        if (isEmpty()) {
            System.out.println("No lines exist!");
            System.out.println("Adding " + text + " as a separate line...");
            add(text);
        } else {
            set(
                size() - 1,
                get(size() - 1) + text
            );
        }
    }

    public LineSet getLinesByLength(int maxLength) {
        return fixLinesByLength(this, maxLength);
    }

    public LineSet getLinesyWidth(int maxWidth, Font font) {
        return fixLinesByWidth(this, maxWidth, font);
    }
}