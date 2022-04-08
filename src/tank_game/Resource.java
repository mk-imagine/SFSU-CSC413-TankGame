package tank_game;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static javax.imageio.ImageIO.read;

public class Resource {
    private static Map<String, BufferedImage> images;
    private static Map<String, AudioInputStream> sounds;

    static {
        Resource.images = new HashMap<>();
        Resource.sounds = new HashMap<>();
        try {
            Resource.images.put("title", read(Objects.requireNonNull(GameDriver.class.getClassLoader().getResource("Images/MenuElements/title.png"))));
            Resource.images.put("t1img", read(Objects.requireNonNull(GameDriver.class.getClassLoader().getResource("Images/PlayElements/tank1.png"))));
            Resource.images.put("t2img", read(Objects.requireNonNull(GameDriver.class.getClassLoader().getResource("Images/PlayElements/tank2.png"))));
            Resource.images.put("breakableWall1", read(Objects.requireNonNull(GameDriver.class.getClassLoader().getResource("Images/MapElements/BreakableWall1.gif"))));
            Resource.images.put("breakableWall2", read(Objects.requireNonNull(GameDriver.class.getClassLoader().getResource("Images/MapElements/BreakableWall2.gif"))));
            Resource.images.put("unbreakableWall", read(Objects.requireNonNull(GameDriver.class.getClassLoader().getResource("Images/MapElements/UnbreakableWall.gif"))));
            Resource.images.put("shell", read(Objects.requireNonNull(GameDriver.class.getClassLoader().getResource("Images/PlayElements/Shell.png"))));
            Resource.images.put("floor", read(Objects.requireNonNull(GameDriver.class.getClassLoader().getResource("Images/MapElements/Background.bmp"))));
            Resource.images.put("healthup", read(Objects.requireNonNull(GameDriver.class.getClassLoader().getResource("Images/PowerUp/health.png"))));
            Resource.images.put("dbldmg", read(Objects.requireNonNull(GameDriver.class.getClassLoader().getResource("Images/PowerUp/doubledamage.png"))));
            Resource.images.put("empty", read(Objects.requireNonNull(GameDriver.class.getClassLoader().getResource("Images/empty.png"))));
            Resource.images.put("gameover1", read(Objects.requireNonNull(GameDriver.class.getClassLoader().getResource("Images/GameOver1.png"))));
            Resource.images.put("gameover2", read(Objects.requireNonNull(GameDriver.class.getClassLoader().getResource("Images/GameOver2.png"))));

            for(int i = 1; i < 26; i++) {
                Resource.images.put("explode" + i, read(Objects.requireNonNull(GameDriver.class.getClassLoader().getResource("Images/Explosion/explosion"+ i + ".png"))));
            }

            Resource.sounds.put("smallExplosion", AudioSystem.getAudioInputStream(Objects.requireNonNull(GameDriver.class.getClassLoader().getResource("Sounds/Explosion_small.wav"))));
            Resource.sounds.put("largeExplosion", AudioSystem.getAudioInputStream(Objects.requireNonNull(GameDriver.class.getClassLoader().getResource("Sounds/Explosion_large.wav"))));
            Resource.sounds.put("cannon", AudioSystem.getAudioInputStream(Objects.requireNonNull(GameDriver.class.getClassLoader().getResource("Sounds/CannonShort.wav"))));
            Resource.sounds.put("music", AudioSystem.getAudioInputStream(Objects.requireNonNull(GameDriver.class.getClassLoader().getResource("Sounds/Music.mid"))));
            Resource.sounds.put("powerup", AudioSystem.getAudioInputStream(Objects.requireNonNull(GameDriver.class.getClassLoader().getResource("Sounds/powerup.wav"))));
        } catch (IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
            System.exit(-5);
        }
    }

    public static BufferedImage getResourceImg(String key) {
        return Resource.images.get(key);
    }

    public static AudioInputStream getResourceSound(String key) {
        return Resource.sounds.get(key);
    }
}
