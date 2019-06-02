/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroids.gameobject;

import asteroids.Asteroids;
import asteroids.Vector2D;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;

public class Bullet extends GameObject {

    private double theta;
    private static double radX = 5;
    private static double radY = 5;

    public Bullet(Asteroids asteroids, Vector2D p, double theta, Vector2D v) {
        super(asteroids, p, v);
        this.theta = theta;
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        //graphics2D.draw(getBounds());
        graphics2D.fill(new Ellipse2D.Double(p.x, p.y, radX, radY));
    }

    public static double returnRad() {
        return radX;
    }

    @Override
    public void tick() {
//        if (p.x >= asteroids.getWidth() + radX) {
//            p.x -= asteroids.getWidth();
//        } else if (p.x <= -radX) {
//            p.x += asteroids.getWidth();
//        }
//
//        if (p.y >= asteroids.getHeight() + radY) {
//            p.y -= asteroids.getHeight();
//        } else if (p.y <= -radY) {
//            p.y += asteroids.getHeight();
//        }
        p.x += v.x * Math.sin(theta);
        p.y -= v.y * Math.cos(theta);
    }

    public Rectangle getBounds() {
        return new Rectangle((int) (p.x), (int) (p.y), (int) radX, (int) radY);
    }

}
