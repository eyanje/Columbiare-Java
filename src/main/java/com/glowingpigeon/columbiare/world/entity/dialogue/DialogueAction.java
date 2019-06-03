package com.glowingpigeon.columbiare.world.entity.dialogue;

import java.util.Collection;

public class DialogueAction implements ConversationPart {
    String data;
    Collection<String> container;

    public DialogueAction(Collection<String> container, String data) {
        this.container = container;
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void consume() {
        container.add(data);
    }

}