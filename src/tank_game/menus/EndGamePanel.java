package tank_game.menus;

import tank_game.Launcher;
import tank_game.Resource;
import tank_game.SoundPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class EndGamePanel extends JPanel {

    private BufferedImage menuBackground;
    private JButton start;
    private JButton exit;
    private Launcher lf;

    public EndGamePanel(Launcher lf) {
        this.lf = lf;
        menuBackground = Resource.getResourceImg("title");
        this.setBackground(Color.BLACK);
        this.setLayout(null);
        SoundPlayer.MUSIC.loop();

        start = new JButton("Restart Game");
        start.setFont(new Font("Courier New", Font.BOLD ,24));
        start.setBounds(150,300,175,50);
        start.addActionListener((actionEvent -> {
            SoundPlayer.MUSIC.stop();
            this.lf.setFrame("game");
        }));

        exit = new JButton("Exit");
        exit.setFont(new Font("Courier New", Font.BOLD ,24));
        exit.setBounds(150,400,175,50);
        exit.addActionListener((actionEvent -> {
            this.lf.closeGame();
        }));


        this.add(start);
        this.add(exit);

    }

    @Override
    public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.menuBackground,0,0,null);
    }
}
