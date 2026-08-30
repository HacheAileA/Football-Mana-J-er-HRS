package com.kahrs.view.visuals;

import com.kahrs.app.UserConfig;
import com.kahrs.view.GameView;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Classe HRSLanguages qui gère la tradcution.
 *
 * @author Hugo ARNAUD
 * 
 *
 * @since 0.1
 *
 * @version 0.2
 */
public class HRSLanguages {

    /** Constante représentant la langue choisie par l'utilisateur */private static String language = UserConfig.getLanguage();
    /** Gestionnaire de traductions */private static ResourceBundle messages;
    /** Langue */private static Locale locale;
    /** View principale */private static GameView view;

    /** ANGLAIS */public static final String[] ENGLISH = {"EN", "English"};
    /** ESPAGNOL */public static final String[] ESPANOL = {"ES", "Español"};
    /** FRANCAIS */public static final String[] FRENCH = {"FR", "Français"};
    /** ITALIEN */public static final String[] ITALIAN = {"IT", "Italiano"};

    /**
     * Bloc permettant d'initialiser le système de traduction.
     * 
     * 
     * @since 0.2
     */
    static {
        HRSLanguages.loadBundle(language);
        HRSLanguages.setView(null);
    }

    /** Constructeur privé pour résoudre warning de Javadoc */
    private HRSLanguages() {
    }

    /**
     * Méthode permettant de charger le bon fichier de langue.
     * 
     * @param lang La langue du jeu
     * 
     * 
     * @since 0.2
     */
    private static void loadBundle(String lang) {
        HRSLanguages.locale = null;
        
        if (lang.equals(HRSLanguages.ENGLISH[0])) {
            HRSLanguages.locale = Locale.ENGLISH;
        } else if (lang.equals(HRSLanguages.ESPANOL[0])) {
            HRSLanguages.locale = new Locale("es", "ES");
        } else if (lang.equals(HRSLanguages.FRENCH[0])) {
            HRSLanguages.locale = Locale.FRENCH;
        } else if (lang.equals(HRSLanguages.ITALIAN[0])) {
            HRSLanguages.locale = Locale.ITALIAN;
        }

        HRSLanguages.messages = ResourceBundle.getBundle("languages.message", HRSLanguages.locale);
    }

    /**
     * Méthode pour définir la vue.
     * 
     * @param view La GameView principale
     * 
     * @since 0.2
     */
    public static void setView(GameView view) {
        HRSLanguages.view = view;
    }

    /**
     * Méthode pour récupérer la langue.
     * 
     * @return La langue
     * 
     * @since 0.2
     */
    public static Locale getLocale() {
        return HRSLanguages.locale;
    }

    /**
     * Méthode pour mettre à jour la langue.
     * 
     * @param newLanguage La nouvelle langue
     * 
     * 
     * @since 0.1
     */
    public static void updateLanguage(String newLanguage) {
        UserConfig.setLanguage(newLanguage);
        HRSLanguages.language = newLanguage;
        HRSLanguages.loadBundle(newLanguage);
        HRSLanguages.view.updateTexts();
    }

    /**
     * Méthode pour récupérer un texte depuis les fichiers .properties.
     * 
     * @param key  Le nom de la traduction
     * @param args Les variables à afficher
     * 
     * @return Le texte traduit dans la bonne langue
     * 
     * 
     * @since 0.2
     */
    private static String getText(String key, Object... args) {
        String text = HRSLanguages.messages.getString(key);
        if (args != null && args.length > 0) {
            return MessageFormat.format(text, args);
        }
        return text;
    }

    /**
     * Méthode permettant de traduire le texte null.
     * 
     * @return texte traduit
     * 
     * 
     * @since 0.1
     */
    public static String getNullText() {
        return HRSLanguages.getText("global.null");
    }

    /**
     * Méthode permettant de traduire le bouton "Fermer".
     *
     * @return texte traduit
     * 
     *
     * @since 0.1
     */
    public static String getCloseButtonText() {
        return HRSLanguages.getText("global.close");
    }

    /**
     * Classe contenant les méthodes de traduction du panel Calendar
     */
    public static class Calendar {

        /** Constructeur privé pour résoudre warning de Javadoc */
        private Calendar() {
        }

        /**
         * Méthode permettant de traduire le texte du bouton Retour
         * @return Le texte du bouton de retour
         */
        public static String getBackButtonText() {
            return HRSLanguages.getText("calendar.back");
        }

        /**
         * Méthode permettant de traduire le texte "TRAINING".
         *
         * @return texte traduit
         *
         * @since 0.1
         */
        public static String getTrainingText() {
            return HRSLanguages.getText("calendar.training");
        }

        /**
         * Méthode permettant de traduire le texte "REST".
         *
         * @return texte traduit
         *
         * @since 0.1
         */
        public static String getRestText() {
            return HRSLanguages.getText("calendar.rest");
        }

        /**
         * Méthode permettant de traduire le texte "FRIENDLY MATCH".
         *
         * @return texte traduit
         *
         * @since 0.1
         */
        public static String getFriendlyMatchText() {
            return HRSLanguages.getText("calendar.friendly_match");
        }

        /**
         * Méthode permettant de traduire le texte "CHAMPIONSHIP MATCH".
         *
         * @return texte traduit
         *
         * @since 0.1
         */
        public static String getChampionshipMatchText() {
            return HRSLanguages.getText("calendar.championship_match");
        }

        /**
         * Méthode permettant de traduire le texte "MARKET".
         *
         * @return texte traduit
         *
         * @since 0.1
         */
        public static String getMarketText() {
            return HRSLanguages.getText("calendar.market");
        }

        /**
         * Méthode permettant de traduire le texte du mois affiché.
         *
         * @param month Le mois affiché
         *
         * @return texte traduit
         *
         *
         * @since 0.1
         */
        public static String getMonthLabelText(int month) {
            return HRSLanguages.getText("calendar.month." + month);
        }


        /**
         * Méthode permettant de traduire le texte du jour affiché.
         *
         * @param day Le jour affiché
         *
         * @return texte traduit
         *
         *
         * @since 0.1
         */
        public static String getDayLabelText(int day) {
            return HRSLanguages.getText("calendar.day." + day);
        }

    }

    /**
     * Classe contenant les méthodes de traduction du panel Championship
     */
    public static class Championship {

