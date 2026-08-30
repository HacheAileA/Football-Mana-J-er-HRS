package com.kahrs.database.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.kahrs.database.DatabaseManager;
import com.kahrs.model.Match;

/**
 * Classe DataMatch servant à la manipulation de la table matchs.
 * 
 * @author Hugo ARNAUD
 * 
 * 
 * @since 0.1
 * 
 * @version 0.1
 */
public class DataMatch {

    // ==================== ATTRIBUTS =====================

    /** Nom de la table dans la base de données */
    protected static final String TABLE = "matchs";

    /** Constructeur privé pour résoudre warning de Javadoc */
    private DataMatch() {
    }

    // ==================== METHODES =====================
    /**
     * Méthode pour insérer un Match.
     * 
     * @param match Le Match concerné
     * 
     * @return Un boolean permettant de savoir si l'opération a réussi ou non.
     * 
     * @throws SQLException Gestion de l'exception
     * 
     * 
     * @since 0.1
     */
    public static boolean addMatch(Match match) throws SQLException {
        if (match.getId() == 0) {
            return DataMatch.insertMatch(match);
        }
        return false;
    }

    /**
     * Méthode pour insérer un Match dans la table.
     * 
     * @param match Le Match à ajouter
     * 
     * @return Un boolean pour savoir si le Match a été inséré ou non
     * 
     * @throws SQLException Gestion de l'exception
     * 
     * 
     * @since 0.1
     */
    private static boolean insertMatch(Match match) throws SQLException {
        String sql =
        "INSERT INTO " + DataMatch.TABLE + " " +
        "(home, away, home_score, away_score, date) " +
        "VALUES (?, ?, ?, ?, ?) " +
        "RETURNING id;";

        try (PreparedStatement pstmt = DatabaseManager.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, match.getHomeId());
            pstmt.setInt(2, match.getAwayId());
            pstmt.setInt(3, match.getScoreHome());
            pstmt.setInt(4, match.getScoreAway());
            pstmt.setDate(5, java.sql.Date.valueOf(match.getDate()));

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    match.setId(rs.getInt("id"));
                    return true;
                }
            }
        }
        return false;
    }
}
