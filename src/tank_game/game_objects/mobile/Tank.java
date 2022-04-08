package tank_game.game_objects.mobile;



import tank_game.*;
import tank_game.game_objects.Collidable;
import tank_game.game_objects.GameObject;
import tank_game.game_objects.stationary.powerup.PowerUp;
import tank_game.game_objects.stationary.wall.Wall;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author anthony-pc
 * @author Mark Kim
 * 
 */
public class Tank extends Mobile implements Collidable {

    private MapLoader ml;
    private final Player player;

    private final Rectangle hitBox;
    private boolean isCollidable;

    private ArrayList<Bullet> bulletsShot;
    private long bulletLastShot;
    private int bulletPower = 10;

    private int health = 100;
    private int lives = 3;
    private boolean explode = false;

    private boolean upPressed;
    private boolean downPressed;
    private boolean rightPressed;
    private boolean leftPressed;
    private boolean shootPressed;

    public Tank(MapLoader ml, int playerID, int x, int y, int vx, int vy, int angle, BufferedImage img) {
        super(ml, x, y, vx, vy, 2, 3.0f, angle, img);
        this.hitBox = new Rectangle(x,y,this.img.getWidth(),this.img.getHeight());
        this.player = new Player(playerID,this);
        this.ml = ml;
        this.bulletsShot = new ArrayList<>();
        this.isCollidable = true;
    }

    @Override
    public void update() {
        if (this.upPressed) {
            this.moveForwards();
        }
        if (this.downPressed) {
            this.moveBackwards();
        }

        if (this.leftPressed) {
            this.rotateLeft();
        }
        if (this.rightPressed) {
            this.rotateRight();
        }
        if (this.shootPressed) {
            this.shoot();
        }
        this.checkBorder();
        this.hitBox.setLocation(x,y);
        this.player.setCamera(this);
        this.runCollisions();
        if (this.health <= 0) {
            this.die();
            this.explode = true;
        }
        if (this.lives <= 0) {
            this.destroy();
        }
    }

    /**
     * MOVEMENT AND CONTROLS
     */
    void toggleUpPressed() {
        this.upPressed = true;
    }

    void toggleDownPressed() {
        this.downPressed = true;
    }

    void toggleRightPressed() {
        this.rightPressed = true;
    }

    void toggleLeftPressed() {
        this.leftPressed = true;
    }

    void toggleShootPressed() {
        this.shootPressed = true;
    }

    void unToggleUpPressed() {
        this.upPressed = false;
    }

    void unToggleDownPressed() {
        this.downPressed = false;
    }

    void unToggleRightPressed() {
        this.rightPressed = false;
    }

    void unToggleLeftPressed() {
        this.leftPressed = false;
    }

    void unToggleShootPressed() {
        this.shootPressed = false;
    }

    private void rotateLeft() {
        this.angle -= this.rotationSpeed;
    }

    private void rotateRight() {
        this.angle += this.rotationSpeed;
    }

