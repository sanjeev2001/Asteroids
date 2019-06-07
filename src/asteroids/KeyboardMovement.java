/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroids;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyboardMovement extends KeyAdapter {

    private static boolean[] keys = new boolean[256];
    private static boolean[] lastKeys = new boolean[256];

    public static boolean isDown(int key) {
        return keys[key];//checks if the key is being clicked or pressed down
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;//checks if a key is clicked
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;//checks if key is released
    }

    public static void update() {
        for (int i = 0; i < 256; i++) {
            lastKeys[i] = keys[i];//updates what keys werenpressed
        }
    }

    public static boolean wasReleased(int key) {
        return !isDown(key) && lastKeys[key];//allows for action to occur if these conditions are met
    }

    public static boolean wasPressed(int key) {
        return isDown(key) && !lastKeys[key];//allows for action to occur if these conditions are met
    }

}
