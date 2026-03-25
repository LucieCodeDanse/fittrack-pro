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

'''bash
# Cloner le projet 
git clone https://githun.com/LucieCodeDanse/fttrack-pro.git
cd fittrack-pro

# Compiler
mvn clean compile

# Lancer les tests
mvn test

# Exécuter l'application 
mvn exec:java
'''