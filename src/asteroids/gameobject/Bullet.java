/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroids.gameobject;

import asteroids.Asteroids;
import asteroids.Vector2D;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public class Bullet extends GameObject {

    double theta;

    public Bullet(Asteroids asteroids, Vector2D p, double theta, Vector2D v) {
        super(asteroids, p, v);
        this.theta = theta;
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        graphics2D.fill(new Ellipse2D.Double(p.x, p.y, 5, 5));
    }

    @Override
    public void tick() {
        p.x += 3 * Math.sin(theta);
        p.y -= 3 * Math.cos(theta);
    }

}
