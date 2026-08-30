package com.kahrs.view.panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import com.kahrs.model.Player;
import com.kahrs.view.GameView;
import com.kahrs.view.visuals.HRSButtons;
import com.kahrs.view.visuals.HRSColors;
import com.kahrs.view.visuals.HRSLabels;
import com.kahrs.view.visuals.HRSLanguages;

/**
 * Classe MarketPanel gérant l'affichage du marché.
 * 
 * @author Hugo ARNAUD
 * 
 * 
 * @since 0.1
 * 
 * @version 0.1
 */
public class MarketPanel extends JPanel {

    // ==================== ATTRIBUTS ====================

    /** La GameView principale */private GameView view;

    /** Layout pour les colonnes */private static final GridLayout DEFAULT_GRIDLAYOUT = new GridLayout(1, 11, 0, 0);

    // Boutons
    /** Bouton pour revenir à l'écran d'accueil */private JButton closeButton;
    /** Bouton pour mettre à jour les données */private JButton updateDatasButton;
    /** Bouton pour afficher tous les Players */private JButton allPlayersButton;
    /** Bouton pour afficher tous les ATT */private JButton ATTPlayersButton;
    /** Bouton pour afficher tous les MIL */private JButton MILPlayersButton;
    /** Bouton pour afficher tous les DEF */private JButton DEFPlayersButton;
    /** Bouton pour afficher tous les GB */private JButton GBPlayersButton;

    // Labels
    /** Label de titre */private JLabel titleLabel;

    // Panels
    /** Panel du haut */private JPanel header;
    /** Panel des colonnes */private JPanel infos;
    /** Panel de tous les joueurs */private JPanel playersPanel;
    /** Panel de filtre */private JPanel filterPanel;

    // Barre de défilement
    /** Barre de défilement verticale */private JScrollPane scrollPane;

    /** Label d'argent du manager */private JLabel managerMoney;

    // ================== CONSTRUCTEUR ==================

    /**
     * Constructeur de MarketPanel avec la GameView principale.
     * 
     * @param view La GameView principale
     * 
     * 
     * @since 0.1
     */
    public MarketPanel(GameView view) {
        this.view = view;

        this.setBackground(HRSColors.FM_DARK_GREEN);

        this.createButtons();
        this.createLabels();
        this.createPanels();
        this.createScrollPane();
    }

    // ==================== CREATIONS =====================

    /**
     * Méthode pour créer les JButtons.
     * 
     * 
     * @since 0.1
     */
    private void createButtons() {
        this.closeButton = new HRSButtons(HRSLanguages.getCloseButtonText());
        this.closeButton.addActionListener(e -> this.eventCloseButton());

        this.updateDatasButton = new HRSButtons("⟳");
        this.updateDatasButton.addActionListener(e -> this.eventUpdateDatasButton());

        this.allPlayersButton = new HRSButtons("All");
        this.allPlayersButton.addActionListener(e -> this.eventFilterButtons(null));

        this.ATTPlayersButton = new HRSButtons("ATT");
        this.ATTPlayersButton.addActionListener(e -> this.eventFilterButtons(Player.Poste.ATT));

        this.MILPlayersButton = new HRSButtons("MIL");
        this.MILPlayersButton.addActionListener(e -> this.eventFilterButtons(Player.Poste.MIL));

        this.DEFPlayersButton = new HRSButtons("DEF");
        this.DEFPlayersButton.addActionListener(e -> this.eventFilterButtons(Player.Poste.DEF));

        this.GBPlayersButton = new HRSButtons("GB");
        this.GBPlayersButton.addActionListener(e -> this.eventFilterButtons(Player.Poste.GB));
    }

    /**
     * Méthode pour créer les JLabels.
     * 
     * 
     * @since 0.1
     */
    private void createLabels() {
        this.titleLabel = new HRSLabels(HRSLanguages.Market.getTitle());
        this.managerMoney = new JLabel();
    }

    /**
     * Méthode pour créer les JPanels.
     * 
     * 
     * @since 0.1
     */
    private void createPanels() {
        this.header = new JPanel(new BorderLayout());
        this.header.setBackground(HRSColors.FM_DARK_GREEN);

        this.infos = new JPanel(MarketPanel.DEFAULT_GRIDLAYOUT);

        String[] columns = { HRSLanguages.Market.getNameColumnText(), HRSLanguages.Market.getPositionColumnText(), HRSLanguages.Market.getNoteColumnText(), HRSLanguages.Market.getAttackColumnText(), HRSLanguages.Market.getDefenseColumnText(), HRSLanguages.Market.getSpeedColumnText(), HRSLanguages.Market.getShootColumnText(), HRSLanguages.Market.getPassColumnText(), HRSLanguages.Market.getValueColumnText(), "" };
        for (String column : columns) {
            JLabel label = HRSLabels.simpleLabel(column, HRSColors.RED);
            this.infos.add(label);
        }

        this.playersPanel = new JPanel();
        this.playersPanel.setForeground(HRSColors.FM_TEXT_WHITE);

        this.filterPanel = new JPanel();
        this.filterPanel.setBackground(HRSColors.FM_DARK_GREEN);

        JButton[] buttons = { this.allPlayersButton, this.ATTPlayersButton, this.MILPlayersButton, this.DEFPlayersButton, this.GBPlayersButton, this.updateDatasButton };
        for (JButton button : buttons) {
            this.filterPanel.add(button);
        }
    }

