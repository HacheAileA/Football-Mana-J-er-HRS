package com.kahrs.controller;

import java.util.ArrayList;

import com.kahrs.app.MailManager;
import com.kahrs.app.QuestsManager;
import com.kahrs.model.Infrastructure;
import com.kahrs.model.Manager;
import com.kahrs.model.Player;
import com.kahrs.model.Quest;
import com.kahrs.model.Infrastructure.InfrastructureType;

/**
 * Classe InfrastructureController gérant le système d'infrastructure.
 *
 * @author Hugo ARNAUD
 *
 * @since 0.2
 *
 * @version 0.1
 */
public class InfrastructureController {

    // ==================== ATTRIBUTS ====================

    /** Controller du jeu */
    private GameController controller;
    /** Chance d'être blessé */
    private double injuryChance = 0.01;
    /** Chance d'être fatigué */
    private double tiredChance = 0.0;

    // ================== CONSTRUCTEUR ==================

    /**
     * Constructeur de InfrastructureController.
     *
     * @param controller Controller du jeu
     *
     * @since 0.2
     */
    public InfrastructureController(GameController controller) {
        this.controller = controller;
    }

    // ==================== METHODES =====================

    /**
     * Méthode améliorer une infrastructure.
     *
     * @param infrastructure L'infrastructure
     *
     * @return Un boolean pour savoir si l'opération à réussi
     *
     * @since 0.2
     */
    public boolean upgradeInfrastructure(Infrastructure infrastructure) {
        if (infrastructure.isUpgradable()) {
            Manager manager = this.controller.model.getManager();
            int cost = infrastructure.getUpgradeCost();
            if (manager.getMoney() >= cost) {
                manager.setMoney(manager.getMoney() - cost);
                infrastructure.upgradeInfrastructure();

                QuestsManager.advanceQuest(Quest.QuestCategory.UPGRADE_BUILDING, 1);

                this.controller.updateDatas();

                return true;
            }
        }

        return false;
    }

    /**
     * Méthode pour améliorer une équipe via une infrastructure.
     *
     * @param infrastructure L'infrastructure
     *
     * @return Un boolean pour savoir si l'opération à réussi
     *
     * @since 0.2
     */
    public boolean training(Infrastructure infrastructure) {
        Manager manager = this.controller.model.getManager();
        int cost = infrastructure.getTrainingCost();
        if (manager.getMoney() >= cost) {
            manager.setMoney(manager.getMoney() - cost);

            for (Player player : this.controller.model.getTeam().getStarters()) {
                this.upgradePlayer(player, infrastructure.getTrainingStat(), infrastructure.getExp());
                this.controller.changerEtat(player, this.injuryChance, this.tiredChance);
            }
            
            String contenuMail = "Entraînement";

            MailManager.sendMail("Récapitulatif de l'entraînement", contenuMail, this.controller.model.getCurrentDate().toString());

            QuestsManager.advanceQuest(Quest.QuestCategory.TRAINING, 1);
            this.controller.updateDatas();

            return true;
        }

        return false;
    }

    /**
     * Méthode pour soigner les joueurs(bléssés ou fatigués) via une infrastructure.
     *
     * @param infrastructure L'infrastructure
     *
     * @return Un boolean pour savoir si l'opération à réussi
     *
     * @since 0.2
     */
    public boolean cure(Infrastructure infrastructure) {
        Manager manager = this.controller.model.getManager();
        ArrayList<Player> players = this.controller.model.getTeam().getPlayers();
        int cost = infrastructure.getTrainingCost();
        
        boolean needsCure = false;
        for (Player player : players) {
            if (player.getStatus() == Player.Status.INJURY || player.getStatus() == Player.Status.TIRED) {
                needsCure = true;
                break;
            }
        }
        if (!needsCure) {
            return false;
        }

        if (manager.getMoney() >= cost) {
            manager.setMoney(manager.getMoney() - cost);

            for (Player player : players) {
                if (player.getStatus() == Player.Status.INJURY && infrastructure.getInfrastructureType() == InfrastructureType.HEALTH || player.getStatus() == Player.Status.TIRED && infrastructure.getInfrastructureType() == InfrastructureType.RELAXATION) {
                    player.setState(Player.Status.GOOD);
                }
            }

            this.controller.updateDatas();

            return true;
        }

        return false;
    }

    /**
     * Méthode pour améliorer un joueur.
     *
     * @param player Le joueur
     * @param stat La stat amelioré
     * @param exp Niveau gagné
     *
     * @since 0.2
     */
    private void upgradePlayer(Player player, Infrastructure.TrainingStat stat, int exp) {
        switch (stat) {
            case SPEED:
                player.setSpeed(Math.min(99, player.getSpeed() + exp));
                break;
            case SHOOT:
                player.setShoot(Math.min(99, player.getShoot() + exp));
                break;
            case PASS:
                player.setPass(Math.min(99, player.getPass() + exp));
                break;
            default:
                break;
        }
    }
}
