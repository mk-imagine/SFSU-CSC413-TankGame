package tank_game.game_objects.stationary.wall;

import tank_game.game_objects.Collidable;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UnbreakableWall extends Wall implements Collidable {
    Rectangle hitBox;
    boolean isCollidable;

    public UnbreakableWall(int x, int y, BufferedImage img) {
        super(x, y, img);
        this.hitBox = new Rectangle(x,y,this.img.getWidth(),this.img.getHeight());
        this.isCollidable = true;
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
    public Rectangle getHitBox() {
        return this.hitBox.getBounds();
    }

    @Override
    public boolean isCollidable() {
        return true;
    }

    @Override
    public void destroy() {

    }

    @Override
    public boolean checkCollision(Collidable with) {
        return false;
    }

    @Override
    public void collide(Collidable with) {
    }

    @Override
    public String toString() {
        return "UnbreakableWall";
    }
}
