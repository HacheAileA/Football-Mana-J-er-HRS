package com.kahrs.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Classe Championship permettant de représenter un championnat.
 * 
 * @author Hugo ARNAUD
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
public class Championship {

    /**
     * Classe statique TeamStats représentant les statistiques d'une équipe dans le
     * championnat.
     * 
     * @since 0.2
     */
    public static class TeamStats {
        /** Nombre de points */public int points = 0;

        /** Nombre de matchs joués */public int played = 0;
        /** Nombre de matchs gagnés */public int wins = 0;
        /** Nombre de matchs nuls */public int draws = 0;
        /** Nombre de matchs perdus */public int loses = 0;

        /** Nombre de buts marqués */public int goalsScored = 0;
        /** Nombre de buts encaissés */public int goalsAgainst = 0;

        /** Constructeur privé pour résoudre warning de Javadoc */
        private TeamStats() {
        }

        /**
         * Méthode pour ajouter les statistiques d'un match joué.
         * 
         * @param goalsScored   Nombre de buts marqués
         * @param goalsConceded Nombre de buts encaissés
         * 
         * @since 0.2
         */
        private void addMatchResult(int goalsScored, int goalsConceded) {
            this.played++;
            this.goalsScored += goalsScored;
            this.goalsAgainst += goalsConceded;

            if (goalsScored > goalsConceded) {
                this.wins++;
                this.points += 3;
            } else if (goalsScored == goalsConceded) {
                this.draws++;
                this.points += 1;
            } else {
                this.loses++;
            }
        }

        /**
         * Méthode pour récupérer la différence de buts.
         * 
         * @return La différence de buts
         * 
         * @since 0.2
         */
        public int getGoalDifference() {
            return this.goalsScored - this.goalsAgainst;
        }
    }

    // ==================== ATTRIBUTS ====================

    /** Statistiques des équipes **/
    private HashMap<Integer, TeamStats> standing;
    /** Liste des maths **/
    private ArrayList<Match> matchs;
    /** Liste d'équipes **/
    private ArrayList<Team> teams;
    /** Récompense (victoire, défaite, nul)**/
    private int[] rewards = { 100000, 75000, 50000 };
    /**  Nombre de matchs **/
    private int nbMatchs;

    // ================== CONSTRUCTEUR ==================

    /**
     * Constructeur de Championship pour créer un championnat.
     *
     * @since 0.2
     */
    public Championship() {
        this.standing = new HashMap<>();
        this.matchs = new ArrayList<>();
        this.teams = new ArrayList<>();
        this.nbMatchs = 10;
    }

    // ==================== ACCESSEURS ===================

    /**
     * Getter pour récupérer le classement.
     * 
     * @return Le classement
     * 
     * @since 0.2
     */
    public HashMap<Integer, TeamStats> getStanding() {
        return this.standing;
    }

    /**
     * Getter pour récupérer la liste des matchs.
     * 
     * @return La liste des Match
     * 
     * @since 0.2
     */
    public ArrayList<Match> getMatchs() {
        return this.matchs;
    }

    /**
     * Getter pour récupérer la liste des équipes.
     * 
     * @return La liste des Team
     * 
     * @since 0.2
     */
    public ArrayList<Team> getTeams() {
        this.sortStandings();
        return this.teams;
    }

    /**
     * Getter pour récupérer le nombre de matchs.
     *
     * @return Le nombre de matchs
     *
     * @since 0.2
     */
    public int getNbMatchs() {
        return this.nbMatchs;
    }

    /**
     * Méthode vérifiant si un championnat est fini ou non
     *
     * @return un boolean
     *
     * @since 0.2
     */
    public boolean isOver() {
        if (this.teams.isEmpty()) {
            return false;
        }
        for (Team team : this.teams) {
            if (this.standing.get(team.getId()).played < this.nbMatchs) {
                return false;
            }
        }
        return true;
    }

    // ==================== METHODES =====================

    /**
     * Méthode pour initialiser le championnat
     *
     * @param myTeam équipe utilisateur
     *
     * @param opponents Liste des équipes adversaires
     *
     * @since 0.2
     */
    public void init(Team myTeam, ArrayList<Team> opponents) {
        this.addTeam(myTeam);
        for (Team opponent : opponents) {
            this.addTeam(opponent);
        }
    }

    /**
     * Méthode pour ajouter une équipe dans le championnat
     *
     * @param team équipe à ajouter
     *
     * @return un boolean
     *
     * @since 0.2
     */
    private boolean addTeam(Team team) {
        if (this.teams.contains(team)) {
            return false;
        }

        this.teams.add(team);
        this.standing.put(team.getId(), new TeamStats());

        return true;
    }

    /**
     * Méthode pour ajouter un match dans le championnat
     *
     * @param match match à ajouter
     *
     * @return un boolean
     *
     * @since 0.2
     */
    public boolean addMatch(Match match) {
        if (this.isOver() || this.matchs.contains(match)) {
            return false;
        }

        this.matchs.add(match);

        this.standing.get(match.getHomeId()).addMatchResult(match.getScoreHome(), match.getScoreAway());
        this.standing.get(match.getAwayId()).addMatchResult(match.getScoreAway(), match.getScoreHome());

        this.sortStandings();

        return true;
    }

    /**
     * Méthode pour comparer les statistiques de 2 équipes
     *
     * @since 0.2
     */
    private void sortStandings() {
        this.teams.sort((t1, t2) -> {
            TeamStats stats1 = this.standing.get(t1.getId());
            TeamStats stats2 = this.standing.get(t2.getId());

            if (stats1.points != stats2.points) {
                return Integer.compare(stats2.points, stats1.points);
            }

            return Integer.compare(stats2.getGoalDifference(), stats1.getGoalDifference());
        });
    }

    /**
     * Méthode pour mettre fin au championnai
     *
     * @param model Gamodel du jeu
     *
     * @return un tableau contenant le classement et l'argent gagné par l'équipe utilisateur
     *
     * @since 0.2
     */
    public int[] finishChampionship(GameModel model) {
        int[] result = { 0, 0 };
        for (int i = 0; i < this.teams.size(); i++) {
            if (this.teams.get(i).getId() == model.getTeam().getId()) {
                result[0] = i + 1;
                if (i < this.rewards.length) {
                    Long money = model.getManager().getMoney();
                    model.getManager().setMoney(money + (long) this.rewards[i]);
                    result[1] = this.rewards[i];
                }
                break;
            }
        }
        return result;
    }

    /**
     * Méthode pour relancer un championnat
     *
     * @since 0.2
     */
    public void restartChampionship() {
        this.matchs.clear();

        for (Team team : this.teams) {
            this.standing.put(team.getId(), new TeamStats());
        }
    }
}
