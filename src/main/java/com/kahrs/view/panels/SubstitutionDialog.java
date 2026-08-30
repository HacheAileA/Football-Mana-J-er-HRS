package com.kahrs.view.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.kahrs.model.Contract;
import com.kahrs.model.Match;
import com.kahrs.model.Player;
import com.kahrs.model.Team;
import com.kahrs.view.GameView;
import com.kahrs.view.visuals.HRSButtons;
import com.kahrs.view.visuals.HRSColors;
import com.kahrs.view.visuals.HRSLanguages.Substitution;

/**
 * Classe SubstitutionDialog gérant l'interface des remplacements.
 * Cette fenêtre permet au coach de modifier sa composition
 * en filtrant les joueurs par poste et en vérifiant la validité des contrats.
 * @author Sofyane HARISSE
 * @version 0.1
 */
public class SubstitutionDialog extends JDialog {

    /** Vue principale */
    private GameView view;
    
    /** L'équipe dont on gère l'effectif. */
    private Team team;
    
    /** Le match en cours pour le suivi du nombre de changements. */
    private Match match;
    
    /** Menu déroulant des titulaires présents sur le terrain. */
    private JComboBox<Player> comboStarters;
    
    /** Menu déroulant des remplaçants disponibles sur le banc. */
    private JComboBox<Player> comboSubs;
    
    /** Label affichant le nombre de remplacements restants. */
    private JLabel lblRestants;
    
    /** Bouton de validation du changement. */
    private HRSButtons btnRemplacer;

    /** Liste des remplaçants */
    private ArrayList<Player> subPlayers;

    /** Nombre de changements maximal */
    private final int nbChangementsMax = 3;

    /**
     * Constructeur de la fenêtre de remplacement.
     * @param view La fenêtre parente.
     * @param match  L'instance du match en cours.
     */
    public SubstitutionDialog(GameView view, Match match) {
        super(view, Substitution.getFrameTitle(), true);
        this.view = view;
        this.team = this.view.model.getTeam();
        this.match = match;
        this.subPlayers = new ArrayList<>();
        
        this.setSize(650, 400);
        this.setLocationRelativeTo(this.view);
        this.setLayout(new BorderLayout(15, 15));
        this.getContentPane().setBackground(HRSColors.FM_DARK_GREEN);
        
        initComponents();
    }

    /**
     * Initialise les composants graphiques de la fenêtre.
     */
    private void initComponents() {
        // --- HEADER ---
        JLabel lblTitle = new JLabel(Substitution.getTitle(), SwingConstants.CENTER);
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
        lblTitle.setForeground(HRSColors.FM_TEXT_WHITE);
        lblTitle.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
        this.add(lblTitle, BorderLayout.NORTH);

        // --- CENTER ---
        JPanel pnlCenter = new JPanel(new GridLayout(2, 2, 20, 30));
        pnlCenter.setOpaque(false);
        pnlCenter.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JLabel lblOut = new JLabel(Substitution.getPlayerOffText(), SwingConstants.RIGHT);
        lblOut.setForeground(Color.LIGHT_GRAY);
        JLabel lblIn = new JLabel(Substitution.getPlayerOnText(), SwingConstants.RIGHT);
        lblIn.setForeground(Color.LIGHT_GRAY);

        comboStarters = new JComboBox<>(team.getStarters().toArray(new Player[0]));
        comboSubs = new JComboBox<>();

        comboStarters.addActionListener(e -> rafraichirListeRemplacants());

        pnlCenter.add(lblOut);
        pnlCenter.add(comboStarters);
        pnlCenter.add(lblIn);
        pnlCenter.add(comboSubs);
        
        this.add(pnlCenter, BorderLayout.CENTER);

        // --- FOOTER ---
        JPanel pnlFooter = new JPanel(new BorderLayout());
        pnlFooter.setOpaque(false);
        pnlFooter.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        lblRestants = new JLabel(Substitution.getRemainingChangesText() + (this.nbChangementsMax - match.getSubsDone()));
        lblRestants.setForeground(Color.YELLOW);
        lblRestants.setFont(new Font("SansSerif", Font.BOLD, 15));

        JPanel pnlButtons = new JPanel();
        pnlButtons.setOpaque(false);

        btnRemplacer = new HRSButtons(Substitution.getConfirmText());
        btnRemplacer.setCustomBackground(HRSColors.VALIDATE); 
        
        HRSButtons btnReprendre = new HRSButtons(Substitution.getResumeText());
        btnReprendre.setCustomBackground(HRSColors.CANCEL); 

        btnRemplacer.addActionListener(e -> effectuerRemplacement());
        btnReprendre.addActionListener(e -> this.dispose());

        pnlButtons.add(btnRemplacer);
        pnlButtons.add(btnReprendre);

        pnlFooter.add(lblRestants, BorderLayout.WEST);
        pnlFooter.add(pnlButtons, BorderLayout.EAST);

        this.add(pnlFooter, BorderLayout.SOUTH);

        rafraichirListeRemplacants();
    }

    /**
     * Filtre la liste des remplaçants pour n'afficher que ceux qui 
     * correspondent au poste du joueur sélectionné et possédant un contrat valide.
     */
    private void rafraichirListeRemplacants() {
        Player selectionne = (Player) comboStarters.getSelectedItem();
        comboSubs.removeAllItems();

        if (selectionne != null) {
            boolean possible = false;
            for (Player p : team.getSubstitutes()) {
                Contract contract = team.getContractPlayerId(p.getId());
                if (p.getPosition() == selectionne.getPosition() && !this.subPlayers.contains(p) && (contract != null && !contract.isExpired())) {
                    comboSubs.addItem(p);
                    possible = true;
                }
            }
            
            btnRemplacer.setEnabled(possible && match.getSubsDone() < 3);
           
        }
    }

    /**
     * Exécute la logique de remplacement : échange les joueurs dans l'équipe,
     * décrémente la durée du contrat de l'entrant et met à jour l'interface.
     */
    private void effectuerRemplacement() {
        Player starter = (Player) comboStarters.getSelectedItem();
        Player sub = (Player) comboSubs.getSelectedItem();

        if (starter != null && sub != null) {
            this.view.controller.getMatchController().effectuerRemplacement(starter, sub, match);

            comboStarters.removeItem(starter);
            comboStarters.addItem(sub);
            
            rafraichirListeRemplacants();
            
            lblRestants.setText(Substitution.getRemainingChangesText() + (this.nbChangementsMax - match.getSubsDone()));
            JOptionPane.showMessageDialog(this, Substitution.getSuccessText());

            if (match.getSubsDone() >= this.nbChangementsMax) {
                btnRemplacer.setEnabled(false);
                comboStarters.setEnabled(false);
                comboSubs.setEnabled(false);
            }
        }
    }
}