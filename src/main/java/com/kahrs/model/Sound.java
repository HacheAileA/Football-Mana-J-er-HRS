package com.kahrs.model;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.net.URL;


/**
 * Classe Sound implémentant un son.
 *
 * @author Ruben FOALEM
 *
 * @since 0.1
 *
 * @version 0.2
 */
public class Sound {

    /** Chemin vers le fichier son */
    private final String path;
    /** Lecteur audio **/
    private Clip clip;
    /** Emplacement du fichier son**/
    private URL soundURL;

    /**
     * Constructeur de Sound
     *
     * @param path  Chemin
     *
     * @since 0.2
     */
    public Sound(String path) {
        this.path = path;
        try {
            this.soundURL = getClass().getResource(path);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(this.soundURL);
            this.clip = AudioSystem.getClip();
            this.clip.open(audioInputStream);
        } catch (Exception e) {
            System.err.println( "Error loading sound : " + e.getMessage());
        }
    }

    /**
     * Méthode pour jouer un son
     *
     * @since 0.2
     */
    public void play() {
        if (this.clip == null) {
            return;
        }
        if (this.clip.isRunning()) {
            this.clip.stop();
        }
        this.clip.setFramePosition(0);
        this.clip.start();
    }

    /**
     * Méthode pour arrêter un son
     *
     * @since 0.2
     */
    public void stop() {
        if (this.clip != null) {
            this.clip.stop();
        }
    }

    /**
     * Méthode pour boucler un son
     *
     * @since 0.2
     */
    public void loop() {
        if (this.clip != null) {
            this.clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    /**
     * Méthode pour modifier le volume d'un son
     *
     * @param volume volume du son
     *
     * @since 0.2
     */
    public void setVolume(float volume) {
        if (this.clip != null) {
            FloatControl volumeControl =  (FloatControl) this.clip.getControl(FloatControl.Type.MASTER_GAIN);

            if (volumeControl != null) {
                volumeControl.setValue(volume);
            }
        }
    }

    /**
     * Méthode pour dupliquer un son
     *
     * @return une copie du son
     *
     * @since 0.2
     */
    public Sound duplicate() {
        return new Sound(this.path);
    }


}
