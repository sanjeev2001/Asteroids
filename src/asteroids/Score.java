package asteroids;

import asteroids.Asteroids;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;

public class Score {

    protected final Asteroids asteroids;
    private int value = 0;
    private Font f;

    public Score(Asteroids asteroids) {
        this.asteroids = asteroids;
        try {
            f = Font.createFont(Font.PLAIN, new File(System.getProperty("user.dir") + "\\Fonts\\Score.ttf")).deriveFont(25f);
        } catch (FontFormatException ex) {
            System.out.println("Font not valid");
        } catch (IOException ex) {
            System.out.println("Font not found");
        }
        asteroids.setFont(f);
    }

    public void add(int x) {
        value += x;
    }

    public void draw(Graphics2D graphics2D) {
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString(String.valueOf(value), (int) (1100 - graphics2D.getFontMetrics(f).getStringBounds(String.valueOf(value), graphics2D).getWidth()), (int) (5 + graphics2D.getFontMetrics(f).getHeight()));
    }

}
