package asteroids;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameOverPanel extends JPanel {

    protected final Asteroids asteroids;
    private Font f;
    private Score s;
    private JLabel gameOverLabel = new JLabel("GAME OVER");
    private JButton mm = new JButton();
    private JButton quit = new JButton();

    public GameOverPanel(Asteroids asteroids, Score s, JFrame frame) {
        this.asteroids = asteroids;
        this.s = s;
        JLabel scoreLabel = new JLabel(String.valueOf(s.getScore()));
        JPanel btnPanel = new JPanel();
        gameOverLabel.setFont(returnFont(100f));
        gameOverLabel.setForeground(Color.white);
        gameOverLabel.setAlignmentX(CENTER_ALIGNMENT);
        
        //makes font for the score
        scoreLabel.setFont(returnFont(100f));
        scoreLabel.setForeground(Color.white);
        scoreLabel.setAlignmentX(CENTER_ALIGNMENT);
        
        //alligning all the buttons
        mm.setMargin(new Insets(0, 0, 0, 0));
        mm.setAlignmentX(CENTER_ALIGNMENT);
        mm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                asteroids.restart();
            }
        });

        quit.setMargin(new Insets(0, 0, 0, 0));
        quit.setAlignmentX(CENTER_ALIGNMENT);
        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        try {
            //loads images for buttons
            mm.setIcon(new ImageIcon(ImageIO.read(new File(System.getProperty("user.dir") + "\\Graphics\\Misc\\main.png"))));
            quit.setIcon(new ImageIcon(ImageIO.read(new File(System.getProperty("user.dir") + "\\Graphics\\Misc\\quit.png"))));
        } catch (IOException ex) {
        }
        
        //adds button to same panel
        btnPanel.add(mm);
        btnPanel.add(quit);
        btnPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 75, 0));
        btnPanel.setBackground(Color.black);
        
        //adds all panels to main panel
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        add(gameOverLabel);
        add(Box.createRigidArea(new Dimension(0, 150)));
        add(scoreLabel);
        add(Box.createRigidArea(new Dimension(0, 200)));
        add(btnPanel);
        setPreferredSize(new Dimension(asteroids.getWidth(), asteroids.getHeight()));
        setBackground(Color.black);
    }
    
    //imports cunstom font
    public Font returnFont(float size) {
        try {
            f = Font.createFont(Font.PLAIN, new File(System.getProperty("user.dir") + "\\Fonts\\Hyperspace.otf")).deriveFont(size);
        } catch (FontFormatException ex) {
            System.out.println("Font not valid");
        } catch (IOException ex) {
            System.out.println("Font not found");
        }
        return f;
    }

}
