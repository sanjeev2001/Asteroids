/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroids;

import asteroids.gameobject.GameObject;
import asteroids.gameobject.Player;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.LinkedList;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Asteroids extends JPanel implements Runnable {

    private Player player = new Player(this, 550, 350, 3, 3);
    private LinkedList<GameObject> list = new LinkedList<>();
    private final JFrame frame = new JFrame("Asteroids");
    private boolean run = false;

    public Asteroids() {
        addMouseListener(player);
        setPreferredSize(new Dimension(1100, 700));
        list.add(player);
        setBackground(Color.BLACK);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.add(this);
        frame.pack();
        start();
    }

    public static void main(String[] args) {
        Asteroids game = new Asteroids();
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        for (int i = 0; i < list.size(); i++) {
            list.get(i).draw((Graphics2D) graphics);
        }
    }

    @Override
    public void run() {
        while (run) {
            try {
                repaint();
                Thread.sleep(1);
            } catch (Exception e) {

            }
        }
    }

    public void start() {
        Thread thread = new Thread(this);
        run = true;
        thread.start();
    }

    public void stop() {
        run = false;
    }
}
