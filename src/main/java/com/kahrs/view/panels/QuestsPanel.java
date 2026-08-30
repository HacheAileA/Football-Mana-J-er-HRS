package com.kahrs.view.panels;

import java.awt.BorderLayout;
import java.awt.Dimension;

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

import com.kahrs.app.QuestsManager;
import com.kahrs.controller.SoundController;
import com.kahrs.model.Quest;
import com.kahrs.view.GameView;
import com.kahrs.view.visuals.HRSButtons;
import com.kahrs.view.visuals.HRSColors;
import com.kahrs.view.visuals.HRSLabels;
import com.kahrs.view.visuals.HRSLanguages;

/**
 * Classe QuestPanel gérant l'affichage des quêtes.
 * 
 * @author Hugo ARNAUD
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
public class QuestsPanel extends JPanel {

    // ==================== ATTRIBUTS ====================

    /** La GameView principale */
    private GameView view;

    // Boutons
    /** Bouton pour revenir à l'écran d'accueil */
    private JButton closeButton;

    // Labels
    /** Label de titre */
    private JLabel titleLabel;

    // Panels
    /** Panel du haut */
    private JPanel header;
    /** Panel de toutes les quêtes */
    private JPanel questsPanel;

    // Barre de défilement
    /** Barre de défilement verticale */
    private JScrollPane scrollPane;

    // ================== CONSTRUCTEUR ==================

    /**
     * Constructeur de QuestPanel avec la GameView principale.
     * 
     * @param view La GameView principale
     * 
     * @since 0.2
     */
    public QuestsPanel(GameView view) {
        this.view = view;

        this.setBackground(HRSColors.FM_DARK_GREEN);

        this.createComponents();
    }

    // ==================== CREATIONS =====================

    /**
     * Crée et initialise les composants graphiques du panel.
     * * @since 0.2
     */
    private void createComponents() {
        this.closeButton = new HRSButtons(HRSLanguages.getCloseButtonText());
        this.closeButton.addActionListener(e -> this.eventCloseButton());

        this.titleLabel = new HRSLabels(HRSLanguages.Quest.getTitle());

        this.header = new JPanel(new BorderLayout());
        this.header.setBackground(HRSColors.FM_DARK_GREEN);

        this.questsPanel = new JPanel();
        this.questsPanel.setForeground(HRSColors.FM_TEXT_WHITE);

        this.scrollPane = new JScrollPane(this.questsPanel);
        this.scrollPane.setOpaque(false);
        this.scrollPane.getViewport().setOpaque(false);
        this.scrollPane.setBorder(null);
    }


    /**
     * Crée un bouton représentant une ligne pour une quête donnée.
     * 
     * @param quest La quête concernée
     * 
     * @return Le bouton configuré contenant les informations de la quête
     * 
     * 
     * @since 0.2
     */
    private JButton createRowButton(Quest quest) {
        JButton wrapper = new HRSButtons("");
        this.setRowProperties(wrapper);
        wrapper.setLayout(new BorderLayout());

        JPanel block = new JPanel(new BorderLayout());

        if (quest.isCompleted()) {
            block.setOpaque(true);
            block.setBackground(HRSColors.GREEN);
        } else {
            block.setOpaque(false);
        }

        block.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(HRSColors.FM_TEXT_WHITE, 1, true),
            new EmptyBorder(10, 15, 10, 15)));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);

        JLabel descriptionLabel = HRSLabels.simpleLabel(HRSLanguages.Quest.getQuestDescription(quest.getId()), HRSColors.FM_TEXT_WHITE);
        JLabel advancementLabel = HRSLabels.simpleLabel(String.valueOf(quest.getAdvancement() + "/" + String.valueOf(quest.getObjective())), HRSColors.FM_TEXT_WHITE);
        advancementLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel rewardLabel = HRSLabels.simpleLabel(String.valueOf(quest.getReward() + "€"), HRSColors.FM_TEXT_WHITE);

        topPanel.add(descriptionLabel, BorderLayout.WEST);
        topPanel.add(advancementLabel, BorderLayout.CENTER);
        topPanel.add(rewardLabel, BorderLayout.EAST);

        block.add(topPanel);

        wrapper.add(block, BorderLayout.CENTER);

        return wrapper;
    }


    /**
     * Initialise la disposition générale du panel et de ses composants.
     * * @since 0.2
     */
    public void initLayout() {
        this.removeAll();
        this.setLayout(new BorderLayout());
        this.setBorder(new EmptyBorder(30, 50, 30, 50));

        this.header.add(this.titleLabel, BorderLayout.WEST);

        this.add(this.header, BorderLayout.NORTH);
        this.add(this.scrollPane, BorderLayout.CENTER);
        this.add(this.closeButton, BorderLayout.SOUTH);

        this.setPanelProperties(this.questsPanel);
    }

    // ==================== PROPRIETES ===================

    /**
     * Méthode pour définir les propriétés d'un JPanel.
     * 
     * @param panel Le JPanel à modifier
     * 
     * @since 0.2
     */
    private void setPanelProperties(JPanel panel) {
        panel.removeAll();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        for (Quest quest : QuestsManager.getQuests()) {
            JButton questButton = this.createRowButton(quest);
            questButton.setEnabled(quest.isCompleted());
            questButton.addActionListener(e -> this.eventClaimRewardButton(quest));
            panel.add(questButton);
        }
        
        panel.revalidate();
        panel.repaint();
    }


    /**
     * Définit les propriétés de dimension et de bordure pour une ligne de quête.
     * 
     * @param row Le bouton de ligne à configurer
     * 
     * 
     * @since 0.2
     */
    private void setRowProperties(JButton row) {
        row.setOpaque(false);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        row.setBorder(new EmptyBorder(5, 20, 5, 20));
    }

    // ==================== METHODES =====================


    /**
     * Met à jour les libellés textuels des composants selon la langue active.
     */
    public void updateTexts() {
        this.titleLabel.setText(HRSLanguages.Quest.getTitle());
        this.closeButton.setText(HRSLanguages.getCloseButtonText());
    }

    // ==================== EVENEMENTS =====================

    /**
     * Gère l'évênement du bouton "Fermer".
     * 
     * @since 0.2
     */
    private void eventCloseButton() {
        this.view.eventCloseButton();
    }


    /**
     * Gère l'événement de clic pour récupérer la récompense d'une quête terminée.
     * 
     * @param quest La quête dont la récompense doit être récupérée
     * 
     * 
     * @since 0.2
     */
    private void eventClaimRewardButton(Quest quest) {
        String description = HRSLanguages.Quest.getQuestDescription(quest.getId());
        JOptionPane.showMessageDialog(this, HRSLanguages.Quest.getQuestCompleteText(description, quest.getReward()));

        this.view.controller.claimQuest(quest);
        SoundController.playEffect("Achieve");
                
        this.initLayout();
    }
}
