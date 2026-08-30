package com.kahrs.view.visuals;

import java.awt.Color;

/**
 * Classe HRSColors pour gérer toutes les couleurs.
 * 
 * 
 * @since 0.1
 * 
 * @version 0.1
 */
public class HRSColors {
    
    // Bordures
    /** Couleur de bordure */public static final Color FM_BORDER_GREEN = new Color(80, 120, 80);

    // Boutons
    /** Couleur du contour */public static final Color FM_BUTTON_HOVER = new Color(50, 100, 50);
    /** Couleur de remplissage */public static final Color FM_BUTTON_NORMAL = new Color(30, 55, 30);

    // Fond
    /** Couleur de fond */public static final Color FM_DARK_GREEN = new Color(20, 40, 20);

    // Graphiques
    /** Couleur des graphiques */public static final Color FM_GRAPHICS = new Color(0, 0, 0, 160);
    
    // Texte
    /** Couleur du texte*/public static final Color FM_TEXT_WHITE = new Color(240, 240, 240);

    // Couleurs basiques
    /** Noir */public static final Color BLACK = new Color(0, 0, 0);
    /** Vert */public static final Color GREEN = new Color(0, 255, 0);
    /** Rouge */public static final Color RED = new Color(255, 0, 0);
    /** Blanc */public static final Color WHITE = new Color(255, 255, 255);

    // Couleur alphas
    /** Noir */public static final Color BLACK_ALPHA = new Color(0, 0, 0, 0);
    /** Vert clair */public static final Color GREEN_LIGHT_ALPHA = new Color(64, 200, 80, 120);
    /** Blanc */public static final Color WHITE_ALPHA = new Color(255, 255, 255, 60);

    // Barre de status
    /** Fond */public static final Color STATUS_BAR_GRAY = new Color(180, 180, 180);
    /** Progression */public static final Color STATUS_BAR_GREEN = new Color(30, 50, 30);
    /** Progression */public static final Color STATUS_BAR_GREEN_LIGHT = new Color(40, 200, 80);

    // Couleurs d'actions
    /** Annulation/retour (rouge) */public static final Color CANCEL = new Color(198, 40, 40);
    /** Validation (vert) */public static final Color VALIDATE = new Color(46, 125, 50);

    /** Constructeur privé pour résoudre warning de Javadoc */
    private HRSColors() {
    }
}
