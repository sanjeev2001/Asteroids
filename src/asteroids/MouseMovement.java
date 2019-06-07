/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroids;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseMovement extends MouseAdapter {

    protected final Asteroids asteroids;
    private static boolean[] buttons = new boolean[5];
    private static boolean[] lastButtons = new boolean[5];
    private static boolean mouseInside;
    private static int mouseX;
    private static int mouseY;

    public MouseMovement(Asteroids asteroids) {
        this.asteroids = asteroids;
    }
    //If the mouse is moved, set the x and y variables for movement purposes to the x and y location of the where the cursor is
    @Override
    public void mouseMoved(MouseEvent e) {
        mouseInside = true;
        mouseX = e.getX();
        mouseY = e.getY();
    }
    
    //If the mouse is pressed, fire a bullet
    @Override
    public void mousePressed(MouseEvent e) {
        buttons[e.getButton()] = true;
        //bullets.add(new Ellipse2D.Double(10 * Math.cos(Math.toRadians(360) - theta) + x + 10, 10 * Math.sin(Math.toRadians(360) - theta) + y + 10, 5, 5));
    }

    //When the mouse button is released, stop firing
    @Override
    public void mouseReleased(MouseEvent e) {
        buttons[e.getButton()] = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        mouseInside = true;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        mouseInside = false;
    }

    public static boolean isClicked(int button) {
        return buttons[button];
    }

    public static boolean isMouseInside() {
        return mouseInside;
    }

    public static int getX() {
        return mouseX;
    }

    public static int getY() {
        return mouseY;
    }

    public static void update() {
        for (int i = 0; i < 5; i++) {
            lastButtons[i] = buttons[i];
        }
    }

    public static boolean wasReleased(int key) {
        return !isClicked(key) && lastButtons[key];
    }
}
