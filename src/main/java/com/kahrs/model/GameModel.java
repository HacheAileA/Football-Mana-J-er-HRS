package com.kahrs.model;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import com.kahrs.app.CalendarManager;
import com.kahrs.app.ChampionshipManager;
import com.kahrs.app.UserConfig;
import com.kahrs.database.dao.DataManager;
import com.kahrs.database.dao.DataPlayer;
import com.kahrs.database.dao.DataTeam;
import com.kahrs.view.GameView;

/**
 * Classe GameModel gérant le model du jeu.
 *
 * @author Hugo ARNAUD
 *
 * @since 0.1
 * 
 * @version 0.1
 */
public class GameModel {

    // ==================== ATTRIBUTS ====================

    /** Vue du jeu */
    public GameView view;

    /** Manager de l'utilisateur */
    private Manager manager;
    /** Team de l'utilisateur */
    private Team team;
    /** Date du jour */
    private LocalDate date;

    /** Liste de tous les Player de la base */
    private ArrayList<Player> allPlayers;

    /** Liste de toutes les Infrastructures */
    private ArrayList<Infrastructure> allInfrastructures;
    /** L'infrastructure circuit de course */
    private RaceTrack raceTrack;
    /** L'infrastructure champ */
    private Field field;
    /** L'infrastructure cage */
    private Cage cage;
    /** L'infrastructure espace détente */
    private RelaxationArea relaxationArea;
    /** L'infrastructure espace de soin */
    private HealthArea healthArea;
    /** Championnat */
    private Championship championship;

    // ================== CONSTRUCTEURS ==================

    /**
     * Constructeur de GameModel.
     * 
     * 
     * @since 0.1
     */
    public GameModel() {
        this.date = CalendarManager.getDateGame();
        this.allPlayers = new ArrayList<>();
        this.allInfrastructures = new ArrayList<>();
        this.championship = ChampionshipManager.getChampionship();
    }

    // ==================== ACCESSEURS ===================

    /**
     * Méthode pour définir la GameView.
     * 
     * @param view La GameView utilisée
     * 
     * @since 0.1
     */
    public void setView(GameView view) {
        this.view = view;
    }

    /**
     * Getter pour récuperer le Manager.
     *
     * @return Le Manager
     *
     * @since 0.1
     */
    public Manager getManager() {
        return this.manager;
    }

    /**
     * Méthode pour définir le Manager.
     *
     * @param manager Le Manager utilisé
     *
     * @since 0.1
     */
    public void setManager(Manager manager) {
        this.manager = manager;
    }

    /**
     * Getter pour récuperer la Team.
     *
     * @return La Team
     *
     * @since 0.1
     */
    public Team getTeam() {
        return this.team;
    }

    /**
     * Méthode pour définir la Team.
     *
     * @param team La Team utilisée
     *
     * @since 0.1
     */
    public void setTeam(Team team) {
        this.team = team;
    }

    /**
     * Getter pour récuperer la date du jour.
     *
     * @return La date du jour
     *
     * @since 0.1
     */
    public LocalDate getCurrentDate() {
        return this.date;
    }


    /**
     * Setter pour définir la date du jour.
     *
     * @param date  La nouvelle date du jour
     *
     * @since 0.1
     */
    public void setCurrentDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Getter pour récuperer la liste de tous les Player.
     *
     * @return La liste de tous les Player
     *
     * @since 0.1
     */
    public ArrayList<Player> getAllPlayers() {
        return this.allPlayers;
    }

    /**
     * Getter pour récuperer la liste de toutes les Infrastructures.
     *
     * @return La liste de toutes les Infrastructures
     *
     * @since 0.1
     */
    public ArrayList<Infrastructure> getAllInfrastructures() {
        return this.allInfrastructures;
    }

    /**
     * Getter pour récuperer l'infrastructure circuit de course.
     *
     * @return l'infrastructure circuit de course
     *
     * @since 0.1
     */
    public RaceTrack getRaceTrack() {
        return this.raceTrack;
    }

    /**
     * Getter pour récuperer l'infrastructure champ.
     *
     * @return l'infrastructure champ
     *
     * @since 0.1
     */
    public Field getField() {
        return this.field;
    }

