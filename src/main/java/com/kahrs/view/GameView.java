package com.kahrs.view;

import java.io.IOException;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.imageio.ImageIO;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;

import com.kahrs.app.UserConfig;
import com.kahrs.controller.GameController;
import com.kahrs.controller.SoundController;
import com.kahrs.database.dao.DataManager;
import com.kahrs.model.GameModel;
import com.kahrs.model.Manager;
import com.kahrs.view.panels.*;
import com.kahrs.view.visuals.HRSLanguages;

/**
 * Classe GameView qui gère l'affichage.
 * 
 * @author Hugo ARNAUD
 * 
 * 
 * @since 0.1
 * 
 * @version 0.2
 */
public class GameView extends JFrame {

    // ==================== ATTRIBUTS ====================

    /** Taille X de la fenêtre */
    private final int X_SCREEN_SIZE = (int) this.getToolkit().getScreenSize().getWidth() / 2;
    /** Taille Y de la fenêtre */
    private final int Y_SCREEN_SIZE = (int) this.getToolkit().getScreenSize().getHeight();
    /** Position X de la fenêtre */
    private final int X_POSITION = (X_SCREEN_SIZE - this.getWidth()) / 2;
    /** Position Y de la fenêtre */
    private final int Y_POSITION = 0;

    /** Nom de la fenêtre JOptionPane */
    public String JOpTitle = "HRS - JOptionPane";

    /** Model du jeu */
    public GameModel model;

    /** Controleur du jeu */
    public GameController controller;

    /** Panel du Calendrier */
    private CalendarPanel calendarPanel;
    /** Panel du Championnat */
    private ChampionshipPanel championshipPanel;
    /** Panel des Players */
    private EffectivePanel effectivePanel;
    /** Panel de l'écran de démarrage du jeu */
    private HomePanel homePanel;
    /** Panel de gestion des Infrastructures */
    private InfrastructurePanel infrastructurePanel;
    /** Panel des Mails */
    private MailPanel mailPanel;
    /** Panel de l'écran principal */
    private MainPanel mainPanel;
    /** Panel du Marché */
    private MarketPanel marketPanel;
    /** Panel du Match */
    private MatchPanel matchPanel;
    /** Panel de choix du Match (bot ou PvP) */
    private MatchSettingsPanel matchSettingsPanel;
    /** Panel de création du Manager */
    private NewUserPanel newUserPanel;
    /** Panel des stats des Player */
    private PlayerPanel playerPanel;
    /** Panel des quêtes */
    private QuestsPanel questsPanel;
    /** Panel de paramétrages d'options du jeu */
    private SettingsPanel settingsPanel;
    /** Panel de composition de la Team */
    private StadiumPanel stadiumPanel;    

    /** Panel de transition */
    private TransitionMenu transitionMenu;

    // ================== CONSTRUCTEUR ==================

    /**
     * Constructeur de GameView.
     * 
     * @param model Le GameModel utilisé
     * 
     * 
     * @since 0.1
     */
    public GameView(GameModel model) {
        this.setModel(model);
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("[ERREUR] Problème lors de la création de la GameView");
        }

        this.createPanels();

