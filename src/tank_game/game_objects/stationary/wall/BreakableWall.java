package tank_game.game_objects.stationary.wall;

import tank_game.Resource;
import tank_game.game_objects.Collidable;

import java.awt.*;
import java.awt.image.BufferedImage;

import static javax.imageio.ImageIO.read;

public class BreakableWall extends Wall implements Collidable {

    int health = 20;
    Rectangle hitBox;
    boolean isCollidable;

    public BreakableWall(int x, int y, BufferedImage img) {
        super(x, y, img);
        this.hitBox = new Rectangle(x,y,this.img.getWidth(),this.img.getHeight());
        isCollidable = true;
    }

    public void removeHealth (int health) {
        if(this.health > 0) {
            this.health -= health;
        }
        if(this.health <= 0) {
            this.img = Resource.getResourceImg("empty");
        } else if (this.health <= 10) {
            this.img = Resource.getResourceImg("breakableWall1");
        }
    }

    @Override
    public void reset() {
        this.img = Resource.getResourceImg("breakableWall2");
        health = 20;
        this.isCollidable = true;
        this.destroyed = false;
    }

    @Override
    public void drawImage(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, x, y, null);
    }

    @Override
    public void update() {
        if(this.health <= 0) {
            this.destroy();
        }
    }

    @Override
    public Rectangle getHitBox() {
        return this.hitBox.getBounds();
    }

    @Override
    public boolean isCollidable() {
        return this.isCollidable;
    }

    @Override
    public void destroy() {
        this.isCollidable = false;
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
        return "Breakable Wall";
    }
}
