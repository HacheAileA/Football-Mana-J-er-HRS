package com.kahrs.view.panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.kahrs.view.GameView;
import com.kahrs.view.visuals.HRSButtons;
import com.kahrs.view.visuals.HRSColors;
import com.kahrs.view.visuals.HRSFonts;
import com.kahrs.view.visuals.HRSLabels;
import com.kahrs.view.visuals.HRSLanguages;

/**
 * Classe NewUserPanel gérant la création d'un nouvel utilisateur (Manager +
 * Team).
 * 
 * @author Hugo ARNAUD
 * 
 * 
 * @since 0.1
 * 
 * @version 0.2
 */
public class NewUserPanel extends JPanel {

    // ==================== ATTRIBUTS ====================

    // Zones de texte
    /** Zone de nom du Manager */private JTextField managerNameField;
    /** Zome de nom de la Team */private JTextField teamNameField;

    // Boutons
    /** Bouton pour valider la création du Manager/Team */private JButton playButton;
    /** Bouton pour revenir au menu principal */private JButton previousButton;

    /** La GameView principale */private GameView view;

    // ================== CONSTRUCTEUR ==================

    /**
     * Constructeur de NewUserPanel avec la GameView principale.
     * 
     * @param view La GameView principale
     * 
     * 
     * @since 0.1
     */
    public NewUserPanel(GameView view) {
        this.view = view;

        this.createTextFields();
        this.createButtons();
        this.initLayouts();
    }

    // ==================== CREATIONS =====================

    /**
     * Méthode pour créer les boutons.
     * 
     * 
     * @since 0.1
     */
    private void createButtons() {
        this.playButton = new HRSButtons(HRSLanguages.NewUser.getPlayButtonText());
        this.playButton.addActionListener(e -> this.eventPlayButton());

        this.previousButton = new HRSButtons(HRSLanguages.NewUser.getPreviousButtonText());
        this.previousButton.addActionListener(e -> this.eventPreviousButton());

        this.updateButtons();
    }

    /**
     * Méthodes pour créer les zones de texte.
     * 
     * 
     * @since 0.1
     */
    private void createTextFields() {
        this.managerNameField = new JTextField();
        this.setFieldsProperties(this.managerNameField);

        this.teamNameField = new JTextField();
        this.setFieldsProperties(this.teamNameField);
    }

    /**
     * Méthode pour initialiser tous les Layouts.
     * 
     * 
     * @since 0.1
     */
    private void initLayouts() {
        this.setBackground(HRSColors.FM_DARK_GREEN);
        this.setLayout(new BorderLayout());
        this.setBorder(new EmptyBorder(30, 50, 30, 50));

        JPanel textFieldPanel = new JPanel(new GridBagLayout());
        textFieldPanel.setOpaque(false);
        this.addTextFieldToPanel(textFieldPanel);

        this.add(textFieldPanel, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new BorderLayout());
        buttonsPanel.setOpaque(false);
        this.addButtonsToPanel(buttonsPanel);

        this.add(buttonsPanel, BorderLayout.SOUTH);
    }

    // ==================== PROPRIETES ===================

    /**
     * Méthode pour définir les propriétés des zones de texte.
     * 
     * @param textField La JTextField à modifier
     * 
     * 
     * @since 0.1
     */
    private void setFieldsProperties(JTextField textField) {
        DocumentListener listener = new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {updateButtons();}
            public void removeUpdate(DocumentEvent e) {updateButtons();}
            public void changedUpdate(DocumentEvent e) {updateButtons();}
        };

        textField.setFont(HRSFonts.SEGEO_UI);
        textField.setPreferredSize(new Dimension(300, 40));
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.getDocument().addDocumentListener(listener);
    }

    // ==================== METHODES ===================

    /**
     * Méthode pour ajouter tous les boutons à un JPanel.
     * 
     * @param panel Le JPanel  à modifier
     * 
     * 
     * @since 0.1
     */
    private void addButtonsToPanel(JPanel panel) {
        panel.add(this.previousButton, BorderLayout.WEST);
        panel.add(this.playButton, BorderLayout.EAST);
    }

    /**
     * Méthode pour ajouter toutes les zones de texte à un JPanel.
     *
     * @param panel Le JPanel à modifier
     * 
     * 
     * @since 0.1
     */
    private void addTextFieldToPanel(JPanel panel) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel managerLabel = HRSLabels.simpleLabel(HRSLanguages.NewUser.getManagerLabelText(), null);
        panel.add(managerLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(this.managerNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel teamLabel = HRSLabels.simpleLabel(HRSLanguages.NewUser.getTeamLabelText(), null);
        panel.add(teamLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(this.teamNameField, gbc);
    }

    /**
     * Active ou désactive le bouton play.
     * 
     * 
     * @since 0.1
     */
    private void updateButtons() {
        if (this.playButton != null) {
            String managerField = this.managerNameField.getText();
            String teamField = this.teamNameField.getText();

            boolean fieldsNotEmpty = !managerField.trim().isEmpty() && !teamField.trim().isEmpty();
            boolean fieldsSizeOK = (!managerField.isEmpty() && managerField.length() <= 50) && (!teamField.isEmpty() && teamField.length() <= 40);

            this.playButton.setEnabled(fieldsNotEmpty && fieldsSizeOK);
        }
    }

    /**
     * Méthode pour mettre à jour le texte affiché.
     * 
     * 
     * @since 0.2
     */
    public void updateTexts() {
        this.playButton.setText(HRSLanguages.NewUser.getPlayButtonText());
        this.previousButton.setText(HRSLanguages.NewUser.getPreviousButtonText());
        this.managerNameField.setText(HRSLanguages.NewUser.getManagerLabelText());
        this.teamNameField.setText(HRSLanguages.NewUser.getTeamLabelText());
    }

    // ==================== EVENEMENTS =====================

    /**
     * Gère l'événement du bouton "Jouer".
     * 
     * 
     * @since 0.1
     */
    private void eventPlayButton() {
        if (JOptionPane.showConfirmDialog(this, HRSLanguages.NewUser.getPlayButtonEventText(), this.view.JOpTitle, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            try {
                this.view.controller.createNewUser(this.managerNameField.getText(), this.teamNameField.getText());
                this.view.model.initDatas();
                this.view.setPanel(this.view.getMainPanel());
                this.view.updateTexts();
            } catch (SQLException e) {
                System.err.println("[ERREUR] Problème lors de la création d'un nouvel utilisateur");
            }
        }
    }

    /**
     * Gère l'événement du bouton "Précédent".
     * 
     * 
     * @since 0.1
     */
    private void eventPreviousButton() {
        this.view.setPanel(this.view.getHomePanel());
    }
}
