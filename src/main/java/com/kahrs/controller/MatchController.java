package com.kahrs.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.Timer;

import com.kahrs.app.ChampionshipManager;
import com.kahrs.app.MailManager;
import com.kahrs.app.QuestsManager;
import com.kahrs.database.dao.DataManager;
import com.kahrs.database.dao.DataMatch;
import com.kahrs.database.dao.DataTeam;
import com.kahrs.model.Championship;
import com.kahrs.model.Contract;
import com.kahrs.model.Manager;
import com.kahrs.model.Match;
import com.kahrs.model.Match.MatchEvent;
import com.kahrs.model.Player;
import com.kahrs.model.Quest;
import com.kahrs.model.Team;
import com.kahrs.view.panels.MatchPanel;
import com.kahrs.view.panels.SubstitutionDialog;
import com.kahrs.view.visuals.HRSLanguages;

/**
 * Classe MatchController gérant le système de match.
 *
 * @author Sofyane HARISSE
 *
 * @since 0.1
 *
 * @version 0.2
 */
public class MatchController {

    /** Controller du jeu */
    private GameController controller;

    /** Timer pour le déroulement du chronomètre et des actions. */
    private Timer clockTimer;

    /** Nombre d'actions (pour les bots) */private int nbActions = 20;
    /** Chance de but (pour les bots) */private int butChance = 15;
    
    /** Stats actuelles du match */
    /** Stats de l'équipe à domicile */private double homePass, homeSpeed, homeShoot, homeDef;
    /** Stats de l'équipe à l'extérieur */private double awayPass, awaySpeed, awayShoot, awayDef;

    /** Chance d'être blessé */
    private double injuryChance = 0.03;
    /** Chance d'être fatigué */
    private double tiredChance = 0.1;

    /** Liste des matchs des bots */
    private ArrayList<Match> botsMatchs;

    /**
     * Constructeur de MatchController.
     *
     * @param controller Controller du jeu
     *
     * @since 0.1
     */
    public MatchController(GameController controller) {
        this.controller = controller;

        this.botsMatchs = new ArrayList<>();
    }

    /**
     * Méthode pour préparer le lancement d'un match
     *
     * @since 0.1
     */
    public void preparerMatch() {
        this.botsMatchs.clear();
        try {
            Team teamHome = this.controller.model.getTeam();
            Team teamAway = DataTeam.getRandomTeam(true);
            this.controller.view.getMatchSettingsPanel().preparerMatch(teamHome, teamAway, false);
        } catch (SQLException e) {
            System.err.println("[ERREUR] Chargement équipes : " + e.getMessage());
        }
    }

    /**
     * Méthode pour préparer le lancement d'un match de championnat
     *
     * @param championship Championnat
     *
     * @since 0.1
     */
    public void preparerMatchChampionnat(Championship championship) {
        ArrayList<Team> teams = new ArrayList<>(championship.getTeams());

        ArrayList<Match> matchs = new ArrayList<>();
        Match myMatch = null;

        Team myTeam = this.controller.model.getTeam();

        for (int i = 0; i < teams.size() - 1; i += 2) {
            Team homeTeam = teams.get(i);
            Team awayTeam = teams.get(i + 1);

            if (homeTeam == null || awayTeam == null) {
                continue;
            }

            if (homeTeam.equals(myTeam) || awayTeam.equals(myTeam)) {
                Team adversaire = homeTeam.equals(myTeam) ? awayTeam : homeTeam;
                myMatch = new Match(myTeam, adversaire);
            } else {
                Match match = new Match(homeTeam, awayTeam);
                matchs.add(match);
            }
        }

        if (myMatch != null) {
            this.botsMatchs = matchs;
            this.controller.view.getMatchSettingsPanel().preparerMatch(myMatch, true);
        } else {
            System.out.println("Votre équipe se repose cette journée.");
            this.controller.view.setPanel(this.controller.view.getChampionshipPanel());
            this.simulerMatchs(matchs, true);
        }
    }

