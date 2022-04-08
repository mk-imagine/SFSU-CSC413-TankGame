package tank_game.game_objects.stationary;

import tank_game.game_objects.GameObject;

import java.awt.image.BufferedImage;

public abstract class Stationary extends GameObject {

    protected Stationary(int x, int y, BufferedImage img) {
        super(x, y, img);
    }

}
