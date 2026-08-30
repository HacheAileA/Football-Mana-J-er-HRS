package com.kahrs.model;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Classe Match permettant de représenter un match.
 * 
 * @author Ruben FOALEM
 * 
 * 
 * @since 0.1
 * 
 * @version 0.1
 */
public class Match {

    /**
     * Enum représentant les événements importants du match.
     * Utilisé pour les commentaires contextuels.
     * 
     * @since 0.1
     */
    public enum MatchEvent {
        /** But pour l'équipe à domicile */          GOAL_HOME,
        /** But pour l'équipe à l'extérieur */       GOAL_AWAY,
        /** Tir cadré pour l'équipe à domicile */    SHOT_ON_TARGET_HOME,
        /** Tir cadré pour l'équipe à l'extérieur */ SHOT_ON_TARGET_AWAY
    }

    // ==================== ATTRIBUTS ====================

    /** Id du Match*/private int id;

    /** Team receveuse*/private final Team home;
    /** Team visiteuse*/private final Team away;

    /** Score de la Team receveuse*/private int score_home;
    /** Score de la Team visiteuse */private int score_away;
    
    /** Nombre de remplacements effectués */
    private int subsDone = 0;

    /** Statistiques détaillées */
    /** Stats de tirs */private int shotsHome, shotsAway;
    /** Stats de tirs cadrés */private int shotsOnTargetHome, shotsOnTargetAway;
    /** Stats de passes */private int passesHome, passesAway;

    /** Date du Match */private final LocalDate date;

    /** Durée restante du Match */public int duree = 90;
    /** Durée d'un Match */private static final int DUREE_INIT = 90;

    /** Gain du match pour affichage */public int lastGain = 0;

    // ================== CONSTRUCTEUR ==================

    /**
     * Constructeur de Match pour créer un objet avec un id (int), un home_id (Integer), un away_id (Integer) et une date (LocalDate).
     *
     * @param id           L'id du match
     * @param home      L'équipe à domicile
     * @param away      L'équipe à l'extérieur
     * @param date         La date du Match
     *
     * @since 0.1
     */
    public Match(int id, Team home, Team away, LocalDate date) {
        this.id = id;
        this.home = home;
        this.away = away;
        this.score_home = 0;
        this.score_away = 0;
        this.date = date;

        this.duree = Match.DUREE_INIT;
    }

    /**
     * Constructeur de Match pour créer un objet avec un home_id (Integer) et un away_id (Integer).
     *
     * @param home      L'équipe à domicile
     * @param away      L'équipe à l'extérieur
     *
     * @since 0.1
     */
    public Match(Team home, Team away) {
        this.home = home;
        this.away = away;
        this.score_home = 0;
        this.score_away = 0;
        this.date = LocalDate.now();

        this.duree = Match.DUREE_INIT;
    }

    // ==================== ACCESSEURS ===================

    /**
     * Méthode pour récupérer l'id du Match.
     * 
     * @return L'id
     * 
     * @since 0.1
     */
    public int getId() {
        return this.id;
    }

    /**
     * Setter pour définir l'id du Match.
     * 
     * @param id Le nouvel id
     * 
     * @since 0.1
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Méthode pour renvoyer l'id de l'équipe gagnante.
     *
     * @return Un int qui représente l'id de l'équipe gagnante :
     * -2 -> match pas fini
     * -1 -> match nul
     * 
     *
     * @since 0.1
     */
    public int getTeamIdWinner() {
        if (!this.endMatch()) {
            return -2;
        }
            if (this.score_home > this.score_away) {
            return this.home.getId();
        } else if (this.score_away > this.score_home) {
            return this.away.getId();
        } else {
            return -1;
        }
    }

    /**
     * Getter pour récuperer l'équipe à domicile.
     *
     * @return l'équipe home
     *
     * @since 0.1
     */
    public Team getHome() {
        return this.home;
    }

    /**
     * Getter pour récuperer l'id de l'équipe à domicile.
     *
     * @return L'id de l'équipe home
     *
     * @since 0.1
     */
    public Integer getHomeId() {
        return this.home.getId();
    }

    /**
     * Getter pour récuperer l'équipe à l'extérieur.
     *
     * @return l'équipe away
     *
     * @since 0.1
     */
    public Team getAway() {
        return this.away;
    }

    /**
     * Getter pour récuperer l'id de l'équipe à l'extérieur.
     *
     * @return L'id de l'équipe away
     *
     * @since 0.1
     */
    public Integer getAwayId() {
        return this.away.getId();
    }

    /**
     * Getter pour récuperer le score de l'équipe à domicile.
     *
     * @return Le score_home
     *
     * @since 0.1
     */
    public int getScoreHome() {
        return this.score_home;
    }

    /**
     * Setter pour définir le score de l'équipe à domicile.
     *
     * @param score_home Le nouveau score_home
     *
     * @since 0.1
     */
    public void setScoreHome(int score_home) {
        this.score_home = score_home;
    }

    /**
     * Getter pour récuperer le score de l'équipe à l'extérieur.
     *
     * @return Le score_away
     *
     * @since 0.1
     */
    public int getScoreAway() {
        return this.score_away;
    }

    /**
     * Setter pour définir le score de l'équipe à l'extérieur.
     *
     * @param score_away Le nouveau score_away
     *
     * @since 0.1
     */
    public void setScoreAway(int score_away) {
        this.score_away = score_away;
    }

