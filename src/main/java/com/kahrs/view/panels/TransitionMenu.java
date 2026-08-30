package com.kahrs.view.panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import com.kahrs.app.UserConfig;
import com.kahrs.controller.SoundController;
import com.kahrs.view.GameView;
import com.kahrs.view.visuals.HRSColors;
import com.kahrs.view.visuals.HRSFonts;
import com.kahrs.view.visuals.HRSLanguages;

/**
 * Menu de transition informatif affiché au démarrage du jeu.
 * Il gère l'affichage d'un écran de chargement stylisé, effectue la
 * vérification
 * de l'existence d'un profil utilisateur local, et redirige vers le panel
 * approprié
 * (Menu Principal ou Création de Club).
 *
 * @author Sofyane Harisse
 * @version 0.1
 */
public class TransitionMenu extends JPanel {

    // ==================== ATTRIBUTS ====================

    /** La GameView principale contenant ce menu */
    private GameView view;

    /** Label affichant le statut technique actuel du chargement */
    private JLabel statusLabel;

    /** Barre de progression indiquant l'avancement du chargement */
    private JProgressBar progressBar;

    // ================== CONSTRUCTEUR ==================

    /**
     * Constructeur du TransitionMenu.
     * Initialise l'interface graphique avec les couleurs et polices du projet.
     *
     * @param view La GameView principale pour gérer la navigation
     */
    public TransitionMenu(GameView view) {
        this.view = view;
        this.setLayout(new BorderLayout());
        this.setBackground(HRSColors.FM_DARK_GREEN); // Fond vert sombre

        // --- HEADER : TITRE DU JEU ---
        JPanel topPanel = new JPanel(new GridBagLayout());
        topPanel.setOpaque(false);
        topPanel.setBorder(new EmptyBorder(60, 0, 0, 0));

        // Titre stylisé
        JLabel title = new JLabel("<html><center><font color='#40C850'>HRS</font><br>FOOTBALL MANAGER</center></html>");
        title.setFont(HRSFonts.SEGEO_UI_MOY);
        title.setForeground(HRSColors.WHITE);
        topPanel.add(title);
        this.add(topPanel, BorderLayout.CENTER);

        // LOGS ET BARRE DE PROGRESSION ---
        JPanel bottomPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(new EmptyBorder(20, 40, 80, 40));

        // Label de statut
        statusLabel = new JLabel(HRSLanguages.Transition.getStatusBarLabelText(), SwingConstants.CENTER);
        statusLabel.setForeground(HRSColors.STATUS_BAR_GRAY);
        statusLabel.setFont(HRSFonts.MONOSPACED);

        // Barre de progression
        progressBar = new JProgressBar();
        progressBar.setIndeterminate(true); // Animation en boucle au début
        progressBar.setForeground(HRSColors.STATUS_BAR_GREEN_LIGHT);
        progressBar.setBackground(HRSColors.STATUS_BAR_GREEN);
        progressBar.setBorderPainted(false);
        progressBar.setPreferredSize(new Dimension(300, 10));

        bottomPanel.add(statusLabel);
        bottomPanel.add(progressBar);
        this.add(bottomPanel, BorderLayout.SOUTH);
    }

    // ==================== METHODES ===================

    /**
     * Lance la séquence visuelle de chargement via un Timer Swing.
     * Cette méthode met à jour le label de statut et la barre de progression
     * pour simuler les étapes de connexion.
     */
    public void lancerSequence() {

        SoundController.playMusic("Loading");

        Timer timer = new Timer(700, null);
        final int[] etape = { 0 };

        timer.addActionListener(e -> {
            switch (etape[0]) {
                case 0:
                    statusLabel.setText(HRSLanguages.Transition.getStatusBarSequenceText(0));
                    break;
                case 1:
                    statusLabel.setText(HRSLanguages.Transition.getStatusBarSequenceText(1));
                    break;
                case 2:
                    statusLabel.setText(HRSLanguages.Transition.getStatusBarSequenceText(2));
                    break;
                case 3:
                    statusLabel.setText(HRSLanguages.Transition.getStatusBarSequenceText(3));
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(100);
                    break;
                case 4:
                    timer.stop();
                    SoundController.stopMusic();
                    verifierEtLancer();
                    break;
            }
            etape[0]++;
        });
        timer.start();
    }

    /**
     * Vérifie la présence d'un ID de manager dans la configuration locale.
     * Mene l'utilisateur vers le panel principal si un profil existe,
     * ou vers le panneau de création de profil le cas contraire.
     */
    private void verifierEtLancer() {
        try {
            int id = UserConfig.getManagerId();
            System.out.println("[DEBUG] Manager ID détecté au lancement : " + id);
            
            if (id != -1) { 
                this.view.model.initDatas();
                statusLabel.setText(HRSLanguages.Transition.getStatusBarCheckText(1));
                this.view.setPanel(this.view.getMainPanel());
                this.view.updateTexts();
            } else { 
                statusLabel.setText(HRSLanguages.Transition.getStatusBarCheckText(0));
                this.view.setPanel(this.view.getHomePanel());
            }
        } catch (SQLException e) {
            statusLabel.setText(HRSLanguages.Transition.getStatusBarErrorText());
            statusLabel.setForeground(HRSColors.RED);
            System.err.println("[ERREUR] Problème lors de la vérification avec la BDD");
        }
    }
}