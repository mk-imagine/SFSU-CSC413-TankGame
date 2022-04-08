package tank_game.game_objects;

import java.awt.*;

public interface Collidable {

    Rectangle getHitBox();

    boolean isCollidable();

    void destroy();

    boolean checkCollision(Collidable with);

    void collide(Collidable with);
}
