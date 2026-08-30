package com.kahrs.view.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.kahrs.controller.SoundController;
import com.kahrs.model.Contract;
import com.kahrs.model.Match;
import com.kahrs.model.Player;
import com.kahrs.model.Team;
import com.kahrs.view.GameView;
import com.kahrs.view.visuals.HRSButtons;
import com.kahrs.view.visuals.HRSLanguages;

/**
 * Classe MatchSettingsPanel gérant l'affichage de l'écran d'avant-match (Le Vestiaire).
 * Elle permet de charger les informations du club du manager, de sélectionner 
 * un adversaire aléatoire et de préparer les statistiques pour la simulation.
 * * @author Sofyane HARISSE
 * @since 0.1
 * @version 0.1
 */
public class MatchSettingsPanel extends JPanel {

    /** L'image du fond d'écran du stade. */
    private BufferedImage backgroundImage;
    /** L'image des différents maillots. */
    private BufferedImage jerseyATT, jerseyMIL, jerseyDEF, jerseyGB;
    /** La GameView principale */
    private GameView view;

    
    /** JLabels */
    /** Labels du haut */private JLabel lblHomeName, lblAwayName, lblVs;
    /** Labels du haut */private JLabel lblHomeStats, lblAwayStats;
    /** Label du centre */private JPanel centerPanel;
    /** Labels du bas */private JButton btnStart, btnBack;

    /** Equipe à domicile*/private Team teamHome;
    /** Equipe à l'extérieur*/private Team teamAway;

    /** Indicateur déterminant s'il s'agit d'un match de championnat false sinon. */
    private boolean isChampionshipMatch;

    /**
     * Constructeur de MatchSettingsPanel avec la GameView principale.
     *
     * @param view La GameView principale
     *
     *
     * @since 0.1
     */
    public MatchSettingsPanel(GameView view) {
        this.view = view;
        this.loadResources();
        this.initComponents();
    }


    /**
     * Méthode de chargement des ressources d'images du panel d'avant-match.
     *
     * @since 0.2
     */
    private void loadResources() {
        this.backgroundImage = this.view.loadImage("stadium/stadium_panel_background.png");
        this.jerseyATT = this.view.loadImage("stadium/stadium_panel_jersey_att.png");
        this.jerseyMIL = this.view.loadImage("stadium/stadium_panel_jersey_mil.png");
        this.jerseyDEF = this.view.loadImage("stadium/stadium_panel_jersey_def.png");
        this.jerseyGB = this.view.loadImage("stadium/stadium_panel_jersey_gb.png");
    }


