package com.kahrs.view.panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import com.kahrs.model.CalendarEvent;
import com.kahrs.model.Championship;
import com.kahrs.model.Team;
import com.kahrs.view.GameView;
import com.kahrs.view.visuals.HRSButtons;
import com.kahrs.view.visuals.HRSColors;
import com.kahrs.view.visuals.HRSLabels;
import com.kahrs.view.visuals.HRSLanguages;


/**
 * Classe représentant le panel d'affichage du championnat et de son classement.
 *
 * @author Hugo ARNAUD
 *
 * @since 0.2
 */

public class ChampionshipPanel extends JPanel {

    /** La vue principale du jeu. */
    private GameView view;

    /** Le championnat en cours d'affichage. */
    private Championship championship;

    /** Le layout pour les lignes du classement. */
    private static GridLayout DEFAULT_GRIDLAYOUT = new GridLayout(1, 4, 0, 0);

    /** Le bouton pour lancer le prochain match. */
    private JButton playNextMatchButton;

    /** Le bouton pour fermer le panel. */
    private JButton closeButton;

    /** Le label affichant le titre du panel. */
    private JLabel titleLabel;

    /** Le panel d'en-tête contenant le titre. */
    private JPanel header;

    /** Le panel du bas contenant les boutons d'action. */
    private JPanel bottom;

    /** Le panel contenant la liste du classement. */
    private JPanel rankingPanel;

    /** Le conteneur défilant pour le classement des équipes. */
    private JScrollPane scrollPane;


    /**
     * Constructeur de ChampionshipPanel.
     *
     * @param view La vue principale de l'application
     * @param championship Le championnat en cours 
     *
     * @since 0.2
     */
    public ChampionshipPanel(GameView view, Championship championship) {
        this.view = view;
        this.setLayout(new BorderLayout());
        this.setBackground(HRSColors.FM_DARK_GREEN);

        this.championship = championship;
        this.createButtons();
        this.createLabels();
        this.createPanels();
        this.createScrollPane();
    }

    /**
     * Méthode pour créer et configurer les boutons du panel.
     *
     * @since 0.2
     */
    private void createButtons() {
        this.closeButton = new HRSButtons(HRSLanguages.MatchSettings.getBackButtonText(), 180, 60);
        this.closeButton.addActionListener(e -> this.eventCloseButton());

        this.playNextMatchButton = new HRSButtons(HRSLanguages.MatchSettings.getStartMatchButtonText(), 250, 60);
        this.playNextMatchButton.addActionListener(e -> {
            if (!this.view.getCalendarPanel().getCalendar().hasNotEventToday()){
                JOptionPane.showMessageDialog(this, HRSLanguages.Main.getMessageNoEventButtonText());
                return;
            }
            if (!this.view.getCalendarPanel().getCalendar().canUse(CalendarEvent.Event.CHAMPIONSHIPMATCH)) {
                JOptionPane.showMessageDialog(this, HRSLanguages.Main.getMessageChampionshipButtonText());
                return;
            }
            this.eventMatchButton();
        });
    }


    /**
     * Méthode pour initialiser les labels du panel.
     *
     * @since 0.2
     */
    private void createLabels() {
        this.titleLabel = new HRSLabels(HRSLanguages.Championship.getTitle());
    }


