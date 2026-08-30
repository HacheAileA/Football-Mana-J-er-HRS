package com.kahrs.view.panels;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.kahrs.model.Infrastructure;
import com.kahrs.view.GameView;
import com.kahrs.view.visuals.HRSButtons;
import com.kahrs.view.visuals.HRSColors;
import com.kahrs.view.visuals.HRSLabels;
import com.kahrs.view.visuals.HRSLanguages;

/**
 * Classe InfrastructurePanel gérant l'affichage et les actions des infrastructures.
 *
 *
 * @since 0.1
 *
 * @version 0.2
 */

public class InfrastructurePanel extends JPanel {

    // ==================== ATTRIBUTS ====================

    /** Vue principale */
    private GameView view;

    // Boutons
    /** Bouton pour fermer */private JButton closeButton;
    /** Bouton pour l'infrastructure de vitesse */private JButton raceTrackButton;
    /** Bouton pour l'infrastructure de passe */private JButton fieldButton;
    /** Bouton pour l'infrastructure de tir */private JButton cageButton;
    /** Bouton pour l'infrastructure de santé */private JButton relaxationAreaButton;
    /** Bouton pour l'infrastructure de santé */private JButton healthAreaButton;

    // Labels
    /** Label de titre */private JLabel titleLabel;

    // Panels
    /** Panel du haut */private JPanel header;
    /** Panel central */private JPanel infrastructuresPanel;

    // ================== CONSTRUCTEUR ==================

    /**
     * Constructeur de InfrastructurePanel pour initialiser l'interface avec la vue principale.
     * @param view La vue principale
     * 
     * @since 0.2
     */
    public InfrastructurePanel(GameView view) {
        this.view = view;

        this.setBackground(HRSColors.FM_DARK_GREEN);

        this.createComponents();
    }

    // ==================== CREATIONS =====================

    /**
     * Méthode pour créer et initialiser les composants du panel.
     *
     * @since 0.2
     */
    private void createComponents() {
        this.closeButton = new HRSButtons(HRSLanguages.getCloseButtonText());
        this.closeButton.addActionListener(e -> this.eventCloseButton());

        this.raceTrackButton = new HRSButtons("⚡", 200, 200);
        this.raceTrackButton.addActionListener(e -> this.eventChooseAction(this.view.model.getRaceTrack()));

        this.fieldButton = new HRSButtons("👟", 200, 200);
        this.fieldButton.addActionListener(e -> this.eventChooseAction(this.view.model.getField()));

        this.cageButton = new HRSButtons("🎯", 200, 200);
        this.cageButton.addActionListener(e -> this.eventChooseAction(this.view.model.getCage()));

        this.relaxationAreaButton = new HRSButtons("🛏️", 200, 200);
        this.relaxationAreaButton.addActionListener(e -> this.eventChooseAction(this.view.model.getRelaxationArea()));

        this.healthAreaButton = new HRSButtons("🩹", 200, 200);
        this.healthAreaButton.addActionListener(e -> this.eventChooseAction(this.view.model.getHealthArea()));

        this.titleLabel = new HRSLabels(HRSLanguages.Infrastructure.getTitle());

        this.header = new JPanel(new BorderLayout());
        this.header.setBackground(HRSColors.FM_DARK_GREEN);

        this.infrastructuresPanel = new JPanel();
        this.infrastructuresPanel.setForeground(HRSColors.FM_TEXT_WHITE);
    }

    /**
     * Méthode pour initialiser et agencer l'interface graphique du panel.
     *
     * @since 0.2
     */
    public void initLayout() {
        this.removeAll();
        this.updateTexts();
        this.setLayout(new BorderLayout());
        this.setBorder(new EmptyBorder(30, 50, 30, 50));

        this.header.add(this.titleLabel, BorderLayout.WEST);
        this.infrastructuresPanel = this.createCenterPanel();

        this.add(this.header, BorderLayout.NORTH);
        this.add(this.infrastructuresPanel, BorderLayout.CENTER);
        this.add(this.closeButton, BorderLayout.SOUTH);
    }

