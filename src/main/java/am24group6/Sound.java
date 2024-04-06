package am24group6;

import java.net.*;

import javax.sound.sampled.*;

public class Sound {
    Clip clip;
    URL soundUrl[] = new URL[10];

    public Sound() {
        soundUrl[0] = getClass().getResource("/jump.wav");
        soundUrl[1] = getClass().getResource("/death.wav");
        soundUrl[2] = getClass().getResource("/tada.wav");
    }

    public void setFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundUrl[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);

        } catch (Exception e) {
            System.err.println("Sound file couldn't load: " + e.getMessage());
        }
    }

    public void play() {
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
    }
}
