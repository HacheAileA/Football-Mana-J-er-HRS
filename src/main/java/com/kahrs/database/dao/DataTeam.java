package com.kahrs.database.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import com.kahrs.app.UserConfig;
import com.kahrs.database.DatabaseManager;
import com.kahrs.model.Contract;
import com.kahrs.model.Manager;
import com.kahrs.model.Player;
import com.kahrs.model.Team;

/**
 * Classe DataTeam servant à la manipulation de la table teams.
 * 
 * @author Hugo ARNAUD
 * 
 * 
 * @since 0.1
 * 
 * @version 0.2
 */
public class DataTeam {

    // ==================== ATTRIBUTS =====================

    /** Nom de la table dans la base de données */protected static final String TABLE = "teams";

    /** Constructeur privé pour résoudre warning de Javadoc */
    private DataTeam() {
    }

    // ==================== METHODES =====================

    /**
     * Méthode pour insérer/mettre à jour une Team dans la table.
     * 
     * @param team La Team à insérer/mettre à jour
     * 
     * @return Un boolean pour savoir si la Team a été insérée/mise à jour ou non
     * 
     * @throws SQLException Gestion de l'exception
     * 
     * 
     * @since 0.1
     */
    public static boolean addTeam(Team team) throws SQLException {
        return (DataTeam.getTeam(team.getId()) == null || team.getId() == 0)
                ? DataTeam.insertTeam(team)
                : DataTeam.updateTeam(team);
    }

    /**
     * Méthode pour créer une nouvelle Team.
     * 
     * @param teamName Le nom de la Team
     * 
     * @return La Team créée
     * 
     * @throws SQLException Gestion de l'exception
     * 
     * 
     * @since 0.1
     */
    protected static Team createNewTeam(String teamName) throws SQLException {
        Team defaulTeam = DataTeam.getTeam(0);
        if (defaulTeam == null) {
            throw new SQLException("[ERREUR CRITIQUE] L'équipe par défaut (ID 0) est introuvable en base de données.");
        }

        Team newTeam = new Team(teamName);
        DataTeam.addTeam(newTeam);

        for (Player defaultPlayer : defaulTeam.getPlayers()) {
            Player newPlayer = new Player(
                    defaultPlayer.getName(),
                    defaultPlayer.getPosition(),
                    defaultPlayer.getStatus(),
                    defaultPlayer.getNote(),
                    defaultPlayer.getValue(),
                    defaultPlayer.getAttack(),
                    defaultPlayer.getDefense(),
                    defaultPlayer.getSpeed(),
                    defaultPlayer.getShoot(),
                    defaultPlayer.getPass());
            DataPlayer.addPlayer(newPlayer);
            DataContract.addContract(new Contract(newTeam.getId(), newPlayer.getId()));
        }

        return newTeam;
    }

    /**
     * Méthode pour insérer une Team dans la table.
     * 
     * @param team La Team à insérer
     * 
     * @return Un boolean pour savoir si la Team a été insérée ou non
     * 
     * @throws SQLException Gestion de l'exception
     * 
     * 
     * @since 0.1
     */
    private static boolean insertTeam(Team team) throws SQLException {
        String sql =
        "INSERT INTO " + DataTeam.TABLE + " " +
        "(name, bot) " +
        "VALUES (?, ?) " +
        "RETURNING id;";

        try (PreparedStatement pstmt = DatabaseManager.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, team.getName());
            pstmt.setBoolean(2, team.isBot());

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    team.setId(rs.getInt("id"));
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Méthode pour récuperer une Team de la table teams avec un team_id.
     * 
     * @param team_id L'id de la Team
     * 
     * @return Une Team
     * 
     * @throws SQLException Gestion de l'exception
     * 
     * 
     * @since 0.1
     */
    public static Team getTeam(int team_id) throws SQLException {
        Team team = null;

        String sql =
        "SELECT * FROM " + DataTeam.TABLE + " " +
        "WHERE id = ?;";

        try (PreparedStatement pstmt = DatabaseManager.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, team_id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    team = new Team(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getBoolean("bot")
                    );

                    team.addPlayers(DataPlayer.getPlayersTeamId(team_id));
                    team.addContracts(DataContract.getContractsByTeamId(team_id));
                }
            }
        }

        return team;
    }

    /**
     * Méthode pour récupérer aléatoirement une Team différente de celle de l'utilisateur (avec des conditions).
     * 
     * @param isBot Boolean indiquant si la Team est une Team de bot ou non
     * 
     * @return Une Team tirée aléatoirement
     * 
     * @throws SQLException Gestion de l'exception
     * 
     * 
     * @since 0.2
     */
    public static Team getRandomTeam(boolean isBot) throws SQLException {
        ArrayList<Team> teams = new ArrayList<>();

        Manager myManager = DataManager.getManager(UserConfig.getManagerId());
        Team myTeam = DataTeam.getTeam(myManager.getTeamId());

        String sql =
        "SELECT * FROM " + DataTeam.TABLE + " " +
        "WHERE bot = ? AND id >= 0 AND id <> ?;";

        try (PreparedStatement pstmt = DatabaseManager.getConnection().prepareStatement(sql)) {
            pstmt.setBoolean(1, isBot);
            pstmt.setInt(2, myTeam.getId());

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    teams.add(new Team(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getBoolean("bot")
                    ));
                }
            }
        }

        Random rnd = new Random();
        Team team = teams.get(rnd.nextInt(teams.size()));
        team.addPlayers(DataPlayer.getPlayersTeamId(team.getId()));

        return team;
    }

    /**
     * Méthode pour mettre à jour une Team.
     * 
     * @param team La Team à mettre à jour
     * 
     * @return Un boolean pour savoir si la Team a été mise à jour
     * 
     * @throws SQLException Gestion de l'exception
     * 
     * 
     * @since 0.1
     */
    private static boolean updateTeam(Team team) throws SQLException {
        String sql =
        "UPDATE " + DataTeam.TABLE + " " +
        "SET name = ?, bot = ? " +
        "WHERE id = ?;";

        try (PreparedStatement pstmt = DatabaseManager.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, team.getName());
            pstmt.setBoolean(2, team.isBot());
            pstmt.setInt(3, team.getId());

            return pstmt.executeUpdate() > 0;
        }
    }


    /**
     * Méthode pour récupérer uniquement le nom d'une équipe via son ID.
     * Idéal pour les emails pour ne pas charger tous les joueurs.
     * @param team_id L'id de la Team
     * @return Le nom de l'équipe 
     * @throws SQLException Gestion de l'exception
     * @since 0.1
     */
    public static String getTeamNameById(int team_id)throws SQLException{
        String name = "Equipe inconnue";

        String sql = "SELECT name FROM " + DataTeam.TABLE + " " +
        "WHERE id = ?;";

        try (PreparedStatement pstmt = DatabaseManager.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, team_id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    name = rs.getString("name");
                }
            }
        }
        return name;
    }
    }

