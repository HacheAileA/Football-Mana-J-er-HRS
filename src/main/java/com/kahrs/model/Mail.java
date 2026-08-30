package com.kahrs.model;

/**
 * Classe Mail représentant un message électronique.
 * @author Sofyane HARISSE
 * @since 0.1
 * @version 0.1
 */
public class Mail {

    // ==================== ATTRIBUTS ====================

    /** Sujet du message */
    private String subject;

    /** Contenu détaillé du message */
    private String content;

    /** État de lecture (true si lu, false sinon) */
    private boolean isRead;

    /** Date de réception du message */
    private String date;


    // ================== CONSTRUCTEUR ==================

    /**
     * Constructeur de Mail pour créer un nouveau message.
     * Par défaut, le message est marqué comme non lu.
     *
     * @param subject Le sujet du mail
     * @param content Le corps du message
     * @param date    La date de réception
     * * @since 0.1
     */
    public Mail(String subject, String content, String date) {
        this.subject = subject;
        this.content = content;
        this.date = date;
        this.isRead = false;
    }

// ==================== ACCESSEURS ===================

    /**
     * Méthode pour récupérer le sujet du mail.
     * @return Le sujet
     */
    public String getSubject() { 
        return subject; 
    }

    /**
     * Méthode pour récupérer le contenu du mail.
     * @return Le contenu
     */
    public String getContent() { 
        return content; 
    }

    /**
     * Méthode pour vérifier si le mail a été lu.
     * @return true si lu, false sinon
     */
    public boolean isRead() { 
        return isRead; 
    }

    /**
     * Setter pour modifier l'état de lecture du mail.
     * @param read Le nouvel état de lecture
     */
    public void setRead(boolean read) { 
        isRead = read; 
    }

    /**
     * Méthode pour récupérer la date du mail.
     * @return La date sous forme de String
     */
    public String getDate() { 
        return date; 
    }

    // ==================== METHODES =====================

    /**
     * Retourne une représentation textuelle du mail pour l'affichage et ajoute un indicateur visuel si le mail n'est pas lu.
     * * @return Une chaîne de caractères formatée pour la liste
     */
    @Override
    public String toString() {
        String prefix = isRead() ? "    " : "🔵 "; 
        return prefix + subject; 
    }
}