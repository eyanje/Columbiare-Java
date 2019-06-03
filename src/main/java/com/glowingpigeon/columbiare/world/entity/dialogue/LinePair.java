package com.glowingpigeon.columbiare.world.entity.dialogue;

public class LinePair implements ConversationPart {
    private String speaker;
    private String line;

    public LinePair(String speaker, String line) {
        this.speaker = speaker;
        this.line = line;
    }

    public String getSpeaker() {
        return speaker;
    }

    public String getLine() {
        return line;
    }
}