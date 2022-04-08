package tank_game.game_objects.stationary.wall;

import tank_game.game_objects.Collidable;

import java.awt.*;
import java.awt.image.BufferedImage;

public class OuterWall extends Wall {

    public OuterWall (int x, int y, BufferedImage img) {
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
        this.destroyed = false;
    }

    @Override
    public String toString() {
        return "UnbreakableWall";
    }
}
