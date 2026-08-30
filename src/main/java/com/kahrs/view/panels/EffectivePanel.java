package com.kahrs.view.panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.kahrs.model.Contract;
import com.kahrs.model.Player;
import com.kahrs.view.GameView;
import com.kahrs.view.visuals.HRSButtons;
import com.kahrs.view.visuals.HRSColors;
import com.kahrs.view.visuals.HRSLabels;
import com.kahrs.view.visuals.HRSLanguages;

/**
 * Classe StadiumPanel gérant l'affichage de la composition de la Team.
 * 
 * 
 * @since 0.1
 * 
 * @version 0.1
 */
public class EffectivePanel extends JPanel {

    // ==================== ATTRIBUTS ====================

    /** La GameView principale */
    private GameView view;

    // Boutons
    /** Bouton pour revenir à l'écran d'accueil */
    private JButton closeButton;

    // Labels
    /** Label de titre */
    /** Label de titre */private JLabel titleLabel;
    /** Label des titulaires */private JLabel startersLabel;
    /** Label des remplaçants */private JLabel substitutesLabel;

    // Panels
    /** Panel du haut */
    private JPanel header;
    /** Panel de tous les joueurs */
    private JPanel playersPanel;

    // Barre de défilement
    /** Barre de défilement verticale */
    private JScrollPane scrollPane;

    // ================== CONSTRUCTEUR ==================

    /**
     * Constructeur de StadiumPanel avec la GameView principale.
     * 
     * @param view La GameView principale
     * 
     * @since 0.1
     */
    public EffectivePanel(GameView view) {
        this.view = view;

        this.setBackground(HRSColors.FM_DARK_GREEN);

        this.createComponents();
    }


    /**
     * Méthode pour initialiser les composants graphiques du panel.
     *
     * @since 0.2
     */
    private void createComponents() {
        this.closeButton = new HRSButtons(HRSLanguages.getCloseButtonText());
        this.closeButton.addActionListener(e -> this.eventCloseButton());

        this.titleLabel = new HRSLabels(HRSLanguages.Effective.getTitle());
        this.startersLabel = new HRSLabels(HRSLanguages.Effective.getStartersTitle());
        this.startersLabel.setAlignmentX(CENTER_ALIGNMENT);
        this.substitutesLabel = new HRSLabels(HRSLanguages.Effective.getSubstitutesTitle());
        this.substitutesLabel.setAlignmentX(CENTER_ALIGNMENT);

        this.header = new JPanel(new BorderLayout());
        this.header.setBackground(HRSColors.FM_DARK_GREEN);

        this.playersPanel = new JPanel();
        this.playersPanel.setForeground(HRSColors.FM_TEXT_WHITE);

        this.scrollPane = new JScrollPane(this.playersPanel);
        this.scrollPane.setOpaque(false);
        this.scrollPane.getViewport().setOpaque(false);
        this.scrollPane.setBorder(null);
    }


