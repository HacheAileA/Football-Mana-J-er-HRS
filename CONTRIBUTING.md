# Guide de contribution au projet « Football Manager »

Avant de commencer, veuillez lire attentivement les consignes ci-dessous, ainsi que le [README](./README.md).  

CE PROJET PROVIENT D'UNE INSTANCE PRIVEE GITLAB

## Table des matières

- [Organisation des branches](#organisation-des-branches)
- [Règles de contribution](#règles-de-contribution)
- [Mettre à jour le projet](#mettre-à-jour-le-projet)
- [Processus de relecture et validation](#processus-de-relecture-et-validation)
- [Questions](#questions)

---

## Organisation des branches

Le projet utilise une organisation stricte des branches pour une bonne clarté des contributions.

### Branche principale

- **dev** : sert à sauvegarder les modifications stables et constitue le point de bascule vers `main` pour les releases.

Ces deux branches sont **protégées** et ne doivent pas être modifiées directement par les contributeurs.

### Branches secondaires protégées

- **database** : pour la modification de code concernant principalement la BDD.
- **documentation** : pour la mise à jour ou la création de documentation.
- **feature** : pour le développement de nouvelles fonctionnalités.
- **tests** : pour les tests unitaires et d’intégration.

### Création d’une branche de contribution

Pour contribuer, il est nécessaire de créer une branche dérivée d’une des branches secondaires protégées selon le type de contribution. Le nom de la branche doit respecter la convention suivante :

`<qualificatif>_<nom_de_branche_explicite>`

- `d_` ou `doc_`pour **documentation**
- `db_` ou `database_` pour **database**
- `f_` ou `feature_`pour **feature**
- `t_` ou `tests_`pour **tests**

---

## Règles de contribution

1. **Cloner** le dépôt. Dans le terminal, se placer dans le dossier souhaité et exécuter la commande suivante :

2. Créer un **ticket** pour proposer votre suggestion, ou traiter un ticket existant. N'oubliez pas d'y ajouter les bons labels et jalon.

3. Se placer dans une des branches protégées principale et créer une **branche** dédiée à vos modifications :

   ```bash
   git checkout nom-de-la-branche-protégée
   git checkout -b nom-de-la-branche
   ```

   exemple :

   ```bash
   git checkout feature
   git checkout -b feature_ajoutModel
   ```

4. Faire des **modifications** et **ajouter** le fichier :

   ```bash
   git add nom-du-fichier
   ```

5. Faire un ou plusieurs **commit** après chaque modification avec un message clair :

   ```bash
   git commit -m "type(fichier): message"
   ```

   exemple :

   ```bash
   git commit -m "doc(readme): Ajout du README"
   ```

6. Répéter les étapes `4` et `5` aussi souvent que possible et **pousser** la branche créée sur le dépôt :

   ```bash
   git push -u origin nom-de-la-branche
   ```

7. Ouvrez une MR vers la branche appropriée (se reporter au [README](./README.md)).

## Mettre à jour le projet

Il se peut que la branche par défaut ait été modifé et que vous n'ayez plus la dernière version lors de la MR. Pour cela :

1. Dans le terminal, se placer dans le dossier où le projet a été cloné.

2. Exécuter la commande suivante :
   ```bash
   git pull
   ```

### Mettre à jour avec une branche distante

Il se peut que la branche par défaut ait été modifé et que vous n'ayez plus la dernière version lors de la MR. Pour cela :

1. Dans le terminal, se placer dans le dossier où le projet a été cloné.

Si la branche cible est :

2. la branche par défaut (`dev`), exécuter la commande suivante :
   ```bash
   git pull
   ```

3. sinon, exécuter la commande suivante :
   ```bash
   git pull origin <branche_cible>
   ```

### Renommer une branche

Il se peut que votre branche n'ait pas un nom approprié. Pour la renommer :

1. Dans le terminal, exécuter la commande suivante :
   ```bash
   git branch -m <NomActuel> <NouveauNom>
   ```

---

## Processus de relecture et validation

**Chaque MR** doit suivre le processus suivant :

1. **Critères d’approbation**

   Une MR ne peut être fusionnée que si :

   - Au moins 2 personnes ont relues et approuvé la MR.
   - La MR passe la pipeline.
   - Les modifications respectent les **contraintes techniques** et les **standards du projet**.
   - Les éventuels **commentaires** ou **demandes de modification** ont été pris en compte et corrigés.

   Une MR peut échapper aux règles précédentes si :

   - Cela concerne un ajout/correction de documentation
   - Correction de fautes
   - Correction(s) mineures

2. **Fusion (merge)**

   - Une fois approuvée, la MR peut être fusionnée dans la branche appropriée.

3. **Conflits de fusion**

    - Si des conflits de fusion apparaissent, merci de **NE PAS** rebaser, mais de :  
        3.1 Clôner la branche en locale  
        3.2 La mettre à jour avec la branche cible  
        3.3 Corriger les conflits  
        3.4 Faire un commit clair indiquant que les conflits ont été résolus  
        3.5 Pousser la branche pour mettre à jour la MR
        3.6 S'assurer qu'aucuns conflits ne persistent

4. **Délais de relecture**

   - Chaque MR doit être revue dans un délai **raisonnable** après sa soumission.
   - Les contributions urgentes ou critiques peuvent bénéficier d’un traitement accéléré.
   - Les contributions avec des modifications infimes peuvent être fusionnée directement (sans relecture et approbation nécessaire)

5. **Communication**

   - Les relecteurs doivent **laisser des commentaires constructifs** et clairs.
   - En cas de désaccord, une **discussion** sur la MR permet de trouver un consensus avant la fusion.

```
MR soumise → Relecture et commentaires
                       ↓
                    Validée ?
            ↙                       ↘
         Non                         Oui
    Commentaires       Merge dans la branche correspondante
```

---

## Questions

Si vous avez une question, vérifiez d’abord que la réponse ne se trouve pas dans le [README](./README.md), la **documentation du projet**, ou dans un **ticket**.

Si vous n'avez pas de réponse à votre question, ouvrez un **ticket** sur le dépôt en décrivant clairement votre problème ou demande.

N’hésitez pas à participer aux discussions pour clarifier ou enrichir les réponses.
