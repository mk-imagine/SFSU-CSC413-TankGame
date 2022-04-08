package tank_game.game_objects;

import tank_game.MapLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;

/**
 *
 * @author Mark Kim
 */

public abstract class GameObject {

    protected boolean destroyed;
    protected int x;
    protected int y;
    protected BufferedImage img;

    protected GameObject(int x, int y, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.img = img;
        this.destroyed = false;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public abstract void drawImage(Graphics g);

    public abstract void update();

    public abstract void reset();

}
