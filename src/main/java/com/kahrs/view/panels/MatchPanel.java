package com.kahrs.view.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import com.kahrs.controller.SoundController;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import com.kahrs.model.Match;
import com.kahrs.model.Team;
import com.kahrs.view.GameView;
import com.kahrs.view.visuals.HRSButtons;
import com.kahrs.view.visuals.HRSLanguages;

/**
 * Classe MatchPanel gérant l'affichage du terrain et le déroulement du match.
 * Le chronomètre et les actions sont synchronisés via un Timer unique.
 * @author Sofyane HARISSE
 * @since 0.1
 * @version 0.1
 */
public class MatchPanel extends JPanel {

    /** Vue principale de l'application. */
    private GameView view;

    /** Labels pour l'affichage des informations de match. */
    private JLabel lblEquipes, lblScore, lblVerdict, lblCommentaire, lblChrono, lblStats;

    /** Bouton pour revenir au menu principal. */
    private JButton btnRetour;    

    /** Instance du match en cours de simulation. */
    private Match currentMatch;

    /** Timer pour cacher l'animation de but. */
    private Timer goalTimer;

    /**
     * Constructeur du panel de match.
     * @param view La vue principale de l'application.
     */
    public MatchPanel(GameView view) {
        this.view = view;
        this.setLayout(new BorderLayout(20, 20));
        this.setBorder(new EmptyBorder(40, 40, 40, 40));
        this.setBackground(new Color(20, 80, 20));
        initComponents();
    }

    /**
     * Méthode pour mettre à jour l'affichage du chronomètre.
     *
     * @param chrono La minute actuelle du match
     *
     * @since 0.1
     */
    public void setChrono(int chrono) {
        lblChrono.setText(chrono + "'");
    }

    /**
     * Méthode pour mettre à jour l'affichage du score.
     *
     * @param scoreHome Le score de l'équipe à domicile
     * @param scoreAway Le score de l'équipe à l'extérieur
     *
     * @since 0.1
     */
    public void setScore(int scoreHome, int scoreAway) {
        lblScore.setText(scoreHome + " - " + scoreAway);
    }

    /**
     * Méthode pour mettre à jour le commentaire textuel du match.
     *
     * @param commentary Le texte du commentaire à afficher
     *
     * @since 0.1
     */
    public void setCommentary(String commentary) {
        lblCommentaire.setText(commentary);
    }

