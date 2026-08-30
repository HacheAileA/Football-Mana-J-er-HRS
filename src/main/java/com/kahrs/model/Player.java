package com.kahrs.model;

/**
 * Classe Player implémentant un joueur.
 * 
 * @author Hugo ARNAUD
 * 
 * 
 * @since 0.1
 * 
 * @version 0.1
 */
public class Player {

    /**
     * Enum pour les postes.
     * 
     * @since 0.1
     */
    public enum Poste {
        /** Gardien de But*/ GB,
        /** Défenseur*/      DEF,
        /** Milieu*/         MIL,
        /** Attaquant*/      ATT
    }

    /**
     * Enum pour l'état de santé.
     * 
     * @since 0.2
     */
    public enum Status {
        /** Rien */     GOOD,
        /** Fatigué */  TIRED,
        /** Blessé */   INJURY
    }

    // ==================== ATTRIBUTS ====================

    /** L'id du joueur */private int id;
    /** Le nom du joueur */private final String name;

    /** Le poste de jeu du joueur */private final Poste position;
    
    /** Etat de santé du Player*/private Status state;

    /** Note générale du Player*/private Integer note;
    /** Valeur du Player*/private Integer value;

    /** Note d'attaque du Player */private Integer attack;
    /** Note de défense du Player */private Integer defense;
    /** Note de vitesse du Player */private Integer speed;
    /** Note de tir du Player*/private Integer shoot;
    /** Note de passe du Player */private Integer pass;

    /** Etat de modification **/private boolean isModified;

    // ================== CONSTRUCTEURS ==================

    /**
     * Constructeur de Player pour créer un objet avec tous ses attributs.
     * 
     * @param id       L'id du Player
     * @param name     Le nom du Player
     * @param position Le Poste du Player
     * @param state   L'état de santé du Player
     * @param note     La note générale du Player
     * @param value    La valeur du Player
     * @param attack   La note d'attaque du Player
     * @param defense  La note de défense du Player
     * @param speed    La note de vitesse du Player
     * @param shoot    La note de tir du Player
     * @param pass     La note de passe du Player
     * 
     * @since 0.1
     */
    public Player(int id, String name, Poste position, Status state, Integer note, Integer value, Integer attack, Integer defense, Integer speed, Integer shoot, Integer pass) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.state = state;
        this.note = note;
        this.value = value;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
        this.shoot = shoot;
        this.pass = pass;

