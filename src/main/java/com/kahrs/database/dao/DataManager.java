package com.kahrs.database.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.kahrs.database.DatabaseManager;
import com.kahrs.model.Manager;
import com.kahrs.model.Team;

/**
 * Classe DataManager servant à la manipulation de la table managers.
 * 
 * @author Hugo ARNAUD
 * 
 * 
 * @since 0.1
 * 
 * @version 0.1
 */
public class DataManager {

    // ==================== ATTRIBUTS =====================

    /** Nom de la table dans la base de données */protected static final String TABLE = "managers";

    /** Constructeur privé pour résoudre warning de Javadoc */
    private DataManager() {
    }

    // ==================== METHODES =====================

    /**
     * Méthode pour insérer ou mettre à jour un Manager.
     * 
     * @param manager Le Manager concerné
     * 
     * @return Un boolean permettant de savoir si l'opération a réussi ou non
     * 
     * @throws SQLException Gestion de l'exception
     * 
     * 
     * @since 0.1
     */
    public static boolean addManager(Manager manager) throws SQLException {
        return (DataManager.getManager(manager.getId()) == null || (manager.getId() == 0))
                ? DataManager.insertManager(manager)
                : DataManager.updateManager(manager);
    }

    /**
     * Méthode pour créer un nouveau Manager.
     * 
     * @param managerName Le nom du Manager
     * @param teamName    Le nom de la Team
     * 
     * @return Le Manager créé
     * 
     * @throws SQLException Gestion de l'exception
     * 
     * 
     * @since 0.1
     */
    public static Manager createNewManager(String managerName, String teamName) throws SQLException {
        Team newTeam = DataTeam.createNewTeam(teamName);

        Manager newManager = new Manager(managerName, newTeam.getId());
        DataManager.addManager(newManager);

        return newManager;
    }

    /**
     * Méthode pour récuperer Manager de la table managers avec un manager_id.
     * 
     * @param manager_id L'id du Manager
     * 
     * @return Un Manager
     * 
     * @throws SQLException Gestion de l'exception
     * 
     */
    public static Manager getManager(int manager_id) throws SQLException {
        Manager manager = null;

        String sql =
        "SELECT * FROM " + DataManager.TABLE + " " +
        "WHERE id = ?;";

        try (PreparedStatement pstmt = DatabaseManager.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, manager_id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    manager = new Manager(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("team_id"),
                        rs.getLong("money"),
                        rs.getInt("wins"),
                        rs.getInt("draws"),
                        rs.getInt("loses")
                    );
                }
            }
        }
        return manager;
    }

    /**
     * Méthode pour récupérer les noms des X premiers Managers.
     * 
     * @return Une chaîne avec les noms des X premiers Managers
     * 
     * @throws SQLException Gestion de l'exception
     * 
     * 
     * @since 0.1
     */
    public static ArrayList<Manager> getTopManagers() throws SQLException {
        ArrayList<Manager> managers = new ArrayList<>();
        final int top = 10;

        String sql =
        "SELECT * FROM " + DataManager.TABLE + " m " +
        "JOIN " + DataTeam.TABLE + " t ON m.team_id = t.id " +
        "WHERE t.bot = FALSE " + 
        "ORDER BY wins DESC, draws ASC, loses ASC, money DESC " +
        "LIMIT ?;";

        try (PreparedStatement pstmt = DatabaseManager.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, top);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    managers.add(new Manager(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("team_id"),
                        rs.getLong("money"),
                        rs.getInt("wins"),
                        rs.getInt("draws"),
                        rs.getInt("loses")
                    ));
                }
            }
        }

        return managers;
    }

    /**
     * Méthode pour insérer un Manager dans la table.
     * 
     * @param manager Le Manager à ajouter
     * 
     * @return Un boolean pour savoir si le Manager a été inséré ou non
     * 
     * @throws SQLException Gestion de l'exception
     * 
     * 
     * @since 0.1
     */
    private static boolean insertManager(Manager manager) throws SQLException {
        String sql =
        "INSERT INTO " + DataManager.TABLE + " " +
        "(name, team_id, money, wins, draws, loses) " +
        "VALUES (?, ?, ?, ?, ?, ?) " + 
        "RETURNING id;";

        try (PreparedStatement pstmt = DatabaseManager.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, manager.getName());
            pstmt.setInt(2, manager.getTeamId());
            pstmt.setLong(3, manager.getMoney());
            pstmt.setInt(4, manager.getWins());
            pstmt.setInt(5, manager.getDraws());
            pstmt.setInt(6, manager.getLoses());

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    manager.setId(rs.getInt("id"));
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Méthode pour mettre à jour un Manager.
     * 
     * @param manager Le Manager à mettre à jour
     * 
     * @return Un boolean pour savoir si le Manager a été mis à jour
     * 
     * @throws SQLException Gestion de l'exception
     * 
     * 
     * @since 0.1
     */
    private static boolean updateManager(Manager manager) throws SQLException {
        String sql =
        "UPDATE " + DataManager.TABLE + " " +
        "SET name = ?, team_id = ?, money = ?, wins = ?, draws = ?, loses = ? " + 
        "WHERE id = ?;";

        try (PreparedStatement pstmt = DatabaseManager.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, manager.getName());
            pstmt.setInt(2, manager.getTeamId());
            pstmt.setLong(3, manager.getMoney());
            pstmt.setInt(4, manager.getWins());
            pstmt.setInt(5, manager.getDraws());
            pstmt.setInt(6, manager.getLoses());
            pstmt.setInt(7, manager.getId());

            return pstmt.executeUpdate() > 0;
        }
    }
}
