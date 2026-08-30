package com.kahrs.view.panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import com.kahrs.view.GameView;
import com.kahrs.view.visuals.HRSButtons;
import com.kahrs.view.visuals.HRSColors;
import com.kahrs.view.visuals.HRSLabels;
import com.kahrs.view.visuals.HRSLanguages;
import com.kahrs.view.visuals.HRSFonts;

/**
 * Classe HomePanel gérant l'affichage de l'écran de démarrage.
 * 
 * @author Sofyane HARISSE
 * 
 * 
 * @since 0.1
 * 
 * @version 0.1
 */
public class HomePanel extends JPanel {

    // ==================== ATTRIBUTS ====================

    // Boutons
    /** Bouton pour commencer une nouvelle partie*/private JButton newGameButton;
    /** Bouton pour afficher les règles */private JButton rulesButton;
    /** Bouton pour afficher les paramètres */private JButton settingsButton;
    /** Bouton pour quitter le jeu */private JButton quitButton;

    /** La GameView principale */private GameView view;

    // ================== CONSTRUCTEUR ==================

    /**
     * Constructeur de HomePanel avec la GameView principale.
     * 
     * @param view La GameView principale
     * 
     * 
     * @since 0.1
     */
    public HomePanel(GameView view) {
        this.view = view;

        this.setBackground(HRSColors.FM_DARK_GREEN);

        this.createButtons();
        this.initLayouts();
    }

    // ==================== METHODES =====================

    /**
     * Méthode pour créer les boutons.
     * 
     * 
     * @since 0.1
     */
    private void createButtons() {
        this.newGameButton = new HRSButtons(HRSLanguages.Home.getNewGameButtonText());
        this.newGameButton.addActionListener(e -> this.eventNewGameButton());

        this.rulesButton = new HRSButtons(HRSLanguages.Home.getRulesButtonText());
        this.rulesButton.addActionListener(e -> this.eventRulesButton());

        this.quitButton = new HRSButtons(HRSLanguages.Home.getQuitButtonText());
        this.quitButton.addActionListener(e -> this.eventQuitButton());

        this.settingsButton = new HRSButtons(HRSLanguages.Home.getSettingsButtonText());
        this.settingsButton.addActionListener(e -> this.eventSettingsButton());
    }

    /**
     * Méthode pour ajouter tous les boutons au JPanel.
     * 
     * @param panel La JPanel visé
     * 
     * 
     * @since 0.1
     */
    private void addAllButtons(JPanel panel) {
        panel.add(this.newGameButton);
        panel.add(this.rulesButton);
        panel.add(this.settingsButton);
        panel.add(this.quitButton);
    }

    /**
     * Méthode pour définir les Layouts.
     * 
     * 
     * @since 0.1
     */
    private void initLayouts() {
        this.setLayout(new BorderLayout());
        this.setBorder(new EmptyBorder(30, 50, 30, 50));

        JLabel titleLabel = new HRSLabels("FOOTBALL MANA-J-ER");
        this.add(titleLabel, BorderLayout.NORTH);

        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setOpaque(false);

        JPanel buttonsPanel = new JPanel(new GridLayout(4, 1, 0, 15));
        buttonsPanel.setOpaque(false);
        buttonsPanel.setPreferredSize(new Dimension(450, 400));
        this.addAllButtons(buttonsPanel);

        centerWrapper.add(buttonsPanel);
        this.add(centerWrapper, BorderLayout.CENTER);
    }

    /**
     * Méthode pour mettre à jour le texte affiché.
     * 
     * 
     * @since 0.2
     */
    public void updateTexts() {
        this.newGameButton.setText(HRSLanguages.Home.getNewGameButtonText());
        this.rulesButton.setText(HRSLanguages.Home.getRulesButtonText());
        this.settingsButton.setText(HRSLanguages.Home.getSettingsButtonText());
        this.quitButton.setText(HRSLanguages.Home.getQuitButtonText());
    }

    /**
     * Gère l'événement du bouton "Nouvelle partie".
     * 
     * 
     * @since 0.1
     */
    private void eventNewGameButton() {
        this.view.setPanel(this.view.getNewUserPanel());
    }

    /**
     * Gère l'événement du bouton "Règles".
     * 
     * 
     * @since 0.1
     */
    private void eventRulesButton() {
        JTextArea textArea = new JTextArea(HRSLanguages.Home.getRulesButtonEventText());
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(HRSFonts.SEGEO_UI);
        textArea.setOpaque(false);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 350));
        scrollPane.setBorder(null);

        JOptionPane.showMessageDialog(this, scrollPane, HRSLanguages.Home.getRulesTitleText(), JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Gère l'événement du bouton "Quitter". Ferme l'application.
     * 
     * 
     * @since 0.1
     */
    private void eventQuitButton() {
        if (JOptionPane.showConfirmDialog(this, HRSLanguages.Home.getQuitButtonEventText(), this.view.JOpTitle, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    /**
     * Gère l'événement du bouton "Settings". Ouvre le SettingsPanel.
     * 
     * 
     * @since 0.1
     */
    private void eventSettingsButton() {
        this.view.setPanel(this.view.getSettingsPanel());
    }
}
