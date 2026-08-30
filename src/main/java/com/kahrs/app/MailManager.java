package com.kahrs.app;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import com.kahrs.model.Mail;

/**
 * Classe MailManager gérant la logique des messages.
 * @author Sofyane HARISSE
 * @since 0.2
 * @version 0.2
 */
public class MailManager {

    // ==================== ATTRIBUTS ====================

    /** Chemin vers le fichier de sauvegarde JSON. */
    private static final File MAILS_FILE = new File("mails.json");

    /** Liste statique contenant tous les mails chargés en mémoire. */
    private static ArrayList<Mail> mails = new ArrayList<>();

    /** Instance pour l'archivage. */
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    // ================== CONSTRUCTEUR ==================

    /** Méthode d'initialisation des Mails
     * 
     * @since 0.2
     */
    static {
        MailManager.loadMails();
    }

    /** Constructeur privé pour résoudre warning de Javadoc */
    private MailManager() {
    }

    /**
     * Charge les mails depuis le fichier JSON vers la liste en mémoire.
     * @since 0.2
     */
    public static void loadMails() {
        if (!MAILS_FILE.exists()) {
            mails.add(0, new Mail("Bienvenue dans Football Mana-J-er", "Ceci est la messagerie, où vous recevrez des comptes rendus des matchs et achat de joueurs.", LocalDate.now().toString()));
            MailManager.saveMails();
        } else {
            try (FileReader reader = new FileReader(MailManager.MAILS_FILE)) {
                Type listType = new TypeToken<ArrayList<Mail>>() {}.getType();
                mails = GSON.fromJson(reader, listType);
                
                if (mails == null) {
                    mails = new ArrayList<>();
                }
            } catch (IOException e) {
                System.err.println("[ERREUR] Problème lors de la création des quêtes");
            }
        }
    }

    // ==================== ACCESSEURS ===================
    
    /**
     * Getter pour récupérer la liste complète des mails.
     * @return L'ArrayList des mails.
     * @since 0.2
     */
    public static ArrayList<Mail> getMails() {
        return MailManager.mails;
    }

    /**
     * Compte le nombre de messages non lus dans la boîte de réception.
     * @return Le nombre de mails ayant isRead à false.
     * @since 0.2
     */
    public static int getUnreadCount() {
        int count = 0;
        for (Mail m : mails) {
            if (!m.isRead()) {
                count++;
            }
        }
        return count;
    }

    // ==================== METHODES =====================

    /**
     * Sauvegarde la liste actuelle des mails dans le fichier JSON.
     * @since 0.2
     */
    public static void saveMails() {
        try (FileWriter writer = new FileWriter(MAILS_FILE)) {
            GSON.toJson(mails, writer);
        } catch (IOException e) {
            System.err.println("[ERREUR] Problème lors de l'enregistrement des mails");
        }
    }


    /**
     * Crée et envoie un nouveau mail, puis sauvegarde la liste.
     * Le mail est ajouté en haut de la liste.
     * @param subject Le sujet du mail
     * @param content Le contenu du message
     * @param date    La date actuelle
     * @since 0.2
     */
    public static void sendMail(String subject, String content, String date) {
        mails.add(0, new Mail(subject, content, date));
        MailManager.saveMails();
    }
}