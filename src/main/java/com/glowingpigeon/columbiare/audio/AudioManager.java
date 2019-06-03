package com.glowingpigeon.columbiare.audio;

import javax.sound.sampled.*;

import java.io.*;
import java.util.*;

import com.glowingpigeon.columbiare.Columbiare;

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
                    new BufferedInputStream(
                        Columbiare.class.getClassLoader().getResourceAsStream(path)
                    )
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
            currentClip.flush();
            currentClip = null;
        }
    }

    public static void playSound(String path) {
        Clip clip = getClip(path);
        clip.setFramePosition(0);
        clip.start();
    }
}