/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroids;

import asteroids.gameobject.Enemy;
import asteroids.gameobject.Player;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferStrategy;
import java.util.LinkedList;
import java.util.Random;
import javax.swing.JFrame;

public class Asteroids extends Canvas implements Runnable {

    private Player player = new Player(this, new Vector2D(550, 350), new Vector2D(0, 0));
    private LinkedList<Enemy> enemies = new LinkedList<>();
    private final JFrame frame = new JFrame("Asteroids");
    private boolean run = false;
    Random r = new Random();
    public int enemyCount = 0;

    public Asteroids() {
        addKeyListener(new KeyboardMovement());
        MouseMovement mListener = new MouseMovement(this);
        addMouseListener(mListener);
        addMouseMotionListener(mListener);
        setSize(new Dimension(1100, 700));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.add(this);
        frame.pack();
        start();
    }

    public void bulletCollisions() {
        for (int i = 0; i < player.returnBullets().size(); i++) {
            for (int j = 0; j < enemies.size(); j++) {
                if (player.returnBullets().get(i).collidesWith(enemies.get(j))) {
//                    if (enemies.get(j).getSize().equals("L")) {
//                        enemyCount--;
//                    }
                    player.returnBullets().remove(i);
                    enemies.get(j).explode();
                    enemies.remove(j);
                    if (player.returnBullets().size() == 0) {
                        break;
                    }
                    i -= 1;

                }
                if (player.returnBullets().size() == 0) {
                    break;
                }
            }
        }
    }

    public void enemySpawner(String size, String type, double x, double y) {
        enemyCount++;
        enemies.add(new Enemy(this, new Vector2D(x, y), new Vector2D(2, 2), size));
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
        
        player.draw(g2d);

        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).draw(g2d);
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
            if (enemyCount == 0) {
                enemySpawner("L", "Random", r.nextDouble() * getWidth(), r.nextDouble() * getHeight());
            }

            bulletCollisions();

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
        player.tick();
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).tick();
        }
    }
}