    private void moveBackwards() {
        vx = (int) Math.round(speed * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(speed * Math.sin(Math.toRadians(angle)));
        x -= vx;
        y -= vy;
    }

    private void moveForwards() {
        vx = (int) Math.round(speed * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(speed * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
    }

    private void shoot() {
        if (bulletsShot.size() < GameSettings.MAX_BULLETS_ALLOWED && System.currentTimeMillis() - bulletLastShot > GameSettings.BULLET_FIRE_RATE) {
            int startx = (this.x + 12) + (37 * (int) Math.round(Math.cos(Math.toRadians(angle))));
            int starty = (this.y + 12) + (37 * (int) Math.round(Math.sin(Math.toRadians(angle))));
            Bullet b = new Bullet(ml, this, startx, starty, 0,0, 7, 0, this.angle, Resource.getResourceImg("shell"));
            ml.addGameObject(b);
            this.bulletsShot.add(b);
            this.bulletLastShot = System.currentTimeMillis();
//            System.out.println("Bullet Shot " + bulletsShot.size());
        }
    }

    /**
     * COLLISIONS:
     * DETECTION AND RESULTS
     */
    private void checkBorder() {
        if (x < 60) {
            x = 60;
        }
        if (x >= GameSettings.WORLD_WIDTH - 132) {
            x = GameSettings.WORLD_WIDTH - 132;
        }
        if (y < 64) {
            y = 64;
        }
        if (y >= GameSettings.WORLD_HEIGHT - 148) {
            y = GameSettings.WORLD_HEIGHT - 148;
        }
    }

    private void bump() {
        if (this.upPressed) {
            if(vx != 0) {
                x -= vx;
            }
            if(vy != 0) {
                y -= vy;
            }
        }
        if (this.downPressed) {
            if(vx != 0) {
                x += vx;
            }
            if(vy != 0) {
                y += vy;
            }
        }
    }

    private void push(GameObject t) {
        vx = (int) Math.round(speed / 2 * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(speed / 2 * Math.sin(Math.toRadians(angle)));
        if (this.upPressed) {
            x -= vx*4;
            y -= vy*4;
            t.setX(t.getX() + vx);
            t.setY(t.getY() + vy);
        }
        if (this.downPressed) {
            x += vx*4;
            y += vy*4;
            t.setX(t.getX() - vx);
            t.setY(t.getY() - vy);
        }
    }

    private void runCollisions() {
        for(GameObject o : ml.getGameObjects()) {
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
        return this.isCollidable;
    }

    @Override
    public boolean checkCollision(Collidable with) {
        return this.getHitBox().intersects(with.getHitBox());
    }

    @Override
    public void collide(Collidable with) {
        if (with.isCollidable()) {
            if (with instanceof Tank) {
                this.push((GameObject) with);
            }
            if(with instanceof Wall) {
                this.bump();
            }
            if(with instanceof PowerUp) {
                ((PowerUp) with).apply(this);
//                System.out.println("PowerUp: " + with.toString());
                with.destroy();
            }
        }
    }

    /**
     * TANK ATTRIBUTE MANIPULATIONS
     * FOR DAMAGE AND POWER-UPS
     */
    public void addHealth(int qty) {
        this.health += qty;
    }

    public void removeHealth(int damage) {
        this.health -= damage;
    }

    public void removeBullet(Bullet b) {
        bulletsShot.remove(b);
    }

    public void setBulletPower(int power) {
        this.bulletPower = power;
    }

    private void die() {
        SoundPlayer.LARGE_EXPLOSION.play();
        lives--;
        this.health = 100;
//        System.out.println("Tank dies/n/tLives left: " + lives);
    }

    @Override
    public void destroy() {
        this.destroyed = true;
    }

    /**
     * MISC GETTERS & SETTERS
     */
    public int getLives() {
        return this.lives;
    }

    public void setExplode(boolean explode) {
        this.explode = explode;
    }

    public boolean isExplode() {
        return this.explode;
    }

    public int getBulletPower() {
        return this.bulletPower;
    }

    public BufferedImage getImg() {
        return this.img;
    }

    public MapLoader getMapLoader() {
        return this.ml;
    }

    public Player getPlayer() {
        return this.player;
    }

    public void setImg(BufferedImage img) {
        this.img = img;
    }

    /**
     * RESET FUNCTIONS
     */
    public void reset(int x, int y, int angle) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        reset();
        player.setCamera(this);
    }

    @Override
    public void reset() {
        this.destroyed = false;
        this.unToggleDownPressed();
        this.unToggleLeftPressed();
        this.unToggleRightPressed();
        this.unToggleUpPressed();
        this.unToggleShootPressed();
        this.bulletPower = 10;
        this.health = 100;
        this.lives = 3;
        player.setCamera(this);
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
        g2d.setColor(Color.BLACK);
        g2d.drawRect(this.x, this.y - 20, 50, 5);
        g2d.fillRect(this.x, this.y - 20, 50, 5);
        g2d.setColor(Color.RED);
        g2d.fillRect(this.x, this.y - 20, this.health/2, 5);

    }

    @Override
    public String toString() {
        return "Lives: " + lives + "Health: " + health + "x=" + x + ", y=" + y + ", angle=" + angle + ", vx: " + vx + ", vy: " + vy;
    }
}
