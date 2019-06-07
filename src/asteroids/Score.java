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
        //loads font for numbers
        try {
            f = Font.createFont(Font.PLAIN, new File(System.getProperty("user.dir") + "\\Fonts\\Hyperspace.otf")).deriveFont(40f);
        } catch (FontFormatException ex) {
            System.out.println("Font not valid");
        } catch (IOException ex) {
            System.out.println("Font not found");
        }
        asteroids.setFont(f);
    }

    public void add(int x) {
        //counts score
        value += x;
    }

    public void draw(Graphics2D graphics2D) {
        //prints the score
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString(String.valueOf(value), (int) (1100 - graphics2D.getFontMetrics(f).getStringBounds(String.valueOf(value), graphics2D).getWidth()), (int) (- 15 + graphics2D.getFontMetrics(f).getHeight()));
    }

    public int getScore() {
        return value;
    }

}
