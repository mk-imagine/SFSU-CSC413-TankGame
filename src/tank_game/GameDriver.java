/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tank_game;


import tank_game.game_objects.mobile.Tank;
import tank_game.game_objects.mobile.TankControl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;


import static javax.imageio.ImageIO.read;

/**
 *
 * @author anthony-pc
 * @author Mark Kim
 */
public class GameDriver extends JPanel implements Runnable {

    private BufferedImage world;
    private Launcher lf;
    private long tick = 0;
    private MapLoader mapLoader;
    private ArrayList<Player> players;
    private ArrayList<BufferedImage> explosion;
    private long explosionTimeBeginP1;
    private long explosionLengthP1;
    private long explosionTimeBeginP2;
    private long explosionLengthP2;
    private int framerate = 50;

    public GameDriver(Launcher lf){
        this.lf = lf;
    }

    /**
     * Load all resources for Tank Wars Game. Set all Game Objects to their
     * initial state as well.
     */
    void gameInitialize() {
        this.world = new BufferedImage(GameSettings.WORLD_WIDTH,
                                       GameSettings.WORLD_HEIGHT,
                                       BufferedImage.TYPE_INT_RGB);
        mapLoader = new MapLoader();
        this.mapLoader.initMap();
        players = new ArrayList<>();
        explosion = new ArrayList<>();

        for(int i=1; i < 26; i++) {
            explosion.add(Resource.getResourceImg("explode" + i));
        }

        Tank t1 = new Tank(mapLoader, 1, GameSettings.TANK1_START_X, GameSettings.TANK1_START_Y, 0, 0, GameSettings.TANK1_START_ANGLE, Resource.getResourceImg("t1img"));
        Tank t2 = new Tank(mapLoader, 2, GameSettings.TANK2_START_X, GameSettings.TANK2_START_Y, 0, 0, GameSettings.TANK2_START_ANGLE, Resource.getResourceImg("t2img"));
        TankControl tc1 = new TankControl(t1, GameSettings.tank1_up, GameSettings.tank1_down, GameSettings.tank1_left, GameSettings.tank1_right, GameSettings.tank1_shoot);
        TankControl tc2 = new TankControl(t2, GameSettings.tank2_up, GameSettings.tank2_down, GameSettings.tank2_left, GameSettings.tank2_right, GameSettings.tank2_shoot);
        players.add(t1.getPlayer());
        players.add(t2.getPlayer());
        mapLoader.addGameObject(t1);
        mapLoader.addGameObject(t2);
        this.lf.getJf().addKeyListener(tc1);
        this.lf.getJf().addKeyListener(tc2);
    }

    /**
     * Reset game to its initial state.
     */
    public void resetGame(){
        this.tick = 0;
        mapLoader.resetMap();
        this.resetPlayers();
    }

    private void resetPlayers() {
        players.get(0).reset(GameSettings.TANK1_START_X, GameSettings.TANK1_START_Y, GameSettings.TANK1_START_ANGLE);
        players.get(1).reset(GameSettings.TANK2_START_X, GameSettings.TANK2_START_Y, GameSettings.TANK2_START_ANGLE
        );
    }

    private BufferedImage crop (BufferedImage img, int width, int height) throws IOException {
        BufferedImage clippedImg = new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
        Graphics2D area = (Graphics2D) clippedImg.getGraphics().create();
        area.drawImage(img, 0,0, clippedImg.getWidth(), clippedImg.getHeight(), 0,0,clippedImg.getWidth(),clippedImg.getHeight(), null);
        area.dispose();
        return clippedImg;
    }

    private void drawExplosions(Graphics2D g) {
        if(players.get(0).isExplode()) {
            if(explosionLengthP1 == 0) {
                explosionTimeBeginP1 = System.currentTimeMillis();
            }
            int frame = (int) (explosionLengthP1 / framerate);
            g.drawImage(explosion.get(frame), players.get(0).getTankX(), players.get(0).getTankY(), null);
            players.get(0).setImg(Resource.getResourceImg("empty"));
            explosionLengthP1 = System.currentTimeMillis() - explosionTimeBeginP1;
            if(explosionLengthP1/framerate > 24) {
                explosionLengthP1 = 0;
                players.get(0).setExplode(false);
                players.get(0).setImg(Resource.getResourceImg("t1img"));
            }
        }
        if(players.get(1).isExplode()) {
            if (explosionLengthP2 == 0) {
                explosionTimeBeginP2 = System.currentTimeMillis();
            }
            int frame = (int) (explosionLengthP2 / framerate);
            g.drawImage(explosion.get(frame), players.get(1).getTankX(), players.get(1).getTankY(), null);
            players.get(1).setImg(Resource.getResourceImg("empty"));
            explosionLengthP2 = System.currentTimeMillis() - explosionTimeBeginP2;
            if (explosionLengthP2 / framerate > 24) {
                explosionLengthP2 = 0;
                players.get(1).setExplode(false);
                players.get(1).setImg(Resource.getResourceImg("t2img"));
            }
        }
    }