        this.isModified = false;
    }

    /**
     * Constructeur de Player pour créer un objet avec tous ses attributs sauf id (Integer).
     * 
     * @param name     Le nom du Player
     * @param position Le Poste du Player
     * @param state   L'état de santé du Player
     * @param note     La note générale du Player
     * @param value    La valeur du Player
     * @param attack   La note d'attaque du Player
     * @param defense  La note de défense du Player
     * @param speed    La note de vitesse du Player
     * @param shoot    La note de tir du Player
     * @param pass     La note de passe du Player
     * 
     * @since 0.1
     */
    public Player(String name, Poste position, Status state,Integer note, Integer value, Integer attack, Integer defense, Integer speed, Integer shoot, Integer pass) {
        this(0, name, position, state, note, value, attack, defense, speed, shoot, pass);

        this.isModified = true;
    }

    // ==================== METHODES ===================

    /**
     * Méthode pour calculer la note générale du Player
     *
     * @return La note générale du Player
     *
     * @since 0.1
     */
    private Integer calculNote() {
        Integer noteDef = 0;
        
        Integer[] stats = {this.attack, this.defense};
        for (Integer stat : stats) {
            noteDef += stat;
        }

        return (int) (60 + (noteDef / stats.length) * 0.4);
    }

    /**
     * Méthode pour calculer la note générale du Player
     *
     * @return La valeur du Player
     * 
     *
     * @since 0.1
     *
     */
    private Integer calculValue() {
        return (int) Math.pow(1.2, this.note);
    }

    /**
     * Méthode pour calculer la note d'attaque du Player
     *
     * @return la note d'attaque du Player
     *
     * @since 0.1
     *
     */
    private Integer calculAttack() {
        Integer attackDef = 0;

        Integer[] attackStats = {this.speed, this.shoot};
        for (Integer stat : attackStats) {
            attackDef += stat;
        }
        return (int) (attackDef / attackStats.length);
    }

    /**
     * Méthode pour calculer la note de défense du Player
     *
     * @return la note de défense du Player
     *
     * @since 0.1
     *
     */
    private Integer calculDefense() {
        Integer defenseDef = 0;

        Integer[] defenseStats = {this.speed, this.pass};
        for (Integer stat : defenseStats) {
            defenseDef += stat;
        }
        return (int) (defenseDef / defenseStats.length);
    }

    // ==================== ACCESSEURS ===================

    /**
     * Getter pour récuperer l'id du Player.
     * 
     * @return L'id
     * 
     * @since 0.1
     */
    public int getId() {
        return this.id;
    }

    /**
     * Setter pour définir l'id du Player.
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
     * Getter pour récuperer le nom du Player.
     * 
     * @return Le nom
     * 
     * @since 0.1
     */
    public String getName() {
        return this.name;
    }

    /**
     * Getter pour récuperer le Poste du Player.
     * 
     * @return Le Poste
     * 
     * @since 0.1
     */
    public Poste getPosition() {
        return this.position;
    }

    /**
     * Getter pour récuperer l'état de santé du Player.
     * 
     * @return L'état de santé
     * 
     * @since 0.1
     */
    public Status getStatus() {
        return this.state;
    }

    /**
     * Setter pour définir l'état de santé du Player.
     * 
     * @param state Le nouvel état de santé
     * 
     * @since 0.1
     */
    public void setState(Status state) {
        this.state = state;
        this.setModified(true);
    }

    /**
     * Getter pour récuperer la note générale du Player.
     * 
     * @return La note
     * 
     * @since 0.1
     */
    public Integer getNote() {
        return this.note;
    }

    /**
     * Méthode pour mettre à jour la note du Player
     *
     *
     * @since 0.1
     *
     */
    private void updateNote() {
        this.note = this.calculNote();
        this.updateValue();
    }

    /**
     * Getter pour récuperer la valeur du Player.
     * 
     * @return La valeur
     * 
     * @since 0.1
     */
    public Integer getValue() {
        return this.value;
    }

    /**
     * Méthode pour mettre à jour la valeur du Player
     *
     *
     * @since 0.1
     *
     */
    private void updateValue() {
        this.value = this.calculValue();
    }

    /**
     * Getter pour récuperer la note d'attaque du Player.
     * 
     * @return La note d'attaque
     * 
     * @since 0.1
     */
    public Integer getAttack() {
        return this.attack;
    }

    /**
     * Méthode pour mettre à jour la note d'attaque du Player.
     * 
     *
     * @since 0.2
     */
    private void updateAttack() {
        this.attack = this.calculAttack();
        this.updateNote();
    }

    /**
     * Setter pour définir la note d'ataque du Player.
     * Et met à jour la note et la valeur du player
     *
     *
     * @param attack La nouvelle note
     * 
     * @since 0.1
     */
    public void setAttack(Integer attack) {
        this.attack = attack;
        this.updateNote();
        this.setModified(true);
    }

    /**
     * Getter pour récuperer la note de défense du Player.
     *
     *
     * @return La note de défense
     * 
     * @since 0.1
     */
    public Integer getDefense() {
        return this.defense;
    }

    /**
     * Méthode pour mettre à jour la note de défense du Player.
     * 
     *
     * @since 0.2
     */
    private void updateDefense() {
        this.defense = this.calculDefense();
        this.updateNote();
    }

    /**
     * Setter pour définir la note de défense du Player.
     * Et met à jour la note du player
     *
     *
     * @param defense La nouvelle note
     * 
     * @since 0.1
     */
    public void setDefense(Integer defense) {
        this.defense = defense;
        this.updateNote();
        this.setModified(true);
    }

    /**
     * Getter pour récuperer la note de vitesse du Player.
     *
     * @return La note de vitesse
     * 
     * @since 0.2
     */
    public Integer getSpeed() {
        return this.speed;
    }

    /**
     * Setter pour définir la note de vitesse du Player.
     * Et met à jour la note du player
     *
     *
     * @param speed La nouvelle note de vitesse
     * 
     * @since 0.2
     */
    public void setSpeed(Integer speed) {
        this.speed = speed;
        this.updateAttack();
        this.updateDefense();
        this.setModified(true);
    }

    /**
     * Getter pour récuperer la note de tir du Player.
     *
     * @return La note de tir
     * 
     * @since 0.2
     */
    public Integer getShoot() {
        return this.shoot;
    }

    /**
     * Setter pour définir la note de tir du Player.
     * Et met à jour la note du player
     *
     *
     * @param shoot La nouvelle note de tir
     * 
     * @since 0.2
     */
    public void setShoot(Integer shoot) {
        this.shoot = shoot;
        this.updateAttack();
        this.setModified(true);
    }

    /**
     * Getter pour récuperer la note de passe du Player.
     *
     * @return La note de passe
     * 
     * @since 0.2
     */
    public Integer getPass() {
        return this.pass;
    }

    /**
     * Setter pour définir la note de passe du Player.
     * Et cascade la mise à jour vers la défense et la note.
     *
     *
     * @param pass La nouvelle note de passe
     * 
     * @since 0.2
     */
    public void setPass(Integer pass) {
        this.pass = pass;
        this.updateDefense();
        this.setModified(true);
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

    /**
     * Getter pour récuperer le nom du player.
     *
     * @return nom du player
     *
     * @since 0.2
     */
    @Override
    public String toString() {
        return this.name; 
    }
}
