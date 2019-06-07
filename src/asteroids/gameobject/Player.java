package asteroids.gameobject;

import asteroids.Asteroids;
import asteroids.MouseMovement;
import asteroids.KeyboardMovement;
import asteroids.Sound;
import asteroids.Vector2D;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import javax.imageio.ImageIO;
import javax.swing.Timer;

public class Player extends GameObject {

    private boolean alive = true;
    private Timer animationTimer;
    private LinkedList<Bullet> bullets = new LinkedList<>();
    private LinkedList<Bullet> bombs = new LinkedList<>();
    private BufferedImage bombImage = null;
    private String imageName = "";
    private int frameNumber = 1;
    private BufferedImage image = null;
    private boolean invincible = false;
    private int invincibleTime = 0;
    private int lives = 3;
    private BufferedImage lifeImage = null;
    private int numberOfBombs = 2;
    private Timer respawnTimer;
    private double theta = 0;
    private double time = 0.1;
    private Vector2D mouseVector = new Vector2D(MouseMovement.getX(), MouseMovement.getY());

    public Player(Asteroids asteroids, Vector2D p, Vector2D v) {
        super(asteroids, p, v);
        //loads images for the player
        imageName = System.getProperty("user.dir") + "\\Graphics\\Ship\\Ship_" + frameNumber + ".png";
        try {
            image = ImageIO.read(new File(imageName));
            lifeImage = ImageIO.read(new File(System.getProperty("user.dir") + "\\Graphics\\Ship\\Ship_1.png"));
            bombImage = ImageIO.read(new File(System.getProperty("user.dir") + "\\Graphics\\Misc\\Bomb_Icon.png"));
        } catch (IOException ex) {
        }
    }

    public void applyPowerUp(String type) {
        //adds the power up to the player
        if (type.equals("Health")) {
            lives += 1;
        } else {
            numberOfBombs += 1;
        }
    }

    public void draw(Graphics2D graphics2D) {
        AffineTransform at = new AffineTransform();
        at.setToTranslation(p.x - image.getWidth() / 2, p.y - image.getHeight() / 2);
        at.rotate(theta, (image.getWidth() / 2) + 1, (image.getHeight() / 2) + 1);
        graphics2D.setColor(Color.white);
        //finds the heading of the player and spawns bullets and bombs acordingly
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).draw(graphics2D);
        }

        for (int i = 0; i < bombs.size(); i++) {
            bombs.get(i).draw(graphics2D);
        }

        if (lives > 0 && lives <= 3) {
            for (int i = 0; i < lives; i++) {
                graphics2D.drawImage(lifeImage, i * 26 + 5, 5, asteroids);
            }
        }

        if (numberOfBombs > 0 && numberOfBombs <= 3) {
            for (int i = 0; i < numberOfBombs; i++) {
                graphics2D.drawImage(bombImage, i * 26 + 5, 31, asteroids);
            }
        }
        graphics2D.drawImage(image, at, asteroids);
    }

    public void dyingAnimation() {
        ActionListener ac = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                imageName = System.getProperty("user.dir") + "\\Graphics\\Ship\\Dying_" + frameNumber + ".png";
                try {
                    image = ImageIO.read(new File(imageName));
                } catch (IOException ex) {
                }
                frameNumber++;
                //respawns player in the center
                if (frameNumber > 8) {
                    p.x = 550;
                    p.y = 350;
                    animationTimer.stop();
                }
            }
        };
        animationTimer = new Timer(60, ac);
        animationTimer.start();
    }

    public LinkedList<Bullet> getBombs() {
        return bombs;
    }

    public Rectangle getBounds() {
        return new Rectangle((int) p.x - image.getWidth() / 2, (int) p.y - image.getHeight() / 2, image.getWidth(), image.getHeight());
    }

    public LinkedList<Bullet> getBullets() {
        return bullets;
    }

    public int getLives() {
        return lives;
    }

    public int getPowerUpNumber(String type) {
        return type.equals("Health") ? lives : numberOfBombs;
    }

    public boolean isAlive() {
        return alive;
    }

    public boolean isInvincible() {
        return invincible;
    }
    //moves the player accoring to the mouse and key inputs
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
                    alive = true;
                    if (frameNumber != 8) {
                        frameNumber++;
                    }
                    if (v.add(new Vector2D(time, time)).x <= 5) {
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
        //checks mouse movement
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

    public void reset() {
        p.x = 550;
        p.y = 350;
        v.x = 0;
        v.y = 0;
        lives = 1;
        bullets = new LinkedList<>();
        alive = true;
        invincible = false;
    }

    public void respawn() {
        //makes sure the player can't lose all lives at once
        Sound ded = new Sound("pls");
        bullets = new LinkedList<>();
        alive = false;
        invincible = true;
        lives--;
        dyingAnimation();
        ActionListener ac = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alive = true;
                invincibleTime++;
                v.x = 0;
                v.y = 0;
                if (invincibleTime > 3) {
                    invincible = false;
                    invincibleTime = 0;
                    respawnTimer.stop();
                }
            }
        };
        respawnTimer = new Timer(1000, ac);
        respawnTimer.start();
    }

    public void shoot(String type) {
        if (type.equals("Bullet")) {
            if (bullets.size() >= 5) {
                bullets.remove(0);
            }
            bullets.add(new Bullet(asteroids, new Vector2D(p.x + 18 * Math.sin(theta) - (Bullet.returnRad() / 2), p.y - 18 * Math.cos(theta)), theta, new Vector2D(10, 10), type));
            Sound shot = new Sound("shoot");
        } else {
            bombs.add(new Bullet(asteroids, new Vector2D(p.x + 18 * Math.sin(theta) - (Bullet.returnRad() / 2), p.y - 18 * Math.cos(theta)), theta, new Vector2D(5, 5), type));
            bombs.get(bombs.size() - 1).bombTimer("Flashing");
        }
    }

    public void tick() {
        //checks for any change in player
        if (alive) {
            imageName = System.getProperty("user.dir") + "\\Graphics\\Ship\\Ship_" + frameNumber + ".png";
            try {
                image = ImageIO.read(new File(imageName));
            } catch (IOException ex) {
            }
            if (MouseMovement.isMouseInside()) {
                mouseMove();
            }

            if (!invincible) {
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
            }

            if (!KeyboardMovement.isDown(KeyEvent.VK_W) && !KeyboardMovement.isDown(KeyEvent.VK_UP)) {
                if (frameNumber != 1 && alive) {
                    frameNumber--;
                } else if (frameNumber < 1 && alive) {
                    frameNumber = 1;
                }

                playerMovement(KeyEvent.VK_W, true);
            }

            if (KeyboardMovement.wasPressed(KeyEvent.VK_SPACE)) {
                shoot("Bullet");
            }

            if (KeyboardMovement.wasPressed(KeyEvent.VK_B)) {
                if (numberOfBombs >= 1) {
                    numberOfBombs--;
                    shoot("Bomb");
                }
            }

            for (int i = 0; i < bullets.size(); i++) {
                bullets.get(i).tick();
            }

            for (int i = 0; i < bombs.size(); i++) {
                bombs.get(i).tick();
            }
        }
    }

}