    private void drawPlayersLives(Graphics2D g) {
        g.scale(3f/4,3f/4);
        int p1lives = players.get(0).getLives();
        int p2lives = players.get(1).getLives();
        for (int i = 0; i < p1lives; i++) {
            g.drawImage(players.get(0).getImg(), 50 + (75 * i), 50, null);
        }
        for (int i = 0; i < p2lives; i++) {
            g.drawImage(players.get(1).getImg(), (GameSettings.GAME_SCREEN_WIDTH + 300) - (75 * i), 50, null);
        }
        g.scale(4f/3,4f/3);
    }

    private void drawMiniMap(Graphics2D g, BufferedImage mm) {
        g.scale(GameSettings.MM_SCALE,GameSettings.MM_SCALE);
        try {
            mm = crop(mm, (int) (mm.getWidth()*0.99), (int) (mm.getHeight()*0.975));
        } catch (IOException e) {
            e.printStackTrace();
        }
        double mm_xCenter = GameSettings.GAME_SCREEN_WIDTH/GameSettings.MM_SCALE - mm.getWidth();
        double mm_yCenter = (GameSettings.GAME_SCREEN_HEIGHT)/GameSettings.MM_SCALE - mm.getHeight();
        g.drawImage(mm, (int) (mm_xCenter * 0.5), (int) (mm_yCenter * 0.92), null);
        g.scale(1f/GameSettings.MM_SCALE,1f/GameSettings.MM_SCALE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Graphics2D buffer = world.createGraphics();
        buffer.setColor(Color.BLACK);
        buffer.fillRect(0,0, GameSettings.WORLD_WIDTH, GameSettings.WORLD_HEIGHT);
        mapLoader.drawFloors(buffer);
        mapLoader.drawGameObjects(buffer);
        drawExplosions(buffer);
        BufferedImage leftHalf = world.getSubimage(players.get(0).getX(), players.get(0).getY(),GameSettings.GAME_SCREEN_WIDTH/2, GameSettings.GAME_SCREEN_HEIGHT);
        BufferedImage rightHalf = world.getSubimage(players.get(1).getX(), players.get(1).getY(),GameSettings.GAME_SCREEN_WIDTH/2, GameSettings.GAME_SCREEN_HEIGHT);
        BufferedImage mm = world.getSubimage(0,0, GameSettings.WORLD_WIDTH,GameSettings.WORLD_HEIGHT);
        g2.drawImage(leftHalf,-1,0,null);
        g2.drawImage(rightHalf,GameSettings.GAME_SCREEN_WIDTH/2 + 1,0,null);
        drawPlayersLives(g2);
        drawMiniMap(g2, mm);
        if (mapLoader.playerDead() == 2) {
            BufferedImage gameoverImg = Resource.getResourceImg("gameover1");
            g2.drawImage(gameoverImg, GameSettings.GAME_SCREEN_WIDTH / 2 - gameoverImg.getWidth() / 2, GameSettings.GAME_SCREEN_HEIGHT / 2 - gameoverImg.getHeight() / 2, null);
        } else if (mapLoader.playerDead() == 1) {
            BufferedImage gameoverImg = Resource.getResourceImg("gameover2");
            g2.drawImage(gameoverImg, GameSettings.GAME_SCREEN_WIDTH / 2 - gameoverImg.getWidth() / 2, GameSettings.GAME_SCREEN_HEIGHT / 2 - gameoverImg.getHeight() / 2, null);
        }
    }

    @Override
    public void run(){
        try {
            this.resetGame();
            while (true) {
                this.tick++;
                mapLoader.updateMap();
                this.repaint();   // redraw game
                Thread.sleep(1000 / 144); //sleep for a few milliseconds
                if((explosionLengthP1/framerate > 23 || explosionLengthP2/framerate > 23) && mapLoader.playerDead() > 0) {
                    Thread.sleep(4000);
                    SoundPlayer.MUSIC.loop();
                    lf.setFrame("end");
                    return;
                }
            }
        } catch (InterruptedException ignored) {
            System.out.println(ignored);
        }
    }

}