    /**
     * Méthode pour créer une ligne d'affichage détaillée pour un joueur.
     *
     * @param player Le joueur à afficher
     * @return Le panel contenant les informations du joueur
     *
     * @since 0.2
     */
    private JPanel createRowPanel(Player player) {
        JPanel wrapper = new JPanel();
        this.setRowProperties(wrapper);
        wrapper.setLayout(new BorderLayout());

        JPanel block = new JPanel(new BorderLayout());
        block.setOpaque(false);
        block.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(HRSColors.FM_TEXT_WHITE, 1, true),
            new EmptyBorder(10, 15, 10, 15)));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);

        JLabel nameLabel = HRSLabels.simpleLabel("👤 " + player.getName() + "  |  🚩 " + player.getPosition().name(), HRSColors.FM_TEXT_WHITE);
        JLabel noteLabel = HRSLabels.simpleLabel("⭐ GEN : " + player.getNote() + "     ❤️ " + player.getStatus().name(), HRSColors.FM_TEXT_WHITE);

        Contract contract = this.view.model.getTeam().getContractPlayerId(player.getId());
        JButton contractButton = HRSButtons.simpleButton("📃 Contract : " + contract.getDuration());
        contractButton.addActionListener(e -> this.eventUpgradeContractDuration(player.getName(), contract));

        topPanel.add(nameLabel, BorderLayout.WEST);
        topPanel.add(noteLabel, BorderLayout.CENTER);
        topPanel.add(contractButton, BorderLayout.EAST);

        JButton statsButton = HRSButtons.simpleButton("Stats");
        statsButton.addActionListener(e -> this.eventShowStatsButton(player));

        JPanel statsPanel = new JPanel(new GridLayout(1, 6, 10, 0));
        statsPanel.setOpaque(false);
        statsPanel.setBorder(new EmptyBorder(10, 0, 0, 0));

        String[] stats = {
            HRSLanguages.Effective.getStatText("attack", player.getAttack()),
            HRSLanguages.Effective.getStatText("defense", player.getDefense()),
            HRSLanguages.Effective.getStatText("speed", player.getSpeed()),
            HRSLanguages.Effective.getStatText("shoot", player.getShoot()),
            HRSLanguages.Effective.getStatText("pass", player.getPass())
        };

        for (String stat : stats) {
            JLabel statLabel = HRSLabels.simpleLabel(stat, HRSColors.FM_TEXT_WHITE);
            statLabel.setHorizontalAlignment(SwingConstants.CENTER);
            statsPanel.add(statLabel);
        }

        JButton soldButton = HRSButtons.simpleButton("💶");
        soldButton.addActionListener(e -> this.eventSoldPlayer(player));

        if (!this.view.model.getTeam().canSold(player.getPosition())) {
            soldButton.setEnabled(false);
        }

        statsPanel.add(statsButton, BorderLayout.WEST);
        statsPanel.add(soldButton, BorderLayout.EAST);

        block.add(topPanel, BorderLayout.NORTH);
        block.add(statsPanel, BorderLayout.CENTER);

        wrapper.add(block, BorderLayout.CENTER);

        return wrapper;
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

        this.header.add(this.titleLabel, BorderLayout.WEST);

        this.add(this.header, BorderLayout.NORTH);
        this.add(this.scrollPane, BorderLayout.CENTER);
        this.add(this.closeButton, BorderLayout.SOUTH);

        this.setPanelProperties(this.playersPanel);
    }


    /**
     * Méthode pour appliquer les propriétés visuelles au panel des joueurs et le remplir.
     *
     * @param panel Le panel à configurer
     *
     * @since 0.2
     */
    private void setPanelProperties(JPanel panel) {
        panel.removeAll();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        panel.add(this.startersLabel);

        for (Player player : this.view.model.getTeam().getStarters()) {
            panel.add(this.createRowPanel(player));
        }

        HRSLabels separator = new HRSLabels("-------------------");
        separator.setAlignmentX(CENTER_ALIGNMENT);
        panel.add(separator);

        panel.add(this.substitutesLabel);

        for (Player player : this.view.model.getTeam().getSubstitutes()) {
            panel.add(this.createRowPanel(player));
        }

        panel.revalidate();
        panel.repaint();
    }


    /**
     * Méthode pour appliquer les propriétés visuelles à une ligne de joueur.
     *
     * @param row La ligne à configurer
     *
     * @since 0.2
     */
    private void setRowProperties(JPanel row) {
        row.setOpaque(false);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        row.setBorder(new EmptyBorder(5, 20, 5, 20));
    }


    /**
     * Méthode pour rafraîchir les textes de l'interface lors d'un changement de langue.
     *
     * @since 0.2
     */
    public void updateTexts() {
        this.titleLabel.setText(HRSLanguages.Effective.getTitle());
        this.closeButton.setText(HRSLanguages.getCloseButtonText());
        this.startersLabel.setText(HRSLanguages.Effective.getStartersTitle());
        this.substitutesLabel.setText(HRSLanguages.Effective.getSubstitutesTitle());

        this.setPanelProperties(this.playersPanel);
    }


    /**
     * Méthode pour gérer l'événement de vente d'un joueur.
     *
     * @param player Le joueur à vendre
     *
     * @since 0.2
     */
    private void eventSoldPlayer(Player player) {
        long value = player.getValue() / 3;
        if (JOptionPane.showConfirmDialog(this, "Vendre " + player.getName() + " pour " + value + " euros ?", this.view.JOpTitle, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION && this.view.controller.soldPlayer(player, value)) {
            JOptionPane.showMessageDialog(this, "Le joueur a été vendu");
            this.initLayout();
            this.view.updateTexts();
        } else {
            JOptionPane.showMessageDialog(this, "Vous ne pouvez pas vendre ce joueur");
        }
    }

    /**
     * Gère l'évênement du bouton "Fermer".
     * 
     * @since 0.2
     */
    private void eventCloseButton() {
        this.view.eventCloseButton();
    }

    /**
     * Méthode pour gérer l'événement de prolongation du contrat d'un joueur.
     *
     * @param name Le nom du joueur
     * @param contract Le contrat à prolonger
     *
     * @since 0.2
     */
    private void eventUpgradeContractDuration(String name, Contract contract) {
        if (contract.getDuration() >= 40) {
            JOptionPane.showMessageDialog(this, "La durée du contrat a déjà atteint son maximum");
        } else if (JOptionPane.showConfirmDialog(this, HRSLanguages.Effective.getContractUpgradeConfirmText(name, contract.getPrice()), this.view.JOpTitle, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION && this.view.controller.restaureContract(name, contract)) {
            JOptionPane.showMessageDialog(this, HRSLanguages.Effective.getContractUpgradeText(name));
            this.view.updateTexts();
        }
    }

    /**
     * Méthode pour ouvrir le panel détaillé des statistiques d'un joueur.
     *
     * @param player Le joueur sélectionné
     *
     * @since 0.2
     */
    private void eventShowStatsButton(Player player) {
        this.view.getPlayerPanel().initLayout(player);
        this.view.setPanel(this.view.getPlayerPanel());
    }
}
