package com.glowingpigeon.columbiare.world.entity.dialogue;

import java.util.*;

/**
 * A set of conversations to have, if the NPC has multiple different things to say
 * After reading one conversation, it should be removed.
 * The last conversation read should be marked
 */
public class ConversationSet extends ArrayDeque<Conversation> {
    private static final long serialVersionUID = -3239106046007920407L;

    ConversationSet() {
        add(new Conversation());
    }

    /**
     * Returns the next conversation to be used
     * Removes the conversation from the list if there are more
     */
    public Conversation read() {
        Conversation conv = peek();
        if (size() > 1) {
            conv = pop();
        }
        if (conv != null) {
            conv.markRead();
        }
        return conv;
    }

    public boolean isFinished() {
        return size() <= 1 && peekLast().hasBeenRead();
    }
}