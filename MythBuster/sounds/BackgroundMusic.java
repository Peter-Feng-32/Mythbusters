package sounds;

import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import javafx.scene.media.Media;

import java.io.File;

public class BackgroundMusic {
    /**
     * Credit
     * 
     * Escape by Admiral Bob (c) copyright 2021 
     * Licensed under a Creative Commons Attribution Noncommercial (3.0) license. 
     * http://dig.ccmixter.org/files/admiralbob77/63310 
     * Ft: copperhead, 7OOP3D, Martijn de Boer (NiGiD)
     */
    private static final double volumeMarker = .125;

    private static MediaPlayer backgroundTrack;
    private static MediaPlayer bossTrack;
    private static double volume;
    private static boolean bossPlaying;

    public static void initialize() {
        String soundPath = "MythBuster/sounds/BackgroundMusic.mp3";
        String bossMusicPath = "Mythbuster/sounds/BossMusic.wav";

        volume = volumeMarker; // initial volume
        //Instantiating Media class  
        Media media = new Media(new File(soundPath).toURI().toString());
        Media bossMusic = new Media(new File(bossMusicPath).toURI().toString());
        //Instantiating MediaPlayer class   
        backgroundTrack = new MediaPlayer(media);
        backgroundTrack.setCycleCount(MediaPlayer.INDEFINITE);
        backgroundTrack.setVolume(volume);
        bossTrack = new MediaPlayer(bossMusic);
        bossTrack.setCycleCount(MediaPlayer.INDEFINITE);
        bossTrack.setVolume(volume * 2);
    }

    public static MediaPlayer getBackgroundTrack() {
        return backgroundTrack;
    }

    public static MediaPlayer getBossTrack() {
        return bossTrack;
    }

    public static void setVolume(double scalar) {
        volume = scalar * volumeMarker;
        getBackgroundTrack().setVolume(volume);
        getBossTrack().setVolume(volume * 2);
    }

    public static double getVolumeMarker() {
        return volumeMarker;
    }

    public static double getVolume() {
        return volume;
    }


    public static void setBossPlaying(boolean playing) {
        bossPlaying = playing;
    }

    public static boolean isBossPlaying() {
        return bossPlaying;
    }

}
