package com.kahrs.database.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.kahrs.database.DatabaseManager;
import com.kahrs.model.Contract;

/**
 * Classe DataContract servant à la manipulation de la table contracts.
 * 
 * @author Hugo ARNAUD
 * 
 * 
 * @since 0.1
 * 
 * @version 0.2
 */
public class DataContract {

    // ==================== ATTRIBUTS =====================

    /** Nom de la table dans la base de données */
    protected static final String TABLE = "contracts";

    /** Constructeur privé pour résoudre warning de Javadoc */
    private DataContract() {
    }

    // ==================== METHODES =====================

    /**
     * Méthode pour insérer/mettre à jour un Contract dans la table.
     * 
     * @param contract Le Contract à insérer
     * 
     * @return Un boolean pour savoir si le Contract a été ajouté ou non
     * 
     * @throws SQLException Gestion de l'exception
     * 
     * 
     * @since 0.1
     */
    public static boolean addContract(Contract contract) throws SQLException {
        return (DataContract.getContract(contract.getId()) == null || contract.getId() == 0)
                ? DataContract.insertContract(contract)
                : DataContract.updateContract(contract);
    }

    /**
     * Méthode pour supprimer le contract de la BDD
     * 
     * @param contract Le contract
     * 
     * @throws SQLException Gestion de l'exception
     * 
     * @return Un boolean pour savoir si l'opération a réussi
     * 
     * @since 0.2
     */
    protected static boolean removeContract(Contract contract) throws SQLException {
        String sql =
        "DELETE FROM " + DataContract.TABLE + " " +
        "WHERE id = ?";

        try (PreparedStatement pstmt = DatabaseManager.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, contract.getId());

            return pstmt.executeUpdate() == 1;
        }
    }

    /**
     * Méthode pour insérer un Contract dans la table.
     * 
     * @param contract Le Contract à insérer
     * 
     * @return Un boolean pour savoir si le Contract été inséré ou non
     * 
     * @throws SQLException Gestion de l'exception
     * 
     * 
     * @since 0.1
     */
    private static boolean insertContract(Contract contract) throws SQLException {
        String sql =
        "INSERT INTO " + DataContract.TABLE + " " +
        "(team_id, player_id, duration, price) " +
        "VALUES (?, ?, ?, ?) " +
        "RETURNING id;";

        try (PreparedStatement pstmt = DatabaseManager.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, contract.getTeamId());
            pstmt.setInt(2, contract.getPlayerId());
            pstmt.setInt(3, contract.getDuration());
            pstmt.setInt(4, contract.getPrice());

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    contract.setId(rs.getInt("id"));
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Méthode pour récuperer un Contract de la table contracts avec un contract_id.
     * 
     * @param contract_id L'id du Contract
     * 
     * @return Un Contract
     * 
     * @throws SQLException Gestion de l'exception
     * 
     * 
     * @since 0.1
     */
    private static Contract getContract(int contract_id) throws SQLException {
        Contract contract = null;

        String sql =
        "SELECT * FROM " + DataContract.TABLE + " " +
        "WHERE id = ?;";

        try (PreparedStatement pstmt = DatabaseManager.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, contract_id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    contract = new Contract(
                            rs.getInt("id"),
                            rs.getInt("team_id"),
                            rs.getInt("player_id"),
                            rs.getInt("duration"),
                            rs.getInt("price")
                    );
                }
            }
        }

        return contract;
    }

    /**
     * Méthode pour récupérer un Contract en fonction d'un player_id et d'un
     * team_id.
     * 
     * @param player_id L'id du Player
     * @param team_id   L'id de la Team
     * 
     * @return Le Contract existant ou null
     * 
     * @throws SQLException Gestion de l'exception
     * 
     * @since 0.2
     */
    protected static Contract getContractPlayerId(int player_id, int team_id) throws SQLException {
        Contract contract = null;

        String sql =
        "SELECT * FROM " + DataContract.TABLE + " " +
        "WHERE player_id = ? AND team_id = ?;";

        try (PreparedStatement pstmt = DatabaseManager.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, player_id);
            pstmt.setInt(2, team_id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    contract = new Contract(
                            rs.getInt("id"),
                            rs.getInt("team_id"),
                            rs.getInt("player_id"),
                            rs.getInt("duration"),
                            rs.getInt("price")
                    );
                }
            }
        }

        return contract;
    }

    /**
     * Méthode pour récupérer tous les Contracts d'une équipe.
     * 
     * @param team_id L'id de la Team
     * 
     * @return Une liste des Contrats
     * 
     * @throws SQLException Gestion de l'exception
     * 
     * @since 0.2
     */
    protected static ArrayList<Contract> getContractsByTeamId(int team_id) throws SQLException {
        ArrayList<Contract> contracts = new ArrayList<>();

        String sql =
        "SELECT * FROM " + DataContract.TABLE + " " +
        "WHERE team_id = ?;";

        try (PreparedStatement pstmt = DatabaseManager.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, team_id);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    contracts.add(new Contract(
                            rs.getInt("id"),
                            rs.getInt("team_id"),
                            rs.getInt("player_id"),
                            rs.getInt("duration"),
                            rs.getInt("price")
                    ));
                }
            }
        }

        return contracts;
    }

    /**
     * Méthode pour mettre à jour un contract
     * 
     * @param contract Le contract
     * 
     * @throws SQLException Getion de l'exception
     * 
     * @return Un boolean pour savoir si l'opération a réussi
     * 
     * @since 0.1
     */
    private static boolean updateContract(Contract contract) throws SQLException {
        String sql =
        "UPDATE " + DataContract.TABLE + " " +
        "SET duration = ?, price = ? " +
        "WHERE id = ?;";

        try (PreparedStatement pstmt = DatabaseManager.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, contract.getDuration());
            pstmt.setInt(2, contract.getPrice());
            pstmt.setInt(3, contract.getId());

            return pstmt.executeUpdate() > 0;
        }
    }
}