    /**
     * Méthode pour enregistrer la fin d'un match
     *
     * @param match Match
     * @param isChampionship Match de Championnat
     * @param isMyMatch Match de l'utilisateur
     *
     * @since 0.2
     */
    private void enregistrerFinDeMatch(Match match, boolean isChampionship, boolean isMyMatch) {
        int vainqueur = match.getTeamIdWinner();
        Manager manager = this.controller.model.getManager();
        Team team = this.controller.model.getTeam();

        int gainMatch = this.calculerGain(match, team.getId());
        
        match.lastGain = gainMatch;

        String nomAdversaire = "adversaire";
        try {
            nomAdversaire = DataTeam.getTeamNameById(match.getAwayId());
        } catch (SQLException e) {
            System.err.println("[ERREUR] Nom adversaire : " + e.getMessage());
        }
        String scoreFinal = match.getScoreHome() + " - " + match.getScoreAway();

        if (isChampionship) {
            this.controller.model.getChampionship().addMatch(match);
            ChampionshipManager.saveChampionship();
        }

        try {
            DataMatch.addMatch(match);
            
            if (isMyMatch) {
                for (Player player : team.getStarters()) {
                    this.controller.changerEtat(player, this.injuryChance, this.tiredChance);
                }

            manager.setMoney(manager.getMoney() + gainMatch);

                if (vainqueur == team.getId()) {
                    manager.setWins(manager.getWins() + 1);
                    QuestsManager.advanceQuest(Quest.QuestCategory.MATCH_WIN, 1);
                } else if (vainqueur == -1) {
                    manager.setDraws(manager.getDraws() + 1);
                } else {
                    manager.setLoses(manager.getLoses() + 1);
                }
                manager.setModified(true);

                String sujetMail = HRSLanguages.Mail.getEndMatchSubjectText(nomAdversaire);
                String joueursFatigues = "\n\t";
                String joueursBlesses = "\n\t";
                for (Player player : team.getStarters()) {
                    if (player.getStatus() == Player.Status.TIRED) {
                        joueursFatigues += player.getName() + "\n\t";
                    } else if (player.getStatus() == Player.Status.INJURY) {
                        joueursBlesses += player.getName() + "\n\t";
                    }
                }
                String contenuMail = HRSLanguages.Mail.getEndMatchContentText(nomAdversaire, scoreFinal, joueursFatigues, joueursBlesses, gainMatch);

                MailManager.sendMail(sujetMail, contenuMail, this.controller.model.getCurrentDate().toString());

                DataManager.addManager(manager);
                QuestsManager.advanceQuest(Quest.QuestCategory.MATCH_PLAY, 1);
            }

            if (isChampionship && this.controller.model.getChampionship().isOver()) {
                int[] result = this.controller.model.getChampionship().finishChampionship(this.controller.model);
                QuestsManager.advanceQuest(Quest.QuestCategory.CHAMPIONSHIP_PLAY, 1);

                if (this.controller.model.getChampionship().getTeams().get(0).getId() == this.controller.model.getTeam().getId()) {
                    QuestsManager.advanceQuest(Quest.QuestCategory.CHAMPIONSHIP_WIN, 1);
                }

                String subjectMail = HRSLanguages.Mail.getEndChampionshipSubjectText();
                String contentMail = HRSLanguages.Mail.getEndChampionshipContentText(result[0], result[1]);
                MailManager.sendMail(subjectMail, contentMail, this.controller.model.getCurrentDate().toString());
            }
            

            this.controller.updateDatas();

            this.controller.view.updateTexts();

        } catch (SQLException e) {
            System.err.println("[ERREUR] Enregistrement résultats : " + e.getMessage());
        }
    }

