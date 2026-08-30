package com.kahrs.view.panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.time.LocalDate;

import javax.swing.border.EmptyBorder;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.kahrs.app.MailManager;
import com.kahrs.model.CalendarEvent;
import com.kahrs.view.GameView;
import com.kahrs.view.visuals.HRSButtons;
import com.kahrs.view.visuals.HRSColors;
import com.kahrs.view.visuals.HRSLabels;
import com.kahrs.view.visuals.HRSLanguages;

/**
 * Classe MainPanel gérant l'affichage de l'écran principal (Menu Principal).
 * Elle regroupe :
 * - le header (nom du club, argent, paramètres),
 * - le corps (actions de gestion),
 * - le footer (navigation principale).
 * 
 * @author Sofyane HARISSE
 * 
 * @since 0.1
 * 
 * @version 0.2
 */
public class MainPanel extends JPanel {

    /** Image de fond du panel */
    private static BufferedImage BACKGROUND_IMAGE;

    // ==================== ATTRIBUTS ====================

    /** La GameView principale contenant ce menu */
    private GameView view;

    // Éléments du Header

    /** Label affichant le nom du club */
    private JLabel nameLabel;
    /** Label affichant l'argent disponible */
    private JLabel moneyLabel;
    /** Label affichant la date */
    private JLabel dayLabel;
    /** Bouton d'accès aux paramètres */
    private JButton btnSettings;

    // Gros boutons centraux

    /** Bouton d'accès à la gestion du stade */
    private JButton btnStade;
    /** Bouton d'accès à la gestion des infrastructures */
    protected JButton btnInfrastructures;

    // Boutons de navigation (Footer)

    /** Bouton d'accès à la gestion de l'effectif' */
    private JButton btnEffective;
    /** Bouton d'accès à la gestion des matchs */
    protected JButton btnMatch;
    /** Bouton d'accès au marché des transferts */
    protected JButton btnMarket;

    // Boutons utilitaires
    /** Bouton d'accès au calendrier */
    private JButton btnCalendar;
    /** Bouton d'accès aux quêtes */
    private JButton btnQuetes;
    /** Bouton d'accès à la messagerie */
    private JButton btnMails;
    /** Bouton d'accès au classement */
    private JButton btnClassement;
    /** Bouton d'accès au championnat */
    private JButton btnChampionnat;

    // ================== CONSTRUCTEUR ==================

    /**
     * Constructeur du MainPanel.
     * Initialise la structure et les composants graphiques.
     *
     * @param view La GameView principale pour gérer la navigation
     * 
     * @since 0.1
     */
    public MainPanel(GameView view) {
        this.view = view;
        this.setOpaque(false);
        this.setLayout(new BorderLayout());

        BACKGROUND_IMAGE = this.view.loadImage("main/main_panel_background.png");

        this.createButtons();
        this.createLabels();
        this.initComponents();
    }

    // ==================== INITIALISATION ===================

    /**
     * Méthode pour initialiser le panel.
     * 
     * @since 0.1
     */
    private void initComponents() {
        // --- 1. LE HEADER ---
        JPanel headerPanel = this.createHeaderPanel();
        this.add(headerPanel, BorderLayout.NORTH);

        // --- 2. LE CENTRE (CORPS DU MENU) ---
        JPanel centerWrapper = this.createCenterWrapperPanel();
        this.add(centerWrapper, BorderLayout.CENTER);

        // --- 3. BARRE DE NAVIGATION ---
        JPanel footerPanel = this.createFooterPanel();
        this.add(footerPanel, BorderLayout.SOUTH);
    }

    // ==================== MÉTHODES UTILES (BOUTONS) ===================