    /**
     * Getter pour récuperer l'infrastructure cage.
     *
     * @return l'infrastructure cage
     *
     * @since 0.1
     */
    public Cage getCage() {
        return this.cage;
    }

    /**
     * Getter pour récuperer l'infrastructure espace détente.
     *
     * @return l'infrastructure espace détente
     *
     * @since 0.1
     */
    public RelaxationArea getRelaxationArea() {
        return this.relaxationArea;
    }

    /**
     * Getter pour récuperer l'infrastructure espace de soin.
     *
     * @return l'infrastructure espace de soin
     *
     * @since 0.1
     */
    public HealthArea getHealthArea() {
        return this.healthArea;
    }

    /**
     * Getter pour récuperer le championnat.
     *
     * @return le championnat
     *
     * @since 0.1
     */
    public Championship getChampionship() {
        return this.championship;
    }

    // ==================== METHODES =====================

    /**
     * Méthode pour mettre à jour les données.
     * 
     * @throws SQLException Gestion de l'exception
     * 
     * 
     * @since 0.1
     */
    public void initDatas() throws SQLException {
        int id = UserConfig.getManagerId();
        if (id != -1) {
            this.manager = DataManager.getManager(id);
            this.team = DataTeam.getTeam(this.manager.getTeamId());
            this.team.setStarters(UserConfig.getStarters());

            this.allPlayers = this.filterPlayers();

            this.initInfrastructures();

            this.initChampionship();
        }
    }

    /**
     * Méthode pour initialiser les infrastructures.
     *
     * @since 0.1
     */
    private void initInfrastructures() {
        int raceTrackLevel = UserConfig.getInfrastructureLevel(Infrastructure.InfrastructureType.RACE_TRACK);
        int fieldLevel = UserConfig.getInfrastructureLevel(Infrastructure.InfrastructureType.FIELD);
        int cageLevel = UserConfig.getInfrastructureLevel(Infrastructure.InfrastructureType.CAGE);

        this.raceTrack = new RaceTrack(raceTrackLevel);
        this.field = new Field(fieldLevel);
        this.cage = new Cage(cageLevel);
        this.relaxationArea = new RelaxationArea();
        this.healthArea = new HealthArea();

        this.allInfrastructures.add(this.raceTrack);
        this.allInfrastructures.add(this.field);
        this.allInfrastructures.add(this.cage);
        this.allInfrastructures.add(this.relaxationArea);
        this.allInfrastructures.add(this.healthArea);
    }

    /**
     * Méthode pour initialiser le championnat
     *
     * @throws SQLException Gestion de l'exception
     *
     *
     * @since 0.2
     */
    private void initChampionship() throws SQLException {
        int nbTeamsMax = 8;

        if (this.championship.getTeams().isEmpty()) {
            ArrayList<Team> opponents = new ArrayList<>();
            while (opponents.size() != nbTeamsMax - 1) {
                Team randomTeam = DataTeam.getRandomTeam(true);
                if (!opponents.contains(randomTeam) && randomTeam.getId() != this.team.getId()) {
                    opponents.add(randomTeam);
                }
            }

            this.championship.init(this.team, opponents);
            ChampionshipManager.saveChampionship();
        }
    }

    /**
     * Méthode pour mettre à jour toutes les données.
     * 
     * @throws SQLException Gestion de l'exception
     * 
     * 
     * @since 0.2
     */
    public void updateDatas() throws SQLException {
        this.allPlayers = this.filterPlayers();
    }

    /**
     * Méthode pour supprimer des joueurs de l'équipe utilisateur (filtrer).
     *
     * @throws SQLException Gestion de l'exception
     *
     * @return l'équipe filtrée
     *
     * @since 0.2
     */
    private ArrayList<Player> filterPlayers() throws SQLException {
        ArrayList<Player> res = DataPlayer.getAllPlayers();
        ArrayList<Player> deletePlayers = new ArrayList<>();

        if (this.team != null && this.team.getPlayers() != null) {
            for (Player player : res) {
                for (Player myPlayer : this.team.getPlayers()) {
                    if (player.getName().equals(myPlayer.getName())) {
                        deletePlayers.add(player);
                        break;
                    }
                }
            }
        }

        res.removeAll(deletePlayers);
        return res;
    }
}
