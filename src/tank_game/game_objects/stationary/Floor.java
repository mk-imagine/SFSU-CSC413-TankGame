package tank_game.game_objects.stationary;

import tank_game.MapLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Floor extends Stationary {

    public Floor(int x, int y, BufferedImage img) {
        super(x, y, img);
    }

    @Override
    public void drawImage(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, x, y, null);
    }

    @Override
    public void update() {

    }

    @Override
    public void reset() {

    }

    @Override
    public String toString() {
        return "Floor";
    }
}
