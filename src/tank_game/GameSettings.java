package tank_game;

import java.awt.event.KeyEvent;

public class GameSettings {
    public static final int NUMBER_OF_PLAYERS = 2;

    public static final int WORLD_WIDTH = 2093;
    public static final int WORLD_HEIGHT = 2119;

    public static final int GAME_SCREEN_WIDTH = 1309;
    public static final int GAME_SCREEN_HEIGHT = 1002;

    public static final int START_MENU_SCREEN_WIDTH = 500;
    public static final int START_MENU_SCREEN_HEIGHT = 550;

    public static final int END_MENU_SCREEN_WIDTH = 500;
    public static final int END_MENU_SCREEN_HEIGHT = 500;

    public static final int TANK1_START_X = 100;
    public static final int TANK1_START_Y = 100;
    public static final int TANK1_START_ANGLE = 45;
    public static int tank1_up = KeyEvent.VK_W;
    public static int tank1_left = KeyEvent.VK_A;
    public static int tank1_right = KeyEvent.VK_D;
    public static int tank1_down = KeyEvent.VK_S;
    public static int tank1_shoot = KeyEvent.VK_SPACE;

    public static final int TANK2_START_X = WORLD_WIDTH - 170;
    public static final int TANK2_START_Y = WORLD_HEIGHT - 190;
    public static final int TANK2_START_ANGLE = 225;
    public static int tank2_up = KeyEvent.VK_UP;
    public static int tank2_left = KeyEvent.VK_LEFT;
    public static int tank2_right = KeyEvent.VK_RIGHT;
    public static int tank2_down = KeyEvent.VK_DOWN;
    public static int tank2_shoot = KeyEvent.VK_ENTER;

    public static final int BULLET_FIRE_RATE = 550;
    public static final int MAX_BULLETS_ALLOWED = 20;

    public static final double MM_SCALE = 0.10;
}
