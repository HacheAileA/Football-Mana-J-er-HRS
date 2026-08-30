package com.kahrs.controller;

import com.kahrs.model.Sound;

import java.util.HashMap;

/**
 * Classe SoundController gérant le système de sons.
 *
 * @author Ruben FOALEM
 *
 * @since 0.1
 *
 * @version 0.2
 */
public class SoundController {

    /** Volume des sons par défaut */
    private static float MUSICVOLUME = -10f;
    /** Volume des effets par défaut */
    private static float EFFECTVOLUME = -10f;
    /** Liste des musiques **/
    private static final HashMap<String, Sound> MUSICS = new HashMap<>();
    /** Liste des effets **/
    private static final HashMap<String, Sound> EFFECTS = new HashMap<>();
    /** Le son actuel */
    private static Sound currentMusic;

    /**
     * Bloc d'initialisation des sons
     *
     * @since 0.2
     */
    static {

        MUSICS.put("Menu", loadMusic("menu"));
        MUSICS.put("Menu_game", loadMusic("menu_game"));
        MUSICS.put("Victory",  loadMusic("victory"));
        MUSICS.put("Defeat", loadMusic("defeat"));
        MUSICS.put("Tie", loadMusic("tie"));
        MUSICS.put("Loading", loadMusic("loading"));
        MUSICS.put("Notice", loadMusic("notice"));

        EFFECTS.put("click", loadEffect("click"));
        EFFECTS.put("Goal", loadEffect("goal"));
        EFFECTS.put("Kick", loadEffect("kick_off"));
        EFFECTS.put("Achieve", loadEffect("achieve"));
    }

    /** Constructeur privé pour résoudre warning de Javadoc */
    private SoundController() {
    }

    /**
     * Méthode pour arrêter le son actuel.
     *
     * @since 0.2
     */
    public static void stopMusic() {
        if (currentMusic != null) {
            currentMusic.stop();
        }
    }

    /**
     * Méthode pour jouer une musique.
     *
     * @param music Nom du musique
     *
     * @since 0.2
     */
    public static void playMusic(String music) {
        SoundController.stopMusic();

        currentMusic = MUSICS.get(music);
        if (currentMusic != null) {
            currentMusic.play();
            currentMusic.loop();
            currentMusic.setVolume(MUSICVOLUME);
        }
    }

    /**
     * Méthode pour jouer un son de type effets.
     *
     * @param effect Nom de l'effet
     *
     * @since 0.2
     */
    public static void playEffect(String effect) {
        if (EFFECTS.get(effect) != null) {
            Sound ef = EFFECTS.get(effect).duplicate();
            ef.setVolume(EFFECTVOLUME);
            ef.play();
        }
    }


    /**
     * Méthode pour changer le volume des musiques.
     *
     * @param value nouveau volume des musiques
     *
     * @since 0.2
     */
    public static void setMusicVolume(int value) {
        MUSICVOLUME = convert(value);

        if (currentMusic != null) {
            currentMusic.setVolume(MUSICVOLUME);
        }
    }

    /**
     * Méthode pour changer le volume des effets.
     *
     * @param value nouveau volume des effets
     *
     * @since 0.2
     */
    public static void setEffectVolume(int value) {
        EFFECTVOLUME = convert(value);
    }

    /**
     * Méthode pour convertir un entier en float.
     *
     * @param value entier à convertir
     *
     * @return l'entier en float
     *
     * @since 0.2
     */
    private static float convert(int value) {
        if (value == 0) {
            return -80f;
        }

        return (value - 100) / 2f;
    }


    /**
     * Méthode pour charger une musique.
     *
     * @param name Nom de la musique
     *
     * @return la musique
     *
     * @since 0.2
     */
    private static Sound loadMusic(String name) {
        return SoundController.load(name, "musics");
    }

    /**
     * Méthode pour charger un effet.
     *
     * @param name Nom de l'effet
     *
     * @return l'effet
     *
     * @since 0.2
     */
    private static Sound loadEffect(String name) {
        return SoundController.load(name, "effects");
    }

    /**
     * Méthode pour charger un son.
     *
     * @param name Nom du son
     * @param path chemin vers le fichier
     *
     * @return le son
     *
     * @since 0.2
     */
    private static Sound load(String name, String path) {
        return new Sound("/sounds/" + path + "/" + name + ".wav");
    }
}
