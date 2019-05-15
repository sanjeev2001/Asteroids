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
    private double time = 0.005;

    public Player(Asteroids asteroids, int x, int y, double xSpeed, double ySpeed) {
        super(asteroids, x, y, xSpeed, ySpeed);

        asteroids.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, false), "Move up");
        asteroids.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, false), "Move down");
        asteroids.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, false), "Move left");
        asteroids.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, false), "Move right");
        asteroids.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, true), "W released");
        asteroids.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, true), "S released");
        asteroids.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, true), "A released");
        asteroids.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, true), "D released");
        asteroids.getActionMap().put("Move up", movement(KeyEvent.VK_W, false));
        asteroids.getActionMap().put("Move down", movement(KeyEvent.VK_S, false));
        asteroids.getActionMap().put("Move left", movement(KeyEvent.VK_A, false));
        asteroids.getActionMap().put("Move right", movement(KeyEvent.VK_D, false));
        asteroids.getActionMap().put("W released", movement(KeyEvent.VK_W, true));
        asteroids.getActionMap().put("S released", movement(KeyEvent.VK_S, true));
        asteroids.getActionMap().put("A released", movement(KeyEvent.VK_A, true));
        asteroids.getActionMap().put("D released", movement(KeyEvent.VK_D, true));

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
        if ((xSpeed + time) <= 7) {
            xSpeed += time;
            dx += xSpeed * time + 0.5 * time * time;
        }
        if ((ySpeed + time) <= 7) {
            ySpeed += time;
            dy += ySpeed * time + 0.5 * time * time;
        }

        AffineTransform at = AffineTransform.getTranslateInstance(x, y);
        at.rotate(theta, image.getWidth() / 2, image.getHeight() / 2);
        graphics2D.drawImage(image, at, asteroids);
    }

    public Action movement(int key, boolean released) {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (released == false) {
                    switch (key) {
                        case KeyEvent.VK_DOWN:
                        case KeyEvent.VK_S:
                            x -= dx * Math.sin(theta);
                            y += dy * Math.cos(theta);
                            break;
                        case KeyEvent.VK_UP:
                        case KeyEvent.VK_W:
                            System.out.println(xSpeed);
                            x += dx * Math.sin(theta);
                            y -= dy * Math.cos(theta);
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
                    xSpeed = 5;
                    ySpeed = 5;
                }
            }
        };

    }
}
