/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroids.gameobject;

import asteroids.Asteroids;
import java.awt.Graphics2D;

public class PowerUp extends GameObject {

    public PowerUp(Asteroids asteroids, int x, int y, double xSpeed, double ySpeed) {
        super(asteroids, x, y, xSpeed, ySpeed);
    }

    @Override
    public void draw(Graphics2D graphics2D) {
    }

}
