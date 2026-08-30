package com.kahrs.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import com.kahrs.app.MailManager;
import com.kahrs.app.QuestsManager;
import com.kahrs.app.UserConfig;
import com.kahrs.database.dao.DataContract;
import com.kahrs.database.dao.DataManager;
import com.kahrs.database.dao.DataPlayer;
import com.kahrs.database.dao.DataTeam;
import com.kahrs.model.Contract;
import com.kahrs.model.GameModel;
import com.kahrs.model.Infrastructure;
import com.kahrs.model.Manager;
import com.kahrs.model.Player;
import com.kahrs.model.Quest;
import com.kahrs.model.Team;
import com.kahrs.view.GameView;
import com.kahrs.view.visuals.HRSLanguages;

/**
 * Classe GameController gérant le controller du jeu.
 * 
 * @author Hugo ARNAUD
 * @author Ruben FOALEM
 * @author Sofyane HARISSE
 * 
 * 
 * @since 0.1
 * 
 * @version 0.2
 */
public class GameController {

    // ==================== ATTRIBUTS ====================

    /** Model du jeu */
    public GameModel model;

    /** Vue du jeu */
    public GameView view;

    /** Controller du match */
    private MatchController matchController;
    /** Controller des infrastructures */
    private InfrastructureController infrastructureController;

    // ================== CONSTRUCTEUR ==================

    /**
     * Constructeur de GameController.
     * 
     * @param model GameModel principal
     * @param view  GameView principale
     * 
     * 
     * @since 0.1
     */
    public GameController(GameModel model, GameView view) {
        this.model = model;
        this.view = view;
        this.matchController = new MatchController(this);
        this.infrastructureController = new InfrastructureController(this);
    }

    // ==================== ACCESSEURS ===================

    /**
     * Getter pour récuperer le controller du match.
     *
     * @return le controller du match
     *
     * @since 0.2
     */
    public MatchController getMatchController() {
        return this.matchController;
    }

    /**
     * Getter pour récuperer le controller des infrastructures.
     *
     * @return le controller des infrastructures
     *
     * @since 0.2
     */
    public InfrastructureController getInfrastructureController() {
        return this.infrastructureController;
    }

    // ==================== METHODES =====================

    /**
     * Méthode permettant de créer un nouvel utilisateur seulement si non existant.
     * 
     * @param managerName Le nom du Manager
     * @param teamName    Le nom de la Team
     * 
     * @throws SQLException Gestion de l'exception
     * 
     * 
     * @since 0.1
     */
    public void createNewUser(String managerName, String teamName) throws SQLException {
        Manager newManager = DataManager.createNewManager(managerName, teamName);
        UserConfig.setManagerId(newManager.getId());

        this.saveGame();
    }

    /**
     * Méthode pour recruter un Player.
     * 
     * @param player Le Player à recruter
     * 
     * @return Un boolean pour savoir si l'opération à réussi
     * 
     * @throws SQLException Gestion de l'exception
     * 
     * 
     * @since 0.1
     */
    public boolean buyPlayer(Player player) throws SQLException {
        Manager manager = this.model.getManager();
        Team team = this.model.getTeam();

        long managerMoney = manager.getMoney();
        long playerValue = player.getValue();

        if (managerMoney >= playerValue) {
            manager.setMoney(managerMoney - playerValue);

            Player newPlayer = new Player(
                player.getName(),
                player.getPosition(),
                player.getStatus(),
                player.getNote(),
                player.getValue(),
                player.getAttack(),
                player.getDefense(),
                player.getSpeed(),
                player.getShoot(),
                player.getPass()
            );

            DataPlayer.addPlayer(newPlayer);

            Contract newContract = new Contract(team.getId(), newPlayer.getId());
            DataContract.addContract(newContract);

            team.addPlayer(newPlayer);
            team.addContract(newContract);

            QuestsManager.advanceQuest(Quest.QuestCategory.PLAYERS_BUY, 1);

            MailManager.sendMail(HRSLanguages.Mail.getBuyPlayerSubjectText(), HRSLanguages.Mail.getBuyPlayerContentText(player.getName(), playerValue), this.view.model.getCurrentDate().toString());

            this.saveGame();

            return true;
        }

        return false;
    }

