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

public abstract class GameObject {

    protected final Asteroids asteroids;
    public Vector2D p;
    public Vector2D v;
    private Rectangle r;

    public GameObject(Asteroids asteroids, Vector2D p, Vector2D v) {
        this.asteroids = asteroids;
        this.p = p;
        this.v = v;
    }

    public boolean collidesWith(GameObject e) {
        return getBounds().intersects(e.getBounds());
    }

    public abstract void draw(Graphics2D graphics2D);

    public double getX() {
        return p.x;
    }

    public double getY() {
        return p.y;
    }

    public double getXSpeed() {
        return v.x;
    }

    public double getYSpeed() {
        return v.y;
    }

    public abstract Rectangle getBounds();

    public abstract void tick();

}