        SwingUtilities.invokeLater(() -> {
            this.setTitle("Football Mana-J-er");

            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            this.setDefaultDisplay();
            this.setPanel(this.transitionMenu);
            this.transitionMenu.lancerSequence();

            this.setFocusable(true);
            this.setVisible(true);
            this.setAlwaysOnTop(true);
            this.toFront();
            this.requestFocus();
        });
    }

    // ==================== ACCESSEURS ===================

    /**
     * Méthode pour définir le GameModel.
     * 
     * @param model Le GameModel utilisé
     * 
     * @since 0.1
     */
    public void setModel(GameModel model) {
        this.model = model;
    }

    /**
     * Méthode pour retourner le GameModel.
     *
     * @return Le GameModel
     *
     * @since 0.1
     */
    public GameModel getModel() {
        return this.model;
    }

    /**
     * Méthode pour définir le GameController.
     * 
     * @param controller Le GameController utilisé
     * 
     * @since 0.1
     */
    public void setController(GameController controller) {
        this.controller = controller;
    }

    /**
     * Méthode pour retourner le GameController.
     *
     * @return Le GameController
     *
     * @since 0.1
     */
    public GameController getController() {
        return this.controller;
    }

    /**
     * Méthode pour retourner le championshipPanel.
     * 
     * @return Le championshipPanel
     * 
     * @since 0.2
     */
    public ChampionshipPanel getChampionshipPanel() {
        return this.championshipPanel;
    }

    /**
     * Méthode pour retourner le calendarPanel.
     * 
     * @return Le calendarPanel
     * 
     * @since 0.2
     */
    public CalendarPanel getCalendarPanel() {
        return this.calendarPanel;
    }

    /**
     * Méthode pour retourner le effectivePanel.
     * 
     * @return Le effectivePanel
     * 
     * @since 0.1
     */
    public EffectivePanel getEffectivePanel() {
        return this.effectivePanel;
    }

    /**
     * Méthode pour retourner le homePanel.
     * 
     * @return Le homePanel
     * 
     * @since 0.1
     */
    public HomePanel getHomePanel() {
        return this.homePanel;
    }

    /**
     * Méthode pour retourner le infrastructurePanel.
     * 
     * @return Le infrastructurePanel
     * 
     * @since 0.1
     */
    public InfrastructurePanel getInfrastructurePanel() {
        return this.infrastructurePanel;
    }

    /**
     * Méthode pour retourner le mailPanel
     * 
     * @return Le mailPanel
     * 
     * @since 0.2
     */
    public MailPanel getMailPanel() {
        return this.mailPanel;
    }

    /**
     * Méthode pour retourner le mainPanel.
     * 
     * @return Le mainPanel
     * 
     * @since 0.1
     */
    public MainPanel getMainPanel() {
        return this.mainPanel;
    }

    /**
     * Méthode pour retourner le marketPanel.
     * 
     * @return Le marketPanel
     * 
     * @since 0.1
     */
    public MarketPanel getMarketPanel() {
        return this.marketPanel;
    }

    /**
     * Méthode pour retourner le matchPanel.
     * 
     * @return Le matchPanel
     * 
     * @since 0.1
     */
    public MatchPanel getMatchPanel() {
        return this.matchPanel;
    }

    /**
     * Méthode pour retourner le matchSettingsPanel.
     * 
     * @return Le matchSettingsPanel
     * 
     * @since 0.1
     */
    public MatchSettingsPanel getMatchSettingsPanel() {
        return this.matchSettingsPanel;
    }

    /**
     * Méthode pour retourner le newUserPanel.
     * 
     * @return Le newUserPanel
     * 
     * @since 0.1
     */
    public NewUserPanel getNewUserPanel() {
        return this.newUserPanel;
    }

    /**
     * Méthode pour retourner le playerPanel.
     * 
     * @return Le playerPanel
     * 
     * @since 0.2
     */
    public PlayerPanel getPlayerPanel() {
        return this.playerPanel;
    }

    /**
     * Méthode pour retourner le questsPanel.
     * 
     * @return Le questsPanel
     * 
     * @since 0.2
     */
    public QuestsPanel getQuestsPanel() {
        return this.questsPanel;
    }

    /**
     * Méthode pour retourner le settingsPanel.
     * 
     * @return Le settingsPanel
     * 
     * @since 0.1
     */
    public SettingsPanel getSettingsPanel() {
        return this.settingsPanel;
    }

    /**
     * Méthode pour retourner le stadiumPanel.
     * 
     * @return Le stadiumPanel
     * 
     * @since 0.2
     */
    public StadiumPanel getStadiumPanel() {
        return this.stadiumPanel;
    }

    /**
     * Méthode pour retourner le transitionMenu.
     *
     * @return Le transitionMenu
     *
     * @since 0.1
     */
    public TransitionMenu getTransitionMenu() {
        return this.transitionMenu;
    }

    /**
     * Méthode pour définit les paramètres d'affichages par défaut de la fenêtre.
     * 
     * 
     * @since 0.1
     */
    private void setDefaultDisplay() {
        this.pack();
        this.setSize(X_SCREEN_SIZE, Y_SCREEN_SIZE);
        this.setResizable(false);
        this.setLocation(X_POSITION, Y_POSITION);
    }

    /**
     * Méthode pour changer le panel affiché.
     * 
     * @param panel Le panel à afficher
     * 
     * 
     * @since 0.1
     */
    public void setPanel(JPanel panel) {

        SoundController.stopMusic();

        this.setContentPane(panel);
        this.refresh();

        if (panel instanceof HomePanel) {
            SoundController.playMusic("Menu");
        } else if (panel instanceof MainPanel) {
            SoundController.playMusic("Menu_game");
        }

    }

    // ==================== METHODES =====================

    /**
     * Méthode pour mettre à jour l'affichage.
     * 
     * 
     * @since 0.1
     */
    private void refresh() {
        this.revalidate();
        this.repaint();
    }

    /**
     * Méthode pour mettre à jour le texte affiché.
     * 
     * 
     * @since 0.2
     */
    public void updateTexts() {
        this.newUserPanel.updateTexts();
        this.homePanel.updateTexts();
        this.settingsPanel.updateTexts();

        if (this.model.getTeam() != null) {
            this.effectivePanel.updateTexts();
            this.infrastructurePanel.updateTexts();
            this.mainPanel.updateTexts();
            this.marketPanel.updateTexts();
            this.matchSettingsPanel.updateTexts();
            this.playerPanel.updateTexts();
            this.questsPanel.updateTexts();
            this.stadiumPanel.updateTexts();
        }

        if (this.model.getChampionship() != null) {
            this.championshipPanel.updateTexts();
        }
    }

    /**
     * Méthode pour créer les panels de l'interface.
     * 
     * 
     * @since 0.1
     */
    private void createPanels() {
        this.transitionMenu = new TransitionMenu(this);

        this.championshipPanel = new ChampionshipPanel(this, this.model.getChampionship());
        this.effectivePanel = new EffectivePanel(this);
        this.homePanel = new HomePanel(this);
        this.infrastructurePanel = new InfrastructurePanel(this);
        this.calendarPanel = new CalendarPanel(this);
        this.mailPanel = new MailPanel(this);
        this.mainPanel = new MainPanel(this);
        this.marketPanel = new MarketPanel(this);
        this.matchPanel = new MatchPanel(this);
        this.matchSettingsPanel = new MatchSettingsPanel(this);
        this.newUserPanel = new NewUserPanel(this);
        this.playerPanel = new PlayerPanel(this);
        this.questsPanel = new QuestsPanel(this);
        this.settingsPanel = new SettingsPanel(this);
        this.stadiumPanel = new StadiumPanel(this);

        HRSLanguages.setView(this);
    }

    /**
     * Méthode pour montrer le classement des managers
     * (nom, victoires, nuls, défaites)
     *
     *
     * @return le classement des managers
     *
     * @since 0.1
     */
    public String showRanking() {
        ArrayList<Manager> managers = null;
        try {
            managers = DataManager.getTopManagers();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (managers == null || managers.size() == 0) {
            return "Aucunes données";
        }

        String res = "";
        for (int i = 0; i < managers.size(); i++) {
            Manager manager = managers.get(i);
            res += """
                    %d. %s | (%d wins, %d draws, %d loses)\n
                    """.formatted(
                    i+1,
                    manager.getName(),
                    manager.getWins(),
                    manager.getDraws(),
                    manager.getLoses());
        }

        return res;
    }

    /**
     * Méthode pour charger une image.
     * 
     * @param name Le nom de l'image
     * 
     * @return L'image chargée
     * 
     * @since 0.2
     */
    public BufferedImage loadImage(String name) {
        try {
            URL url = getClass().getResource("/image/" + name);
            if (url != null) {
                return ImageIO.read(url);
            }
        } catch (IOException e) {
            System.err.println("[ERREUR] Impossible de charger /image/" + name + " : " + e.getMessage());
        }
        return null;
    }

    /**
     * Méthode pour déclencher l'événement du bouton de fermeture.
     * 
     * @since 0.2
     */
    public void eventCloseButton() {
        this.eventCloseButton("");
    }


    /**
     * Méthode pour fermer un panel spécifique et rediriger l'utilisateur.
     * 
     * @param panel Le nom du panel à fermer
     * 
     * 
     * @since 0.2
     */
    public void eventCloseButton(String panel) {
        switch (panel) {
            case "Effective":
                this.effectivePanel.updateTexts();
                this.setPanel(this.getEffectivePanel());
                break;
            case "Settings":
                if (UserConfig.getManagerId() == -1) {
                    this.homePanel.updateTexts();
                    this.setPanel(this.getHomePanel());
                    break;
                }
            default:
                this.mainPanel.updateTexts();
                this.setPanel(this.getMainPanel());
                break;
        }
    }
}
