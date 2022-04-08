package tank_game.game_objects.stationary.powerup;

import tank_game.Resource;
import tank_game.SoundPlayer;
import tank_game.game_objects.Collidable;
import tank_game.game_objects.mobile.Tank;

import java.awt.*;
import java.awt.image.BufferedImage;

public class HealthUp extends PowerUp {
    private boolean isCollidable;
    private Rectangle hitBox;
    private int healthQty;

    public HealthUp(int x, int y, BufferedImage img) {
        super(x, y, img);
        this.isCollidable = true;
        this.hitBox = new Rectangle(x,y,this.img.getWidth(),this.img.getHeight());
        this.healthQty = 10;
    }

    public void apply(Tank to) {
        to.addHealth(healthQty);
        this.img = Resource.getResourceImg("empty");
        SoundPlayer.POWERUP.play();
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
        this.isCollidable = true;
        this.img = Resource.getResourceImg("healthup");
    }

    @Override
    public Rectangle getHitBox() {
        return this.hitBox.getBounds();
    }

    @Override
    public boolean isCollidable() {
        return isCollidable;
    }

    @Override
    public void destroy() {
        this.destroyed = true;
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
        return "Health Powerup";
    }
}
