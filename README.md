# FitTrack Pro

Application Java de gestion et suivi de seances d'entraînement sportif.

## Fonctionnalités

- Modélisation d'exercice (Cardio, Force)
- Création des séances d'entraînement 
- Calcul automatique de durée et calories 
- Différentes intensités d'exercice (Strategy Pattern)
- Persistance en base de données H2
- Tests unitaires et d'intégration complets

## Technologies 

- **Java 11+**
- **Maven 3.6+** 
- **H2 Database** (en mémoire)
- **JUnit 5** (tests)

## Installation 

```bash
# Cloner le projet 
git clone https://github.com/LucieCodeDanse/fttrack-pro.git
cd fittrack-pro

# Compiler
mvn clean compile

# Lancer les tests
mvn test

# Exécuter l'application 
mvn exec:java
```

## Structure de projet 

```
src/main/java/com/fittrack/
              |-- model/        # Modèle de domaine (Seance, Activite, Exercice,...)
              |-- Strategy/     # Stratégies de calcul de calories 
              |-- dao/          # Data Access Objects 
              |-- datasource/   # Configuration base de données 
              |-- Main.java     # Application principale 
```

## Exemple d'utilisation 

### Créer une séance 

```java
Seance seance = new Seance.Builder()
    .setNom("Morning Workout")
    .setDate(LocalDate.now())
    .addActivite(ActiviteFactory.createCardio("Course", 30, "5km"))
    .addActivite(ActiviteFactory.createForce("Pompes", 10, "Pectoraux", 3, 20))
    .build();
```

### Calculer les calories

```java
CalculStrategy strategy = new IntensiteHaute();
double calories = seance.getCaloriesTotales(strategy);
System.out.printf("Calories brûlées: %.0f cal\n", calories);
```

### Persister en base de données

```java
DatabaseManager dbManager = new DatabaseManager();
dbManager.createTables();

SeanceDAO dao = new H2SeanceDAO(dbManager.getDataSource());
int id = dao.save(seance);

Seance loaded = dao.findById(id);
```

## Tests

```bash
# Tous les tests
mvn test

# Tests spécifiques
mvn test -Dtest=SeanceTest
mvn test -Dtest=DatabaseIntegrationTest
```

## Design Patterns implémentation 

- **Strategy** : Calcul des calories selon différentes intensités 
- **Factory** : Création centralisée d'activités 
- **Builder** : Construction fluide de séances complexes 
- **DAO** : Abstraction de la persistance 

## Auteur 

Lucie QUARTA - M1 InfoMath

## Licence 

Projet pédagogique - 2026