/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroids.gameobject;

import asteroids.Asteroids;
import asteroids.Vector2D;
import java.awt.Graphics2D;

public class Enemy extends GameObject {

    public Enemy(Asteroids asteroids, Vector2D p, Vector2D v) {
        super(asteroids, p, v);
    }

    public void draw(Graphics2D graphics2D) {
    }

    @Override
    public void tick() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
