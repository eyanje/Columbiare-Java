package com.glowingpigeon.columbiare.world.entity.dialogue;

import java.util.*;

/**
 * An uninterrupted dialogue between multiple people
 */
public class Conversation extends LinkedList<ConversationPart> {
    private static final long serialVersionUID = -4520110194990163378L;
    private boolean read;

    public Conversation() {
        read = false;
    }

    public boolean hasBeenRead() {
        return read;
    }

    public void markRead() {
        read = true;
    }

    public void reset() {
        read = false;
    }
}