    /**
     * Méthode permettant de simuler le score du match.
     * 
     * @param match          Le Match en cours
     * @param isChampionship Match de Championnat
     * 
     * @since 0.2
     */
    public void simulerMatch(Match match, boolean isChampionship) {
        MatchPanel panel = this.controller.view.getMatchPanel();

        Team home = match.getHome();
        Team away = match.getAway();

        Team userTeam = this.controller.model.getTeam();
        for (Player player : userTeam.getStarters()) {
            Contract contract = userTeam.getContractPlayerId(player.getId());
            if (contract != null) {
                contract.reduceDuration(1);
            }
        }

        this.mettreAJourStats(home, away);

        Random random = new Random();
        int[] minutesMatch = { 0 };
        int butChance = 15;

        panel.setCommentary("");

        if (!this.botsMatchs.isEmpty()) {
            this.simulerMatchs(this.botsMatchs, isChampionship);
            this.botsMatchs.clear();
        }
        clockTimer = new Timer(200, e -> {

            if (minutesMatch[0] < 90) {
                minutesMatch[0]++;
                panel.setChrono(minutesMatch[0]);
                if (minutesMatch[0] == 1) {
                    panel.setCommentary(HRSLanguages.Match.getCommentary(1)); 
                } else if (minutesMatch[0] == 25) {
                    panel.setCommentary(HRSLanguages.Match.getCommentary(2)); 
                } else if (minutesMatch[0] == 70) {
                    panel.setCommentary(HRSLanguages.Match.getCommentary(4)); 
                } else if (minutesMatch[0] == 88) {
                    panel.setCommentary(HRSLanguages.Match.getCommentary(5)); 
                }

                boolean homePossession = random.nextDouble() * (homePass + awayPass) < homePass;
                if (homePossession) {
                    if (random.nextDouble() * 100 < homePass) {
                        match.addPassHome();
                    }

                    if (random.nextDouble() * (homeSpeed + awayDef) < homeSpeed) {
                        boolean onTarget = random.nextDouble() * 100 < homeShoot;
                        match.addShotHome(onTarget);

                        if (onTarget && random.nextDouble() * 100 < butChance) {
                            match.butHome();
                            panel.setScore(match.getScoreHome(), match.getScoreAway());
                            panel.setCommentary(HRSLanguages.Match.getCommentaryForEvent(MatchEvent.GOAL_HOME, home.getName()));
                            panel.showGoalAnimation(home.getName());
                        } else if (onTarget) {
                            panel.setCommentary(HRSLanguages.Match.getCommentaryForEvent(MatchEvent.SHOT_ON_TARGET_HOME, home.getName()));
                        } else {
                            panel.setCommentary(HRSLanguages.Match.getCommentary(6));
                        }
                    }
                } else {
                    if (random.nextDouble() * 100 < awayPass) {
                        match.addPassAway();
                    }

                    if (random.nextDouble() * (awaySpeed + homeDef) < awaySpeed) {
                        boolean onTarget = random.nextDouble() * 100 < awayShoot;
                        match.addShotAway(onTarget);

                        if (onTarget && random.nextDouble() * 100 < butChance) {
                            match.butAway();
                            panel.setScore(match.getScoreHome(), match.getScoreAway());
                            panel.setCommentary(HRSLanguages.Match.getCommentaryForEvent(MatchEvent.GOAL_AWAY, away.getName()));
                            panel.showGoalAnimation(away.getName());
                        } else if (onTarget) {
                            panel.setCommentary(HRSLanguages.Match.getCommentaryForEvent(MatchEvent.SHOT_ON_TARGET_AWAY, away.getName()));
                        } else {
                            panel.setCommentary(HRSLanguages.Match.getCommentary(6)); 
                        }
                    }
                }
                panel.setScore(match.getScoreHome(), match.getScoreAway());

                // --- Gestion de la mi-temps ---
                if (minutesMatch[0] == 45) {
                    clockTimer.stop();
                    if (panel.demanderChangementsMiTemps()) {
                        this.faireChangements(match);
                        this.mettreAJourStats(home,away);
                    }
                    this.reprendreMatch();
                }
            } else {
                clockTimer.stop();
                match.duree = 0;
                panel.terminerMatch(match);
                enregistrerFinDeMatch(match, isChampionship, true);
            }
        });

        clockTimer.start();
    }

    /**
     * Méthode permettant de simuler le score de plusieurs matchs.
     *
     * @param matchs          Liste de Match
     * @param isChampionship Match de Championnat
     *
     * @since 0.2
     */
    private void simulerMatchs(ArrayList<Match> matchs, boolean isChampionship) {
        for (Match match : matchs) {
            this.simulerMatchBots(match, false, isChampionship);
        }
    }

