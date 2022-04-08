package tank_game.game_objects.stationary.wall;

import tank_game.game_objects.stationary.Stationary;

import java.awt.image.BufferedImage;

public abstract class Wall extends Stationary {

    protected Wall(int x, int y, BufferedImage img) {
        super(x, y, img);
    }

}
