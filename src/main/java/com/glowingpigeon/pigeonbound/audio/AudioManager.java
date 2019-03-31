package com.glowingpigeon.pigeonbound.audio;

import javax.sound.sampled.*;

import java.io.IOException;
import java.util.*;

import com.glowingpigeon.pigeonbound.PigeonBound;

public final class AudioManager {
    private static HashMap<String, Clip> clips = new HashMap<>();
    private static Clip currentClip = null;

    private AudioManager() {
    }

    public static Clip getClip(String path) {
        if (clips.containsKey(path)) {
            return clips.get(path);
        } else {
            try {
                AudioInputStream ais = AudioSystem.getAudioInputStream(
                    PigeonBound.class.getClassLoader().getResourceAsStream(path)
                );
                Clip clip = AudioSystem.getClip();
                clip.open(ais);
                
                clips.put(path, clip);

                return clip;

            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (UnsupportedAudioFileException ex) {
                ex.printStackTrace();
            } catch (LineUnavailableException ex) {
                ex.printStackTrace();
            }
            return null;
        }
    }

    public static void playMusic(String path) {
        stopMusic();
        Clip clip = getClip(path);
        clip.loop(Clip.LOOP_CONTINUOUSLY); 
        clip.start();
        currentClip = clip;
    }

    public static void stopMusic() {
        if (currentClip != null) {
            currentClip.stop();
            currentClip = null;
        }
    }
}