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
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import javax.imageio.ImageIO;

public class Player extends GameObject {

    public LinkedList<Bullet> bullets = new LinkedList<>();
    private double dx;
    private double dy;
    private BufferedImage image = null;
    private double theta = 0;
    private double time = 0.005;
    Vector2D mouseVector = new Vector2D(MouseMovement.getX(), MouseMovement.getY());

    public Player(Asteroids asteroids, Vector2D p, double xSpeed, double ySpeed) {
        super(asteroids, p, xSpeed, ySpeed);
        try {
            image = ImageIO.read(new File("Ship.png"));
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
        if (released == false) {
            switch (key) {
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_S:
                    break;
                case KeyEvent.VK_UP:
                case KeyEvent.VK_W:
                    p = p.add(new Vector2D(3 * Math.sin(theta), -3 * Math.cos(theta)));
//                    if ((xSpeed + time) <= 5) {
//                        xSpeed += time;
//                        dx += xSpeed * time + 0.5 * time * time;
//                    }
//                    if ((ySpeed + time) <= 5) {
//                        ySpeed += time;
//                        dy += ySpeed * time + 0.5 * time * time;
//                    }
//
//                    if (x >= asteroids.getWidth() + image.getWidth()) {
//                        x -= asteroids.getWidth();
//                    } else if (x <= -image.getWidth()) {
//                        x += asteroids.getWidth();
//                    } else {
//                        x += dx * Math.sin(theta);
//                    }
//
//                    if (y >= asteroids.getHeight() + image.getHeight()) {
//                        y -= asteroids.getHeight();
//                    } else if (y <= -image.getWidth()) {
//                        y += asteroids.getHeight();
//                    } else {
//                        y -= dy * Math.cos(theta);
//                    }

                    break;
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_D:
                    theta += Math.toRadians(5);
                    break;
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_A:
                    theta -= Math.toRadians(5);
                    break;
            }
        } else {
            dx = 0;
            dy = 0;
            xSpeed = 3;
            ySpeed = 3;
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

    public void shoot() {
        bullets.add(new Bullet(asteroids, new Vector2D(p.x + 18 * Math.sin(theta) - 2.5, p.y - 18 * Math.cos(theta)), theta, 0, 0));
    }

    public void tick() {
        if (MouseMovement.isMouseInside()) {
            mouseMove();
            //System.out.println(Math.toDegrees(theta));
        }

        if (KeyboardMovement.isDown(KeyEvent.VK_W)) {
            playerMovement(KeyEvent.VK_W, false);
        } else if (KeyboardMovement.isDown(KeyEvent.VK_S)) {
            playerMovement(KeyEvent.VK_S, false);
        } else if (KeyboardMovement.isDown(KeyEvent.VK_A)) {
            playerMovement(KeyEvent.VK_A, false);
        } else if (KeyboardMovement.isDown(KeyEvent.VK_D)) {
            playerMovement(KeyEvent.VK_D, false);
        }

        if (KeyboardMovement.wasPressed(KeyEvent.VK_SPACE)) {
            shoot();
        }

        if (KeyboardMovement.wasReleased(KeyEvent.VK_W)) {
            //playerMovement(KeyEvent.VK_W, true);
        }

        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).tick();
        }
    }
}