    /**
     * Méthode pour créer tous les boutons.
     * 
     * @since 0.2
     */
    private void createButtons() {
        int topHeight = 40;
        int topWidth = 200;

        int centerHeight = 45;
        int centerWidth = 170;

        int bottomHeight = 70;
        int bottomWidth = 0;

        this.btnChampionnat = new HRSButtons(HRSLanguages.Main.getChampionshipButtonText(), centerWidth, centerHeight);
        this.btnChampionnat.addActionListener(e -> this.eventChampionshipButton());

        this.btnClassement = new HRSButtons(HRSLanguages.Main.getRankingButtonText(), topWidth, topHeight);
        this.btnClassement.addActionListener(e -> this.eventRankingButton());

        this.btnSettings = new HRSButtons(HRSLanguages.Main.getSettingsButtonText(), topWidth, topHeight);
        this.btnSettings.setAlignmentX(JButton.LEFT_ALIGNMENT);
        this.btnSettings.setMaximumSize(this.btnSettings.getPreferredSize());
        this.btnSettings.addActionListener(e -> this.eventSettingsButton());

        this.btnInfrastructures = HRSButtons.invisibleButton(350, 300);
        this.btnInfrastructures.addActionListener(e -> {
            if (!this.view.getCalendarPanel().getCalendar().hasNotEventToday()){
                JOptionPane.showMessageDialog(this, HRSLanguages.Main.getMessageNoEventButtonText());
                return;
            }
            if (!this.view.getCalendarPanel().getCalendar().canUse(CalendarEvent.Event.TRAINING)) {
                JOptionPane.showMessageDialog(this, HRSLanguages.Main.getMessageInfrastructureButtonText());
                return;
            }
            this.eventInfrastructureButton();
        });

        this.btnMails = new HRSButtons(HRSLanguages.Main.getMailsButtonText(), centerWidth, centerHeight);
        this.btnMails.addActionListener(e -> this.eventMailsButton());

        this.btnMarket = new HRSButtons(HRSLanguages.Main.getMarketButtonText(), bottomWidth, bottomHeight);
        this.btnMarket.addActionListener(e ->{
            if (!this.view.getCalendarPanel().getCalendar().hasNotEventToday()){
                JOptionPane.showMessageDialog(this, HRSLanguages.Main.getMessageNoEventButtonText());
                return;
            }
            if (!this.view.getCalendarPanel().getCalendar().canUse(CalendarEvent.Event.MARKET)) {
                JOptionPane.showMessageDialog(this, HRSLanguages.Main.getMessageMarketButtonText());
                return;
        }
                this.eventMarketButton();
        });

        this.btnMatch = new HRSButtons(HRSLanguages.Main.getMatchButtonText(), bottomWidth, bottomHeight);
        this.btnMatch.addActionListener(e -> {
            if (!this.view.getCalendarPanel().getCalendar().hasNotEventToday()){
                JOptionPane.showMessageDialog(this, HRSLanguages.Main.getMessageNoEventButtonText());
                return;
            }
            if (!this.view.getCalendarPanel().getCalendar().canUse(CalendarEvent.Event.FRIENDLYMATCH)) {
                JOptionPane.showMessageDialog(this, HRSLanguages.Main.getMessageMatchButtonText());
                return;
            }
            this.eventMatchButton();
        });

        this.btnCalendar = new HRSButtons(HRSLanguages.Main.getCalendarButtonText(), centerWidth, centerHeight);
        this.btnCalendar.addActionListener(e -> this.eventCalendarButton());

        this.btnEffective = new HRSButtons(HRSLanguages.Main.getEffectiveButtonText(), bottomWidth, bottomHeight);
        this.btnEffective.addActionListener(e -> this.eventEffectiveButton());

        this.btnQuetes = new HRSButtons(HRSLanguages.Main.getQuestsButtonText(), centerWidth, centerHeight);
        this.btnQuetes.addActionListener(e -> this.eventQuestsButton());

        this.btnStade = HRSButtons.invisibleButton(425, 375);
        this.btnStade.addActionListener(e -> this.eventStadiumButton());
    }

    /**
     * Méthode pour ajouter un ensemble de boutons à un panel.
     * 
     * @param panel   Le panel
     * @param buttons Les boutons à ajouter
     * 
     * @since 0.2
     */
    private void addButtonsToPanel(JPanel panel, JButton... buttons) {
        for (JButton button : buttons) {
            panel.add(button);
        }
    }

    /**
     * Méthode pour mettre à jour les textes affiché.
     * 
     * @since 0.1
     */
    public void updateTexts() {
        this.btnSettings.setText(HRSLanguages.Main.getSettingsButtonText());
        this.btnEffective.setText(HRSLanguages.Main.getEffectiveButtonText());
        this.btnMatch.setText(HRSLanguages.Main.getMatchButtonText());
        this.btnMarket.setText(HRSLanguages.Main.getMarketButtonText());
        this.btnCalendar.setText(HRSLanguages.Main.getCalendarButtonText());
        this.btnQuetes.setText(HRSLanguages.Main.getQuestsButtonText());
        this.btnClassement.setText(HRSLanguages.Main.getRankingButtonText());
        this.btnChampionnat.setText(HRSLanguages.Main.getChampionshipButtonText());
        int unread = MailManager.getUnreadCount();
        String mailTxt = HRSLanguages.Main.getMailsButtonText();
        if (unread > 0) {
            this.btnMails.setText(mailTxt + " (" + unread + ")");
            this.btnMails.setForeground(java.awt.Color.RED);
        } else {
            this.btnMails.setText(mailTxt);
            this.btnMails.setForeground(java.awt.Color.WHITE);
        }

        this.nameLabel.setText("🛡️ " + this.view.model.getTeam().getName());
        this.moneyLabel.setText("💰 " + HRSLabels.getMoneyFormated(this.view.model.getManager().getMoney()) + " €");
    }

