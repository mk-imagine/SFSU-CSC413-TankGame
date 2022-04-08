package tank_game.game_objects.mobile;

import tank_game.MapLoader;
import tank_game.game_objects.GameObject;

import java.awt.image.BufferedImage;

/**
 *
 * @author Mark Kim
 */

public abstract class Mobile extends GameObject {
    MapLoader ml;
    int speed;
    float rotationSpeed;
    int vx;
    int vy;
    float angle;

    Mobile(MapLoader ml, int x, int y, int vx, int vy, int speed, float rotationSpeed, float angle, BufferedImage img) {
        super(x, y, img);
        this.vx = vx;
        this.vy = vy;
        this.angle = angle;
        this.speed = speed;
        this.rotationSpeed = rotationSpeed;
        this.ml = ml;
    }

}
