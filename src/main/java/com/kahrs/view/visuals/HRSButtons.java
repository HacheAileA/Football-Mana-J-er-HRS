package com.kahrs.view.visuals;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.border.LineBorder;

import com.kahrs.controller.SoundController;
/**
 * Classe de gestion et initialisation des boutons de la view
 *
 * @author Hugo ARNAUD
 *
 * @since 0.1
 *
 * @version 0.2
 *
 */
public class HRSButtons extends JButton {

    /**
     * Constructeur de HRSButtons.
     *
     * @param text Le texte du bouton
     *
     *
     * @since 0.1
     */
    public HRSButtons(String text) {
        super(text);

        this.applyColor();
        this.applyFont();
        this.applyMouseListener();
        this.applySoundListener();
    }

    /**
     * Constructeur de HRSButtons.
     *
     * @param text Le texte du bouton
     * @param width Largeur du bouton
     * @param height Hauteur du bouton
     *
     *
     * @since 0.1
     */
    public HRSButtons(String text, int width, int height) {
        this(text);

        if (width > 0 && height > 0) {
            this.setPreferredSize(new Dimension(width, height));
        }
    }

    /**
     * Crée et retourne un bouton simple avec le texte spécifié,
     *
     * @param text Le texte à afficher sur le bouton
     * @return Une instance de JButton 
     * @since 0.2
     */
    public static JButton simpleButton(String text) {
        JButton button = new JButton(text);
        button.setOpaque(true);
        button.setContentAreaFilled(false);

        button.setFont(HRSFonts.SEGEO_UI);
        button.setForeground(HRSColors.FM_TEXT_WHITE);

        button.setHorizontalAlignment(JButton.CENTER);
        button.addActionListener(e -> SoundController.playEffect("click"));

        return button;
    }

    /**
     * Crée et retourne un bouton transparent et invisible de dimensions fixes.
     *
     * @param width Largeur pour le bouton invisible
     * @param height Hauteur pour le bouton invisible
     * @return Une instance de JButton
     * @since 0.2
     */
    public static JButton invisibleButton(int width, int height) {
        JButton button = new HRSButtons("", width, height);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);

        return button;
    }

    /**
     * Méthode pour appliquer les couleurs.
     * 
     * 
     * @since 0.2
     */
    private void applyColor() {
        this.setForeground(HRSColors.FM_TEXT_WHITE);
        this.setBackground(HRSColors.FM_BUTTON_NORMAL);
        this.setOpaque(true);
        this.setContentAreaFilled(true);
        this.setFocusPainted(false);
        this.setBorder(new LineBorder(HRSColors.FM_BORDER_GREEN, 2));
    }

    /**
     * Méthode pour appliquer la police.
     * 
     * 
     * @since 0.2
     */
    private void applyFont() {
        this.setFont(HRSFonts.SEGEO_UI);
    }

    /**
     * Méthode pour appliquer des lecteurs de souris.
     * 
     * 
     * @since 0.2
     */
    private void applyMouseListener() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(HRSColors.FM_BUTTON_HOVER);
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(HRSColors.FM_BUTTON_NORMAL);
            }
        });
    }
    
    /**
     * Méthode interne pour appliquer un écouteur d'action.
     * 
     * 
     * @since 0.2
     */
    private void applySoundListener() {
        this.addActionListener(e -> SoundController.playEffect("click"));
    }

    /**
     * Permet de modifier la couleur de fond manuellement tout en gardant le style HRS.
     * @param color La nouvelle couleur de fond issue de HRSColors (VALIDATE ou CANCEL)
     */
    public void setCustomBackground(java.awt.Color color) {
        this.setBackground(color);
    }
}
