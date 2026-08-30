package com.kahrs.model;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Classe Team implémentant une équipe.
 * 
 * @author Hugo ARNAUD
 * 
 * @since 0.1
 * 
 * @version 0.2
 */
public class Team {

    // ==================== ATTRIBUTS ====================

    /** L'id de l'équipe */
    private int id;

    /** Le nom de l'équipe */
    private final String name;

    /** Boolean pour savoir si l'équipe est détenue par un utilisateur ou un bot */
    private final boolean bot;

    /** Liste des joueurs titulaires de l'équipe */
    private ArrayList<Player> starters;
    /** Liste des joueurs remplaçants de l'équipe */
    private ArrayList<Player> substitutes;

    /** Liste des contrats */
    private ArrayList<Contract> contracts;

    /** Etat de modification */
    private boolean isModified;

    // ================== CONSTRUCTEURS ==================

    /**
     * Constructeur de Team pour créer un objet avec un id (int), un nom (String) et un attribut bot (boolean).
     * 
     * @param id   L'id de la Team
     * @param name Le nom de la Team
     * @param bot  La Team est un bot ?
     * 
     * 
     * @since 0.1
     */
    public Team(int id, String name, boolean bot) {
        this.id = id;
        this.name = name;
        this.bot = bot;
        this.starters = new ArrayList<>();
        this.substitutes = new ArrayList<>();
        this.contracts = new ArrayList<>();

        this.isModified = false;
    }

    /**
     * Constructeur de Team pour créer un objet avec un nom (String) et un attribut bot (boolean).
     * 
     * @param name Le nom de la Team
     * @param bot  La Team est un bot ?
     * 
     * 
     * @since 0.1
     */
    public Team(String name, boolean bot) {
        this(0, name, bot);

        this.isModified = true;
    }

    /**
     * Constructeur de Team pour créer un objet avec un nom (String).
     * 
     *  @param name Le nom de la Team
     * 
     * 
     * @since 0.1
     */
    public Team(String name) {
        this(name, false);
    }

    // ==================== ACCESSEURS ===================

    /**
     * Getter pour récuperer l'id de la Team.
     * 
     * @return L'id
     * 
     * @since 0.1
     */
    public int getId() {
        return this.id;
    }

    /**
     * Setter pour définir l'id de la Team.
     * 
     * @param id Le nouvel id
     * 
     * @since 0.1
     */
    public void setId(int id) {
        this.id = id;
        this.setModified(true);
    }

    /**
     * Getter pour récuperer le nom de la Team.
     * 
     * @return Le nom
     * 
     * @since 0.1
     */
    public String getName() {
        return this.name;
    }

    /**
     * Getter pour savoir si la Team est un bot.
     * 
     * @return Le boolean bot
     * 
     * @since 0.1
     */
    public boolean isBot() {
        return this.bot;
    }

    /**
     * Méthode pour récuperer la liste de tous les joueurs.
     * 
     * @return La liste de tous les joueurs
     * 
     * @since 0.2
     */
    public ArrayList<Player> getPlayers() {
        ArrayList<Player> allPlayers = new ArrayList<>();
        allPlayers.addAll(this.starters);
        allPlayers.addAll(this.substitutes);
        return allPlayers;
    }

    /**
     * Méthode pour récuperer un joueur via son id.
     *
     * @param id id joueur
     *
     * @return joueur
     *
     * @since 0.2
     */
    private Player getPlayerById(Integer id) {
        for (Player player : this.getPlayers()) {
            if (player.getId() == id) {
                return player;
            }
        }
        return null;
    }

