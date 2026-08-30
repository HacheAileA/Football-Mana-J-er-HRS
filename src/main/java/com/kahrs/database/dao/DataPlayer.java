package com.kahrs.database.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.kahrs.database.DatabaseManager;
import com.kahrs.model.Player;

/**
 * Classe DataPlayer servant à la manipulation de la table players.
 * 
 * @author Hugo ARNAUD
 * 
 * 
 * @since 0.1
 * 
 * @version 0.1
 */
public class DataPlayer {

    // ==================== ATTRIBUTS =====================

    /** Nom de la table dans la base de données */
    protected static final String TABLE = "players";

    /** Constructeur privé pour résoudre warning de Javadoc */
    private DataPlayer() {
    }

    // ==================== METHODES =====================

    /**
     * Méthode pour insérer ou mettre à jour un Player.
     * 
     * @param player Le Player concerné
     * 
     * @return Un boolean permettant de savoir si l'opération a réussi ou non.
     * 
     * @throws SQLException Gestion de l'exception
     * 
     * 
     * @since 0.1
     */
    public static boolean addPlayer(Player player) throws SQLException {
        return (DataPlayer.getPlayer(player.getId()) == null || player.getId() == 0)
                ? DataPlayer.insertPlayer(player)
                : DataPlayer.updatePlayer(player);
    }

