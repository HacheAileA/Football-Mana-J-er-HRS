package com.kahrs.view.panels;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;

import com.kahrs.app.UserConfig;
import com.kahrs.controller.SoundController;
import com.kahrs.view.GameView;
import com.kahrs.view.visuals.HRSColors;
import com.kahrs.view.visuals.HRSButtons;
import com.kahrs.view.visuals.HRSLabels;
import com.kahrs.view.visuals.HRSLanguages;

/**
 * Classe SettingsPanel gérant l'affichage du choix des options du jeu.
 *
 * @author Ruben FOALEM
 *
 * 
 * @since 0.1
 * 
 * @version 0.1
 */
public class SettingsPanel extends JPanel {

    // ==================== ATTRIBUTS ====================

    /** La GameView principale */private GameView view;

    /** Contraintes de positionnement */private GridBagConstraints gbc = new GridBagConstraints();

    /** JPanel central */private JPanel center = new JPanel(new GridBagLayout());

    /** JButtons */
    /** Bouton pour fermer*/private JButton closeButton;
    /** Bouton pour afficher les crédits*/private JButton creditsButton;
    /** Bouton pour quitter */private JButton saveButton;

    /** JLabels */
    /** Label des bruitages */private JLabel bruitageLabel;
    /** Label de la langue */private JLabel langueLabel;
    /** Label du volume */private JLabel volumeLabel;

    // ================== CONSTRUCTEUR ==================

    /**
     * Constructeur de SettingsPanel avec la GameView principale.
     * 
     * @param view La GameView principale
     * 
     * 
     * @since 0.1
     */
    public SettingsPanel(GameView view) {
        this.view = view;

        this.createSettingsPanel();
    }

    // ==================== ACCESSEUR ===================

    /**
     * Méthode pour créer la page des settings.
     *
     *
     * @since 0.1
     */
    private void createSettingsPanel() {
        this.setBackground(HRSColors.FM_DARK_GREEN);
        this.setLayout(new BorderLayout());

        this.centerPanel();

        this.volumePanel();
        this.bruitagePanel();
        this.languePanel();

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.weighty = 1;
        JPanel filler = new JPanel();
        filler.setBackground(HRSColors.FM_DARK_GREEN);
        center.add(filler, gbc);

        this.add(center, BorderLayout.CENTER);

        this.initialButton();
    }

    /**
     * Méthode pour organiser les boutons credits et quitter de la page.
     * 
     *
     * @since 0.1
     */
    private void initialButton() {

        JPanel south = new JPanel(new GridLayout(1, 2, 10, 10));
        south.setBackground(HRSColors.FM_DARK_GREEN);
        south.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        this.creditsButton = new HRSButtons(HRSLanguages.Settings.getCreditsButtonText());
        this.creditsButton.addActionListener(e -> this.eventCreditsButton());

        this.closeButton = new HRSButtons(HRSLanguages.getCloseButtonText());
        this.closeButton.addActionListener(e -> this.eventCloseButton());

        this.saveButton = new HRSButtons(HRSLanguages.Settings.getSaveButtonText());
        this.saveButton.addActionListener(e -> this.eventSaveButton());

        south.add(this.creditsButton);
        south.add(this.saveButton);
        south.add(this.closeButton);

        this.add(south, BorderLayout.SOUTH);
    }

