/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroids.gameobject;

import asteroids.Asteroids;
import java.awt.Graphics;
import java.awt.Graphics2D;

public abstract class GameObject {

    protected final Asteroids asteroids;
    public int x;
    public int y;
    public double xSpeed;
    public double ySpeed;

    public GameObject(Asteroids asteroids, int x, int y, double xSpeed, double ySpeed) {
        this.asteroids = asteroids;
        this.x = x;
        this.y = y;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double getXSpeed() {
        return xSpeed;
    }

    public double getYSpeed() {
        return ySpeed;
    }

    public abstract void draw(Graphics2D graphics2D);
}
