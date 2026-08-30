package com.kahrs.view.visuals;

import java.awt.Font;

/**
 * Classe HRSFonts pour gérer toutes les polices.
 * 
 * 
 * @since 0.1
 * 
 * @version 0.1
 */
public class HRSFonts {

    // Petites
    /** Monospaced */public static final Font MONOSPACED = new Font("Monospaced", Font.PLAIN, 12);
    /** Segeo UI */public static final Font SEGEO_UI = new Font("Segeo UI ", Font.BOLD, 18);

    // Moyenne
    /** Segeo UI */public static final Font SEGEO_UI_MOY = new Font("Segeo UI", Font.BOLD, 28);

    // Grandes
    /** Impact */public static final Font IMPACT = new Font("Impact", Font.BOLD, 64);

    /** Constructeur privé pour résoudre warning de Javadoc */
    private HRSFonts() {
    }
}