    /**
     * Méthode permettant de simuler le score d'un match de bot.
     *
     * @param match          Match en cours
     * @param isMyMatch     Match de l'utilisateur
     * @param isChampionship Match de Championnat
     *
     * @since 0.2
     */
    private void simulerMatchBots(Match match, boolean isMyMatch, boolean isChampionship) {
        Random random = new Random();
        boolean onTarget;

        double homeDef = Match.getMoyenneStats(match.getHome(), "defense");
        double awayDef = Match.getMoyenneStats(match.getAway(), "defense");

        for (int i = 0; i < this.nbActions; i++) {
            onTarget = random.nextDouble() * 100 < homeDef;
            if (onTarget && random.nextDouble() * 100 < this.butChance) {
                match.butHome();
            }

            onTarget = random.nextDouble() * 100 < awayDef;
            if (onTarget && random.nextDouble() * 100 < this.butChance) {
                match.butAway();
            }
        }

        this.enregistrerFinDeMatch(match, isChampionship, isMyMatch);
    }

    /**
     * Méthode permettant de faire des changements lors d'un match en cours.
     *
     * @param match Match en cours
     *
     * @since 0.2
     */
    private void faireChangements(Match match) {
        SubstitutionDialog dialog = new SubstitutionDialog(this.controller.view, match);
        dialog.setVisible(true);
    }

    /**
     * Méthode permettant d'effectuer un changement lors d'un match en cours.
     *
     * @param starter Joueur titulaire
     * @param sub Joueur remplaçant
     * @param match Match en cours
     *
     * @since 0.2
     */
    public void effectuerRemplacement(Player starter, Player sub, Match match) {
        Team team = this.controller.model.getTeam();
        team.switchPlayers(starter, sub);
        match.incrementSubsDone();
        team.getSubstitutes().add(starter);
        
        Contract subContract = team.getContractPlayerId(sub.getId());
        if (subContract != null) {
            subContract.reduceDuration(1);
        }
    }

    /**
     * Méthode pour reprendre le match.
     *
     * @since 0.2
     */
    private void reprendreMatch() {
        if (clockTimer != null) {
            clockTimer.start();
        }
        SoundController.playEffect("Kick");
    }

    /**
     * Méthode permettant de mettre à jour les stats des deux équipes.
     *
     * @param home Equipe à domicile
     * @param away Equipe à l'extérieur
     *
     * @since 0.2
     */
    private void mettreAJourStats(Team home, Team away) {
        this.homePass = Match.getMoyenneStats(home, "pass");
        this.homeSpeed = Match.getMoyenneStats(home, "speed");
        this.homeShoot = Match.getMoyenneStats(home, "shoot");
        this.homeDef = Match.getMoyenneStats(home, "defense");
 
        this.awayPass = Match.getMoyenneStats(away, "pass");
        this.awaySpeed = Match.getMoyenneStats(away, "speed");
        this.awayShoot = Match.getMoyenneStats(away, "shoot");
        this.awayDef = Match.getMoyenneStats(away, "defense");
    }

    /**
     * Calcule le gain d'argent en fonction de la performance du match.
     * 
     * @param match Le match terminé
     * @param my_team_id L'équipe du joueur
     * @return Le montant gagné
     */
    public int calculerGain(Match match, int my_team_id) {
        int vainqueur = match.getTeamIdWinner();
        int butsMarques = match.getScoreHome();
        int butsEncaisses = match.getScoreAway();

        int gainMatch = 80000;

        gainMatch += (butsMarques * 40000);   
        gainMatch -= (butsEncaisses * 15000); 

        if (vainqueur == my_team_id) {
            gainMatch += 250000; 
        } else if (vainqueur == -1) {
            gainMatch += 70000;  
        } else {
            gainMatch += 20000;  
        }

        if (gainMatch < 30000) {
            gainMatch = 30000;
        }

        return gainMatch;
    }
}