        /** Constructeur privé pour résoudre warning de Javadoc */
        private Championship() {
        }

        /**
         * Méthode pour récupérer le titre du championnat.
         * 
         * @return Le titre traduit
         * 
         * @since 0.2
         */
        public static String getTitle() {
            return HRSLanguages.getText("championship.title");
        }

        /**
         * Méthode pour récupérer le message de redémarrage de saison.
         * 
         * @return Le message de redémarrage traduit
         * 
         * @since 0.2
         */
        public static String getRestartMessageText() {
            return HRSLanguages.getText("championship.restart");
        }
    }

    /**
     * Classe contenant les méthodes de traduction du panel Effective
     */
    public static class Effective {

        /** Constructeur privé pour résoudre warning de Javadoc */
        private Effective() {
        }

        /**
         * Méthode pour récupérer le titre de l'effectif.
         * 
         * @return Le titre traduit
         * 
         * @since 0.2
         */
        public static String getTitle() {
            return HRSLanguages.getText("effective.title");
        }

        /**
         * Méthode pour récupérer le titre de la section titulaires.
         * 
         * @return Le titre traduit
         * 
         * @since 0.2
         */
        public static String getStartersTitle() {
            return HRSLanguages.getText("effective.starters");
        }

        /**
         * Méthode pour récupérer le titre de la section remplaçants.
         * 
         * @return Le titre traduit
         * 
         * @since 0.2
         */
        public static String getSubstitutesTitle() {
            return HRSLanguages.getText("effective.substitutes");
        }

        /**
         * Méthode pour récupérer le texte de proposition de renouvellement de contrat.
         * 
         * @param name Le nom du joueur
         * 
         * @return Le texte traduit
         * 
         * @since 0.2
         */
        public static String getContractUpgradeText(String name) {
            return HRSLanguages.getText("effective.contract_upgrade", name);
        }

        /**
         * Méthode pour récupérer la confirmation du renouvellement de contrat suivi de son coût.
         * 
         * @param name Le nom du joueur
         * @param cost Le coût du renouvellement
         * 
         * @return Le texte de confirmation traduit
         * @since 0.2
         */
        public static String getContractUpgradeConfirmText(String name, int cost) {
            return HRSLanguages.getText("effective.contract_upgrade_confirm", name, cost);
        }

        /**
         * Méthode pour récupérer le libellé général d'une statistique.
         * 
         * @param text Le nom de la statistique
         * @param note La valeur de la note
         * 
         * 
         * @return Le texte de la statistique traduit
         * @since 0.2
         */
        public static String getStatText(String text, Integer note) {
            return HRSLanguages.getText("effective.stat_" + text, note);
        }

        /**
         * Méthode pour récupérer la statistique d'attaque.
         * 
         * @param note La note d'attaque
         * 
         * @return Le texte d'attaque traduit
         * 
         * 
         * @since 0.2
         */
        public static String getStatAttackText(Integer note) {
            return HRSLanguages.getText("effective.stat_attack", note);
        }

        /**
         * Méthode pour récupérer la statistique de défense.
         * 
         * @param note La note de défense
         * 
         * @return Le texte de défense traduit
         * @since 0.2
         */
        public static String getStatDefenseText(Integer note) {
            return HRSLanguages.getText("effective.stat_defense", note);
        }

        /**
         * Méthode pour récupérer la statistique de vitesse.
         * 
         * @param note La note de vitesse
         * 
         * @return Le texte de vitesse traduit
         * @since 0.2
         */
        public static String getStatSpeedText(Integer note) {
            return HRSLanguages.getText("effective.stat_speed", note);
        }

        /**
         * Méthode pour récupérer la statistique de tir.
         * 
         * @param note La note de tir
         * 
         * @return Le texte de tir traduit
         * @since 0.2
         */
        public static String getStatShootText(Integer note) {
            return HRSLanguages.getText("effective.stat_shoot", note);
        }

        /**
         * Méthode pour récupérer la statistique de passe.
         * 
         * @param note La note de passe
         * 
         * @return Le texte de passe traduit
         * @since 0.2
         */
        public static String getStatPassText(Integer note) {
            return HRSLanguages.getText("effective.stat_pass", note);
        }
    }

    /**
     * Classe contenant les méthodes de traduction
     */
    public static class Home {

        /** Constructeur privé pour résoudre warning de Javadoc */
        private Home() {
        }

        /**
         * Méthode permettant de traduire le bouton "Commencer une nouvelle partie".
         *
         * @return texte traduit
         * 
         *
         * @since 0.1
         */
        public static String getNewGameButtonText() {
            return HRSLanguages.getText("home.new_game");
        }

        /**
         * Méthode permettant de traduire le bouton "Règles".
         *
         * @return texte traduit
         * 
         *
         * @since 0.1
         */
        public static String getRulesButtonText() {
            return HRSLanguages.getText("home.rules");
        }

        /**
         * Méthode permettant de traduire le titre de la fenêtre des règles.
         * 
         * @return texte traduit
         */
        public static String getRulesTitleText() {
            return HRSLanguages.getText("home.rules_title");
        }

        /**
         * Méthode permettant de traduire le bouton "Quitter le jeu".
         *
         * @return texte traduit
         * 
         *
         * @since 0.1
         */
        public static String getQuitButtonText() {
            return HRSLanguages.getText("home.quit");
        }

        /**
         * Méthode permettant de traduire le bouton "Paramètres".
         * 
         * @return texte traduit
         * 
         * 
         * @since 0.1
         */
        public static String getSettingsButtonText() {
            return HRSLanguages.getText("home.settings");
        }

        // Events

        /**
         * Méthode permettant de traduire le bouton "Ouverture des règles".
         *
         * @return texte traduit
         * 
         *
         * @since 0.1
         */
        public static String getRulesButtonEventText() {
            return HRSLanguages.getText("home.rules_event");
        }

        /**
         * Méthode permettant de traduire le bouton "Quitter ?".
         *
         * @return texte traduit
         * 
         *
         * @since 0.1
         */
        public static String getQuitButtonEventText() {
            return HRSLanguages.getText("home.quit_event");
        }
    }

    /**
     * Classe contenant les méthodes de traduction du panel Infrastructure
     */
    public static class Infrastructure {

