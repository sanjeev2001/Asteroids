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
        return keys[key];
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }

    public static void update() {
        for (int i = 0; i < 256; i++) {
            lastKeys[i] = keys[i];
        }
    }

    public static boolean wasReleased(int key) {
        return !isDown(key) && lastKeys[key];
    }

}