    /**
     * Méthode pour initialiser les composants.
     */
    private void initComponents() {
        this.setLayout(new BorderLayout());

        JPanel headerWrapper = new JPanel(new BorderLayout());
        headerWrapper.setOpaque(false);
        headerWrapper.setBorder(new EmptyBorder(20, 50, 10, 50));

        JPanel scoreboard = new JPanel(new GridLayout(1, 3)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(0, 0, 0, 180)); 
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2.setColor(new Color(255, 255, 255, 50));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
                g2.dispose();
            }
        };
        scoreboard.setPreferredSize(new Dimension(800, 90));
        scoreboard.setOpaque(false);

        lblVs = new JLabel("VS", SwingConstants.CENTER);
        lblVs.setFont(new Font("Verdana", Font.ITALIC, 40));
        lblVs.setForeground(Color.YELLOW);

        scoreboard.add(createTeamInfoPanel(true));
        scoreboard.add(lblVs);
        scoreboard.add(createTeamInfoPanel(false));
        
        headerWrapper.add(scoreboard, BorderLayout.CENTER);
        this.add(headerWrapper, BorderLayout.NORTH);

        this.centerPanel = new JPanel();
        this.centerPanel.setOpaque(false);
        this.centerPanel.setLayout(new GridLayout(4, 1, 0, 15)); 
        this.centerPanel.setBorder(new EmptyBorder(40, 40, 10, 40));
        this.add(centerPanel, BorderLayout.CENTER);

        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 20));
        footer.setOpaque(false);

        btnStart = new HRSButtons("⚡ " + HRSLanguages.MatchSettings.getStartMatchButtonText().toUpperCase(), 250, 60);
        btnBack = new HRSButtons(HRSLanguages.MatchSettings.getBackButtonText(), 180, 60);
        
        btnStart.addActionListener(e -> this.eventStartMatchButton());

        btnBack.addActionListener(e -> view.setPanel(view.getMainPanel()));

        footer.add(btnBack);
        footer.add(btnStart);
        this.add(footer, BorderLayout.SOUTH);
    }

    /**
     * Méthode de création du panel d'affichage des informations textuelles d'une équipe.
     *
     * @param isHome Vrai s'il s'agit de l'équipe à domicile, faux sinon
     * @return Le panel d'informations configuré
     *
     * @since 0.2
     */
    private JPanel createTeamInfoPanel(boolean isHome) {
        JPanel p = new JPanel(new GridLayout(2, 1));
        p.setOpaque(false);
        JLabel name = new JLabel("", SwingConstants.CENTER);
        name.setFont(new Font("SansSerif", Font.BOLD, 20));
        name.setForeground(Color.WHITE);
        JLabel stats = new JLabel("", SwingConstants.CENTER);
        stats.setFont(new Font("Monospaced", Font.BOLD, 14));
        stats.setForeground(new Color(200, 200, 200));

        if(isHome) { lblHomeName = name; lblHomeStats = stats; }
        else { lblAwayName = name; lblAwayStats = stats; }

        p.add(name);
        p.add(stats);
        return p;
    }

    /**
     * Méthode préparant l'écran avant-match (la composition) à partir de deux objets Team distincts.
     *
     * @param home L'équipe jouant à domicile
     * @param away L'équipe jouant à l'extérieur
     * @param isChampionship Vrai s'il s'agit d'une rencontre de championnat, faux sinon
     *
     * @since 0.2
     */
    public void preparerMatch(Team home, Team away, boolean isChampionship) {
        this.teamHome = home;
        this.teamAway = away;
        this.isChampionshipMatch = isChampionship;

        lblHomeName.setText(teamHome.getName().toUpperCase());
        lblHomeStats.setText("ATK: " + (int)Match.getMoyenneStats(teamHome, "attack") + " | DEF : " + (int)Match.getMoyenneStats(teamHome, "defense"));
        lblAwayName.setText(teamAway.getName().toUpperCase());
        lblAwayStats.setText("ATK: " + (int)Match.getMoyenneStats(teamAway, "attack") + " | DEF: " + (int)Match.getMoyenneStats(teamAway, "defense"));

        this.centerPanel.removeAll();
        this.centerPanel.add(createProLine(home.getPlayersByPosition(Player.Poste.ATT, true), jerseyATT));
        this.centerPanel.add(createProLine(home.getPlayersByPosition(Player.Poste.MIL, true), jerseyMIL));
        this.centerPanel.add(createProLine(home.getPlayersByPosition(Player.Poste.DEF, true), jerseyDEF));
        this.centerPanel.add(createProLine(home.getPlayersByPosition(Player.Poste.GB, true), jerseyGB));

        this.revalidate();
        this.repaint();
    }

    /**
     * Méthode préparant l'écran avant-match directement depuis un objet Match existant.
     *
     * @param match Le match contenant les deux équipes à préparer
     * @param isChampionship Vrai s'il s'agit d'une rencontre de championnat, faux sinon
     *
     * @since 0.2
     */
    public void preparerMatch(Match match, boolean isChampionship) {
        this.preparerMatch(match.getHome(), match.getAway(), isChampionship);
    }
    
    /**
     * Crée une ligne tactique avec le format "P. Nom".
     * @param players Liste des joueurs à afficher sur la ligne.
     * @param img     Image du maillot selon le poste.
     * @return Un JPanel contenant la ligne des joueurs formatés.
     */
    private JPanel createProLine(ArrayList<Player> players, BufferedImage img) {
        JPanel line = new JPanel(new GridLayout(1, Math.max(1, players.size()), 10, 0));
        line.setOpaque(false);

        for (Player p : players) {
            JPanel playerCard = new JPanel(new BorderLayout());
            playerCard.setOpaque(false);

            JButton jerseyBtn = HRSButtons.invisibleButton(70, 70);
            jerseyBtn.setIcon(new ImageIcon(img));
            jerseyBtn.setHorizontalAlignment(SwingConstants.CENTER);

            String formattedName = formatPlayerName(p.getName());
            
            JLabel nameLabel = new JLabel("<html><center><b style='color:white; font-size:10px;'>" + formattedName + "</b><br>"
                             + "<span style='color:#FFD700; font-size:9px;'>★ " + p.getNote() + "</span></center></html>", SwingConstants.CENTER);
            nameLabel.setPreferredSize(new Dimension(100, 35));

            playerCard.add(jerseyBtn, BorderLayout.CENTER);
            playerCard.add(nameLabel, BorderLayout.SOUTH);

            line.add(playerCard);
        }
        return line;
    }


    /**
     * Méthode appliquant un format d'affichage condensé au nom complet du joueur.
     *
     * @param fullName Le nom complet d'origine du joueur
     * @return Le nom abrégé formaté sous forme "P. NOM"
     *
     * @since 0.2
     */
    private String formatPlayerName(String fullName) {
        if (fullName == null || fullName.isEmpty()) return "";
        String[] parts = fullName.trim().split("\\s+");
        
        if (parts.length > 1) {
            return parts[0].substring(0, 1).toUpperCase() + ". " + parts[parts.length - 1].toUpperCase();
        }
        return fullName.toUpperCase();
    }

    /**
     * Méthode actualisant les libellés textuels des boutons selon la configuration de langue.
     *
     * @since 0.2
     */
    public void updateTexts() {
        this.btnBack.setText(HRSLanguages.getCloseButtonText());
        this.btnStart.setText(HRSLanguages.MatchSettings.getStartMatchButtonText());
    }

    /**
     * Méthode gérant la validation d'équipe et le lancement effectif de la simulation.
     *
     * @since 0.2
     */
    private void eventStartMatchButton() {
        if (this.teamHome != null) {
            if (this.teamHome.hasInjuredPlayer()) {
                int nbJoueurs = teamHome.getPlayersByStatus(Player.Status.INJURY, true).size();
                JOptionPane.showMessageDialog(
                    this,
                    HRSLanguages.MatchSettings.getIncorrectCompositionText(nbJoueurs),
                    HRSLanguages.MatchSettings.getTitle(),
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }
            if (!this.teamHome.isReadyForMatch()) {
                int nbJoueurs = teamHome.getStarters().size();
                JOptionPane.showMessageDialog(
                    this,
                    HRSLanguages.MatchSettings.getIncompleteCompositionText(nbJoueurs),
                    HRSLanguages.MatchSettings.getTitle(),
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }
        }

        for (Player player : this.teamHome.getStarters()) {
            Contract contract = teamHome.getContractPlayerId(player.getId());
            if (contract == null || contract.isExpired()) {
                JOptionPane.showMessageDialog(
                    this,
                    HRSLanguages.MatchSettings.getExpiredContractErrorText(player.getName()),
                    HRSLanguages.MatchSettings.getTitle(),
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }
        }

        SoundController.playEffect("Kick");
        this.view.getMatchPanel().lancerSimulation(this.teamHome, this.teamAway, this.isChampionshipMatch);
        this.view.setPanel(this.view.getMatchPanel());
    }

    /**
     * Méthode redéfinie appliquant le dessin de l'image de fond d'écran du stade.
     *
     * @param g L'instance d'environnement graphique Graphics pour le rendu
     *
     * @since 0.2
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            g.setColor(new Color(0, 0, 0, 50)); 
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}