    // ==================== MÉTHODES UTILES (LABELS) ===================

    /**
     * Méthode pour créer les labels.
     * 
     * @since 0.2
     */
    private void createLabels() {
        this.nameLabel = new HRSLabels("🛡️ Null", JLabel.LEFT);
        this.moneyLabel = new HRSLabels("💰 0 €", JLabel.RIGHT);
    }

    // ==================== MÉTHODES UTILES (PANELS) ===================

    /**
     * Méthode pour créer et renvoie le panel organisé
     *
     * @return Le panel organisé
     *
     * @since 0.1
     */
    private JPanel createCenterWrapperPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();

        // Gros boutons (Stade & Infrastructures)
        JPanel mainButtons = this.createMainPanel();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(mainButtons, gbc);

        // Boutons Quêtes et Mails (bas-droite)
        JPanel sideRightButtons = this.createSideRightPanel();
        gbc.gridy = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        gbc.insets = new Insets(0, 0, 20, 20);
        panel.add(sideRightButtons, gbc);

        // Boutons Championnat
        JPanel sideLeftButtons = this.createSideLeftPanel();
        gbc.anchor = GridBagConstraints.SOUTHWEST;
        gbc.insets = new Insets(0, 20, 20, 0);
        panel.add(sideLeftButtons, gbc);

