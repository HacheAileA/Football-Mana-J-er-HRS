package com.kahrs.model;

/**
 * Classe Infrastructure permettant de représenter une infrastructure.
 *
 * @author Hugo ARNAUD
 *
 * @since 0.2
 *
 * @version 0.1
 */
public abstract class Infrastructure {

    /** Statistique de l'entrainement **/
    public enum TrainingStat {
        /** Stat de vitesse */ SPEED,
        /** Stat de tir */     SHOOT,
        /** Stat de passe */   PASS,
        /** Stat de santé */   STATUS
    }

    /** Type de l'infrastructure **/
    public enum InfrastructureType {
        /** Infrastructure pour la vitesse */ RACE_TRACK,
        /** Infrastructure pour les passes */ FIELD,
        /** Infrastructure pour les tirs */   CAGE,
        /** Infrastructure pour la santé */   RELAXATION,
        /** Infrastructure pour la santé */   HEALTH
    }

    // ==================== ATTRIBUTS ====================
    /** Le niveau de l'infrastructure **/
    protected int level;
    /** Le prix de l'entrainement **/
    protected int trainingCost;
    /** Le prix de l'amélioration **/
    protected int upgradeCost;
    /** Niveau gagné **/
    protected int exp;
    /** Statistique de l'entrainement **/
    protected TrainingStat trainingStat;
    /** Type de l'infrastructure **/
    protected InfrastructureType infrastructureType;
    /** Etat d'amélioration **/
    protected boolean isUpgradable;
    /** Etat de modification **/
    private boolean isModified;

    // ================== CONSTRUCTEUR ==================

    /**
     * Constructeur de Infrastructure.
     *
     * @param level Niveau de l'infrastructure
     * @param trainingStat Statistique de l'entrainement
     * @param infrastructureType Type de l'infrastructure
     *
     * @since 0.2
     */
    public Infrastructure(int level, TrainingStat trainingStat, InfrastructureType infrastructureType) {
        this.level = level;
        this.trainingCost = this.getTrainingCost();
        this.upgradeCost = this.getUpgradeCost();
        this.exp = this.getExp();
        this.trainingStat = trainingStat;
        this.infrastructureType = infrastructureType;
        this.isUpgradable = true;

        this.isModified = false;
    }

    /**
     * Constructeur de Infrastructure.
     *
     * @param trainingStat Statistique de l'entrainement
     * @param infrastructureType Type de l'infrastructure
     *
     * @since 0.2
     */
    public Infrastructure(TrainingStat trainingStat, InfrastructureType infrastructureType) {
        this.level = 0;
        this.trainingCost = this.getTrainingCost();
        this.upgradeCost = 0;
        this.exp = 0;
        this.trainingStat = trainingStat;
        this.infrastructureType = infrastructureType;
        this.isUpgradable = false;
    }

    // ==================== ACCESSEURS ===================

    /**
     * Getter pour récuperer le niveau de l'infrastructure.
     *
     * @return Le niveau de l'infrastructure
     *
     * @since 0.2
     */
    public int getLevel() {
        return this.level;
    }

    /**
     * Getter pour récuperer le prix de l'entrainement.
     *
     * @return Le prix de l'entrainement
     *
     * @since 0.2
     */
    public int getTrainingCost() {
        this.calculTrainingCost();
        return this.trainingCost;
    }

    /**
     * Méthode pour calculer le prix de l'entrainement.
     *
     * @since 0.2
     */
    private void calculTrainingCost() {
        int cost = 1500;
        int players = 11;
        this.trainingCost = Integer.max(0, this.level * cost) * players;
    }

    /**
     * Getter pour récuperer le prix de l'amélioration.
     *
     * @return Le prix de l'amélioration
     *
     * @since 0.2
     */
    public int getUpgradeCost() {
        if (this.isUpgradable) {
            this.calculUpgradeCost();
            return this.upgradeCost;
        }
        return 0;
    }

    /**
     * Méthode pour calculer le prix de l'amélioration.
     *
     * @since 0.2
     */
    private void calculUpgradeCost() {
        int cost = 10000;
        this.upgradeCost = Integer.max(0, this.level * cost);
    }