        /** Constructeur privé pour résoudre warning de Javadoc */
        private Infrastructure() {
        }

        /**
         * Méthode pour récupérer le titre des infrastructures.
         * 
         * @return Le titre traduit
         * 
         * @since 0.2
         */
        public static String getTitle() {
            return HRSLanguages.getText("infrastructures.title");
        }

        /**
         * Méthode pour récupérer le texte de l'infrastructure Vitesse.
         * 
         * @return Le texte traduit
         * 
         * @since 0.2
         */
        public static String getSpeedText() {
            return HRSLanguages.getText("infrastructures.speed");
        }

        /**
         * Méthode pour récupérer le texte de l'infrastructure Passe.
         * 
         * @return Le texte traduit
         * 
         * @since 0.2
         */
        public static String getPassText() {
            return HRSLanguages.getText("infrastructures.pass");
        }

        /**
         * Méthode pour récupérer le texte de l'infrastructure Tir.
         * 
         * @return Le texte traduit
         * 
         * @since 0.2
         */
        public static String getShootText() {
            return HRSLanguages.getText("infrastructures.shoot");
        }

        /**
         * Méthode pour récupérer le texte de l'infrastructure Relaxation.
         * 
         * @return Le texte traduit
         * 
         * @since 0.2
         */
        public static String getRelaxationText() {
            return HRSLanguages.getText("infrastructures.relaxation");
        }

        /**
         * Méthode pour récupérer le texte de l'infrastructure Centre Médical.
         * 
         * @return Le texte traduit
         * 
         * @since 0.2
         */
        public static String getHealthText() {
            return HRSLanguages.getText("infrastructures.health");
        }

        /**
         * Méthode pour récupérer le titre du niveau.
         * 
         * @return Le texte traduit
         * 
         * @since 0.2
         */
        public static String getLvlText() {
            return HRSLanguages.getText("infrastructures.lvl");
        }

        /**
         * Méthode pour récupérer le titre de l'expérience.
         * 
         * @return Le texte traduit
         * 
         * @since 0.2
         */
        public static String getExpText() {
            return HRSLanguages.getText("infrastructures.exp");
        }

        /**
         * Méthode pour récupérer la description du changement d'état lié au repos
         * 
         * @return Le texte descriptif traduit
         * 
         * @since 0.2
         */
        public static String getChangeStatusRelaxationText() {
            return HRSLanguages.getText("infrastructures.change_status_relaxation");
        }

        /**
         * Méthode pour récupérer la description du changement d'état lié aux soins médicaux.
         * 
         * @return Le texte descriptif traduit
         * 
         * @since 0.2
         */
        public static String getChangeStatusHealthText() {
            return HRSLanguages.getText("infrastructures.change_status_health");
        }

        /**
         * Méthode pour récupérer le message de choix d'action sur une infrastructure.
         * 
         * @return Le message de choix traduit
         * 
         * @since 0.2
         */
        public static String getChoiceMessageText() {
            return HRSLanguages.getText("infrastructures.choice");
        }

        /**
         * Méthode pour récupérer le texte de l'action d'amélioration de l'infrastructure.
         * 
         * @return Le texte d'amélioration traduit
         * 
         * @since 0.2
         */
        public static String getUpgradeBuildText() {
            return HRSLanguages.getText("infrastructures.upgrade_build");
        }

        /**
         * Méthode pour récupérer le texte de l'action d'entraînement des joueurs.
         * 
         * @return Le texte d'entraînement traduit
         * 
         * @since 0.2
         */
        public static String getUpgradePlayersText() {
            return HRSLanguages.getText("infrastructures.upgrade_players");
        }

        /**
         * Méthode pour récupérer le message générique de soin des joueurs.
         * 
         * @return Le message de soin traduit
         * 
         * @since 0.2
         */
        public static String getCareMessageText() {
            return HRSLanguages.getText("infrastructures.care");
        }

        /**
         * Méthode pour récupérer la confirmation de soin des joueurs avec son coût.
         * 
         * @param cost Le coût de l'opération médicale
         * 
         * @return Le message de confirmation traduit
         * 
         * @since 0.2
         */
        public static String getCareMessageConfirmText(int cost) {
            return HRSLanguages.getText("infrastructures.care_confirm", cost);
        }

        /**
         * Méthode pour récupérer le message de confirmation des soins.
         * 
         * @return Le message de confirmation traduit
         * 
         * @since 0.2
         */
        public static String getCareMessageSuccessText() {
            return HRSLanguages.getText("infrastructures.care_success");
        }

        /**
         * Méthode pour récupérer le message d'échec des soins.
         * 
         * @return Le message d'échec traduit
         * 
         * @since 0.2
         */
        public static String getCareMessageFailText() {
            return HRSLanguages.getText("infrastructures.care_fail");
        }

        /**
         * Méthode pour récupérer la confirmation d'entraînement d'une statistique avec son coût.
         * 
         * @param stat Le nom de la statistique entraînée
         * @param cost Le coût de l'entraînement
         * 
         * @return Le message de confirmation traduit
         * 
         * @since 0.2
         */
        public static String getUpgradePlayersConfirmText(String stat, int cost) {
            return HRSLanguages.getText("infrastructures.upgrade_players_confirm", stat, cost);
        }

        /**
         * Méthode pour récupérer le message de confirmation de l'entraînement des joueurs.
         * 
         * @return Le message de confirmation traduit
         * 
         * @since 0.2
         */
        public static String getUpgradePlayersSuccessText() {
            return HRSLanguages.getText("infrastructures.upgrade_players_success");
        }

        /**
         * Méthode pour récupérer la confirmation d'amélioration d'une infrastructure avec son futur niveau et son coût.
         * 
         * @param name Le nom du bâtiment
         * @param level Le niveau actuel du bâtiment
         * @param cost Le coût de l'amélioration
         * 
         * @return Le message de confirmation traduit
         * 
         * @since 0.2
         */
        public static String getUpgradeBuildConfirmText(String name, int level, int cost) {
            return HRSLanguages.getText("infrastructures.upgrade_build_confirm", name, level + 1, cost);
        }

        /**
         * Méthode pour récupérer le message de confirmation d'amélioration d'une infrastructure.
         * 
         * @return Le message de succès traduit
         * 
         * @since 0.2
         */
        public static String getUpgradeBuildSuccessText() {
            return HRSLanguages.getText("infrastructures.upgrade_build_success");
        }
    }


