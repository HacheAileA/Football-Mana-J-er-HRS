package com.kahrs.database;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

/**
 * Classe DatabaseManager permettant d'intéragir avec la base de données.
 * 
 * @author Hugo ARNAUD
 * 
 * 
 * @since 0.1
 * 
 * @version 0.1
 */
public class DatabaseManager {

    // ==================== ATTRIBUTS ====================

    /** Connexion à la base de données */
    static final Connection CONN;

    /** Charger les variables d'environnement */
    static final Dotenv DOTENV;

    /** Charger les paramètres de connexion publique */
    static final ResourceBundle CONFIG;

    // ================== INITIALISATION =================

    /**
     * Bloc permettant d'initialiser les attributs (car besoin de gérer les
     * exceptions).
     * 
     * 
     * @since 0.1
     */
    static {
        try {
            DOTENV = Dotenv.configure().ignoreIfMissing().load();
            CONFIG = ResourceBundle.getBundle("config");

            String url = DOTENV.get("DB_URL_SUPABASE");
            if (url == null) {url = System.getenv("DB_URL_SUPABASE");}
            if (url == null) {url = CONFIG.getString("DB_URL_SUPABASE");}

            String user = DOTENV.get("DB_USER_SUPABASE");
            if (user == null) {user = System.getenv("DB_USER_SUPABASE");}
            if (user == null) {user = CONFIG.getString("DB_USER_SUPABASE");}

            String password = DOTENV.get("DB_PASSWORD_SUPABASE");
            if (password == null) {password = System.getenv("DB_PASSWORD_SUPABASE");}
            if (password == null) {password = CONFIG.getString("DB_PASSWORD_SUPABASE");}

            if (url != null && !url.contains("prepareThreshold=0")) {
                url += (url.contains("?") ? "&" : "?") + "prepareThreshold=0";
            }

            if (user != null && password != null && !user.isEmpty()) {
                CONN = DriverManager.getConnection(url, user, password);
            } else {
                CONN = DriverManager.getConnection(url);
            }
            System.out.println("[DEBUG] Connection established");
            DatabaseManager.syncIDs();
        } catch (SQLException e) {
            throw new RuntimeException("[DEBUG] Connection failed", e);
        }
    }

    /** Constructeur privé pour résoudre warning de Javadoc */
    private DatabaseManager() {
    }

    // ==================== ACCESSEURS ===================

    /**
     * Getter pour récupérer le lien de connexion
     * 
     * @return La connexion
     * 
     * @since 0.1
     */
    public static Connection getConnection() {
        return CONN;
    }

    // ==================== METHODES =====================

    /**
     * Méthode pour resynchroniser les séquences des tables.
     * 
     * 
     * @since 0.1
     */
    private static void syncIDs() {
        String[] tables = {"contracts", "managers", "matchs", "players", "teams"};
        for (String table : tables) {
            String sql = """
                    SELECT setval(pg_get_serial_sequence('public.%s', 'id'),
                    COALESCE((SELECT MAX(id) FROM %s), 1));
                    """.formatted(
                    table,
                    table);
            try (Statement stmt = CONN.createStatement()) {
                stmt.execute(sql);
            } catch (SQLException e) {
                System.err.println("[ERREUR] Impossible de synchroniser la table " + table + " : " + e.getMessage());
            }
        }
    }
}