    /**
     * Méthode pour organiser la ligne paramètre langue de la page.
     * 
     * 
     * @since 0.1
     */
    private void languePanel() {
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        this.langueLabel = HRSLabels.simpleLabel(HRSLanguages.Settings.getLangueLabelText(), null);
        center.add(this.langueLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        JComboBox<String> langueBox = new JComboBox<>(new String[] { HRSLanguages.ENGLISH[1], HRSLanguages.ESPANOL[1], HRSLanguages.FRENCH[1], HRSLanguages.ITALIAN[1] });
        langueBox.setBackground(HRSColors.FM_DARK_GREEN);
        langueBox.setForeground(HRSColors.WHITE);

        String lang = UserConfig.getLanguage();
        if (lang.equals(HRSLanguages.ENGLISH[0])) {
            langueBox.setSelectedItem(HRSLanguages.ENGLISH[1]);
        } else if (lang.equals(HRSLanguages.ESPANOL[0])) {
            langueBox.setSelectedItem(HRSLanguages.ESPANOL[1]);
        } else if (lang.equals(HRSLanguages.FRENCH[0])) {
            langueBox.setSelectedItem(HRSLanguages.FRENCH[1]);
        } else if (lang.equals(HRSLanguages.ITALIAN[0])) {
            langueBox.setSelectedItem(HRSLanguages.ITALIAN[1]);
        } 
        langueBox.addActionListener(e -> this.eventChoiceLanguage(langueBox));

        center.add(langueBox, gbc);
    }

    /**
     * Méthode pour organiser la ligne paramètre bruitage de la page.
     * 
     * 
     * @since 0.1
     */
    private void bruitagePanel() {
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        this.bruitageLabel = HRSLabels.simpleLabel(HRSLanguages.Settings.getBruitageLabelText(), null);
        center.add(this.bruitageLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        JSlider bruitageSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 100);
        bruitageSlider.setBackground(HRSColors.FM_DARK_GREEN);
        bruitageSlider.addChangeListener(e -> this.eventSliderValue(bruitageSlider, 1));
        bruitageSlider.setValue(UserConfig.getSoundEffects());

        JPanel bruitageSliderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        bruitageSliderPanel.setBackground(HRSColors.FM_DARK_GREEN);
        bruitageSliderPanel.add(bruitageSlider);

        center.add(bruitageSliderPanel, gbc);
    }

    /**
     * Méthode pour organiser la ligne paramètre volume de la page.
     * 
     *
     * @since 0.1
     */
    private void volumePanel() {
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        this.volumeLabel = HRSLabels.simpleLabel(HRSLanguages.Settings.getVolumeLabelText(), null);
        center.add(this.volumeLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        JSlider volumeSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 100);
        volumeSlider.setBackground(HRSColors.FM_DARK_GREEN);
        volumeSlider.addChangeListener(e -> this.eventSliderValue(volumeSlider, 0));
        volumeSlider.setValue(UserConfig.getVolume());

        JPanel volumeSliderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        volumeSliderPanel.setBackground(HRSColors.FM_DARK_GREEN);
        volumeSliderPanel.add(volumeSlider);


        center.add(volumeSliderPanel, gbc);
    }

    /**
     * Méthode pour organiser le centre de la page.
     * 
     *
     *
     * @since 0.1
     */
    private void centerPanel() {
        center.setBackground(HRSColors.FM_DARK_GREEN);
        center.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 1;
        gbc.weighty = 0;

    }

    /**
     * Méthode pour mettre à jour la langue choisie.
     * 
     * @param box JComboBox des langues
     * 
     * 
     * @since 0.1
     */
    private void eventChoiceLanguage(JComboBox<String> box) {
        String lang = (String) box.getSelectedItem();
        if (lang.equals(HRSLanguages.ENGLISH[1])) {
            HRSLanguages.updateLanguage(HRSLanguages.ENGLISH[0]);
        } else if (lang.equals(HRSLanguages.ESPANOL[1])) {
            HRSLanguages.updateLanguage(HRSLanguages.ESPANOL[0]);
        } else if (lang.equals(HRSLanguages.FRENCH[1])) {
            HRSLanguages.updateLanguage(HRSLanguages.FRENCH[0]);
        } else if (lang.equals(HRSLanguages.ITALIAN[1])) {
            HRSLanguages.updateLanguage(HRSLanguages.ITALIAN[0]);
        }
        this.view.updateTexts();
    }

    /**
     * Méthode pour mettre à jour le texte des boutons et labels.
     * 
     * 
     * @since 0.1
     */
    public void updateTexts() {
        this.volumeLabel.setText(HRSLanguages.Settings.getVolumeLabelText());
        this.bruitageLabel.setText(HRSLanguages.Settings.getBruitageLabelText());
        this.langueLabel.setText(HRSLanguages.Settings.getLangueLabelText());
        this.creditsButton.setText(HRSLanguages.Settings.getCreditsButtonText());
        this.closeButton.setText(HRSLanguages.getCloseButtonText());
        this.saveButton.setText(HRSLanguages.Settings.getSaveButtonText());
    }

    // ==================== EVENEMENTS =====================

    /**
     * Gère l'événement du bouton "Fermer".
     * 
     * 
     * @since 0.1
     */
    private void eventCloseButton() {
        this.view.eventCloseButton("Settings");
    }

    /**
     * Gère l'évênement du bouton "Crédits".
     * 
     * 
     * @since 0.1
     */
    private void eventCreditsButton() {
        JOptionPane.showMessageDialog(this, HRSLanguages.Settings.getCreditsButtonEventText());
    }

    /**
     * Gère l'évênement du slider
     * 
     * @param slider Le JSlider à modifier
     * @param value  Le numéro du JSlider
     * 
     * @since 0.1
     */
    private void eventSliderValue(JSlider slider, int value) {
        switch (value) {
            case 0:
                UserConfig.setVolume(slider.getValue());
                SoundController.setMusicVolume(slider.getValue());
                break;

            case 1:
                UserConfig.setSoundEffects(slider.getValue());
                SoundController.setEffectVolume(slider.getValue());
                break;
        }
        
    }

    /**
     * Gère l'événement d'appui sur le bouton de sauvegarde pour enregistrer l'état du jeu.
     * @since 0.1
     */
    private void eventSaveButton() {
        if (this.view.controller.saveGame()) {
            JOptionPane.showMessageDialog(this, HRSLanguages.Settings.getSaveButtonEventText());
            if (JOptionPane.showConfirmDialog(this, HRSLanguages.Settings.getQuitButtonEventText(), this.view.JOpTitle, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        } else {
            JOptionPane.showMessageDialog(this.view, HRSLanguages.Settings.getSaveErrorEventText());
        }
    }
}
