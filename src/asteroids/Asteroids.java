package asteroids;

import asteroids.gameobject.Bullet;
import asteroids.gameobject.Enemy;
import asteroids.gameobject.Player;
import asteroids.gameobject.PowerUp;
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
    private LinkedList<PowerUp> powers = new LinkedList<>();
    private boolean run = false;
    private Random r = new Random();
    private Score s = new Score(this);
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
        for (int i = 0; i < player.getBullets().size(); i++) {
            for (int j = 0; j < enemies.size(); j++) {
                if (player.getBullets().get(i).collidesWith(enemies.get(j))) {
//                    if (enemies.get(j).getSize().equals("L")) {
//                        enemyCount--;
//                    }
                    if (enemies.get(j).getSize().equals("S")) {
                        powerUpSpawner("Bomb", enemies.get(j).getX(), enemies.get(j).getY());
                    }
                    player.getBullets().remove(i);
                    enemies.get(j).explode();
                    enemies.remove(j);
                    s.add(1);
                    if (player.getBullets().size() == 0) {
                        break;
                    }
                    i -= 1;

                }
                if (player.getBullets().size() == 0) {
                    break;
                }
            }
        }

        for (int i = 0; i < player.getBombs().size(); i++) {
            for (int j = 0; j < enemies.size(); j++) {
                if (player.getBombs().get(i).collidesWith(enemies.get(j))) {
                    player.getBombs().get(i).stopTimer();
                    if (enemies.get(j).getSize().equals("S")) {
                        powerUpSpawner("Bomb", enemies.get(j).getX(), enemies.get(j).getY());
                    }
                    if (player.getBombs().get(i).getCount() < 30) {
                        //player.getBombs().set(i, new Bullet(this, new Vector2D(player.getBombs().get(i).getX(), player.getBombs().get(i).getY()), 0, new Vector2D(0, 0), "Bomb"));
                        player.getBombs().get(i).hit();
                    }
                    //player.getBombs().remove(i);
                    enemies.remove(j);
                    s.add(1);
                    if (player.getBombs().size() == 0) {
                        break;
                    }
                    i -= 1;

                }
                if (player.getBombs().size() == 0) {
                    break;
                }
            }
        }
    }

    public void enemySpawner(String size, String type, double x, double y) {
        enemyCount++;
        enemies.add(new Enemy(this, new Vector2D(x, y), new Vector2D(0, 0), size));
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
        for (int i = 0; i < powers.size(); i++) {
            powers.get(i).draw(g2d);
        }

        s.draw(g2d);
        g2d.dispose();
        strat.show();
    }

    public static void main(String[] args) {
        Asteroids game = new Asteroids();
    }

    public void playerCollisions() {
        for (int i = 0; i < enemies.size(); i++) {
            if (enemies.get(i).getBounds().intersects(player.getBounds()) && player.isAlive()) {
                player.die();
            }
        }
        for (int i = 0; i < powers.size(); i++) {
            if (powers.get(i).getBounds().intersects(player.getBounds()) && player.isAlive()) {
                if (player.getPowerUpNumber("Health") < 3 || player.getPowerUpNumber("Bomb") < 3) {
                    player.applyPowerUp(powers.get(i).getType());
                    powers.remove(i);
                    i -= 1;
                }
            }
        }
    }

    public void powerUpSpawner(String type, double x, double y) {
        powers.add(new PowerUp(this, new Vector2D(x, y), new Vector2D(0, 0), type));
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
//                enemySpawner("L", "Random", r.nextDouble() * getWidth(), r.nextDouble() * getHeight());
//                enemySpawner("M", "Random", r.nextDouble() * getWidth(), r.nextDouble() * getHeight());
//                enemySpawner("M", "Random", r.nextDouble() * getWidth(), r.nextDouble() * getHeight());
//                enemySpawner("S", "Random", r.nextDouble() * getWidth(), r.nextDouble() * getHeight());
//                enemySpawner("S", "Random", r.nextDouble() * getWidth(), r.nextDouble() * getHeight());
            }

            bulletCollisions();
            playerCollisions();

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
