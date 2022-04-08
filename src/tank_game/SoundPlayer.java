package tank_game;

import javax.sound.sampled.*;
import java.io.IOException;

    /**
     * @author Chua Hock-Chuan
     * @author Mark
     *
     * HEAVILY borrowed code from Chua Hock-Chuan:
     * https://www3.ntu.edu.sg/home/ehchua/programming/java/J8c_PlayingSound.html
     */

public enum SoundPlayer {
    MUSIC(Resource.getResourceSound("music")),
    CANNON(Resource.getResourceSound("cannon")),
    SMALL_EXPLOSION(Resource.getResourceSound("smallExplosion")),
    LARGE_EXPLOSION(Resource.getResourceSound("largeExplosion")),
    POWERUP(Resource.getResourceSound("powerup"));

    public static enum Volume {
        MUTE, LOW, MEDIUM, HIGH
    }

    public static Volume volume = Volume.LOW;

    private Clip clip;

    SoundPlayer(AudioInputStream sound) {
        try {
            clip = AudioSystem.getClip();
            clip.open(sound);
        } catch (IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void play() {
        if (volume != Volume.MUTE) {
            if(clip.isActive()) {
                clip.stop();
            }
            clip.setFramePosition(0);
            clip.start();
        }
    }

    public void loop() {
        if (volume != Volume.MUTE) {
            if (clip.isRunning()) {
                clip.stop();
            }
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.setFramePosition(0);
            clip.start();
        }
    }

    public void stop() {
        if (clip.isRunning()) {
            clip.stop();
        }
    }

    public static void init() {
        values();
    }
}