    /**
     * Getter pour récuperer le niveau gagné.
     *
     * @return Niveau gagné
     *
     * @since 0.2
     */
    public int getExp() {
        this.calculExpGain();
        return this.exp;
    }

    /**
     * Méthode pour calculer le niveeau gagné.
     *
     * @since 0.2
     */
    private void calculExpGain() {
        int pack = 3;
        this.exp = Integer.max(0, (this.level - 1) / pack + 1);
    }

    /**
     * Getter pour récuperer la statistique de l'entrainement.
     *
     * @return Statistique de l'entrainement
     *
     * @since 0.2
     */
    public TrainingStat getTrainingStat() {
        return this.trainingStat;
    }

    /**
     * Getter pour récuperer le type de l'infrastructure.
     *
     * @return Type de l'infrastructure
     *
     * @since 0.2
     */
    public InfrastructureType getInfrastructureType() {
        return this.infrastructureType;
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
     * @since 0.1
     */
    public void setModified(boolean modified) {
        this.isModified = modified;
    }

    /**
     * Getter pour récuperer l'état d'amélioration.
     *
     * @return Etat d'amélioration
     *
     * @since 0.2
     */
    public boolean isUpgradable() {
        return this.isUpgradable;
    }

    // ==================== METHODES =====================

    /**
     * Méthode pour améliorer l'infrastructure.
     *
     * @since 0.2
     */
    public void upgradeInfrastructure() {
        this.level++;
        this.calculTrainingCost();
        this.calculUpgradeCost();
        this.calculExpGain();
        this.setModified(true);
    }
}

/**
 * Classe RaceTrack permettant de représenter une infrastructure de type RaceTrack.
 */
class RaceTrack extends Infrastructure {

    // ================== CONSTRUCTEUR ==================

    /**
     * Constructeur de RaceTrack.
     *
     * @param level Niveau de l'infrastructure
     *
     * @since 0.2
     */
    public RaceTrack(int level) {
        super(level, TrainingStat.SPEED, InfrastructureType.RACE_TRACK);
    }
}

/**
 * Classe Field permettant de représenter une infrastructure de type Field.
 */
class Field extends Infrastructure {

    // ================== CONSTRUCTEUR ==================

    /**
     * Constructeur de Field.
     *
     * @param level Niveau de l'infrastructure
     *
     * @since 0.2
     */
    public Field(int level) {
        super(level, TrainingStat.PASS, InfrastructureType.FIELD);
    }
}

/**
 * Classe Cage permettant de représenter une infrastructure de type Cage.
 */
class Cage extends Infrastructure {

    // ================== CONSTRUCTEUR ==================

    /**
     * Constructeur de Cage.
     *
     * @param level Niveau de l'infrastructure
     *
     * @since 0.2
     */
    public Cage(int level) {
        super(level, TrainingStat.SHOOT, InfrastructureType.CAGE);
    }
}

/**
 * Classe RelaxationArea permettant de représenter une infrastructure de type RelaxationArea.
 */
class RelaxationArea extends Infrastructure {

    // ================== CONSTRUCTEUR ==================

    /**
     * Constructeur de RelaxationArea.
     *
     * @since 0.2
     */
    public RelaxationArea() {
        super(TrainingStat.STATUS, InfrastructureType.RELAXATION);
    }

    // ==================== ACCESSEURS ===================

    /**
     * Méthode pour calculer le prix de l'entrainement.
     *
     * @since 0.2
     */
    @Override
    public int getTrainingCost() {
        int cost = 1000;
        int players = 11;
        return cost * players;
    }
}

/**
 * Classe HealthArea permettant de représenter une infrastructure de type HealthArea.
 */
class HealthArea extends Infrastructure {

    // ================== CONSTRUCTEUR ==================

    /**
     * Constructeur de HealthArea.
     *
     * @since 0.2
     */
    public HealthArea() {
        super(TrainingStat.STATUS, InfrastructureType.HEALTH);
    }

    // ==================== ACCESSEURS ===================

    /**
     * Méthode pour calculer le prix de l'entrainement.
     *
     * @since 0.2
     */
    @Override
    public int getTrainingCost() {
        int cost = 1500;
        int players = 11;
        return cost * players;
    }
}