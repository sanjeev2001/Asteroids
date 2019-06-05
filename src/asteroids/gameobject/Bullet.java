package asteroids.gameobject;

import asteroids.Asteroids;
import asteroids.Vector2D;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class Bullet extends GameObject {

    private Color colour = Color.WHITE;
    private boolean hit = false;
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
            setRad(10);
        }
    }

    public void blastRadius() {
        if (count < 30) {
            r = new Rectangle((int) (p.x - 25), (int) (p.y - 25), (int) radX + 50, (int) radY + 50);
        }
    }

    public void bombTimer(String type) {
        ActionListener ac = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (type.equals("Flashing")) {
                    count++;
                    System.out.println(count);
                    if (count == 35) {
                        r = new Rectangle((int) (p.x - 25), (int) (p.y - 25), 0, 0);
                        timer.stop();
                    }
                    if (colour.equals(Color.BLACK)) {
                        colour = Color.WHITE;
                    } else {
                        colour = Color.BLACK;
                    }
                } else {
                    r = new Rectangle((int) (p.x - 25), (int) (p.y - 25), (int) radX + 50, (int) radY + 50);
                    count++;
                    System.out.println(count);
                    if (count == 5) {
                        r = new Rectangle((int) (p.x - 25), (int) (p.y - 25), 0, 0);
                        timer.stop();
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
        graphics2D.draw(getBounds());
        graphics2D.fillOval((int) p.x, (int) p.y, (int) radX, (int) radY);
    }

    public Rectangle getBounds() {
        return r;
    }

    public int getCount() {
        return count;
    }

    public void hit() {
        hit = true;
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
        p.x += v.x * Math.sin(theta);
        p.y -= v.y * Math.cos(theta);
        if (type.equals("Bomb") && count == 30) {
            v.x = 0;
            v.y = 0;
            r = new Rectangle((int) (p.x - 25), (int) (p.y - 25), (int) radX + 50, (int) radY + 50);
        } else if (hit) {
            count = 0;
            bombTimer("");
        } else if (count < 30) {
            r = new Rectangle((int) (p.x), (int) (p.y), (int) radX, (int) radY);
        }

    }

}
