/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroids;

import asteroids.gameobject.Enemy;
import asteroids.gameobject.GameObject;
import asteroids.gameobject.Player;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferStrategy;
import java.util.LinkedList;
import javax.swing.JFrame;

public class Asteroids extends Canvas implements Runnable {

    private Player player = new Player(this, new Vector2D(550, 350), new Vector2D(0, 0));
    //private Enemy ball = new Enemy(this, new Vector2D(750, 350), 3, 3);
    private LinkedList<GameObject> list = new LinkedList<>();
    private final JFrame frame = new JFrame("Asteroids");
    private boolean run = false;

    public Asteroids() {
        addKeyListener(new KeyboardMovement());
        MouseMovement mListener = new MouseMovement(this);
        addMouseListener(mListener);
        addMouseMotionListener(mListener);
        list.add(player);
        //list.add(ball);
        setSize(new Dimension(1100, 700));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.add(this);
        frame.pack();
        start();
    }

    public static void main(String[] args) {
        Asteroids game = new Asteroids();
    }

    public void gameRender() {
        BufferStrategy strat = getBufferStrategy();

        if (strat == null) {
            createBufferStrategy(3);
            return;
        }

        Graphics2D g2d = (Graphics2D) strat.getDrawGraphics();

        g2d.setColor(Color.black);
        g2d.fill(new Rectangle(0, 0, getWidth(), getHeight()));

        for (int i = 0; i < list.size(); i++) {
            list.get(i).draw(g2d);
        }

        g2d.dispose();
        strat.show();
    }

    @Override
    public void run() {
        requestFocus();
        double targetFps = 60.0;
        double tickrate = 1000000000.0 / targetFps;
        long lastUpdate = System.nanoTime();
        long timer = System.currentTimeMillis();
        double unprocessed = 0.0;
        int fps = 0;
        int tps = 0;
        boolean ableRender = false;

        while (run) {
            long now = System.nanoTime();
            unprocessed += (now - lastUpdate) / tickrate;
            lastUpdate = now;

            MouseMovement.update();

            if (unprocessed >= 1) {
                tick();
                KeyboardMovement.update();
                unprocessed--;
                tps++;
                ableRender = true;
            } else {
                ableRender = false;
            }
            try {
                Thread.sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (ableRender) {
                gameRender();
                fps++;
            }

            if (System.currentTimeMillis() - 1000 > timer) {
                timer += 1000;
                //System.out.println("fps: " + fps + " tps: " + tps);
                fps = 0;
                tps = 0;
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

    public void tick() {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).tick();
        }
    }
}