    /**
     * Méthode pour créer le panel central contenant les infrastructures.
     *
     * @return Le panel central configuré
     *
     * @since 0.2
     */
    private JPanel createCenterPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);

        JPanel center_up = this.createButtonsPanel(this.raceTrackButton, this.fieldButton, this.cageButton);
        JPanel center_down = this.createButtonsPanel(this.relaxationAreaButton, this.healthAreaButton);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(center_up, gbc);

        gbc.gridy = 1;
        panel.add(center_down, gbc);

        return panel;
    }

    /**
     * Méthode pour créer une ligne de boutons d'infrastructures.
     *
     * @param buttons La liste des boutons à ajouter
     * @return Le panel contenant les boutons
     *
     * @since 0.2
     */
    private JPanel createButtonsPanel(JButton... buttons) {
        JPanel panel = new JPanel(new GridLayout(1, buttons.length, 50, 0));
        panel.setOpaque(false);

        for (JButton button : buttons) {
            JPanel wrapper = this.createWrapper(button);
            panel.add(wrapper);
        }

        return panel;
    }

    /**
     * Méthode pour créer un conteneur autour d'un bouton afin de gérer son espacement.
     *
     * @param button Le bouton à encapsuler
     * @return Le panel encapsulant le bouton
     *
     * @since 0.2
     */
    private JPanel createWrapper(JButton button) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        panel.add(button);

        return panel;
    }

    // ==================== METHODES ===================

    /**
     * Méthode pour rafraîchir les textes de l'interface.
     *
     * @since 0.2
     */
    public void updateTexts() {
        this.titleLabel.setText(HRSLanguages.Infrastructure.getTitle());
        this.closeButton.setText(HRSLanguages.getCloseButtonText());

        Infrastructure raceTrack = this.view.model.getRaceTrack();
        if (raceTrack != null) {
            this.raceTrackButton.setText(HRSLabels.getInfrastructureButtonTextFormated(HRSLanguages.Infrastructure.getSpeedText(), HRSLanguages.Infrastructure.getLvlText(), raceTrack.getLevel(), HRSLanguages.Infrastructure.getExpText(), raceTrack.getExp()));
        }

        Infrastructure field = this.view.model.getField();
        if (field != null) {
            this.fieldButton.setText(HRSLabels.getInfrastructureButtonTextFormated(HRSLanguages.Infrastructure.getPassText(), HRSLanguages.Infrastructure.getLvlText(), field.getLevel(), HRSLanguages.Infrastructure.getExpText(), field.getExp()));
        }

        Infrastructure cage = this.view.model.getCage();
        if (cage != null) {
            this.cageButton.setText(HRSLabels.getInfrastructureButtonTextFormated(HRSLanguages.Infrastructure.getShootText(), HRSLanguages.Infrastructure.getLvlText(), cage.getLevel(), HRSLanguages.Infrastructure.getExpText(), cage.getExp()));
        }

        Infrastructure relaxationArea = this.view.model.getRelaxationArea();
        if (relaxationArea != null) {
            this.relaxationAreaButton.setText(HRSLabels.getInfrastructureButtonTextFormated(HRSLanguages.Infrastructure.getRelaxationText(), HRSLanguages.Infrastructure.getChangeStatusRelaxationText()));
        }

        Infrastructure healthArea = this.view.model.getHealthArea();
        if (healthArea != null) {
            this.healthAreaButton.setText(HRSLabels.getInfrastructureButtonTextFormated(HRSLanguages.Infrastructure.getHealthText(), HRSLanguages.Infrastructure.getChangeStatusHealthText()));
        }
    }

    // ==================== EVENEMENTS =====================

    /**
     * Méthode pour gérer l'action du bouton de fermeture.
     *
     * @since 0.2
     */
    private void eventCloseButton() {
        this.view.eventCloseButton();
    }

    /**
     * Méthode pour gérer le choix d'action sur une infrastructure (améliorer ou utiliser).
     *
     * @param infrastructure L'infrastructure sélectionnée
     *
     * @since 0.2
     */
    private void eventChooseAction(Infrastructure infrastructure) {
        if (infrastructure.isUpgradable()) {
            String[] options = {HRSLanguages.Infrastructure.getUpgradePlayersText(), HRSLanguages.Infrastructure.getUpgradeBuildText()};

            int choice = JOptionPane.showOptionDialog(this, HRSLanguages.Infrastructure.getChoiceMessageText(), this.view.JOpTitle, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);

            switch (choice) {
                case 0:
                    this.eventTraining(infrastructure);
                    break;
                case 1:
                    this.eventUpgradeInfrastructure(infrastructure);
                    break;
            }
        } else {
            String[] options = {HRSLanguages.Infrastructure.getCareMessageText()};

            int choice = JOptionPane.showOptionDialog(this, HRSLanguages.Infrastructure.getChoiceMessageText(), this.view.JOpTitle, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);

            switch (choice) {
                case 0:
                    this.eventCure(infrastructure);
                    break;
            }
        }
    }

    /**
     * Méthode pour gérer l'entraînement des joueurs via une infrastructure.
     *
     * @param infrastructure L'infrastructure utilisée pour l'entraînement
     *
     * @since 0.2
     */
    private void eventTraining(Infrastructure infrastructure) {
        if (JOptionPane.showConfirmDialog(this, HRSLanguages.Infrastructure.getUpgradePlayersConfirmText(infrastructure.getTrainingStat().name(), infrastructure.getTrainingCost()), this.view.JOpTitle, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION && this.view.controller.getInfrastructureController().training(infrastructure)) {
            JOptionPane.showMessageDialog(this, HRSLanguages.Infrastructure.getUpgradePlayersSuccessText());
            this.view.updateTexts();
        }
    }

    /**
     * Méthode pour gérer l'amélioration du niveau d'une infrastructure.
     *
     * @param infrastructure L'infrastructure à améliorer
     *
     * @since 0.2
     */
    private void eventUpgradeInfrastructure(Infrastructure infrastructure) {
        if (JOptionPane.showConfirmDialog(this, HRSLanguages.Infrastructure.getUpgradeBuildConfirmText(infrastructure.getClass().getSimpleName(), infrastructure.getLevel(), infrastructure.getUpgradeCost()), this.view.JOpTitle, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION && this.view.controller.getInfrastructureController().upgradeInfrastructure(infrastructure)) {
            JOptionPane.showMessageDialog(this, HRSLanguages.Infrastructure.getUpgradeBuildSuccessText());
            this.view.updateTexts();
        }
    }

    /**
     * Méthode pour gérer le soin ou le repos des joueurs via une infrastructure.
     *
     * @param infrastructure L'infrastructure utilisée pour le soin
     *
     * @since 0.2
     */
    private void eventCure(Infrastructure infrastructure) {
        if (JOptionPane.showConfirmDialog(this, HRSLanguages.Infrastructure.getCareMessageConfirmText(infrastructure.getTrainingCost()), this.view.JOpTitle, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            if (this.view.controller.getInfrastructureController().cure(infrastructure)) {
                JOptionPane.showMessageDialog(this, HRSLanguages.Infrastructure.getCareMessageSuccessText());
            } else {
                JOptionPane.showMessageDialog(this, HRSLanguages.Infrastructure.getCareMessageFailText());
            }
        }
    }
}