    /**
     * Classe contenant les méthodes de traduction du panel Mail
     */
    public static class Mail {

        /** Constructeur privé pour résoudre warning de Javadoc */
        private Mail() {
        }

        /**
         * Méthode pour récupérer le titre du panel de messagerie.
         * 
         * @return Le titre traduit
         * 
         * @since 0.2
         */
        public static String getTitle() {
            return HRSLanguages.getText("mail.title");
        }

        /**
         * Méthode pour récupérer l'invitation à sélectionner un mail.
         * 
         * @return Le texte d'invite traduit
         * 
         * @since 0.2
         */
        public static String getSelectMailText() {
            return HRSLanguages.getText("mail.select_mail");
        }

        /**
         * Méthode pour récupérer la date d'un mail formatée.
         * 
         * @param date La date sous forme de chaîne
         * 
         * @return La date traduite
         * * @since 0.2
         */
        public static String getDateText(String date) {
            return HRSLanguages.getText("mail.date", date);
        }

        /**
         * Méthode pour récupérer le texte indiquant une boîte de réception vide.
         * 
         * @return Le texte d'absence de mails traduit
         * 
         * @since 0.2
         */
        public static String getEmptyMailsText() {
            return HRSLanguages.getText("mail.empty_mails");
        }

        /**
         * Méthode pour récupérer le texte de contenu de message vide.
         * 
         * @return Le texte d'absence de contenu traduit
         * 
         * @since 0.2
         */
        public static String getNoMessageText() {
            return HRSLanguages.getText("mail.no_message");
        }

        /**
         * Méthode pour récupérer l'objet du mail lors de l'achat d'un joueur.
         * 
         * @return L'objet du mail traduit
         * 
         * @since 0.2
         */
        public static String getBuyPlayerSubjectText() {
            return HRSLanguages.getText("mail.buy_player_subject");
        }

        /**
         * Méthode pour récupérer le contenu du mail résumant l'achat d'un joueur.
         * 
         * @param name Le nom du joueur acheté
         * @param cost Le coût de la transaction
         * 
         * @return Le contenu du mail traduit
         * 
         * @since 0.2
         */
        public static String getBuyPlayerContentText(String name, long cost) {
            return HRSLanguages.getText("mail.buy_player_content", name, cost);
        }

        /**
         * Méthode pour récupérer l'objet par défaut d'un mail de fin de match.
         * 
         * @return L'objet du mail traduit
         * 
         * @since 0.2
         */
        public static String getEndMatchSubjectText() {
            return HRSLanguages.getText("mail.end_match_subject");
        }

        /**
         * Méthode pour récupérer le contenu basique d'un mail de fin de match.
         * 
         * @param score Le score final de la rencontre
         * 
         * @return Le contenu du mail traduit
         * 
         * @since 0.2
         */
        public static String getEndMatchContentText(String score) {
            return HRSLanguages.getText("mail.end_match_content", score);
        }

        /**
         * Méthode pour récupérer l'objet du mail lors d'une prolongation de contrat.
         * 
         * @return L'objet du mail traduit
         * 
         * @since 0.2
         */
        public static String getExtensionContractSubjectText() {
            return HRSLanguages.getText("mail.extension_contract_subject");
        }

        /**
         * Méthode pour récupérer le contenu du mail résumant la prolongation de contrat d'un joueur.
         * 
         * @param name Le nom du joueur prolongé
         * 
         * @return Le contenu du mail traduit
         * 
         * @since 0.2
         */
        public static String getExtensionContractContentText(String name) {
            return HRSLanguages.getText("mail.extension_contract_content", name);
        }

        /**
         * Méthode pour récupérer l'objet d'un mail de fin de match ciblé sur l'adversaire.
         * 
         * @param opponentName Le nom de l'équipe adverse
         * 
         * @return L'objet du mail personnalisé traduit
         * 
         * @since 0.2
         */
        public static String getEndMatchSubjectText(String opponentName) {
            return HRSLanguages.getText("mail.end_match_subject", opponentName);
        }

        /**
         * Méthode pour récupérer le contenu d'un mail de fin de match complet avec blessures et fatigue.
         * 
         * @param opponentName Le nom de l'équipe adverse
         * @param score Le score final
         * @param tired Les joueurs fatigués
         * @param injury Les joueurs blessés
         * 
         * @return Le contenu détaillé traduit
         * 
         * * @since 0.2
         */
        public static String getEndMatchContentText(String opponentName, String score, String tired, String injury) {
            return HRSLanguages.getText("mail.end_match_content", opponentName, score, tired, injury);
        }

        /**
         * Méthode pour récupérer l'objet du mail de fin de championnat.
         * 
         * @return L'objet du mail traduit
         * 
         * @since 0.2
         */
        public static String getEndChampionshipSubjectText() {
            return HRSLanguages.getText("mail.end_championship_subject");
        }

        /**
         * Méthode pour récupérer le contenu du mail de bilan de championnat avec classement et récompense.
         * 
         * @param ranking La place finale au classement
         * @param reward La prime financière perçue par le vainqueur
         * 
         * @return Le contenu du bilan traduit
         * 
         * @since 0.2
         */
        public static String getEndChampionshipContentText(int ranking, int reward) {
            return HRSLanguages.getText("mail.end_championship_content", ranking, reward);
        }

        /**
         * Méthode pour récupérer le contenu complet d'un mail de fin de match incluant les rapports physiques et le gain financier.
         * 
         * @param opponentName Le nom de l'équipe adverse
         * @param score Le score final
         * @param tired Les joueurs fatigués
         * @param injury Les joueurs blessés
         * @param gain Le montant total du gain de match
         * 
         * @return Le contenu du mail traduit
         * 
         * @since 0.2
         */
        public static String getEndMatchContentText(String opponentName, String score, String tired, String injury, int gain) {
            return HRSLanguages.getText("mail.end_match_content", opponentName, score, tired, injury, gain);
        }
    }

    /**
     * Classe contenant les méthodes de traduction du panel Main
     */
    public static class Main {

        /** Constructeur privé pour résoudre warning de Javadoc */
        private Main() {
        }

