package com.glowingpigeon.pigeonbound.state;

public class PlayState extends GameState {
    Save save;
    
    public PlayState() {
        save = new Save();
    }

    public PlayState(String savePath) {
        save = new Save(savePath);
    }
}