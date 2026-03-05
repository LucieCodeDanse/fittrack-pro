package com.fittrack;

import com.fittrack.model.*;
import com.fittrack.strategy.*;

import java.time.LocalDate;
import java.util.*;

/**
 * Apllication de démonstration de fonctionnalités
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== FitTrack Pro - Demonstration ===\n");

        //Creation d'une séance avec le Builder
        Seance seance = new Seance.Builder()
                .setNomSeance("Seance du matin")
                .setDateSeance(LocalDate.now())
                .addActivite(ActiviteFactory.createCardio(
                        "Echauffement",
                        10,
                        "Jogging leger"
                ))
                .addActivite(ActiviteFactory.createForce(
                        "Squats",
                        15,
                        "Jambes et fessiers ",
                        4,
                        12
                ))
                .addActivite(ActiviteFactory.createCardio(
                        "Couse",
                        30,
                        "5km allure moderee"
                ))
                .addActivite(ActiviteFactory.createForce(
                        "Pompes",
                        10,
                        "Pectoraux et triceps",
                        3,
                        20
                ))
                .build();

        //Afficher la seance
        System.out.println(seance);

        //Affichage des activités
        System.out.println("Detail des activites : ");
        for (Activite activite : seance.getListeActivitesSeance()){
            System.out.println(" - " + activite);
        }
        System.out.println(); //interligne

        //Calcul des calories selon différentes intensités
        // Créer un tableau de type CalculStrategy
        CalculStrategy[] tableauStrategies = {
                new IntensiteFaible(),
                new IntensiteModeree(),
                new IntensiteHaute(),
                new IntensiteMaximale()
        };

        System.out.println("Estimation des calories brulees : ");
        for (CalculStrategy strategy : tableauStrategies){
            //On obtient le nombre de calories totales en fonction de la stratégie
            double NombreCaloriesTotales = seance.getCaloriesTotales(strategy);
            System.out.printf(" - %s : %.0f calories \n ", strategy.getNom(), NombreCaloriesTotales); //printf comme en C
            }

        System.out.println("\n === Fin de la demonstration ===");

    }

}