        /**
         * Méthode permettant de traduire le label "CHARGEMENT".
         *
         * @return texte traduit
         * 
         *
         * @since 0.1
         */
        public static String getNameLabelText() {
            return HRSLanguages.getText("main.name");
        }

        /**
         * Méthode permettant de traduire le bouton "PARAMÈTRES".
         *
         * @return texte traduit
         * 
         *
         * @since 0.1
         */
        public static String getSettingsButtonText() {
            return HRSLanguages.getText("main.settings");
        }

        /**
         * Méthode permettant de traduire le bouton "CLASSEMENT".
         *
         * @return texte traduit
         * 
         *
         * @since 0.1
         */
        public static String getRankingButtonText() {
            return HRSLanguages.getText("main.ranking");
        }

        /**
         * Méthode pour récupérer le libellé du bouton Calendrier.
         * 
         * @return Le texte du bouton traduit
         * 
         * @since 0.2
         */
        public static String getCalendarButtonText() {
            return HRSLanguages.getText("main.calendar");
        }

        /**
         * Méthode permettant de traduire le bouton "QUÊTES".
         *
         * @return texte traduit
         * 
         *
         * @since 0.1
         */
        public static String getQuestsButtonText() {
            return HRSLanguages.getText("main.quests");
        }

        /**
         * Méthode permettant de traduire le bouton "MAILS".
         *
         * @return texte traduit
         * 
         *
         * @since 0.1
         */
        public static String getMailsButtonText() {
            return HRSLanguages.getText("main.mails");
        }

        /**
         * Méthode pour récupérer le libellé du bouton Championnat.
         * 
         * @return Le texte du bouton traduit
         * 
         * 
         * @since 0.2
         */
        public static String getChampionshipButtonText() {
            return HRSLanguages.getText("main.championship");
        }

        /**
         * Méthode pour récupérer le libellé du bouton Effectif.
         * 
         * @return Le texte du bouton traduit
         * 
         * 
         * * @since 0.2
         */
        public static String getEffectiveButtonText() {
            return HRSLanguages.getText("main.effective");
        }

        /**
         * Méthode permettant de traduire le bouton "MATCH".
         *
         * @return texte traduit
         * 
         *
         * @since 0.1
         */
        public static String getMatchButtonText() {
            return HRSLanguages.getText("main.match");
        }

        /**
         * Méthode permettant de traduire le bouton "MARCHE".
         *
         * @return texte traduit
         * 
         *
         * @since 0.1
         */
        public static String getMarketButtonText() {
            return HRSLanguages.getText("main.market");
        }

        /**
         * Méthode pour récupérer le texte d'événement du Championnat.
         * 
         * @return Le texte d'information traduit
         * 
         * 
         * @since 0.2
         */
        public static String getMessageChampionshipButtonText() {
            return HRSLanguages.getText("main.message_championship");
        }

        /**
         * Méthode pour récupérer le texte d'événement du Match.
         * 
         * @return Le texte d'information traduit
         * 
         * 
         * @since 0.2
         */
        public static String getMessageMatchButtonText() {
            return HRSLanguages.getText("main.message_match");
        }

        /**
         * Méthode pour récupérer le texte d'événement du Marché.
         * 
         * @return Le texte d'information traduit
         * 
         * 
         * @since 0.2
         */
        public static String getMessageMarketButtonText() {
            return HRSLanguages.getText("main.message_market");
        }

        /**
         * Méthode pour récupérer le texte d'événement des Infrastructures.
         * 
         * @return Le texte d'information traduit
         * 
         * 
         * @since 0.2
         */
        public static String getMessageInfrastructureButtonText() {
            return HRSLanguages.getText("main.message_infrastructure");
        }


        /**
         * Méthode pour récupérer le texte indicatif lorsqu'aucun événement n'est prévu.
         * 
         * @return Le texte informatif traduit
         * 
         * 
         * @since 0.2
         */
        public static String getMessageNoEventButtonText() {
            return HRSLanguages.getText("main.message_no_event");
        }
    }

    /**
     * Classe contenant les méthodes de traduction du panel Match
     */
    public static class Market {

        /** Constructeur privé pour résoudre warning de Javadoc */
        private Market() {
        }

        /**
         * Méthode pour récupérer le titre du marché des transferts.
         * 
         * @return Le titre traduit
         * 
         * 
         * @since 0.2
         */
        public static String getTitle() {
            return HRSLanguages.getText("market.title");
        }

        /**
         * Méthode permettant de traduire le message de confirmation d'achat d'un
         * Player.
         * 
         * @param name  La nom du Player
         * @param note  La note du Player
         * @param value La valeur du Player
         *
         * @return texte traduit
         * 
         *
         * @since 0.1
         */
        public static String getBuyPlayerButtonEventText(String name, Integer note, Integer value) {
            return HRSLanguages.getText("market.buy_event", name, note, value);
        }

        /**
         * Méthode pour récupérer le message de succès consécutif à l'achat d'un joueur.
         * 
         * @param name Le nom du joueur acheté
         * 
         * @return Le message de confirmation traduit
         * 
         * 
         * @since 0.2
         */
        public static String getBuyPlayerSuccessText(String name) {
            return HRSLanguages.getText("market.buy_event_success", name);
        }

        /**
         * Méthode permettant de traduire le message de la colonne Nom.
         * 
         * @return texte traduit
         * 
         * @since 0.2 
         */
        public static String getNameColumnText() {
            return HRSLanguages.getText("market.name");
        }

        /**
         * Méthode permettant de traduire le message de la colonne Poste.
         * 
         * @return texte traduit
         * 
         * @since 0.2 
         */
        public static String getPositionColumnText() {
            return HRSLanguages.getText("market.position");
        }

        /**
         * Méthode permettant de traduire le message de la colonne Note.
         * 
         * @return texte traduit
         * 
         * @since 0.2 
         */
        public static String getNoteColumnText() {
            return HRSLanguages.getText("market.note");
        }

        /**
         * Méthode permettant de traduire le message de la colonne Attaque.
         * 
         * @return texte traduit
         * 
         * 
         * @since 0.2
         */
        public static String getAttackColumnText() {
            return HRSLanguages.getText("market.attack");
        }

        /**
         * Méthode permettant de traduire le message de la colonne Défense.
         * 
         * @return texte traduit
         * 
         * 
         * @since 0.2
         */
        public static String getDefenseColumnText() {
            return HRSLanguages.getText("market.defense");
        }