    /**
     * Méthode pour créer les sous-panels du championnat (en-tête, bas, classement).
     *
     * @since 0.2
     */
    private void createPanels() {
        this.header = new JPanel(new BorderLayout());
        this.header.setBackground(HRSColors.FM_DARK_GREEN);

        this.bottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 20));
        this.bottom.setOpaque(false);

        this.rankingPanel = new JPanel();
        this.rankingPanel.setForeground(HRSColors.FM_TEXT_WHITE);
    }


    /**
     * Méthode pour initialiser et agencer l'interface graphique du panel.
     *
     * @since 0.2
     */
    public void initLayout() {
        this.removeAll();
        this.setLayout(new BorderLayout());
        this.setBorder(new EmptyBorder(30, 50, 30, 50));

        this.createButtons();
        this.createLabels();
        this.createPanels();
        this.createScrollPane();

        this.header.add(this.titleLabel, BorderLayout.CENTER);
        this.bottom.add(this.closeButton);
        this.bottom.add(this.playNextMatchButton);

        this.add(this.header, BorderLayout.NORTH);
        this.add(this.scrollPane, BorderLayout.CENTER);
        this.add(this.bottom, BorderLayout.SOUTH);

        this.setPanelProperties(this.rankingPanel);
    }


    /**
     * Méthode pour appliquer les propriétés visuelles au panel de classement et le remplir.
     *
     * @param panel Le panel à configurer
     *
     * @since 0.2
     */
    private void setPanelProperties(JPanel panel) {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        for (Team team : this.view.model.getChampionship().getTeams()) {
            panel.add(this.createRowTeam(team));
        }
    }

    /**
     * Méthode pour créer une ligne de statistiques détaillée pour une équipe.
     *
     * @param team L'équipe à afficher dans le classement
     * @return Le panel contenant la ligne formatée
     *
     * @since 0.2
     */
    private JPanel createRowTeam(Team team) {
        JPanel row = new JPanel(ChampionshipPanel.DEFAULT_GRIDLAYOUT);
        this.setRowProperties(row);

        Championship.TeamStats stats = this.championship.getStanding().get(team.getId());

        int pts = (stats != null ? stats.points : 0);
        int played = (stats != null ? stats.played : 0);
        int diff = (stats != null ? stats.getGoalDifference() : 0);
        String[] columns = {
                team.getName(),
                "Pts: " + pts,
                "J: " + played + " / " + this.championship.getNbMatchs(),
                "Diff: " + diff
        };

        for (String col : columns) {
            row.add(HRSLabels.simpleLabel(col, HRSColors.FM_TEXT_WHITE));
        }

        return row;
    }

    /**
     * Méthode pour appliquer les propriétés visuelles à une ligne de classement.
     *
     * @param row La ligne à configurer
     *
     * @since 0.2
     */
    private void setRowProperties(JPanel row) {
        row.setOpaque(false);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        row.setBorder(new EmptyBorder(10, 0, 10, 0));
    }

    /**
     * Méthode pour créer et configurer le menu défilant du classement.
     *
     * @since 0.2
     */
    private void createScrollPane() {
        this.scrollPane = new JScrollPane(this.rankingPanel);

        this.scrollPane.setOpaque(false);
        this.scrollPane.getViewport().setOpaque(false);
        this.scrollPane.setBorder(null);
    }

    /**
     * Méthode pour rafraîchir les textes de l'interface lors d'un changement de langue.
     *
     * @since 0.2
     */
    public void updateTexts() {
        this.titleLabel.setText(HRSLanguages.Championship.getTitle());
        this.playNextMatchButton.setText(HRSLanguages.MatchSettings.getStartMatchButtonText());
        this.closeButton.setText(HRSLanguages.getCloseButtonText());
    }

    /**
     * Méthode pour gérer l'action du bouton de fermeture et retourner à la vue précédente.
     *
     * @since 0.2
     */
    private void eventCloseButton() {
        this.view.eventCloseButton();
    }

    /**
     * Méthode pour préparer et lancer le prochain match de championnat.
     *
     * @since 0.2
     */
    private void eventMatchButton() {
        if (this.view.model.getChampionship().isOver()) {
            JOptionPane.showMessageDialog(this, HRSLanguages.Championship.getRestartMessageText(), this.view.JOpTitle, JOptionPane.YES_OPTION);
            this.view.model.getChampionship().restartChampionship();
        }
        MatchSettingsPanel settings = this.view.getMatchSettingsPanel();

        this.view.controller.getMatchController().preparerMatchChampionnat(this.championship);
        this.view.setPanel(settings);

        settings.requestFocusInWindow();
    }
}
