package com.kahrs.view.panels;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.SwingConstants;

import com.kahrs.model.Player;
import com.kahrs.view.GameView;
import com.kahrs.view.visuals.HRSButtons;
import com.kahrs.view.visuals.HRSLanguages;

/**
 * Classe StadiumPanel gérant l'affichage de la composition de la Team.
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
public class StadiumPanel extends JPanel {

    // ==================== ATTRIBUTS ====================

    /** Image de fond du panel */
    private static BufferedImage BACKGROUND_IMAGE;

    /** La GameView principale */
    private GameView view;

    // Boutons
    /** Bouton pour revenir à l'écran d'accueil */
    private JButton closeButton;

    // ComboBox
    /** ComboBox pour le choix de la composition */
    private JComboBox<String> compoBox;

    // Panels
    /** Panel du stade */
    private JPanel topPanel;
    /** Panel de la composition */
    private JPanel centerPanel;

    // Images
    /** Image pour les attaquants */
    BufferedImage jerseyATT;
    /** Image pour les milieux */
    BufferedImage jerseyMIL;
    /** Image pour les défenseurs */
    BufferedImage jerseyDEF;
    /** Image pour les gardiens */
    BufferedImage jerseyGB;
    /** Image pour le poste vide */
    BufferedImage jerseyEmpty;

    // ================== CONSTRUCTEUR ==================

    /**
     * Constructeur de StadiumPanel avec la GameView principale.
     * 
     * @param view La GameView principale
     * 
     * @since 0.1
     */
    public StadiumPanel(GameView view) {
        this.view = view;

        BACKGROUND_IMAGE = this.view.loadImage("stadium/stadium_panel_background.png");
        this.jerseyATT = this.view.loadImage("stadium/stadium_panel_jersey_att.png");
        this.jerseyMIL = this.view.loadImage("stadium/stadium_panel_jersey_mil.png");
        this.jerseyDEF = this.view.loadImage("stadium/stadium_panel_jersey_def.png");
        this.jerseyGB = this.view.loadImage("stadium/stadium_panel_jersey_gb.png");
        this.jerseyEmpty = this.view.loadImage("stadium/stadium_panel_jersey_empty.png");

        this.createComponents();
    }

    /**
     * Instancie et configure les composants du panneau.
     */
    private void createComponents() {
        this.closeButton = new HRSButtons(HRSLanguages.getCloseButtonText());
        this.closeButton.addActionListener(e -> this.eventCloseButton());

        this.compoBox = new JComboBox<>(new String[] { "4-4-2", "4-3-3", "3-4-3", "5-4-1", "4-2-4", "5-3-2", "3-5-2" });
        this.compoBox.addActionListener(e -> this.eventResetCompo());

        this.topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        this.topPanel.setOpaque(false);
        this.topPanel.add(this.compoBox);

        this.centerPanel = new JPanel();
    }


    /**
     * Initialise la disposition graphique et assemble les composants principaux.
     */
    public void initlayout() {
        this.removeAll();
        this.setOpaque(false);
        this.setLayout(new BorderLayout());

        this.centerPanel = this.createCenterPanel();

        this.add(this.topPanel, BorderLayout.NORTH);
        this.add(this.centerPanel, BorderLayout.CENTER);
        this.add(this.closeButton, BorderLayout.SOUTH);
    }

    /**
     * Crée le conteneur central pour organiser les lignes de la formation.
     * @return Le JPanel configuré contenant les lignes de joueurs
     * @since 0.2
     */
    private JPanel createCenterPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 1, 0, 20));
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(150, 50, 80, 50));

        for (JPanel layout : this.createCompo()) {
            panel.add(layout);
        }

        return panel;
    }

    /**
     * Génère la liste des lignes représentant la composition de l'équipe sur le terrain.
     * @return Une liste de JPanel contenant les différentes lignes de l'équipe
     * @since 0.2
     */
    private ArrayList<JPanel> createCompo() {
        ArrayList<JPanel> compo = new ArrayList<>();

        int def = Character.getNumericValue(this.compoBox.getSelectedItem().toString().charAt(0));
        int mil = Character.getNumericValue(this.compoBox.getSelectedItem().toString().charAt(2));
        int att = Character.getNumericValue(this.compoBox.getSelectedItem().toString().charAt(4));

        ArrayList<Player> gbList = this.view.model.getTeam().getPlayersByPosition(Player.Poste.GB, true);
        ArrayList<Player> defList = this.view.model.getTeam().getPlayersByPosition(Player.Poste.DEF, true);
        ArrayList<Player> milList = this.view.model.getTeam().getPlayersByPosition(Player.Poste.MIL, true);
        ArrayList<Player> attList = this.view.model.getTeam().getPlayersByPosition(Player.Poste.ATT, true);

        compo.add(this.createLine(att, attList, Player.Poste.ATT, this.jerseyATT));
        compo.add(this.createLine(mil, milList, Player.Poste.MIL, this.jerseyMIL));
        compo.add(this.createLine(def, defList, Player.Poste.DEF, this.jerseyDEF));
        compo.add(this.createLine(1, gbList, Player.Poste.GB, this.jerseyGB));

        return compo;
    }


    /**
     * Crée un panel horizontal représentant une ligne de joueurs pour un poste donné.
     * 
     * @param columns Le nombre d'emplacements sur la ligne
     * @param players La liste des joueurs actuellement titulaires à ce poste
     * @param poste Le poste correspondant à la ligne
     * @param jerseyImage L'image du maillot associée à ce poste
     * 
     * 
     * @return Le JPanel représentant la ligne sur le terrain
     * 
     * 
     * @since 0.2
     */
    private JPanel createLine(int columns, ArrayList<Player> players, Player.Poste poste, BufferedImage jerseyImage) {
        JPanel line = new JPanel(new GridLayout(1, columns, 30, 0));
        line.setOpaque(false);

        for (int i = 0; i < columns; i++) {
            Player player = (i < players.size()) ? players.get(i) : null;
            JButton button = HRSButtons.invisibleButton(10, 10);
            button.setBorderPainted(false);

            button.setHorizontalTextPosition(SwingConstants.CENTER);

            if (player != null) {
                button.setIcon(new ImageIcon(jerseyImage));
                button.addActionListener(e -> this.eventPosteButton(player));
                String formattedName = formatPlayerName(player.getName());
                String labelText = "<html><center><b style='color:white; font-size:10px;'>" + formattedName + "</b><br>"
                     + "<span style='color:#FFD700; font-size:9px;'>★ " + player.getNote() + "</span></center></html>";
                button.setText(labelText);
            } else {
                button.setIcon(new ImageIcon(this.jerseyEmpty));
                button.addActionListener(e -> this.eventPosteButton(poste));
            }
            line.add(button);
        }

        return line;
    }

    /**
     * Met à jour les libellés textuels des boutons selon la langue active.
     * @since 0.2
     */
    public void updateTexts() {
        this.closeButton.setText(HRSLanguages.getCloseButtonText());
    }

    /**
     * Gère l'événement de fermeture de la vue tactique en sauvegardant la composition.
     * @since 0.2
     */
    private void eventCloseButton() {
        this.view.controller.saveComposition();
        this.view.eventCloseButton();
    }

    /**
     * Gère l'événement de clic sur un joueur titulaire pour effectuer un remplacement.
     * @param starter Le joueur titulaire qui va potentiellement sortir
     * @since 0.2
     */
    private void eventPosteButton(Player starter) {
        ArrayList<Player> substitutes = this.view.model.getTeam().getSubstitutes();

        Object[] choices = substitutes.stream().filter(p -> p.getPosition() == starter.getPosition()).map(Player::getName).toArray();

        if (choices.length == 0) {
            JOptionPane.showMessageDialog(this, HRSLanguages.Stadium.getEmptySubstitute());
            return;
        }

        String selectedName = (String) JOptionPane.showInputDialog(this, HRSLanguages.Stadium.getOptionText(), "",
                JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);

        if (selectedName != null) {
            Player substitute = substitutes.stream().filter(p -> p.getName().equals(selectedName)).findFirst().orElse(null);

            if (substitute != null) {
                this.view.model.getTeam().switchPlayers(starter, substitute);
                this.eventChangeCompo();
            }
        }
    }


    /**
     * Gère l'événement de clic sur un emplacement vide du terrain pour insérer un remplaçant.
     * 
     * @param poste Le poste ciblé par l'emplacement vide
     * 
     * 
     * @since 0.2
     */
    private void eventPosteButton(Player.Poste poste) {
        ArrayList<Player> substitutes = this.view.model.getTeam().getSubstitutes();

        Object[] choices = substitutes.stream().filter(p -> p.getPosition() == poste).map(Player::getName).toArray();

        if (choices.length == 0) {
            JOptionPane.showMessageDialog(this, HRSLanguages.Stadium.getEmptySubstitute());
            return;
        }

        String selectedName = (String) JOptionPane.showInputDialog(this, HRSLanguages.Stadium.getOptionText(), "",
                JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);

        if (selectedName != null) {
            Player substitute = substitutes.stream().filter(p -> p.getName().equals(selectedName)).findFirst().orElse(null);

            if (substitute != null) {
                this.view.model.getTeam().switchPlayers(null, substitute);
                this.eventChangeCompo();
            }
        }
    }


    /**
     * Gère la réinitialisation par défaut de la composition d'équipe.
     */
    private void eventResetCompo() {
        this.view.model.getTeam().resetStarters();
        this.view.controller.saveComposition();
        this.eventChangeCompo();
    }


    /**
     * Actualise graphiquement l'affichage du terrain après une modification de la formation tactique.
     * @since 0.2
     */
    private void eventChangeCompo() {
        this.centerPanel.removeAll();
        for (JPanel layout : this.createCompo()) {
            this.centerPanel.add(layout);
        }
        this.centerPanel.revalidate();
        this.centerPanel.repaint();
    }


    /**
    * Formate le nom du joueur en "P. Nom".
    * @param fullName Nom complet à formater.
    * @return Nom formaté en majuscules ou chaîne vide si nullou vide.
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