    /**
     * Getter pour récuperer la date du match.
     *
     * @return La date
     *
     * @since 0.1
     */
    public LocalDate getDate() {
        return this.date;
    }

    /**
     * Getter pour récuperer la durée du match.
     *
     * @return La durée
     *
     * @since 0.1
     */
    public int getDuree() {
        return this.duree;
    }

    /**
     * Setter pour définir la durée du match.
     *
     * @param duree La nouvelle durée
     *
     * @since 0.1
     */
    public void setDuree(int duree) {
        this.duree = duree;
    }

    /**
     * Getter pour récuperer le nombre de remplacements effectués.
     *
     * @return Nombre de remplacements effectués
     *
     * @since 0.1
     */
    public int getSubsDone() {
        return this.subsDone;
    }

    /**
     * Méthode pour incrémenter le nombre de remplacements effectués.
     *
     * @since 0.1
     */
    public void incrementSubsDone() {
        this.subsDone++;
    }

    // ==================== METHODES =====================

    /**
     * Méthode pour ajouter un but à l'équipe à domicile.
     * 
     *
     * @since 0.1
     */
    public void butHome() {
        this.setScoreHome(this.getScoreHome() + 1);
    }

    /**
     * Méthode pour ajouter un but à l'équipe à l'extérieur.
     * 
     *
     * @since 0.1
     */
    public void butAway() {
        this.setScoreAway(this.getScoreAway() + 1);
    }


    /**
     * Getter pour récuperer le nombre de tirs cadrés de l'équipe à domicile
     *
     * @param onTarget tir cadré
     *
     * @since 0.1
     */
    public void addShotHome(boolean onTarget) { 
        this.shotsHome++; 
        if(onTarget) this.shotsOnTargetHome++; 
    }


    /**
     * Getter pour récuperer le nombre de tirs cadrés de l'équipe à l'extérieur
     *
     * @param onTarget tir cadré
     *
     * @since 0.1
     */
    public void addShotAway(boolean onTarget) { 
        this.shotsAway++; 
        if(onTarget) this.shotsOnTargetAway++; 
    }

    /**
     * Méthode pour incrémenter le nombre de passes de l'équipe à domicile.
     *
     * @since 0.1
     */
    public void addPassHome() {
        this.passesHome++;
    
    }

    /**
     * Méthode pour incrémenter le nombre de passes de l'équipe à l'extérieur.
     *
     * @since 0.1
     */
    public void addPassAway() {
        this.passesAway++;
    }

    /**
     * Getter pour récuperer le nombre de tirs de l'équipe à domicile.
     *
     * @return Nombre de tirs de l'équipe à domicile
     *
     * @since 0.1
     */
    public int getShotsHome() {
        return shotsHome;
    }


    /**
     * Getter pour récuperer le nombre de tirs de l'équipe à l'extérieur.
     *
     * @return Nombre de tirs de l'équipe à l'extérieur
     *
     * @since 0.1
     */
    public int getShotsAway() {
        return shotsAway;
    }

    /**
     * Getter pour récuperer le nombre de tirs cadrés de l'équipe à domicile.
     *
     * @return Nombre de tirs cadrés de l'équipe à domicile
     *
     * @since 0.1
     */
    public int getShotsOnTargetHome() {
        return shotsOnTargetHome;
    }

    /**
     * Getter pour récuperer le nombre de tirs cadrés de l'équipe à l'extérieur.
     *
     * @return Nombre de tirs cadrés de l'équipe à l'extérieur
     *
     * @since 0.1
     */
    public int getShotsOnTargetAway() {
        return shotsOnTargetAway;
    }

    /**
     * Getter pour récuperer le nombre de passes de l'équipe à domicile.
     *
     * @return Nombre de passes de l'équipe à domicile
     *
     * @since 0.1
     */
    public int getPassesHome() {
        return passesHome;
    }

    /**
     * Getter pour récuperer le nombre de passes de l'équipe à l'extérieur.
     *
     * @return Nombre de passes de l'équipe extérieur
     *
     * @since 0.1
     */
    public int getPassesAway() {
        return passesAway;
    }

    /**
     * Méthode qui vérifie si le match est fini.
     *
     * @return Un boolean pour savoir si le match est fini
     *
     * @since 0.1
     */
    private boolean endMatch() {
        return this.duree == 0;
    }

    /**
     * Méthode statique pour calculer la moyenne d'une stat d'équipe.
     * @param team L'équipe concernée
     * @param stat Le nom de la statistique (pass, speed, shoot, attack, defense)
     * @return La moyenne calculée
     * * @since 0.2
     */
    public static double getMoyenneStats(Team team, String stat) {
        double total = 0;
        int ratio = 2;
        ArrayList<Player> players = (team.isBot()) ? team.getPlayers() : team.getStarters();
        for (Player p : players) {
            switch(stat) {
                case "pass":    total += (p.getStatus() == Player.Status.GOOD) ? p.getPass() : p.getPass() / ratio; break;
                case "speed":   total += (p.getStatus() == Player.Status.GOOD) ? p.getSpeed() : p.getSpeed() / ratio; break;
                case "shoot":   total += (p.getStatus() == Player.Status.GOOD) ? p.getShoot() : p.getShoot() / ratio; break;
                case "attack":  total += (p.getStatus() == Player.Status.GOOD) ? p.getAttack() : p.getAttack() / ratio; break;
                case "defense": total += (p.getStatus() == Player.Status.GOOD) ? p.getDefense() : p.getDefense() / ratio; break;
            }
        }
        return total / players.size();
    }
}
