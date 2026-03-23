package com.fittrack;

import com.fittrack.dao.H2SeanceDAO;
import com.fittrack.dao.SeanceDAO;
import com.fittrack.datasource.DatabaseManager;
import com.fittrack.model.*;
import com.fittrack.strategy.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

/**
 * Apllication de démonstration de fonctionnalités
 */
public class Main {
    public static void main(String[] args) {
        try {  //Toujours mettre une exception en cas de problème lors de la connexion à une base de données !!!!!!!!!!!!!!!!

            System.out.println("=== FitTrack Pro - Demonstration ===\n");

            //1. Initialiser la base de données
            // On crée une instance de la classe SatabaseManager pour gérer la
            //connexion à la base de données H2 et l'utilisateur des tables
            DatabaseManager databaseManager = new DatabaseManager();
            //On crée les tables
            databaseManager.createTables();
            //on initie une instance H2SeanceDAO avec en paramètre une connexion à la base de données H2
            SeanceDAO seanceDAO = new H2SeanceDAO(databaseManager.getDataSource());

//            //Creation d'une séance avec le Builder
//            Seance seance = new Seance.Builder()
//                    .setNomSeance("Seance du matin")
//                    .setDateSeance(LocalDate.now())
//                    .addActivite(ActiviteFactory.createCardio(
//                            "Echauffement",
//                            10,
//                            "Jogging leger"
//                    ))
//                    .addActivite(ActiviteFactory.createForce(
//                            "Squats",
//                            15,
//                            "Jambes et fessiers ",
//                            4,
//                            12
//                    ))
//                    .addActivite(ActiviteFactory.createCardio(
//                            "Couse",
//                            30,
//                            "5km allure moderee"
//                    ))
//                    .addActivite(ActiviteFactory.createForce(
//                            "Pompes",
//                            10,
//                            "Pectoraux et triceps",
//                            3,
//                            20
//                    ))
//                    .build();
//
//            //Afficher la seance
//            System.out.println(seance);
//
//            //Affichage des activités
//            System.out.println("Detail des activites : ");
//            for (Activite activite : seance.getListeActivitesSeance()) {
//                System.out.println(" - " + activite);
//            }
//            System.out.println(); //interligne
//
//            //Calcul des calories selon différentes intensités
//            // Créer un tableau de type CalculStrategy
//            CalculStrategy[] tableauStrategies = {
//                    new IntensiteFaible(),
//                    new IntensiteModeree(),
//                    new IntensiteHaute(),
//                    new IntensiteMaximale()
//            };
//
//            System.out.println("Estimation des calories brulees : ");
//            for (CalculStrategy strategy : tableauStrategies) {
//                //On obtient le nombre de calories totales en fonction de la stratégie
//                double NombreCaloriesTotales = seance.getCaloriesTotales(strategy);
//                System.out.printf(" - %s : %.0f calories \n ", strategy.getNom(), NombreCaloriesTotales); //printf comme en C
//            }

            //2. Créer et sauvegarder la première séance
            Seance seance1 = new Seance.Builder()
                    .setNomSeance("Seance Full Body")
                    .setDateSeance(LocalDate.now())
                    .addActivite(ActiviteFactory.createCardio("Echauffement", 10, "Tapis"))
                    .addActivite(ActiviteFactory.createForce("Squats", 15, "Jambes", 4, 12))
                    .addActivite(ActiviteFactory.createCardio("Course", 30, "5 km"))
                    .addActivite(ActiviteFactory.createForce("Pompes", 12, "Pectoraux", 3, 20))
                    .build();

            //Sauvagarder
            int id1 = seanceDAO.save(seance1);
            System.out.println("Seance sauvegarder avec ID : " + id1);
            System.out.println(" " + seance1 + "\n");

            //3.Créer une deuxième séance
            Seance seance2 = new Seance.Builder()
                    .setNomSeance("Seance Cardio")
                    .setDateSeance(LocalDate.now().minusDays(1))
                    .addActivite(ActiviteFactory.createCardio("Velo", 45, "Route"))
                    .addActivite(ActiviteFactory.createCardio("Natation", 30, "Crawl"))
                    .build();

            //Sauvegarder
            int id2 = seanceDAO.save(seance2);
            System.out.println("Seance sauvegarder avec ID : " + id2);
            System.out.println(" " + seance2 + "\n");

            //4. Récupérer toutes les séances enregistrées
            System.out.println("Toutes les seances enregistrees : ");
            List<Seance> listeSeances = seanceDAO.findAll();
            CalculStrategy strategy = new IntensiteModeree();
            String nomStrategy = strategy.getNom();
            //On lit toutes les seance
            for (Seance seance : listeSeances) {
                System.out.println(" " + seance);
                // Calculer les calories
                double nombreCalories = seance.getCaloriesTotales(strategy);
                System.out.printf("Nom de la strategie : %s \nNombres de calories : %.0f ", nombreCalories, nombreCalories);
            }

            System.out.println("\n === Fin de la demonstration ===");

        }catch (SQLException sqlException) {
            //Toujours mettre une exception en cas de problème lors de la connexion à une base de données !!!!!!!!!!!!!!!!
            System.err.printf("Erreur Base de données : " + sqlException.getMessage());
            //On affiche d'où vient l'exception
            sqlException.printStackTrace();
        }
    }

}
