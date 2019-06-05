package asteroids.gameobject;

import asteroids.Asteroids;
import asteroids.Vector2D;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

public class Enemy extends GameObject {

    private double theta;
    private BufferedImage image = null;
    private String size;

    public Enemy(Asteroids asteroids, Vector2D p, Vector2D v, String size) {
        super(asteroids, p, v);
        this.size = size;
        Random r = new Random();
        try {
            image = ImageIO.read(new File(System.getProperty("user.dir") + "\\Graphics\\Asteroids\\" + size + "_Asteroid_" + String.valueOf((int) Math.floor(Math.random() * 4) + 1) + ".png"));
        } catch (IOException ex) {
        }
        theta = r.nextDouble() * Math.PI * 2;
    }

    public void draw(Graphics2D graphics2D) {
        AffineTransform at = new AffineTransform();

        //at.rotate(theta, getBounds().getX() + getBounds().getWidth() / 2, getBounds().getY() + getBounds().getHeight() / 2);
        //graphics2D.draw(at.createTransformedShape(getBounds()));
        at.setToTranslation(p.x - image.getWidth() / 2, p.y - image.getHeight() / 2);
        at.rotate(theta, (image.getWidth() / 2) + 1, (image.getHeight() / 2) + 1);
        graphics2D.drawImage(image, at, asteroids);
    }

    public void explode() {
        switch (size) {
            case "L":
                asteroids.enemySpawner("M", "Explode", p.x - 30, p.y + 30);
                asteroids.enemySpawner("M", "Explode", p.x - 30, p.y + 30);
                break;
            case "M":
                asteroids.enemySpawner("S", "Explode", p.x - 20, p.y + 20);
                asteroids.enemySpawner("S", "Explode", p.x - 20, p.y + 20);
                break;
        }
    }

    public String getSize() {
        return size;
    }

    @Override
    public void tick() {
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

        p.x += v.x * Math.sin(theta);
        p.y -= v.y * Math.cos(theta);
    }

    public Rectangle getBounds() {
        return new Rectangle((int) p.x - image.getWidth() / 2, (int) p.y - image.getHeight() / 2, image.getWidth() + 1, image.getHeight() + 1);
    }
}
