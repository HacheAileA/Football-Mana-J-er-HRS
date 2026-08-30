package com.kahrs.view.visuals;

import java.awt.Color;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import javax.swing.JLabel;

/**
 * Classe de gestion et initialisation des Jlabels de la view
 *
 * @author Hugo ARNAUD
 *
 * @since 0.1
 *
 * @version 0.2
 */
public class HRSLabels extends JLabel {

    /**
     * Constructeur de HRSLabels.
     *
     * @param text Le texte du Label
     * 
     *
     * @since 0.1
     */
    public HRSLabels(String text) {
        super(text);

        this.applyColor();
        this.applyFont();
    }

    /**
     * Constructeur de HRSLabels.
     *
     * @param text Le texte du Label
     * @param alignement alignement du label
     *
     *
     * @since 0.1
     */
    public HRSLabels(String text, int alignement) {
        this(text);

        this.applyColor();
        this.setFont(HRSFonts.SEGEO_UI);
        this.setHorizontalAlignment(alignement);
    }

    /**
     * Méthode pour créer un label simple.
     * 
     * @param text Le texte du JLabel
     * @param color La couleur du JLabel
     * 
     * @return Un JLabel simple
     * 
     * 
     * @since 0.1
     */
    public static JLabel simpleLabel(String text, Color color) {
        JLabel label = new JLabel(text);

        label.setFont(HRSFonts.SEGEO_UI);

        if (color != null) {
            label.setForeground(color);
        } else {
            label.setForeground(HRSColors.FM_TEXT_WHITE);
        }

        label.setHorizontalAlignment(JLabel.CENTER);

        return label;
    }

    /**
     * Méthode pour formater l'argent du Manager (groupes de 3 chiffres).
     * 
     * @param money L'argent à formater
     * 
     * @return La chaîne formatée
     * 
     * 
     * @since 0.2
     */
    public static String getMoneyFormated(Long money) {
        DecimalFormat format = new DecimalFormat("#,###");
        format.setDecimalFormatSymbols(new DecimalFormatSymbols(HRSLanguages.getLocale()));

        return format.format(money);
    }


    /**
     * Méthode pour formater le texte d'un bouton d'infrastructure.
     * @param text Le nom de l'infrastructure
     * @param lvlText Le titre du niveau
     * @param level Le niveau actuel
     * @param expText Le titre de l'expérience
     * @param exp L'expérience actuelle
     * @return La chaîne formatée en HTML
     * @since 0.2
     */
    public static String getInfrastructureButtonTextFormated(String text, String lvlText, int level, String expText, int exp) {
        return "<html><center><b style='font-size:20px'>" +
                text +
                "</b><br><br>" + lvlText + level +
                "</b><br><br>" + expText + exp +
                "</center></html>";
    }

    /**
     * Méthode pour formater le texte d'un bouton d'infrastructure avec modification.
     * @param text Le nom de l'action
     * @param change La description de la modification
     * @return La chaîne formatée en HTML
     * @since 0.2
     */
    public static String getInfrastructureButtonTextFormated(String text, String change) {
        return "<html><center><b style='font-size:20px'>" +
                text +
                "</b><br><br>" + change +
                "</center></html>";
    }

    /**
     * Méthode pour appliquer les couleurs.
     * 
     * 
     * @since 0.2
     */
    private void applyColor() {
        this.setForeground(HRSColors.FM_TEXT_WHITE);
    }

    /**
     * Méthode pour appliquer la police.
     * 
     * 
     * @since 0.2
     */
    private void applyFont() {
        this.setFont(HRSFonts.IMPACT);
        this.setHorizontalAlignment(JLabel.CENTER);
    }
}
