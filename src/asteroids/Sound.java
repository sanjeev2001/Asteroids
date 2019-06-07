/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroids;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.SwingUtilities;

/**
 *
 * @author sujan
 */
public class Sound {

    private Clip clip;

    public Sound(String name) {
        try {
            clip = AudioSystem.getClip();
            // getAudioInputStream() also accepts a File or InputStream
            AudioInputStream ais = AudioSystem.getAudioInputStream((new File(System.getProperty("user.dir") + "\\Audio\\" + name + ".wav")));
            clip.open(ais);
            clip.start();
            SwingUtilities.invokeLater(() -> {

            });
        } catch (Exception e) {

        }
    }

    public void stopSound() {
        clip.stop();
    }
}