        /**
         * Méthode permettant de traduire le message de la colonne Vitesse.
         * 
         * @return texte traduit
         * 
         * 
         * @since 0.2
         */
        public static String getSpeedColumnText() {
            return HRSLanguages.getText("market.speed");
        }

        /**
         * Méthode permettant de traduire le message de la colonne Tir.
         * 
         * @return texte traduit
         * 
         * 
         * @since 0.2
         */
        public static String getShootColumnText() {
            return HRSLanguages.getText("market.shoot");
        }

        /**
         * Méthode permettant de traduire le message de la colonne Passe.
         * 
         * @return texte traduit
         * 
         * 
         * @since 0.2
         */
        public static String getPassColumnText() {
            return HRSLanguages.getText("market.pass");
        }

        /**
         * Méthode permettant de traduire le message de la colonne Valeur.
         * 
         * @return texte traduit
         * 
         * @since 0.2 
         */
        public static String getValueColumnText() {
            return HRSLanguages.getText("market.value");
        }
    }

    /**
     * Classe contenant les méthodes de traduction du panel Match
     */
    public static class Match {
        
        /** Constructeur privé pour résoudre warning de Javadoc */
        private Match() {
        }

        /**
         * Méthode permettant de traduire le texte du match
         * @return Le titre du panel de match en direct
        */
        public static String getLiveMatchTitle() {
            return HRSLanguages.getText("match.live");
        }

        /**
         * Méthode permettant de traduire le texte du bouton Retour
         * @return Le texte du bouton de retour au club
        */
        public static String getReturnButtonText() {
            return HRSLanguages.getText("match.return");
        }

        /**
         * Méthode permettant de traduire le texte des commentaires
         * @param index L'index de phrase (1 à 5)
         * @return Le commentaire de match
         */
        public static String getCommentary(int index) {
            return HRSLanguages.getText("match.commentary_" + index);
        }

       /**
         * Commentaire basé sur l'action.
         *
         * @param event    Le type d'événement
         * @param teamName Le nom de l'équipe concernée
         * @return Un commentaire correspondant à l'action en cours
         * 
         */
        public static String getCommentaryForEvent(com.kahrs.model.Match.MatchEvent event, String teamName) {
            switch (event) {
                case GOAL_HOME:
                case GOAL_AWAY:
                    return HRSLanguages.getText("match.goal", teamName.toUpperCase());

                case SHOT_ON_TARGET_HOME:
                case SHOT_ON_TARGET_AWAY:
                    return HRSLanguages.Match.getCommentary(3); 
                
                default:
                    return "";
            }
        }

        /**
         * Méthode permettant de traduire le texte du message de victoire
         * @param gain Le gain
         * @return Le texte de victoire
        */
        public static String getVictoryText(int gain) {
            return HRSLanguages.getText("match.victory", gain);
        }

        /**
         * Méthode permettant de traduire le texte du message de défaite
         * @param gain Le gain
         * @return Le texte de défaite
        */
        public static String getDefeatText(int gain) {
            return HRSLanguages.getText("match.defeat", gain);
        }

        /**
         * Méthode permettant de traduire le texte du message de nul
         * @param gain Le gain
         * @return Le texte de match nul
        */
        public static String getDrawText(int gain) {
            return HRSLanguages.getText("match.draw", gain);
        }

        /**
         * Méthode pour récupérer le texte de transition de la mi-temps.
         * @return Le texte de mi-temps traduit
         * @since 0.2
         */
        public static String getHalfTimeText() {
            return HRSLanguages.getText("match.half_time");
        }

        /**
         * Méthode pour récupérer le titre du panneau de mi-temps.
         * @return Le titre de mi-temps traduit
         * @since 0.2
         */
        public static String getHalfTimeTitleText() {
            return HRSLanguages.getText("match.half_time_title");
        }

        /**
         * Méthode pour récupérer le récapitulatif textuel complet des statistiques d'un match à sa fin.
         * 
         * @param shotHome Tirs de l'équipe à domicile
         * @param shotOTHome Tirs cadrés de l'équipe à domicile
         * @param shotAway Tirs de l'équipe à l'extérieur
         * @param shotOTAway Tirs cadrés de l'équipe à l'extérieur
         * @param passHome Passes réussies à domicile
         * @param passAway Passes réussies à l'extérieur
         * 
         * @return Le bloc de statistiques formaté et traduit
         * 
         * 
         * @since 0.2
         */
        public static String getStatsText(int shotHome, int shotOTHome, int shotAway, int shotOTAway, int passHome, int passAway) {
            return HRSLanguages.getText("match.stats", shotHome, shotOTHome, shotAway, shotOTAway, passHome, passAway);
        }
    }

    /**
     * Classe contenant les méthodes de traduction du panel MatchSettings
     */
    public static class MatchSettings {   

        /** Constructeur privé pour résoudre warning de Javadoc */
        private MatchSettings() {
        }

        /**
         * Méthode pour récupérer le titre des configurations d'avant-match.
         * @return Le titre traduit
         * @since 0.2
         */
        public static String getTitle() {
            return HRSLanguages.getText("match_settings.title");
        }
        
        /**
         * Méthode permettant de traduire le texte de l'affrontement du Match
         * @return Le titre de l'affiche à venir
        */
        public static String getUpcomingMatchTitle() {
            return HRSLanguages.getText("match_settings.upcoming");
        }

        /**
         * Méthode permettant de traduire le texte du bouton Jouer
         * @return Le texte du bouton pour lancer le match
        */
        public static String getStartMatchButtonText() {
            return HRSLanguages.getText("match_settings.start");
        }

        /**
         * Méthode permettant de traduire le texte du bouton Retour
         * @return Le texte du bouton de retour
        */
        public static String getBackButtonText() {
            return HRSLanguages.getText("match_settings.back");
        }

        /**
         * Méthode permettant de traduire le texte du chargement
         * @return Le texte de chargement
        */
        public static String getLoadingText() {
            return HRSLanguages.getText("match_settings.loading");
        }

        /**
         * Méthode permettant de traduire l'erreur de contrat expiré.
         * @param playerName Le nom du joueur
         * @return Le message d'erreur formaté
         */
        public static String getExpiredContractErrorText(String playerName){ 
            return HRSLanguages.getText("match_settings.expired_contract_error", playerName);
        }

