package asteroids;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class MainMenu extends JPanel {

    protected final Asteroids asteroids;
    private JButton play = new JButton();
    private JButton quit = new JButton();
    private JButton leader = new JButton();
    private JLayeredPane pane = new JLayeredPane();
    private JLabel bg = new JLabel();
    private JLabel title = new JLabel();

    public MainMenu(Asteroids asteroids, JFrame frame) {
        this.asteroids = asteroids;
        this.setLayout(null);
        this.setPreferredSize(new Dimension(1100, 700));
        title.setBounds(1100 / 2 - 550 / 2, 125, 550, 120);//Sets component size and location
        pane.setBounds(0, 0, 1100, 700);
        bg.setBounds(0, 0, 1100, 700);
        play.setBounds(1100 / 2 - 200 / 2, 300, 200, 60);
        leader.setBounds(1100 / 2 - 450 / 2, 400, 450, 60);
        quit.setBounds(1100 / 2 - 200 / 2, 500, 200, 60);
        Sound sound = new Sound("intro");

        try {
            bg.setIcon(new ImageIcon(this.getClass().getResource("titlescreen.gif"))); //Displays image on JLabel
            title.setIcon(new ImageIcon(ImageIO.read(new File(System.getProperty("user.dir") + "\\Graphics\\Misc\\title.png")))); //Setting image for each button
            play.setIcon(new ImageIcon(ImageIO.read(new File(System.getProperty("user.dir") + "\\Graphics\\Misc\\play.png"))));
            leader.setIcon(new ImageIcon(ImageIO.read(new File(System.getProperty("user.dir") + "\\Graphics\\Misc\\leader.png"))));
            quit.setIcon(new ImageIcon(ImageIO.read(new File(System.getProperty("user.dir") + "\\Graphics\\Misc\\quit.png"))));
        } catch (IOException ex) {
        }

        this.add(pane);

        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bg.setVisible(false);
                pane.setVisible(false);
                sound.stopSound();
                frame.add(asteroids);
                asteroids.start();
            }
        });

        leader.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        this.add(pane);
        pane.add(title);
        pane.add(play);
        pane.add(leader);
        pane.add(quit);
        this.add(bg);

    }
}
