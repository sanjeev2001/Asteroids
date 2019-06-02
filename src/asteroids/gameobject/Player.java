/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroids.gameobject;

import asteroids.Asteroids;
import asteroids.MouseMovement;
import asteroids.KeyboardMovement;
import asteroids.Vector2D;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import javax.imageio.ImageIO;

public class Player extends GameObject {

    public LinkedList<Bullet> bullets = new LinkedList<>();
    private BufferedImage image = null;
    private double theta = 0;
    private double time = 0.1;
    Vector2D mouseVector = new Vector2D(MouseMovement.getX(), MouseMovement.getY());

    public Player(Asteroids asteroids, Vector2D p, Vector2D v) {
        super(asteroids, p, v);
        try {
            image = ImageIO.read(new File(System.getProperty("user.dir") + "\\Graphics\\Ship.png"));
        } catch (IOException ex) {
        }
    }

    public void draw(Graphics2D graphics2D) {
        AffineTransform at = new AffineTransform();
        at.setToTranslation(p.x - image.getWidth() / 2, p.y - image.getHeight() / 2);
        at.rotate(theta, (image.getWidth() / 2) + 1, (image.getHeight() / 2) + 1);
        graphics2D.setColor(Color.white);
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).draw(graphics2D);
        }
        graphics2D.drawImage(image, at, asteroids);
    }

    public void playerMovement(int key, boolean released) {
        if (p.x >= asteroids.getWidth() + image.getWidth()) {
            p.x -= asteroids.getWidth();
        } else if (p.x <= -image.getWidth()) {
            p.x += asteroids.getWidth();
        }

        if (p.y >= asteroids.getHeight() + image.getHeight()) {
            p.y -= asteroids.getHeight();
        } else if (p.y <= -image.getWidth()) {
            p.y += asteroids.getHeight();
        }

        if (released == false) {
            switch (key) {
                case KeyEvent.VK_UP:
                case KeyEvent.VK_W:
                    //System.out.println(v.x);
                    if (v.add(new Vector2D(time, time)).x <= 9) {
                        v = v.add(new Vector2D(time, time));
                    }
                    p = p.add(new Vector2D(v.x * Math.sin(theta), -v.y * Math.cos(theta)));
                    break;
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_D:
                    theta += Math.toRadians(3);
                    break;
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_A:
                    theta -= Math.toRadians(3);
                    break;
            }
        } else {
            if (v.subtract(new Vector2D(time, time)).x >= 0) {
                v = v.subtract(new Vector2D(time, time));
                p = p.add(new Vector2D(v.x * Math.sin(theta), -v.y * Math.cos(theta)));
            }
        }
    }

    public void mouseMove() {
        boolean deadzone = !(MouseMovement.getX() >= p.x - 25 && MouseMovement.getX() <= p.x + image.getHeight() + 25 && MouseMovement.getY() >= p.y - 25 && MouseMovement.getY() <= p.y + image.getHeight() + 25);
        mouseVector.setX(MouseMovement.getX());
        mouseVector.setY(MouseMovement.getY());
        Vector2D r = mouseVector.subtract(p);

        if ((MouseMovement.getX() - p.x - (image.getHeight() / 2)) != 0 && MouseMovement.isMouseInside() == true && deadzone) {
            if (MouseMovement.getX() > p.x && MouseMovement.getY() < p.y) {
                theta = -r.angleOf() + Math.toRadians(90);
            } else if (MouseMovement.getX() < p.x && MouseMovement.getY() < getY()) {
                theta = r.angleOf() - Math.toRadians(90);
            } else if (MouseMovement.getX() < p.x && MouseMovement.getY() >= p.y) {
                theta = -r.angleOf() - Math.toRadians(90);
            } else {
                theta = r.angleOf() + Math.toRadians(90);
            }
        }
    }

    public LinkedList<Bullet> returnBullets() {
        return bullets;
    }

    public void shoot() {
        if (bullets.size() >= 5) {
            bullets.remove(0);
        }
        bullets.add(new Bullet(asteroids, new Vector2D(p.x + 18 * Math.sin(theta) - (Bullet.returnRad() / 2), p.y - 18 * Math.cos(theta)), theta, new Vector2D(10, 10)));
    }

    public void tick() {
        //System.out.println(lastBulletTime);
        if (MouseMovement.isMouseInside()) {
            mouseMove();
            //System.out.println(Math.toDegrees(theta));
        }

        if (KeyboardMovement.isDown(KeyEvent.VK_W) || KeyboardMovement.isDown(KeyEvent.VK_UP)) {
            if (KeyboardMovement.isDown(KeyEvent.VK_A) || KeyboardMovement.isDown(KeyEvent.VK_LEFT)) {
                playerMovement(KeyEvent.VK_A, false);
            } else if (KeyboardMovement.isDown(KeyEvent.VK_D) || KeyboardMovement.isDown(KeyEvent.VK_RIGHT)) {
                playerMovement(KeyEvent.VK_D, false);
            }
            playerMovement(KeyEvent.VK_W, false);
        } else if (KeyboardMovement.isDown(KeyEvent.VK_A) || KeyboardMovement.isDown(KeyEvent.VK_LEFT)) {
            playerMovement(KeyEvent.VK_A, false);
        } else if (KeyboardMovement.isDown(KeyEvent.VK_D) || KeyboardMovement.isDown(KeyEvent.VK_RIGHT)) {
            playerMovement(KeyEvent.VK_D, false);
        }

        if (!KeyboardMovement.isDown(KeyEvent.VK_W) && !KeyboardMovement.isDown(KeyEvent.VK_UP)) {
            playerMovement(KeyEvent.VK_W, true);
        }

        if (KeyboardMovement.wasPressed(KeyEvent.VK_SPACE)) {
            shoot();
        }

        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).tick();
        }
    }

    public Rectangle getBounds() {
        return new Rectangle((int) p.x - image.getWidth() / 2, (int) p.y - image.getHeight() / 2, image.getWidth(), image.getHeight());
    }
}