        /**
         * Méthode pour récupérer l'affichage d'une erreur de composition d'équipe contenant des joueurs blessés ou inaptes.
         * 
         * @param blessed Nombre de joueurs blessés ou inaptes (en terme de contrat) sélectionnés
         * 
         * @return Le message d'erreur traduit
         * 
         * 
         * @since 0.2
         */
        public static String getIncorrectCompositionText(int blessed) {
            return HRSLanguages.getText("match_settings.incorrect_composition", blessed);
        }

        /**
         * Méthode pour récupérer l'affichage d'une erreur de composition d'équipe incomplète.
         * 
         * @param nb Nombre de joueurs manquants pour démarrer le match
         * 
         * @return Le message d'erreur traduit
         * 
         * 
         * @since 0.2
         */
        public static String getIncompleteCompositionText(int nb) {
            return HRSLanguages.getText("match_settings.incomplete_composition", nb);
        }

        
    }

    /**
     * Classe contenant les méthodes de traduction du panel NewUser
     */
    public static class NewUser {

        /** Constructeur privé pour résoudre warning de Javadoc */
        private NewUser() {
        }

        /**
         * Méthode permettant de traduire le bouton "Jouer".
         *
         * @return texte traduit
         * 
         *
         * @since 0.1
         */
        public static String getPlayButtonText() {
            return HRSLanguages.getText("new_user.play");
        }

        /**
         * Méthode permettant de traduire le bouton "Précédent".
         *
         * @return texte traduit
         * 
         *
         * @since 0.1
         */
        public static String getPreviousButtonText() {
            return HRSLanguages.getText("new_user.previous");
        }

        /**
         * Méthode permettant de traduire le texte "Nom du Manager :".
         *
         * @return texte traduit
         * 
         *
         * @since 0.1
         */
        public static String getManagerLabelText() {
            return HRSLanguages.getText("new_user.manager");
        }

        /**
         * Méthode permettant de traduire le texte "Nom de l'Equipe :".
         *
         * @return texte traduit
         * 
         *
         * @since 0.1
         */
        public static String getTeamLabelText() {
            return HRSLanguages.getText("new_user.team");
        }

        /**
         * Méthode permettant de traduire le texte "Confirmer les choix ? Ils sont
         * définitifs".
         *
         * @return texte traduit
         * 
         *
         * @since 0.1
         */
        public static String getPlayButtonEventText() {
            return HRSLanguages.getText("new_user.play_event");
        }
    }

    /**
     * Classe contenant les méthodes de traduction liées aux attributs d'un Player
     */
    public static class Player {

        /** Constructeur privé pour résoudre warning de Javadoc */
        private Player() {
        }

        /**
         * Méthode pour récupérer le libellé traduit d'une statistique propre au joueur.
         * 
         * @param text Le nom technique de la statistique
         * @param note La valeur associée de la note
         * 
         * @return La caractéristique traduite
         * 
         * @since 0.2
         */
        public static String getStatText(String text, Integer note) {
            return HRSLanguages.getText("player." + text, note);
        }
    }


    /**
     * Classe contenant les méthodes de traduction du panel Quests
     */
    public static class Quest {

        /** Constructeur privé pour résoudre warning de Javadoc */
        private Quest() {
        }

        /**
         * Méthode pour récupérer le titre du gestionnaire de quêtes.
         * 
         * @return Le titre traduit
         * 
         * @since 0.2
         */
        public static String getTitle() {
            return HRSLanguages.getText("quest.title");
        }

        /**
         * Méthode pour récupérer l'intitulé complet d'une quête.
         * 
         * @param id L'identifiant textuel de la quête
         * 
         * @return La description de quête traduite
         * 
         * 
         * @since 0.2
         */
        public static String getQuestDescription(String id) {
            return HRSLanguages.getText("quest." + id + ".description");
        }

        /**
         * Méthode pour récupérer le message de notification de validation d'une quête accompagnée de sa récompense.
         * 
         * @param description L'intitulé de la quête accomplie
         * @param reward Le montant de la prime de succès
         * 
         * 
         * @return Le message de félicitations formaté et traduit
         * 
         * 
         * @since 0.2
         */
        public static String getQuestCompleteText(String description, int reward) {
            return HRSLanguages.getText("quest.complete", description, reward);
        }
    }

    /**
     * Classe contenant les méthodes de traduction
     */
    public static class Settings {

        /** Constructeur privé pour résoudre warning de Javadoc */
        private Settings() {
        }

        /**
         * Méthode permettant de traduire le bouton "Crédits".
         *
         * @return texte traduit
         * 
         *
         * @since 0.1
         */
        public static String getCreditsButtonText() {
            return HRSLanguages.getText("settings.credits");
        }

        /**
         * Méthode permettant de traduire le texte "Langue".
         *
         * @return texte traduit
         * 
         *
         * @since 0.1
         */
        public static String getLangueLabelText() {
            return HRSLanguages.getText("settings.language");
        }

        /**
         * Méthode permettant de traduire le texte "Bruitage".
         *
         * @return texte traduit
         * 
         *
         * @since 0.1
         */
        public static String getBruitageLabelText() {
            return HRSLanguages.getText("settings.sound_effect");
        }

        /**
         * Méthode permettant de traduire le texte "Volume".
         *
         * @return texte traduit
         * 
         *
         * @since 0.1
         */
        public static String getVolumeLabelText() {
            return HRSLanguages.getText("settings.volume");
        }

        /**
         * Méthode permettant de traduire le texte du bouton "Crédit".
         *
         * @return texte traduit
         * 
         *
         * @since 0.1
         */
        public static String getCreditsButtonEventText() {
            return HRSLanguages.getText("settings.credits_event");
        }

        /**
         * Méthode pour récupérer l'intitulé du bouton de sauvegarde.
         * 
         * @return Le texte du bouton traduit
         * 
         * 
         * @since 0.2
         */
        public static String getSaveButtonText() {
            return HRSLanguages.getText("settings.save");
        }

        /**
         * Méthode pour récupérer le message de succès d'une sauvegarde réussie.
         * 
         * @return Le message de confirmation traduit
         * 
         * 
         * @since 0.2
         */
        public static String getSaveButtonEventText() {
            return HRSLanguages.getText("settings.save_event");
        }

