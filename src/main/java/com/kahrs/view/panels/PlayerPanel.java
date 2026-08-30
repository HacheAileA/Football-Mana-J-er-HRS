package com.kahrs.view.panels;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.SpiderWebPlot;
import org.jfree.data.category.DefaultCategoryDataset;

import com.kahrs.model.Player;
import com.kahrs.view.GameView;
import com.kahrs.view.visuals.HRSButtons;
import com.kahrs.view.visuals.HRSColors;
import com.kahrs.view.visuals.HRSLanguages;

/**
 * Classe PlayerPanel gérant l'affichage des stats d'un Player sous la forme d'un diagramme de Kiviat.
 * 
 * @author Hugo ARNAUD
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
public class PlayerPanel extends JPanel {

    // ==================== ATTRIBUTS ====================

    /** La GameView principale */
    private GameView view;

    // Boutons
    /** Bouton pour revenir à l'écran d'accueil */
    private JButton closeButton;

    // ================== CONSTRUCTEUR ==================

    /**
     * Constructeur de PlayerPanel avec la GameView principale.
     * 
     * @param view La GameView principale
     * 
     * @since 0.2
     */
    public PlayerPanel(GameView view) {
        this.view = view;

        this.setBackground(HRSColors.FM_DARK_GREEN);

        this.createComponents();
    }

    // ==================== METHODES =====================

    /**
     * Instancie et configure les composants graphiques du panel.
     * @since 0.2
     */
    private void createComponents() {
        this.closeButton = new HRSButtons(HRSLanguages.getCloseButtonText());
        this.closeButton.addActionListener(e -> this.eventCloseButton());
    }

    /**
     * Initialise la disposition du panel pour le joueur spécifié.
     * @param player Le joueur dont les statistiques doivent être affichées
     * @since 0.2
     */
    public void initLayout(Player player) {
        this.removeAll();
        this.setLayout(new BorderLayout());

        this.add(this.createGraphics(player), BorderLayout.CENTER);
        this.add(this.closeButton, BorderLayout.SOUTH);
    }

    /**
     * Crée le panel contenant le graphique des statistiques du joueur.
     * 
     * @param player Le joueur concerné par le graphique
     * @return Le JPanel contenant le graphique configuré
     * 
     * 
     * @since 0.2
     */
    public JPanel createGraphics(Player player) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(HRSColors.FM_DARK_GREEN);

        DefaultCategoryDataset dataset = this.createDataset(player);
        SpiderWebPlot plot = this.createWebPlot(dataset);
        JFreeChart design = this.createDesign(player.getName(), player.getPosition().name(), plot);
        ChartPanel chart = this.createChart(design);

        panel.add(chart, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Méthode pour ajouter les valeurs à afficher.
     * 
     * @param player Le Player
     * 
     * @return Un ensemble de valeurs
     * 
     * @since 0.2
     */
    private DefaultCategoryDataset createDataset(Player player) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        String fakeSpeed = HRSLanguages.Player.getStatText("speed", player.getSpeed());
        String fakeShoot = HRSLanguages.Player.getStatText("shoot", player.getShoot());
        String fakePass = HRSLanguages.Player.getStatText("pass", player.getPass());
        String fakeAttack = HRSLanguages.Player.getStatText("attack", player.getAttack());
        String fakeDefense = HRSLanguages.Player.getStatText("defense", player.getDefense());

        for (int i = 20; i <= 100; i += 20) {
            String gradName = "" + i;
            dataset.addValue(i, gradName, fakeSpeed);
            dataset.addValue(i, gradName, fakeShoot);
            dataset.addValue(i, gradName, fakePass);
            dataset.addValue(i, gradName, fakeAttack);
            dataset.addValue(i, gradName, fakeDefense);
        }

        dataset.addValue(player.getSpeed(), player.getName(), fakeSpeed);
        dataset.addValue(player.getShoot(), player.getName(), fakeShoot);
        dataset.addValue(player.getPass(), player.getName(), fakePass);
        dataset.addValue(player.getAttack(), player.getName(), fakeAttack);
        dataset.addValue(player.getDefense(), player.getName(), fakeDefense);

        return dataset;
    }

    /**
     * Méthode pour créer le diagramme avec un ensemble de valeurs.
     * 
     * @param dataset L'ensemble de valeurs
     * 
     * @return Le diagramme
     * 
     * @since 0.2
     */
    private SpiderWebPlot createWebPlot(DefaultCategoryDataset dataset) {
        SpiderWebPlot webPlot = new SpiderWebPlot(dataset);
        webPlot.setMaxValue(100.0);
        webPlot.setBackgroundPaint(HRSColors.FM_DARK_GREEN);
        webPlot.setLabelPaint(HRSColors.FM_TEXT_WHITE);

        for (int i = 0; i < 5; i++) {
            webPlot.setSeriesPaint(i, HRSColors.BLACK_ALPHA);
            webPlot.setSeriesOutlinePaint(i, HRSColors.WHITE_ALPHA);
        }
        
        webPlot.setSeriesPaint(5, HRSColors.GREEN_LIGHT_ALPHA);
        webPlot.setSeriesOutlinePaint(5, HRSColors.FM_TEXT_WHITE);

        return webPlot;
    }

    /**
     * Méthode pour créer le design.
     * 
     * @param name      Le nom du Player
     * @param position  Le Poste du Player
     * @param plot      Le diagramme
     * 
     * @return          Le design avec un titre et un diagramme
     * 
     * @since 0.2
     */
    private JFreeChart createDesign(String name, String position, SpiderWebPlot plot) {
        JFreeChart design = new JFreeChart(name + " (" + position + ")", JFreeChart.DEFAULT_TITLE_FONT, plot, false);
        design.setBackgroundPaint(HRSColors.FM_DARK_GREEN);
        design.getTitle().setPaint(HRSColors.WHITE); 

        return design;
    }

    /**
     * Encapsule le graphique JFreeChart dans un conteneur ChartPanel.
     * @param design Le modèle JFreeChart configuré
     * 
     * @return Le ChartPanel prêt à l'affichage
     * 
     * 
     * @since 0.2
     */
    private ChartPanel createChart(JFreeChart design) {
        ChartPanel chart = new ChartPanel(design);
        chart.setBackground(HRSColors.FM_DARK_GREEN);

        return chart;
    }

    /**
     * Met à jour les libellés textuels des boutons selon la langue active.
     * * @since 0.2
     */
    public void updateTexts() {
        this.closeButton.setText(HRSLanguages.getCloseButtonText());
    }

    /**
     * Gère l'évênement du bouton "Fermer".
     * 
     * @since 0.2
     */
    private void eventCloseButton() {
        this.view.eventCloseButton("Effective");
    }
}
