package asteroids.gameobject;

import asteroids.Asteroids;
import asteroids.Vector2D;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Timer;

public class Bullet extends GameObject {

    private boolean animate = false;
    private Color colour = Color.WHITE;
    private boolean done = false;
    private int frameNumber = 1;
    private BufferedImage image = null;
    private boolean hit = false;
    private int hitTime = 0;
    private static double radX;
    private static double radY;
    private Rectangle r = new Rectangle((int) (p.x), (int) (p.y), (int) radX, (int) radY);
    private double theta;
    private Timer timer;
    private String type;
    int count = 0;

    public Bullet(Asteroids asteroids, Vector2D p, double theta, Vector2D v, String type) {
        super(asteroids, p, v);
        this.theta = theta;
        this.type = type;
        if (type.equals("Bullet")) {
            setRad(5);
        } else {
            try {
                image = ImageIO.read(new File(System.getProperty("user.dir") + "\\Graphics\\Misc\\Bomb_" + frameNumber + ".png"));
            } catch (IOException ex) {
            }
            setRad(10);
        }
    }

    public Bullet blastRadius() {
        return new Bullet(asteroids, p, theta, new Vector2D(0, 0), type);
    }

    public void bombTimer(String type) {
        ActionListener ac = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (type.equals("Flashing")) {
                    count++;
                    if (colour.equals(Color.BLACK)) {
                        colour = Color.WHITE;
                    } else {
                        colour = Color.BLACK;
                    }
                    if (animate && !done) {
                        try {
                            image = ImageIO.read(new File(System.getProperty("user.dir") + "\\Graphics\\Misc\\Bomb_" + frameNumber + ".png"));
                        } catch (IOException ex) {
                        }
                        frameNumber++;
                        if (frameNumber > 6) {
                            animate = false;
                            hit = false;
                            done = true;
                            timer.stop();
                        }
                    }
                }
            }
        };
        timer = new Timer(100, ac);
        timer.start();
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        if (type.equals("Bomb")) {
            graphics2D.setColor(colour);
        }
        //graphics2D.draw(getBounds());
        if (type.equals("Bomb") && (count >= 5 && count <= 10 && !hit) || (count <= 10 + hitTime && hit) && !done) {
            graphics2D.drawImage(image, (int) (p.x - 20), (int) (p.y - 20), asteroids);
        } else {
            graphics2D.fillOval((int) p.x, (int) p.y, (int) radX, (int) radY);

        }
    }

    public Rectangle getBounds() {
        return r;
    }

    public int getCount() {
        return count;
    }

    public void hit() {
        hitTime = Integer.valueOf(count);
        hit = true;
    }

    public boolean returnDone() {
        return done;
    }

    public static double returnRad() {
        return radX;
    }

    public void setRad(double rad) {
        radX = rad;
        radY = rad;
    }

    public void stopTimer() {
        timer.stop();
    }

    @Override
    public void tick() {
        //System.out.println(done);
        p.x += v.x * Math.sin(theta);
        p.y -= v.y * Math.cos(theta);
        if (type.equals("Bomb") && (count >= 5 && count <= 10 && !hit) || (count <= 10 + hitTime && hit)) {
            animate = true;
            v.x = 0;
            v.y = 0;
            r = new Rectangle((int) (p.x - 25), (int) (p.y - 25), (int) radX + 50, (int) radY + 50);
            //System.out.println("1");
        } else if ((count > 10 && !hit) || (count >= 10 + hitTime && hit)) {
            v.x = 0;
            v.y = 0;
            r = new Rectangle();
            //System.out.println("2");
        } else {
            r = new Rectangle((int) p.x, (int) p.y, (int) radX, (int) radY);
            //System.out.println("3");
        }
    }
}