    /**
     * Filtre la liste des joueur pour ne garder que ceux d'un poste précis.
     * @param poste Le poste (GB, DEF, MIL, ATT).
     * @param isStarter Le joueur est un titulaire ?
     * @return Une liste de joueurs titulaires correspondant au poste demandé.
     */
    public ArrayList<Player> getPlayersByPosition(Player.Poste poste, boolean isStarter) {
        if (isStarter) {
            return this.starters.stream().filter(p -> p.getPosition() == poste).collect(Collectors.toCollection(ArrayList::new));
        }
        return this.substitutes.stream().filter(p -> p.getPosition() == poste).collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Filtre la liste des joueur pour ne garder que ceux d'un état précis.
     * @param status Le poste (GOOD,TIRED,INJURY)
     * @param isStarter Le joueur est un titulaire ?
     * @return Une liste de joueurs titulaires correspondant à l'état demandé.
     */
    public ArrayList<Player> getPlayersByStatus(Player.Status status, boolean isStarter) {
        if (isStarter) {
            return this.starters.stream().filter(p -> p.getStatus() == status).collect(Collectors.toCollection(ArrayList::new));
        }
        return this.substitutes.stream().filter(p -> p.getStatus() == status).collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Getter pour récuperer la liste des titualires de la Team.
     * 
     * @return La liste des titulaires
     * 
     * @since 0.2
     */
    public ArrayList<Player> getStarters() {
        return this.starters;
    }

    /**
     * Setter pour définir la liste des titualires de la Team.
     *
     * @param startersId La nouvelle liste des titualires de la Team
     *
     * @since 0.2
     */
    public void setStarters(ArrayList<Integer> startersId) {
        this.resetStarters();
        for (Integer starterId : startersId) {
            Player p = this.getPlayerById(starterId);
            if (p != null) {
                this.switchPlayers(null, p);
            }
        }
    }

    /**
     * Getter pour récuperer la liste des remplaçants de la Team.
     * 
     * @return La liste des remplaçants
     * 
     * @since 0.2
     */
    public ArrayList<Player> getSubstitutes() {
        return this.substitutes;
    }

    /**
     * Getter pour récuperer la liste des Contracts.
     * 
     * @return La liste des Contracts
     * 
     * @since 0.2
     */
    public ArrayList<Contract> getContracts() {
        return this.contracts;
    }

    /**
     * Méthode pour récuperer le contract d'un joueur via son id.
     *
     * @param player_id id du joueur
     *
     * @return Le contract du joueur
     *
     * @since 0.2
     */
    public Contract getContractPlayerId(int player_id) {
        for (Contract contract : this.contracts) {
            if (contract.getPlayerId() == player_id) {
                return contract;
            }
        }
        return null;
    }

    /**
     * Getter pour récuperer l'état de modification.
     *
     * @return Etat de modification
     *
     * @since 0.2
     */
    public boolean isModified() {
        return this.isModified;
    }

    /**
     * Setter pour définir l'état de modification.
     *
     * @param modified  La nouvel état de modification
     *
     * @since 0.2
     */
    public void setModified(boolean modified) {
        this.isModified = modified;
    }

    // ==================== METHODES =====================

    /**
     * Méthode pour ajouter un Player à la Team.
     * 
     * @param player Le Player à ajouter
     * 
     * @return Un boolean pour savoir si le Player a été ajouté
     * 
     * 
     * @since 0.1
     */
    public boolean addPlayer(Player player) {
        if (!this.substitutes.contains(player)) {
            this.substitutes.add(player);
            this.setModified(true);
            return true;
        }
        return false;
    }

    /**
     * Méthode pour ajouter plusieurs Player à la Team.
     * 
     * @param playersList Les Player à ajouter
     * 
     * 
     * @since 0.1
     */
    public void addPlayers(ArrayList<Player> playersList) {
        for (Player player : playersList) {
            this.addPlayer(player);
        }
    }

    /**
     * Méthode pour supprimer un Player de la Team.
     *
     * @param player Player à supprimer
     *
     * @return boolean
     *
     * @since 0.1
     */
    public boolean removePlayer(Player player) {
        if (this.getPlayers().contains(player)) {
            if (this.starters.contains(player)) {
                this.starters.remove(player);
            } else {
                this.substitutes.remove(player);
            }
            return true;
        }
        return false;
        
    }

    /**
     * Méthode pour changer la titularisation de deux joueurs.
     *
     * @param starter Joueur titulaire
     * @param substitute Joueur remplaçant
     *
     * @since 0.1
     */
    public void switchPlayers(Player starter, Player substitute) {
        if (starter != null) {
            this.starters.remove(starter);
            this.substitutes.add(starter);
        }

        this.substitutes.remove(substitute);
        this.starters.add(substitute);
    }

    /**
     * Méthode pour remettre tous les titulaires sur le banc des remplaçants.
     * 
     * @since 0.2
     */
    public void resetStarters() {
        this.substitutes.addAll(this.starters);
        this.starters.clear();
        this.setModified(true);
    }

    /**
     * Méthode pour ajouter un contract
     *
     * @param contract Contract à ajouter
     *                 .
     * @return boolean
     *
     * @since 0.2
     */
    public boolean addContract(Contract contract) {
        if (!this.contracts.contains(contract)) {
            this.contracts.add(contract);
            this.setModified(true);
            return true;
        }
        return false;
    }

    /**
     * Méthode pour ajouter plusieurs contracts à la Team.
     *
     * @param contractsList Les contracts à ajouter
     *
     * @since 0.1
     */
    public void addContracts(ArrayList<Contract> contractsList) {
        for (Contract contract : contractsList) {
            this.addContract(contract);
        }
    }

    /**
     * Méthode pour supprimer un contract
     *
     * @param contract Contract à supprimer
     *                 .
     * @return boolean
     *
     * @since 0.2
     */
    public boolean removeContract(Contract contract) {
        if (this.contracts.contains(contract)) {
            this.contracts.remove(contract);
            return true;
        }
        return false;
    }

    /**
     * Vérifie si l'équipe possède exactement 11 titulaires pour débuter un match.
     * @return true si l'effectif est complet (11 joueurs), false sinon.
     * @since 0.2
     */
    public boolean isReadyForMatch() {
        return this.starters.size() == 11;
    }

    /**
     * Vérifie si l'équipe possède exactement un titulaire blessé.
     * @return true si un titulaire est blessé, false sinon.
     * @since 0.2
     */
    public boolean hasInjuredPlayer() {
        for (Player player : this.starters) {
            if (player.getStatus() == Player.Status.INJURY) {
                return true;
            }
        }
        return false;
    }

    /**
     * Méthode qui vérifié si on peut vendre un joueur selon son poste et le minimun requis pour celui-ci
     *
     * @param poste   Poste
     *              .
     * @return boolean
     *
     * @since 0.2
     */
    public boolean canSold(Player.Poste poste) {
        ArrayList<Player> list = new ArrayList<>();
        list.addAll(this.getPlayersByPosition(poste, true));
        list.addAll(this.getPlayersByPosition(poste, false));

        int startersCount = this.getPlayersByPosition(poste, true).size();
        int[] nbsMin = {1, 4, 4, 2};

        switch (poste) {
            case GB:  return list.size() > Math.max(nbsMin[0], startersCount);
            case DEF: return list.size() > Math.max(nbsMin[1], startersCount);
            case MIL: return list.size() > Math.max(nbsMin[2], startersCount);
            case ATT: return list.size() > Math.max(nbsMin[3], startersCount);
            default: return false;
        }
    }

    /**
     * Méthode pour comparer un objet avec this
     * @param obj   objet à comparer.
     * @return boolean
     * @since 0.2
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        
        Team team = (Team) obj;
        return id == team.id;
    }

    /**
     * Méthode pour renvoyer l'id de la Team en hashcode
     * @return Id de la Team en hashcode
     * @since 0.2
     */
    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