        /**
         * Méthode pour récupérer le message d'erreur survenant lors d'un échec de la sauvegarde.
         * 
         * @return Le message d'erreur traduit
         * 
         * 
         * @since 0.2
         */
        public static String getSaveErrorEventText() {
            return HRSLanguages.getText("settings.save_error_event");
        }

        /**
         * Méthode pour récupérer l'intitulé du bouton permettant de quitter l'interface des options.
         * 
         * @return Le texte du bouton traduit
         * 
         * 
         * @since 0.2
         */
        public static String getQuitButtonEventText() {
            return HRSLanguages.getText("settings.quit");
        }
    }


    /**
     * Classe contenant les méthodes de traduction du panel Stadium
     */
    public static class Stadium {

        /** Constructeur privé pour résoudre warning de Javadoc */
        private Stadium() {
        }

        /**
         * Méthode pour récupérer les configurations du stade.
         * 
         * @return Le libellé textuel traduit
         * 
         * 
         * @since 0.2
         */
        public static String getOptionText() {
            return HRSLanguages.getText("stadium.option");
        }

        /**
         * Méthode pour récupérer le message d'avertissement relatif à l'absence de joueurs remplaçants sur le banc.
         * 
         * @return Le message d'avertissement traduit
         * 
         * 
         * @since 0.2
         */
        public static String getEmptySubstitute() {
            return HRSLanguages.getText("stadium.empty_substitute");
        }
    }

    /**
     * Classe contenant les méthodes de traduction de la boîte de dialogue de remplacement de joueurs
     */
    public static class Substitution {

        /** Constructeur privé pour résoudre warning de Javadoc */
        private Substitution() {
        }

        /**
         * Méthode pour récupérer le titre de la fenêtre de changements.
         * 
         * @return Le titre de fenêtre traduit
         * 
         * 
         * @since 0.2
         */
        public static String getFrameTitle() {
            return HRSLanguages.getText("substitution.frame_title");
        }
        
        /**
         * Méthode pour récupérer le titre principal affiché à l'intérieur du menu de remplacement.
         * 
         * @return Le titre de section traduit
         * 
         * 
         * @since 0.2
         */
        public static String getTitle() {
            return HRSLanguages.getText("substitution.title");
        }

        /**
         * Méthode pour récupérer l'intitulé du joueur sortant du terrain.
         * 
         * @return Le libellé traduit
         * 
         * 
         * @since 0.2
         */
        public static String getPlayerOffText() {
            return HRSLanguages.getText("substitution.player_off");
        }

        /**
         * Méthode pour récupérer l'intitulé du joueur entrant sur la pelouse.
         * 
         * @return Le libellé traduit
         * 
         * 
         * @since 0.2
         */
        public static String getPlayerOnText() {
            return HRSLanguages.getText("substitution.player_on");
        }

        /**
         * Méthode pour récupérer le décompte des changements tactiques autorisées restantes.
         * 
         * @return Le décompte textuel traduit
         * 
         * 
         * @since 0.2
         */
        public static String getRemainingChangesText() {
            return HRSLanguages.getText("substitution.remaining_changes");
        }

        /**
         * Méthode pour récupérer l'intitulé du bouton de validation définitive des changements.
         * @return Le texte du bouton traduit
         * @since 0.2
         */
        public static String getConfirmText() {
            return HRSLanguages.getText("substitution.confirm");
        }

        /**
         * Méthode pour récupérer l'intitulé du bouton de reprise immédiate du cours du match.
         * @return Le texte du bouton traduit
         * @since 0.2
         */
        public static String getResumeText() {
            return HRSLanguages.getText("substitution.resume");
        }

        /**
         * Méthode pour récupérer la confirmation visuelle d'un changement tactique opéré avec succès.
         * @return Le texte de succès traduit
         * @since 0.2
         */
        public static String getSuccessText() {
            return HRSLanguages.getText("substitution.success");
        }
    }

    /**
     * Classe contenant les méthodes de traduction du panel Transition
     */
    public static class Transition {

        /** Constructeur privé pour résoudre warning de Javadoc */
        private Transition() {
        }

        /**
         * Méthode permettant de traduire le texte "Chargement des données...".
         *
         * @return texte traduit
         * 
         *
         * @since 0.1
         */
        public static String getLoadingLabelText() {
            return HRSLanguages.getText("transition.loading");
        }

        /**
         * Méthode permettant de traduire le message d'erreur "Erreur fatale : La base de données ne répond pas".
         *
         * @return texte traduit
         * 
         *
         * @since 0.1
         */
        public static String getCheckUserErrorText() {
            return HRSLanguages.getText("transition.database_error");
        }

        /**
         * Méthode permettant de traduire le texte "Initialisation du système...".
         *
         * @return texte traduit
         * 
         *
         * @since 0.1
         */
        public static String getStatusBarLabelText() {
            return HRSLanguages.getText("transition.status");
        }

        /**
         * Méthode permettant de traduire le texte de la barre de chargement.
         * 
         * @param etape Etape du chargement
         *
         * @return texte traduit
         * 
         *
         * @since 0.1
         */
        public static String getStatusBarSequenceText(int etape) {
            switch (etape) {
                case 0: return HRSLanguages.getText("transition.status_step_connexion");
                case 1: return HRSLanguages.getText("transition.status_step_checking");
                case 2: return HRSLanguages.getText("transition.status_step_analysing");
                case 3: return HRSLanguages.getText("transition.status_step_completed");
                default: return "";
            }
        }

        /**
         * Méthode permettant de traduire le texte de la barre de chargement.
         * 
         * @param etape Etape du chargement
         *
         * @return texte traduit
         * 
         *
         * @since 0.1
         */
        public static String getStatusBarCheckText(int etape) {
            switch (etape) {
                case 0: return HRSLanguages.getText("transition.status_check_no");
                case 1: return HRSLanguages.getText("transition.status_check_ok");
                default: return "";
            }
        }

        /**
         * Méthode permettant de traduire le message d'erreur "ERREUR : Base de données inaccessible;".
         *
         * @return texte traduit
         * 
         *
         * @since 0.1
         */
        public static String getStatusBarErrorText() {
            return HRSLanguages.getText("transition.status_error");
        }
    }
}