package tank_game.game_objects.mobile;

import tank_game.GameSettings;
import tank_game.SoundPlayer;
import tank_game.game_objects.Collidable;
import tank_game.MapLoader;
import tank_game.game_objects.GameObject;
import tank_game.game_objects.stationary.wall.BreakableWall;
import tank_game.game_objects.stationary.wall.UnbreakableWall;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Bullet extends Mobile implements Collidable {
    private Rectangle hitBox;
    private boolean isCollidable;
    private int power;
    private Tank t;

    Bullet(MapLoader ml, Tank t, int x, int y, int vx, int vy, int speed, int rotationSpeed, float angle, BufferedImage img) {
        super(ml, x, y, vx, vy, speed, rotationSpeed, angle, img);
        this.hitBox = new Rectangle(x,y,this.img.getWidth(), this.img.getHeight());
        this.t = t;
        this.isCollidable = true;
        this.power = t.getBulletPower();
        SoundPlayer.CANNON.play();
    }

    @Override
    public void update() {
        this.moveForwards();
        this.checkBorder();
        this.hitBox.setLocation(this.x, this.y);
        this.runCollisions();
    }

    /**
     * MOVEMENT
     */
    private void moveForwards() {
        vx = (int) Math.round(speed * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(speed * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        checkBorder();
    }

    /**
     * COLLISIONS:
     * DETECTION AND RESULTS
     */
    private void checkBorder() {
        if (x < 30) {
            x = 30;
            this.destroy();
        }
        if (x >= GameSettings.WORLD_WIDTH - 100) {
            x = GameSettings.WORLD_WIDTH - 100;
            this.destroy();
        }
        if (y < 34) {
            y = 34;
            this.destroy();
        }
        if (y >= GameSettings.WORLD_HEIGHT - 118) {
            y = GameSettings.WORLD_HEIGHT - 118;
            this.destroy();
        }
    }

    private void runCollisions() {
        for(GameObject o : this.ml.getGameObjects()) {
            if(!o.equals(this) && o instanceof Collidable && this.checkCollision((Collidable) o)) {
                collide((Collidable) o);
            }
        }
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
    public boolean checkCollision(Collidable with) {
        return this.getHitBox().intersects(with.getHitBox());
    }

    @Override
    public void collide(Collidable with) {
        if (with instanceof Tank) {
            ((Tank) with).removeHealth(power);
            SoundPlayer.SMALL_EXPLOSION.play();
            this.destroy();
        }
        if (with instanceof UnbreakableWall) {
            SoundPlayer.SMALL_EXPLOSION.play();
            this.destroy();
        }
        if (with instanceof BreakableWall && with.isCollidable()) {
            ((BreakableWall) with).removeHealth(this.power);
            SoundPlayer.SMALL_EXPLOSION.play();
            this.destroy();
        }
//        ADD SOMETHING HERE IF WE WANT IT TO DO SOMETHING
//        if (with instanceof PowerUp) {
//
//        }
    }

    @Override
    public void destroy() {
        this.destroyed = true;
        t.removeBullet(this);
    }

    @Override
    public void reset() {

    }

    /**
     * DRAW
     */
    @Override
    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(this.angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, rotation, null);
    }

    @Override
    public String toString() {
        return "bullet";
    }
}