    /**
     * Méthode pour créer une ligne.
     * 
     * @param player Le joueur à afficher
     * 
     * @return La ligne créée
     * 
     * 
     * @since 0.1
     */
    private JPanel createRowPanel(Player player) {
        JPanel row = new JPanel(MarketPanel.DEFAULT_GRIDLAYOUT);
        this.setRowProperties(row);

        String[] columns = { player.getName(), player.getPosition().name(), String.valueOf(player.getNote()), String.valueOf(player.getAttack()), String.valueOf(player.getDefense()), String.valueOf(player.getSpeed()), String.valueOf(player.getShoot()), String.valueOf(player.getPass()), HRSLabels.getMoneyFormated((long) player.getValue()) + " €" };

        for (String column : columns) {
            JLabel label = HRSLabels.simpleLabel(column, null);
            row.add(label);
        }

        JButton buyButton = new HRSButtons("💲");
        row.add(buyButton);
        buyButton.addActionListener(e -> this.eventBuyPlayerButton(player));
        if (player.getValue() > this.view.model.getManager().getMoney()) {
            buyButton.setEnabled(false);
        }

        return row;
    }

    /**
     * Méthod pour créer la barre de défilement.
     * 
     * 
     * @since 0.1
     */
    private void createScrollPane() {
        this.scrollPane = new JScrollPane(this.playersPanel);

        this.scrollPane.setOpaque(false);
        this.scrollPane.getViewport().setOpaque(false);
        this.scrollPane.setBorder(null);
    }

    /**
     * Méthode pour initialiser le layout.
     * 
     * 
     * @since 0.1
     */
    public void initLayout() {
        this.removeAll();
        this.setLayout(new BorderLayout());
        this.setBorder(new EmptyBorder(30, 50, 30, 50));

        this.createButtons();
        this.createLabels();
        this.createPanels();
        this.createScrollPane();

        this.header.add(this.titleLabel, BorderLayout.WEST);

        this.managerMoney = new HRSLabels(HRSLabels.getMoneyFormated(this.view.model.getManager().getMoney()) + " €");
        this.header.add(this.managerMoney, BorderLayout.EAST);
        this.header.add(this.filterPanel, BorderLayout.SOUTH);

        this.add(this.header, BorderLayout.NORTH);
        this.add(this.scrollPane, BorderLayout.CENTER);
        this.add(this.closeButton, BorderLayout.SOUTH);

        this.setPanelProperties(this.playersPanel);
    }

    // ==================== PROPRIETES ===================

    /**
     * Méthode pour définir les propriétés d'un JPanel.
     * 
     * @param panel Le JPanel à modifier
     * 
     * 
     * @since 0.1
     */
    private void setPanelProperties(JPanel panel) {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        panel.add(this.infos);

        for (Player player : this.view.model.getAllPlayers()) {
            panel.add(this.createRowPanel(player));
        }
    }

    /**
     * Méthode pour définir les propriétés d'une ligne.
     * 
     * @param row La ligne à modifier
     * 
     * 
     * @since 0.1
     */
    private void setRowProperties(JPanel row) {
        row.setOpaque(false);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        row.setBorder(new EmptyBorder(10, 0, 10, 0));
    }

    /**
     * Méthode pour mettre à jour les textes des boutons.
     * 
     * 
     * @since 0.1
     */
    public void updateTexts() {
        this.closeButton.setText(HRSLanguages.getCloseButtonText());
        this.managerMoney.setText(HRSLabels.getMoneyFormated(this.view.model.getManager().getMoney()) + " €");
    }

    // ==================== EVENEMENTS =====================

    /**
     * Gère l'évênement du bouton "Acheter".
     * 
     * @param player Le Player à acheter
     * 
     * 
     * @since 0.1
     */
    private void eventBuyPlayerButton(Player player) {
        if (JOptionPane.showConfirmDialog(this.view,
                HRSLanguages.Market.getBuyPlayerButtonEventText(player.getName(), player.getNote(), player.getValue()),
                this.view.JOpTitle, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            try {
                if (this.view.controller.buyPlayer(player)) {
                    JOptionPane.showMessageDialog(this.view, HRSLanguages.Market.getBuyPlayerSuccessText(player.getName()));
                    this.eventUpdateDatasButton();
                    this.view.updateTexts();
                }
            } catch (SQLException e) {
                System.err.println("[ERREUR] Problème lors de l'achat du Player");
            }
        }
    }

    /**
     * Gère l'évênement du bouton "Fermer".
     * 
     * 
     * @since 0.1
     */
    private void eventCloseButton() {
        this.view.eventCloseButton();
    }

    /**
     * Gère l'évênement du bouton "Mise à jour".
     * 
     * 
     * @since 0.2
     */
    private void eventUpdateDatasButton() {
        this.view.controller.updateDatas();
        this.eventFilterButtons(null);
    }

    /**
     * Gère l'évênements des boutons pour filtrer.
     * 
     * @param choice Le Poste choisi, ou null
     * 
     * 
     * @since 0.2
     */
    private void eventFilterButtons(Player.Poste choice) {
        this.playersPanel.removeAll();
        this.playersPanel.add(this.infos);

        for (Player player : this.view.model.getAllPlayers()) {
            if (choice == null || player.getPosition() == choice) {
                this.playersPanel.add(this.createRowPanel(player));
            }
        }

        this.playersPanel.revalidate();
        this.playersPanel.repaint();
    }
}
