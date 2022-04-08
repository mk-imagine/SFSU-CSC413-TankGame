package tank_game.game_objects.stationary.powerup;

import tank_game.game_objects.Collidable;
import tank_game.game_objects.mobile.Tank;
import tank_game.game_objects.stationary.Stationary;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class PowerUp extends Stationary implements Collidable {

    protected PowerUp(int x, int y, BufferedImage img) {
        super(x, y, img);
    }

    public abstract void apply(Tank tank);
}
