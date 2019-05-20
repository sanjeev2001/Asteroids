/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroids.gameobject;

import asteroids.Asteroids;
import asteroids.Vector2D;
import java.awt.Graphics2D;

public abstract class GameObject {

    protected final Asteroids asteroids;
    public Vector2D p;
    public double xSpeed;
    public double ySpeed;

    public GameObject(Asteroids asteroids, Vector2D p, double xSpeed, double ySpeed) {
        this.asteroids = asteroids;
        this.p = p;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }

    public abstract void draw(Graphics2D graphics2D);

    public double getX() {
        return p.x;
    }

    public double getY() {
        return p.y;
    }

    public double getXSpeed() {
        return xSpeed;
    }

    public double getYSpeed() {
        return ySpeed;
    }

    public abstract void tick();

}