    /**
     * Méthode pour vendre un Player.
     *
     * @param player Le Player à vendre
     *
     * @param value Valeur du joueur
     *
     * @return Un boolean pour savoir si l'opération à réussi
     *
     * @since 0.1
     */
    public boolean soldPlayer(Player player, long value) {
        Manager manager = this.model.getManager();
        Team team = this.model.getTeam();
        Contract contract = team.getContractPlayerId(player.getId());

        try {
            DataPlayer.removePlayer(player, team.getId());

            team.removePlayer(player);
            team.removeContract(contract);

            manager.setMoney(manager.getMoney() + value);

            this.saveGame();

            return true;
        } catch (SQLException e) {
            System.err.println("[ERREUR] Impossible de vendre le joueur : " + e.getMessage());
        }

        return false;
    }

    /**
     * Méthode pour poursuivre un Contract.
     *
     * @param name Nom du Player
     *
     * @param contract Contract à prolonger
     *
     * @return Un boolean pour savoir si l'opération à réussi
     *
     *
     * @since 0.1
     */
    public boolean restaureContract(String name, Contract contract) {
        Manager manager = this.model.getManager();
        int cost = contract.getPrice();
        if (manager.getMoney() >= cost) {
            manager.setMoney(manager.getMoney() - cost);
            contract.setDuration(contract.getDuration() + 10);

            MailManager.sendMail(HRSLanguages.Mail.getExtensionContractSubjectText(), HRSLanguages.Mail.getExtensionContractContentText(name), this.model.getCurrentDate().toString());

            this.updateDatas();

            return true;
        }
        return false;
    }

    /**
     * Méthode pour mettre à jour les données du model.
     * 
     * 
     * @since 0.2
     */
    public void updateDatas() {
        try {
            this.model.updateDatas();
            this.saveGame();
        } catch (SQLException e) {
            System.err.println("[ERREUR] Problème lors de la mise à jour : " + e);
        }
    }

    /**
     * Méthode pour sauvegarder toutes les données avec la BDD.
     * 
     * @return Un boolean pour savoir si la sauvegarde a été effectuée
     * 
     * @since 0.2
     */
    public boolean saveGame() {
        try {
            this.saveComposition();
            
            if (this.model.getTeam() != null && this.model.getManager() != null) {
                for (Player player : this.model.getTeam().getPlayers()) {
                    if (player.isModified()) {
                        DataPlayer.addPlayer(player);
                        player.setModified(false);
                    }
                }

                for (Contract contract : this.model.getTeam().getContracts()) {
                    if (contract.isModified()) {
                        DataContract.addContract(contract);
                        contract.setModified(false);
                    }
                }

                if (this.model.getTeam().isModified()) {
                    DataTeam.addTeam(this.model.getTeam());
                    this.model.getTeam().setModified(false);
                }

                if (this.model.getManager().isModified()) {
                    DataManager.addManager(this.model.getManager());
                    this.model.getManager().setModified(false);
                }

                for (Infrastructure infrastructure : this.model.getAllInfrastructures()) {
                    if (infrastructure.isModified()) {
                        UserConfig.setInfrastructureLevel(infrastructure);
                        infrastructure.setModified(false);
                    }
                }

                return true;
            }
        } catch (SQLException e) {
            System.err.println("[ERREUR] Problème dans la sauvegarde du jeu : " + e);
        }
        return false;
    }

    /**
     * Méthode pour sauvegarder la composition d'une équipe.
     *
     * @since 0.2
     */
    public void saveComposition() {
        if (this.model != null && this.model.getTeam() != null) {
            ArrayList<Integer> startersList = new ArrayList<>();
            for (Player player : this.model.getTeam().getStarters()) {
                startersList.add(player.getId());
            }
            UserConfig.setStarters(startersList);
        }
    }

    /**
     * Méthode pour cloturer une quête.
     *
     * @param quest quête cloturer
     *
     * @since 0.2
     */
    public void claimQuest(Quest quest) {
        Manager manager = this.view.model.getManager();
        manager.setMoney(manager.getMoney() + quest.getReward());

        QuestsManager.advanceQuest(Quest.QuestCategory.QUESTS_FINISH, 1);
        quest.levelUp();

        this.saveGame();
    }

    /**
     * Méthode pour changer l'état d'un Player.
     *
     * @param player Le Player
     *
     * @param injuryChance Chance d'être blessé
     *
     * @param tiredChance Chance d'être fatigué
     *
     * @since 0.2
     */
    protected void changerEtat(Player player, double injuryChance, double tiredChance) {
        Random rd = new Random();

        if (rd.nextDouble() < injuryChance) {
            player.setState(Player.Status.INJURY);
        } else if (rd.nextDouble() < tiredChance) {
            player.setState(Player.Status.TIRED);
        }
    }
}
