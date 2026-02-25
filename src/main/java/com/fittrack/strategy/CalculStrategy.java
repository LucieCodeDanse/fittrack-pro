package com.fittrack.strategy;

/**
 * l'interface CalculStrategy définit un contrat sous forme d'un ensemble de signatures de méthodes
 * dictant la conduite des méthodes des classes qui l'implemente.
 *
 * Stratégie de calcul des calories
 * Basée sur le MET (Metabolic Equivalent of Task) --> Mesure l'intensité d'une activité physique et la dépense énergétique.
 *                                                     C'est le rapport de l'activité sur la demande du métabolisme de base.
 */

public interface CalculStrategy {

    /**
     * @return le coefficient MET pour cette intensité --> flottant
     */
    double getMet();

    /**
     * @return le nom de la stratégie --> chaîne de caractères
     */
    String getNom();
}