        return panel;
    }

    /**
     * Méthode pour créer et renvoie le panel du bas
     *
     * @return Le panel du bas
     *
     * @since 0.1
     */
    private JPanel createFooterPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 3, 15, 0));
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(0, 25, 30, 25));

        this.addButtonsToPanel(panel, this.btnEffective, this.btnMatch, this.btnMarket);

        return panel;
    }

    /**
     * Méthode pour créer et renvoie le panel de l'en-tête
     *
     * @return Le panel de l'en-tête
     *
     * @since 0.1
     */
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(15, 25, 15, 25));
        panel.setBackground(HRSColors.FM_DARK_GREEN);

        // Panel gauche du header (Nom du club + Bouton Settings en dessous)
        JPanel headerLeft = this.createHeaderLeftPanel();
        panel.add(headerLeft, BorderLayout.WEST);

        JPanel headerCenter = this.createHeaderCenterPanel();
        panel.add(headerCenter, BorderLayout.CENTER);

        // Panel de droite (Classement et Argent)
        JPanel headerRight = this.createHeaderRightPanel();
        panel.add(headerRight, BorderLayout.EAST);

        return panel;
    }

    /**
     * Méthode pour créer et renvoie le panel de l'en-tête gauche
     *
     * @return Le panel de l'en-tête gauche
     *
     * @since 0.1
     */
    private JPanel createHeaderLeftPanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Nom du club
        panel.add(this.nameLabel);

        // Ajout d'un petit espace vertical
        panel.add(javax.swing.Box.createRigidArea(new Dimension(0, 5)));

        // Bouton Paramètres (en dessous du nom)
        panel.add(this.btnSettings);

        return panel;
    }

    /**
     * Méthode pour créer et renvoie le panel de l'en-tête central
     *
     * @return Le panel de l'en-tête central
     *
     * @since 0.2
     */
    private JPanel createHeaderCenterPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        this.dayLabel = new HRSLabels(String.valueOf(this.view.model.getCurrentDate()));
        panel.setOpaque(false);
        panel.add(this.dayLabel);

        return panel;
    }

    /**
     * Méthode pour mettre à jour la date affichée
     *
     * @param date La date à afficher
     * 
     * @since 0.2
     */
    public void updateDate(LocalDate date) {
        this.dayLabel.setText(String.valueOf(date));
    }

    /**
     * Méthode pour créer et renvoie le panel de l'en-tête droit
     *
     * @return Le panel de l'en-tête droit
     *
     * @since 0.1
     */
    private JPanel createHeaderRightPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 1, 0, 5));
        panel.setOpaque(false);

        this.addButtonsToPanel(panel, this.btnClassement);
        panel.add(this.moneyLabel);

        return panel;
    }

    /**
     * Méthode pour créer et renvoie le panel central
     *
     * @return Le panel central
     *
     * @since 0.1
     */
    private JPanel createMainPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 0, 0));
        panel.setOpaque(false);

        JPanel stadeWrapper = new JPanel(new GridBagLayout());
        stadeWrapper.setOpaque(false);
        stadeWrapper.add(this.btnStade);

        JPanel infraWrapper = new JPanel(new GridBagLayout());
        infraWrapper.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 80, 50, 0);
        infraWrapper.add(this.btnInfrastructures, gbc);

        panel.add(stadeWrapper);
        panel.add(infraWrapper);

        return panel;
    }

    /**
     * Méthode pour créer et renvoie le panel côté gauche.
     * 
     * @return Le panel côté gauche
     * 
     * @since 0.1
     */
    private JPanel createSideLeftPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 1, 0, 10));
        panel.setOpaque(false);

        this.addButtonsToPanel(panel, this.btnChampionnat);

        return panel;
    }

    /**
     * Méthode pour créer et renvoie le panel côté droit
     *
     * @return Le panel côté droit
     *
     * @since 0.1
     */
    private JPanel createSideRightPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 1, 0, 10));
        panel.setOpaque(false);

        this.addButtonsToPanel(panel, this.btnCalendar, this.btnQuetes, this.btnMails);

        return panel;
    }

    // ==================== MÉTHODES UTILES (EVENEMENTS) ===================

    /**
     * Gère l'évênement du bouton "Championnat".
     * 
     * @since 0.2
     */
    private void eventChampionshipButton() {
        this.view.getChampionshipPanel().initLayout();
        this.view.setPanel(this.view.getChampionshipPanel());
    }
    /**
     * Gère l'évênement du bouton "Infrastructures".
     * 
     * @since 0.1
     */
    private void eventInfrastructureButton() {
        this.view.getInfrastructurePanel().initLayout();
        this.view.setPanel(this.view.getInfrastructurePanel());
    }

    /**
     * Méthode pour afficher les mails.
     * 
     * @since 0.1
     */
    private void eventMailsButton() {
        this.view.getMailPanel().refreshList();
        this.view.setPanel(this.view.getMailPanel());
    }

    /**
     * Gère l'évênement du bouton "Marché".
     * 
     * @since 0.1
     */
    private void eventMarketButton() {
        this.view.getMarketPanel().initLayout();
        this.view.setPanel(this.view.getMarketPanel());
    }

    /**
     * Gère l'évênement du bouton "Match".
     * 
     * @since 0.2
     */
    private void eventMatchButton() {
        MatchSettingsPanel settings = this.view.getMatchSettingsPanel();
        
        this.view.controller.getMatchController().preparerMatch();
        this.view.setPanel(settings);

        settings.requestFocusInWindow();
    }

    /**
     * Méthode pour afficher le calendrier.
     *
     * @since 0.2
     */
    private void eventCalendarButton() {
        this.view.getCalendarPanel().setViewDate(this.view.getModel().getCurrentDate());
        this.view.getCalendarPanel().refreshCalendar();
        this.view.setPanel(this.view.getCalendarPanel());
    }

    /**
     * Méthode pour afficher les quêtes.
     * 
     * @since 0.1
     */
    private void eventQuestsButton() {
        this.view.getQuestsPanel().initLayout();
        this.view.setPanel(this.view.getQuestsPanel());
    }

    /**
     * Méthode pour afficher le classement.
     * 
     * @since 0.1
     */
    private void eventRankingButton() {
        JOptionPane.showMessageDialog(this.view, this.view.showRanking());
    }

    /**
     * Gère l'évênement du bouton "Paramètres".
     * 
     * @since 0.2
     */
    private void eventSettingsButton() {
        this.view.setPanel(this.view.getSettingsPanel());
    }

    /**
     * Gère l'évênement du bouton "Effective".
     * 
     * @since 0.2
     */
    private void eventEffectiveButton() {
        this.view.getEffectivePanel().initLayout();
        this.view.setPanel(this.view.getEffectivePanel());
    }

    /**
     * Gère l'évênement du bouton "Stadium".
     * 
     * @since 0.1
     */
    private void eventStadiumButton() {
        this.view.getStadiumPanel().initlayout();
        this.view.setPanel(this.view.getStadiumPanel());
    }

    /**
     * Initialise et organise les composants graphiques du panneau.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (BACKGROUND_IMAGE != null) {
            g.drawImage(BACKGROUND_IMAGE, 0, 0, getWidth(), getHeight(), this);
        }
    }
}