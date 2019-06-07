package asteroids;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
import javax.swing.JTextField;

public class GameOverPanel extends JPanel {

    protected final Asteroids asteroids;
    private Font f;
    private Score s;
    private JLabel gameOverLabel = new JLabel("GAME OVER");
    private JTextField field = new JTextField("Enter your name here");
    private JButton leader = new JButton();
    private JButton play = new JButton();
    private JButton quit = new JButton();

    public GameOverPanel(Asteroids asteroids, Score s, JFrame frame) {
        this.asteroids = asteroids;
        this.s = s;
        JLabel scoreLabel = new JLabel(String.valueOf(s.getScore()));
        JPanel btnPanel = new JPanel();
        gameOverLabel.setFont(returnFont(100f));
        gameOverLabel.setForeground(Color.white);
        gameOverLabel.setAlignmentX(CENTER_ALIGNMENT);

        scoreLabel.setFont(returnFont(100f));
        scoreLabel.setForeground(Color.white);
        scoreLabel.setAlignmentX(CENTER_ALIGNMENT);

        field.setFont(returnFont(75f));
        field.setBorder(null);
        field.setCaretColor(Color.white);
        field.requestFocusInWindow();
        field.setForeground(Color.white);
        field.setBackground(Color.black);
        field.setHorizontalAlignment(JTextField.CENTER);
        field.setPreferredSize(new Dimension(1100, 100));
        field.setMaximumSize(field.getPreferredSize());
        field.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (field.getText().length() >= 3)                {
                    e.consume();
                }
                char keyChar = e.getKeyChar();
                if (Character.isLowerCase(keyChar)) {
                    e.setKeyChar(Character.toUpperCase(keyChar));
                }
            }
        });

        play.setMargin(new Insets(0, 0, 0, 0));
        play.setAlignmentX(CENTER_ALIGNMENT);
        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                //frame.removeAll();
                asteroids.restart();
            }
        });

        leader.setMargin(new Insets(0, 0, 0, 0));
        leader.setAlignmentX(CENTER_ALIGNMENT);

        quit.setMargin(new Insets(0, 0, 0, 0));
        quit.setAlignmentX(CENTER_ALIGNMENT);
        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        try {
            play.setIcon(new ImageIcon(ImageIO.read(new File(System.getProperty("user.dir") + "\\Graphics\\Misc\\play.png"))));
            leader.setIcon(new ImageIcon(ImageIO.read(new File(System.getProperty("user.dir") + "\\Graphics\\Misc\\leader.png"))));
            quit.setIcon(new ImageIcon(ImageIO.read(new File(System.getProperty("user.dir") + "\\Graphics\\Misc\\quit.png"))));
        } catch (IOException ex) {
        }

        btnPanel.add(play);
        btnPanel.add(leader);
        btnPanel.add(quit);
        btnPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 75, 0));
        btnPanel.setBackground(Color.black);

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        add(gameOverLabel);
        add(Box.createRigidArea(new Dimension(0, 125)));
        add(field);
        add(Box.createRigidArea(new Dimension(0, 40)));
        add(scoreLabel);
        add(Box.createRigidArea(new Dimension(0, 50)));
        add(btnPanel);
        setPreferredSize(new Dimension(asteroids.getWidth(), asteroids.getHeight()));
        setBackground(Color.black);
    }

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
