/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroids.gameobject;

import asteroids.Asteroids;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;

public class Player extends GameObject implements MouseListener {

    private double dx;
    private double dy;
    private BufferedImage image = null;
    private boolean mouseInside = false;
    private int mouseX;
    private int mouseY;
    private double theta = 0;
    private double time = 0.2;

    public Player(Asteroids asteroids, int x, int y, double xSpeed, double ySpeed) {
        super(asteroids, x, y, xSpeed, ySpeed);

        asteroids.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, false), "Move up");
        asteroids.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, false), "Move down");
        asteroids.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, false), "Move left");
        asteroids.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, false), "Move right");
        asteroids.getActionMap().put("Move up", Movement(KeyEvent.VK_W));
        asteroids.getActionMap().put("Move down", Movement(KeyEvent.VK_S));
        asteroids.getActionMap().put("Move left", Movement(KeyEvent.VK_A));
        asteroids.getActionMap().put("Move right", Movement(KeyEvent.VK_D));

    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        mouseInside = true;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        mouseInside = false;
    }

    public void draw(Graphics2D graphics2D) {
        Point mouse = asteroids.getMousePosition();

        try {
            image = ImageIO.read(new File("Ship.png"));
        } catch (IOException ex) {
        }


        try {
            mouseX = mouse.x;
            mouseY = mouse.y;
        } catch (NullPointerException e) {

        }

        if ((mouseX - getX() - (image.getHeight() / 2)) != 0 && mouseInside == true) {
            if (mouseX > getX() && mouseY < getY()) {
                theta = -Math.atan((Double.valueOf(getY() - mouseY + (image.getHeight() / 2))) / (Double.valueOf(mouseX - getX() - (image.getHeight() / 2)))) + Math.toRadians(90);
            } else if (mouseX < getX() && mouseY < getY()) {
                theta = Math.atan((Double.valueOf(getY() - mouseY + (image.getHeight() / 2))) / (Double.valueOf(getX() - (image.getHeight() / 2) - mouseX))) - Math.toRadians(90);
            } else if (mouseX < getX() && mouseY > getY()) {
                theta = Math.atan(((Double.valueOf(mouseY - getY() + (image.getHeight() / 2)))) / ((Double.valueOf(mouseX - getX() - (image.getHeight() / 2))))) - Math.toRadians(90);
            } else {
                theta = Math.atan((Double.valueOf(mouseY - getY() + (image.getHeight() / 2))) / (Double.valueOf(mouseX - getX() - (image.getHeight() / 2)))) + Math.toRadians(90);
            }
        }

        dy = 3 * Math.cos(theta);
        dx = 3 * Math.sin(theta);

        AffineTransform at = AffineTransform.getTranslateInstance(x, y);
        at.rotate(theta, image.getWidth() / 2, image.getHeight() / 2);
        graphics2D.drawImage(image, at, asteroids);
    }

    public Action Movement(int key) {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (key) {
                    case KeyEvent.VK_DOWN:
                    case KeyEvent.VK_S:
                        /*
                        if (ySpeed + time <= 20) {
                            ySpeed += time;
                        }
                        y += ySpeed * time + 0.5 * time * time;
                        System.out.println(y + " " + ySpeed);
                         */
                        x -= dx;
                        y += dy;
                        break;
                    case KeyEvent.VK_UP:
                    case KeyEvent.VK_W:
                        //ySpeed -= time;
                        //y += ySpeed * time + 0.5 * time * time;
                        x += dx;
                        y -= dy;
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
            }
        };

    }
}
