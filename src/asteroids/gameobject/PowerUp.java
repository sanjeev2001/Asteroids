package asteroids.gameobject;

import asteroids.Asteroids;
import asteroids.Vector2D;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class PowerUp extends GameObject {

    BufferedImage image = null;
    String type = "";

    public PowerUp(Asteroids asteroids, Vector2D p, Vector2D v, String type) {
        super(asteroids, p, v);
        this.type = type;
        try {
            image = ImageIO.read(new File(System.getProperty("user.dir") + "\\Graphics\\Power Ups\\" + type + ".png"));
        } catch (IOException ex) {
        }
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        graphics2D.drawImage(image, (int) p.x, (int) p.y, asteroids);
    }

    public String getType() {
        return type;
    }

    @Override
    public void tick() {
    }

    public Rectangle getBounds() {
        return new Rectangle((int) p.x - image.getWidth() / 2, (int) p.y - image.getHeight() / 2, image.getWidth(), image.getHeight());
    }
}
