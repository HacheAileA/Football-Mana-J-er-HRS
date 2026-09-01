# 2026-CB2-C-HRS

CE PROJET PROVIENT D'UNE INSTANCE PRIVEE GITLAB

## Table des matières

- [Description du projet](#description-du-projet)
- [Architecture](#architecture)
- [Prérequis](#prérequis)
- [Compilation et exécution](#compilation-et-exécution)
- [Tests](#Tests)
- [Outils et dépendences](#outils-et-dépendences)
- [Auteurs](#auteurs)

---

## Description du projet

Le but de ce projet est d'implémenter en JAVA une application de simulation de gestion d'une équipe de football en utilisant Swing. Il inclue une connexion à une base de données pour stocker les informations des joueurs, équipes, matchs et résultats.

Le joueur pourra gérer une équipe, organiser des matchs, gérer les finances et développer des stratégies pour améliorer son équipe (performances des joueurs, amélioration des infrastructures, etc.).

Le projet utilise l'architecture MVC (Modèle-Vue-Contrôleur) pour séparer clairement la logique des données (modèle), la logique métier (contrôleur), et l'interface graphique (vue), ainsi qu'une base de données simple pour stocker les informations des joueurs, des matchs, et des finances.

Il suit une méthodologie Agile (Scrum), avec des nouveautés chaque semaine.

---

## Architecture

Organisation du projet :

```
./
└── src/
    ├── main/
    │   ├── java/
    │   │   └── com/
    │   │       └── kahrs/
    │   │           ├── app/
    │   │           ├── controller/
    │   │           ├── database/
    │   │           │   └── dao/
    │   │           ├── model/
    │   │           └── view/
    │   │               ├── panels/
    │   │               └── visuals/
    │   │
    │   └── resources/
    │       ├── image/
    │       ├── languages/
    │       └── sounds/
    │           ├── effects/
    │           └── musics/
    └── tests/
```

---

## Prérequis

- **Java 17** ou supérieur
- **Maven** (gestion du build et des dépendances)
- **Connexion internet** (pour la base de données)

---

## Compilation et exécution

Pour compiler, placez-vous à la racine du projet (`./`) pour utiliser les commandes Maven.  
**La compilation est obligatoire quand le dépôt est cloné**  
**L'utilisation des scripts Maven dépend de son exécutant** :

- `./mvnw` -> Utilisation du wrapper local
- `mvn` -> Utilisation du module Maven
- `wsl mvn` -> Utilisation du module Maven sous WSL

### Compilation

- **Lancer le build complet :**
  ```bash
  mvn clean package
  ```

### Exécution

Pour exécuter le programme, lancez simplement la commande suivante :
- **Exécuter le fichier JAR :**
  ```bash
  java -jar target/2026-cb2-c-hrs-0.2.jar
  ```

---

## Tests

### Vérifications

Les commandes suivantes permettent de s'assurer que le projet ne possède pas de problèmes autres que la compilation ou les tests unitaires.

-   **Checkstyle :**
    ```bash
    mvn checkstyle:check -B
    ```

-   **Javadoc :**
    ```bash
    mvn javadoc:javadoc -B
    ```

### Tests unitaires

Les tests unitaires (avec JUnit) seront ajoutés au fur et à mesure dans le dossier `src/test/java/`.

-   **Lancer les tests :**
    ```bash
    mvn test -B
    ```

---

## Outils et dépendences

### Outils

- GitLab (système de gestion de versions, CI/CD)
- Maven (build & dépendances)
- Java 17
- Swing / AWT (interface graphique)

### Dépendences

- Checkstyle (qualité du code)
- Dotenv (variables d'environnement)
- Gson (sauvegarde interne)
- JFreeChart (création de graphiques)
- JUnit 5 et Mockito (tests)
- Supabase (base de données)

---

## Auteurs

Les auteurs sont consignés dans le fichier [AUTHORS](./AUTHORS.md).

Projet réalisé dans le cadre de l'UE Projet Informatique (JAVA) (Université Paris Cité – 2025–2026).

Projet académique — Licence libre MIT (voir fichier [LICENSE](./LICENSE)).
