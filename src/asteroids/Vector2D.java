/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroids;

import java.awt.Point;

public class Vector2D {

    public double x;
    public double y;

    public Vector2D(double x, double y) {
        //set coordinates
        this.x = x;
        this.y = y;
    }

    public Vector2D(Point p) {
        //sets speed and direction
        this.x = p.x;
        this.y = p.y;
    }

    public Vector2D add(Vector2D u) {
        return new Vector2D(x + u.x, y + u.y);
    }

    public double angleBetween(Vector2D u) {
        return Math.acos(((x * u.x) + (y * u.y)) / (magnitude() * u.magnitude()));
    }

    public double angleOf() {
        return Math.atan(Math.abs(y) / Math.abs(x));
    }

    public Vector2D divide(double scalar) {
        return new Vector2D(x / scalar, y / scalar);
    }

    public double magnitude() {
        return Math.sqrt((x * x) + (y * y));
    }

    public Vector2D multiply(double scalar) {
        return new Vector2D(x * scalar, y * scalar);
    }

    public void setX(double newX) {
        x = newX;
    }

    public void setY(double newY) {
        y = newY;
    }

    public Vector2D subtract(Vector2D u) {
        return new Vector2D(x - u.x, y - u.y);
    }
}