    /**
     * Méthode pour supprimer le joueur de la BDD
     * 
     * @param player Le joueur
     * @param team_id ID de son équipe
     * 
     * @throws SQLException Gestion de l'exception
     * 
     * @return Un boolean pour savoir si l'opération a réussi
     * 
     * @since 0.2
     */
    public static boolean removePlayer(Player player, int team_id) throws SQLException {
        DataContract.removeContract(DataContract.getContractPlayerId(player.getId(), team_id));
        
        String sql =
        "DELETE FROM " + DataPlayer.TABLE + " " +
        "WHERE id = ?";

        try (PreparedStatement pstmt = DatabaseManager.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, player.getId());

            return pstmt.executeUpdate() == 1;
        }
    }

    /**
     * Méthode pour récuperer un Player de la table players avec un player_id.
     * 
     * @param player_id L'id du Player
     * 
     * @return Un Player
     * 
     * @throws SQLException Gestion de l'exception
     * 
     * 
     * @since 0.1
     */
    private static Player getPlayer(int player_id) throws SQLException {
        Player player = null;

        String sql =
        "SELECT * FROM " + DataPlayer.TABLE + " " + 
        "WHERE id = ?;";

        try (PreparedStatement pstmt = DatabaseManager.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, player_id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    player = new Player(
                            rs.getInt("id"),
                            rs.getString("name"),
                            Player.Poste.valueOf(rs.getString("position")),
                            Player.Status.valueOf(rs.getString("state")),
                            rs.getInt("note"),
                            rs.getInt("value"),
                            rs.getInt("attack"),
                            rs.getInt("defense"),
                            rs.getInt("speed"),
                            rs.getInt("shoot"),
                            rs.getInt("pass"));
                }
            }
        }
        return player;
    }

    /**
     * Méthode pour récuperer tous les Player de la table players avec un team_id.
     * 
     * @param team_id L'id de la Team
     * 
     * @return Une ArrayList de Player
     * 
     * @throws SQLException Gestion de l'exception
     * 
     * 
     * @since 0.1
     */
    protected static ArrayList<Player> getPlayersTeamId(int team_id) throws SQLException {
        ArrayList<Player> players = new ArrayList<>();

        String sql =
        "SELECT DISTINCT p.* FROM " + DataPlayer.TABLE + " p " +
        "JOIN " + DataContract.TABLE + " c ON p.id = c.player_id " +
        "WHERE c.team_id = ?;";

        try (PreparedStatement pstmt = DatabaseManager.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, team_id);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    players.add(new Player(
                            rs.getInt("id"),
                            rs.getString("name"),
                            Player.Poste.valueOf(rs.getString("position")),
                            Player.Status.valueOf(rs.getString("state")),
                            rs.getInt("note"),
                            rs.getInt("value"),
                            rs.getInt("attack"),
                            rs.getInt("defense"),
                            rs.getInt("speed"),
                            rs.getInt("shoot"),
                            rs.getInt("pass")));
                }
            }
        }
        return players;
    }

    /**
     * Méthode pour récuperer tous les Players de la base (sauf ceux d'init).
     * 
     * @return Une ArrayList de tous les Players
     * 
     * @throws SQLException Gestion de l'exception
     * 
     * 
     * @since 0.1
     */
    public static ArrayList<Player> getAllPlayers() throws SQLException {
        ArrayList<Player> players = new ArrayList<>();

        String sql =
        "SELECT * FROM (" +
        "  SELECT DISTINCT ON (p.name, p.note, p.value) p.* FROM " + DataPlayer.TABLE + " p " +
        "  JOIN " + DataContract.TABLE + " c ON p.id = c.player_id " +
        "  JOIN " + DataTeam.TABLE + " t ON c.team_id = t.id " +
        "  WHERE t.id = -2" +
        ") AS p " +
        "ORDER BY p.note DESC, p.position ASC, p.name ASC;";

        try (PreparedStatement pstmt = DatabaseManager.getConnection().prepareStatement(sql)) {
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    players.add(new Player(
                            rs.getInt("id"),
                            rs.getString("name"),
                            Player.Poste.valueOf(rs.getString("position")),
                            Player.Status.valueOf(rs.getString("state")),
                            rs.getInt("note"),
                            rs.getInt("value"),
                            rs.getInt("attack"),
                            rs.getInt("defense"),
                            rs.getInt("speed"),
                            rs.getInt("shoot"),
                            rs.getInt("pass")));
                }
            }
        }
        return players;
    }

    /**
     * Méthode pour insérer un Player dans la table.
     * 
     * @param player Le Player à ajouter
     * 
     * @return Un boolean pour savoir si le Player a été inséré ou non
     * 
     * @throws SQLException Gestion de l'exception
     * 
     * 
     * @since 0.1
     */
    private static boolean insertPlayer(Player player) throws SQLException {
        String sql =
        "INSERT INTO " + DataPlayer.TABLE + " " +
        "(name, position, state, note, value, attack, defense, speed, shoot, pass) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " +
        "RETURNING id;";

        try (PreparedStatement pstmt = DatabaseManager.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, player.getName());
            pstmt.setObject(2, player.getPosition().name(), java.sql.Types.OTHER);
            pstmt.setObject(3, player.getStatus().name(), java.sql.Types.OTHER);
            
            pstmt.setInt(4, player.getNote());
            pstmt.setInt(5, player.getValue());
            pstmt.setInt(6, player.getAttack());
            pstmt.setInt(7, player.getDefense());
            pstmt.setInt(8, player.getSpeed());
            pstmt.setInt(9, player.getShoot());
            pstmt.setInt(10, player.getPass());

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    player.setId(rs.getInt("id"));
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Méthode pour mettre à jour un Player.
     * 
     * @param player Le Player à mettre à jour
     * 
     * @return Un boolean pour savoir si le Player a été mis à jour
     * 
     * @throws SQLException Gestion de l'exception
     * 
     * 
     * @since 0.1
     */
    /**
     * Méthode pour mettre à jour un Player.
     * @param player Le Player à mettre à jour
     * @return Un boolean pour savoir si le Player a été mis à jour
     * @throws SQLException Gestion de l'exception
     * @since 0.1
     */
    private static boolean updatePlayer(Player player) throws SQLException {
        String sql =
        "UPDATE " + DataPlayer.TABLE + " " +
        "SET name = ?, position = ?, state = ?, note = ?, value = ?, attack = ?, defense = ?, speed = ?, shoot = ?, pass = ? " +
        "WHERE id = ?;";

        try (PreparedStatement pstmt = DatabaseManager.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, player.getName());
            pstmt.setObject(2, player.getPosition().name(), java.sql.Types.OTHER);
            pstmt.setObject(3, player.getStatus().name(), java.sql.Types.OTHER);
            pstmt.setInt(4, player.getNote());
            pstmt.setInt(5, player.getValue());
            pstmt.setInt(6, player.getAttack());
            pstmt.setInt(7, player.getDefense());
            pstmt.setInt(8, player.getSpeed());
            pstmt.setInt(9, player.getShoot());
            pstmt.setInt(10, player.getPass());
            pstmt.setInt(11, player.getId());

            return pstmt.executeUpdate() > 0;
        }
    }
}
