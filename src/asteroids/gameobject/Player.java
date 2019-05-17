/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroids.gameobject;

import asteroids.Asteroids;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;

public class Player extends GameObject implements MouseListener {

    private LinkedList<Ellipse2D> bullets = new LinkedList<>();
    private double dx;
    private double dy;
    private BufferedImage image = null;
    private boolean mouseClicked = false;
    private boolean mouseInside = false;
    private int mouseX;
    private int mouseY;
    private double theta = 0;
    private double time = 0.005;

    public Player(Asteroids asteroids, int x, int y, double xSpeed, double ySpeed) {
        super(asteroids, x, y, xSpeed, ySpeed);

        try {
            image = ImageIO.read(new File("Ship.png"));
        } catch (IOException ex) {
        }

        asteroids.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, false), "Move up");
        asteroids.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, false), "Move down");
        asteroids.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, false), "Rotate left");
        asteroids.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, false), "Rotate right");
        asteroids.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, true), "W released");
        asteroids.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, true), "S released");
        asteroids.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, true), "A released");
        asteroids.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, true), "D released");
        asteroids.getActionMap().put("Move up", movement(KeyEvent.VK_W, false));
        asteroids.getActionMap().put("Move down", movement(KeyEvent.VK_S, false));
        asteroids.getActionMap().put("Rotate left", movement(KeyEvent.VK_A, false));
        asteroids.getActionMap().put("Rotate right", movement(KeyEvent.VK_D, false));
        asteroids.getActionMap().put("W released", movement(KeyEvent.VK_W, true));
        //asteroids.getActionMap().put("S released", movement(KeyEvent.VK_S, true));
        //asteroids.getActionMap().put("A released", movement(KeyEvent.VK_A, true));
        //asteroids.getActionMap().put("D released", movement(KeyEvent.VK_D, true));

    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseClicked = true;
        bullets.add(new Ellipse2D.Double(10 * Math.cos(Math.toRadians(360) - theta) + x + 10, 10 * Math.sin(Math.toRadians(360) - theta) + y - 10, 5, 5));
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
            mouseX = mouse.x;
            mouseY = mouse.y;
        } catch (NullPointerException e) {

        }

        boolean deadzone = !(mouseX >= x - 25 && mouseX <= x + image.getHeight() + 25 && mouseY >= y - 25 && mouseY <= y + image.getHeight() + 25);

        if ((mouseX - x - (image.getHeight() / 2)) != 0 && mouseInside == true && deadzone) {
            if (mouseX > x && mouseY < y) {
                theta = -Math.atan((Double.valueOf(y - mouseY + (image.getHeight() / 2))) / (Math.abs(Double.valueOf(mouseX - x - (image.getHeight() / 2))))) + Math.toRadians(90);
            } else if (mouseX < x && mouseY < getY()) {
                theta = Math.atan((Double.valueOf(y - mouseY + (image.getHeight() / 2))) / (Math.abs(Double.valueOf(x - (image.getHeight() / 2) - mouseX)))) - Math.toRadians(90);
            } else if (mouseX < x && mouseY > y) {
                theta = Math.atan(((Double.valueOf(mouseY - y + (image.getHeight() / 2)))) / ((Double.valueOf(mouseX - x - (image.getHeight() / 2))))) - Math.toRadians(90);
            } else {
                theta = Math.atan((Double.valueOf(mouseY - y + (image.getHeight() / 2))) / (Math.abs(Double.valueOf(mouseX - x - (image.getHeight() / 2))))) + Math.toRadians(90);
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
        graphics2D.setColor(Color.white);
        if (mouseClicked == true) {
            for (int i = 0; i < bullets.size(); i++) {
                graphics2D.fill(bullets.get(i));
            }
        }
        System.out.println(Math.toDegrees(theta));
        //graphics2D.drawRect(x, y, 20, 20);
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
                            mouseClicked = true;
                            bullets.add(new Ellipse2D.Double(20 * Math.cos(Math.toRadians(360) - theta) + x, 20 * Math.sin(Math.toRadians(360) - theta) + y, 5, 5));
                            break;
                        case KeyEvent.VK_UP:
                        case KeyEvent.VK_W:
                            if (x >= asteroids.getWidth() + image.getWidth()) {
                                x -= asteroids.getWidth();
                            } else if (x <= -image.getWidth()) {
                                x += asteroids.getWidth();
                            } else {
                                x += dx * Math.sin(theta);
                            }

                            if (y >= asteroids.getHeight() + image.getHeight()) {
                                y -= asteroids.getHeight();
                            } else if (y <= -image.getWidth()) {
                                y += asteroids.getHeight();
                            } else {
                                y -= dy * Math.cos(theta);
                            }

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
                    time = 3.5;
                    while (xSpeed - time > 0 && ySpeed - time > 0) {
                        try {
                            Thread.sleep(75);
                        } catch (InterruptedException ex) {
                        }
                        xSpeed -= time;
                        dx += xSpeed * time + 0.5 * time * time;
                        ySpeed -= time;
                        dy += ySpeed * time + 0.5 * time * time;
                        //System.out.println(xSpeed + " " + ySpeed);
                        x += dx * Math.sin(theta);
                        y -= dy * Math.cos(theta);
                    }
                    time = 0.005;
                    dx = 0;
                    dy = 0;
                    xSpeed = 5;
                    ySpeed = 5;
                }
            }
        };
    }
}
