package tank_game;

import tank_game.game_objects.GameObject;
import tank_game.game_objects.mobile.Bullet;
import tank_game.game_objects.mobile.Tank;
import tank_game.game_objects.stationary.Floor;
import tank_game.game_objects.stationary.powerup.DoubleDamage;
import tank_game.game_objects.stationary.powerup.HealthUp;
import tank_game.game_objects.stationary.wall.BreakableWall;
import tank_game.game_objects.stationary.wall.OuterWall;
import tank_game.game_objects.stationary.wall.UnbreakableWall;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;

public class MapLoader {
    private ArrayList<GameObject> gameObjects;
    private ArrayList<Floor> floors;
    private int playerDead = 0;

    public ArrayList<GameObject> getGameObjects() {
        return gameObjects;
    }

    public void addGameObject(GameObject go) {
        this.gameObjects.add(go);
    }

    int playerDead() {
        return playerDead;
    }

    void resetMap() {
        this.playerDead = 0;
        gameObjects.forEach(GameObject::reset);
    }

    void updateMap() {
        for(int i = 0; i < gameObjects.size(); i++) {
            gameObjects.get(i).update();
            if(gameObjects.get(i).isDestroyed()) {
                if(gameObjects.get(i) instanceof Tank) {
                    playerDead = ((Tank) gameObjects.get(i)).getPlayer().getPlayerID();
                } else if (gameObjects.get(i) instanceof Bullet){
                    gameObjects.remove(i);
                }
            }
        }
    }

    void initMap() {
        this.gameObjects = new ArrayList<>();
        this.floors = new ArrayList<>();

        try {
            /*
             * note class loaders read files from the out folder (build folder in Netbeans) and not the
             * current working directory.
             */
            InputStreamReader isr = new InputStreamReader(Objects.requireNonNull(GameDriver.class.getClassLoader().getResourceAsStream("maps/map1")));
            BufferedReader mapReader = new BufferedReader(isr);

            String row = mapReader.readLine();
            if (row == null) {
                throw new IOException("no data in file");
            }

            String[] mapInfo = row.split("\t");
            int numCols = Integer.parseInt(mapInfo[0]);
            int numRows = Integer.parseInt(mapInfo[1]);

            for(int curRow = 0; curRow < numRows; curRow++) {
                row = mapReader.readLine();
                mapInfo = row.split("\t");
                for(int curCol = 0; curCol < numCols; curCol++) {
                    this.floors.add(new Floor((curCol * 320) - 500, (curRow * 240) - 500, Resource.getResourceImg("floor")));
                    switch(mapInfo[curCol]) {
                        case "2":
                            this.gameObjects.add(new BreakableWall(curCol*30, curRow * 30, Resource.getResourceImg("breakableWall2")));
                            break;
                        case "3":
                            this.gameObjects.add(new UnbreakableWall(curCol*30, curRow * 30, Resource.getResourceImg("unbreakableWall")));
                            break;
                        case "4":
                            this.gameObjects.add(new HealthUp(curCol*30, curRow * 30, Resource.getResourceImg("healthup")));
                            break;
                        case "5":
                            this.gameObjects.add(new DoubleDamage(curCol*30, curRow * 30, Resource.getResourceImg("dbldmg")));
                            break;
                        case "9":
                            this.gameObjects.add(new OuterWall(curCol*30, curRow * 30, Resource.getResourceImg("unbreakableWall")));
                    }
                }
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    void drawFloors(Graphics g) {
        floors.forEach(floor -> {
            floor.drawImage(g);
        });
    }

    void drawGameObjects(Graphics g) {
        for(int i = 0; i<gameObjects.size(); i++) {
            gameObjects.get(i).drawImage(g);
        }
    }
}
