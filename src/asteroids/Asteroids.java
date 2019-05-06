/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroids;

import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.*;

public class Asteroids {

    public Asteroids() {
        intro.setPreferredSize(new Dimension(1100, 700));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setContentPane(intro);
        frame.pack();
    }

    private final JFrame frame = new JFrame("Asteroids");
    private final JPanel intro = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));

    public static void main(String[] args) {
        Asteroids gui = new Asteroids();
    }

}
