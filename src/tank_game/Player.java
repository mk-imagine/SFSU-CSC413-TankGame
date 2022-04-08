package tank_game;

import tank_game.GameSettings;
import tank_game.game_objects.mobile.Tank;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player {

    private int playerID;
    private BufferedImage img;
    private Tank t;
    private int x;
    private int y;

    public Player(int playerID, Tank t) {
        this.t = t;
        setCamera(t);
        this.img = t.getImg();
        this.playerID = playerID;
    }

    public void setCamera(Tank t) {
        if (t.getX() - GameSettings.GAME_SCREEN_WIDTH/4 <= 0) {
            x = 0;
        } else if (t.getX() - GameSettings.GAME_SCREEN_WIDTH/4 >= GameSettings.GAME_SCREEN_WIDTH + 110) {
            x = GameSettings.GAME_SCREEN_WIDTH + 110;
        } else {
            x = t.getX() - GameSettings.GAME_SCREEN_WIDTH/4;
        }

        if (t.getY() - GameSettings.GAME_SCREEN_HEIGHT/2 <= 0) {
            y = 0;
        } else if (t.getY() - GameSettings.GAME_SCREEN_HEIGHT/2 >= GameSettings.GAME_SCREEN_HEIGHT + 106) {
            y = GameSettings.GAME_SCREEN_HEIGHT + 106;
        } else {
            y = t.getY() - GameSettings.GAME_SCREEN_HEIGHT/2;
        }
    }

    BufferedImage getImg() {
        return this.img;
    }

    void setImg(BufferedImage img) {
        this.t.setImg(img);
    }

    int getLives() {
        return this.t.getLives();
    }

    int getX() {
        return this.x;
    }

    int getY() {
        return this.y;
    }

    boolean isExplode() { return this.t.isExplode();}

    void setExplode(boolean explode) {
        this.t.setExplode(explode);
    }

    int getTankX() {
        return this.t.getX();
    }

    int getTankY() {
        return this.t.getY();
    }

    public int getPlayerID() {
        return this.playerID;
    }

    void reset(int x, int y, int angle) {
        t.reset(x, y, angle);
    }
}