    /**
     * Méthode pour demander au joueur s'il souhaite faire des changements à la mi-temps.
     *
     * @return Vrai si le joueur accepte, Faux sinon
     *
     * @since 0.1
     */
    public boolean demanderChangementsMiTemps() {
        return JOptionPane.showConfirmDialog(this, HRSLanguages.Match.getHalfTimeText(), HRSLanguages.Match.getHalfTimeTitleText(), JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    /**
     * Affiche une animation quand un but est marqué.
     * 
     * @param teamName Le nom de l'équipe qui a marqué
     * 
     * @since 0.1
     */
    public void showGoalAnimation(String teamName) {
        lblVerdict.setText(HRSLanguages.Match.getCommentaryForEvent(Match.MatchEvent.GOAL_HOME, teamName));
        lblVerdict.setForeground(Color.YELLOW);
        lblVerdict.setFont(new Font("SansSerif", Font.BOLD, 38));

        if (goalTimer != null && goalTimer.isRunning()) {
            goalTimer.stop();
        }

        goalTimer = new Timer(2000, e -> {
            lblVerdict.setText("");
        });
        goalTimer.setRepeats(false);
        goalTimer.start();
    }

    /**
     * Initialise les composants graphiques du terrain.
     */
    private void initComponents() {
        // --- Header (Noms + Chrono) ---
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);

        lblEquipes = new JLabel(HRSLanguages.Match.getLiveMatchTitle(), SwingConstants.CENTER);
        lblEquipes.setFont(new Font("SansSerif", Font.BOLD, 26));
        lblEquipes.setForeground(Color.WHITE);

        lblChrono = new JLabel("0'", SwingConstants.RIGHT);
        lblChrono.setFont(new Font("Monospaced", Font.BOLD, 35));
        lblChrono.setForeground(Color.YELLOW);
        lblChrono.setPreferredSize(new Dimension(120, 50));

        topPanel.add(lblEquipes, BorderLayout.CENTER);
        topPanel.add(lblChrono, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        // --- Centre (Score + Commentaires) ---
        JPanel centerPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        centerPanel.setOpaque(false);
        
        lblScore = new JLabel("0 - 0", SwingConstants.CENTER);
        lblScore.setFont(new Font("Monospaced", Font.BOLD, 90));
        lblScore.setForeground(Color.WHITE);
        
        lblCommentaire = new JLabel(" ", SwingConstants.CENTER);
        lblCommentaire.setFont(new Font("SansSerif", Font.ITALIC, 22));
        lblCommentaire.setForeground(Color.LIGHT_GRAY);

        lblVerdict = new JLabel("", SwingConstants.CENTER);
        lblVerdict.setFont(new Font("SansSerif", Font.BOLD, 32));

        lblStats = new JLabel("", SwingConstants.CENTER);
        lblStats.setFont(new Font("SansSerif", Font.PLAIN, 16));
        lblStats.setForeground(Color.WHITE);

        centerPanel.add(lblScore);
        centerPanel.add(lblCommentaire);
        centerPanel.add(lblVerdict);
        centerPanel.add(lblStats);
        add(centerPanel, BorderLayout.CENTER);

        // --- Footer (Bouton Retour) ---
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footer.setOpaque(false);

        btnRetour = new HRSButtons(HRSLanguages.Match.getReturnButtonText(), 280, 55);
        btnRetour.setVisible(false);

        btnRetour.addActionListener(e -> {
            SoundController.stopMusic();
            view.updateTexts();
            view.setPanel(view.getMainPanel());
        });

        footer.add(btnRetour);
        add(footer, BorderLayout.SOUTH);
    }

    /**
     * Lance la simulation du match.
     * @param teamHome L'équipe à domicile
     * @param teamAway L'équipe à l'extérieur (bot)
     * @param isChampionship Match de championnat
     */
    public void lancerSimulation(Team teamHome, Team teamAway, boolean isChampionship) {
        lblEquipes.setText(teamHome.getName() + "  VS  " + teamAway.getName());
        this.setScore(0, 0);
        this.setChrono(0);
        lblVerdict.setText("");
        lblStats.setText("");
        btnRetour.setVisible(false);

        currentMatch = new Match(teamHome, teamAway);
        this.view.controller.getMatchController().simulerMatch(currentMatch, isChampionship);
    }

    /**
     * Finalise le match et affiche le verdict traduit.
     * 
     * @param match Le match à afficher
     */
    public void terminerMatch(Match match) {
        if (goalTimer != null && goalTimer.isRunning()) {
            goalTimer.stop();
        }

        int scoreHome = match.getScoreHome();
        int scoreAway = match.getScoreAway();

        this.setScore(scoreHome, scoreAway);
        this.setChrono(90);
        this.setCommentary("🏁");

        int gain = this.view.controller.getMatchController().calculerGain(match, match.getHomeId());
        if (scoreHome > scoreAway) {
            lblVerdict.setText(HRSLanguages.Match.getVictoryText(gain));
            lblVerdict.setForeground(Color.GREEN);
            SoundController.playMusic("Victory");
        } else if (scoreAway > scoreHome) {
            lblVerdict.setText(HRSLanguages.Match.getDefeatText(gain));
            lblVerdict.setForeground(Color.RED);
            SoundController.playMusic("Defeat");
        } else {
            lblVerdict.setText(HRSLanguages.Match.getDrawText(gain));
            lblVerdict.setForeground(Color.YELLOW);
            SoundController.playMusic("Tie");
        }

        String statsTxt = HRSLanguages.Match.getStatsText(
            currentMatch.getShotsHome(), currentMatch.getShotsOnTargetHome(),
            currentMatch.getShotsAway(), currentMatch.getShotsOnTargetAway(),
            currentMatch.getPassesHome(), currentMatch.getPassesAway()
        );
        lblStats.setText(statsTxt);
        
        btnRetour.setVisible(true);
    }

    /**
     * Met à jour les textes du panel selon la langue
     */
    public void updateTexts() {
        btnRetour.setText(HRSLanguages.Match.getReturnButtonText());
        if (lblChrono.getText().equals("0'") || lblChrono.getText().equals("90'")) {
            lblEquipes.setText(HRSLanguages.Match.getLiveMatchTitle());
        }
    }
}