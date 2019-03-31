package com.glowingpigeon.pigeonbound.state;

import com.glowingpigeon.pigeonbound.data.*;

public class PlayState extends GameState {
    Save save;
    
    public PlayState() {
        save = new Save();
    }

    public PlayState(String savePath) {
        save = new Save(savePath);
    }
}