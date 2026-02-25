package com.fittrack.model;
import com.fittrack.strategy.CalculStrategy;

/**
 * Interface définissant le contrat pour toute activité physique
 */

public interface Activite {

    /**
     * @return le nom de l'activité
     */
    String getNom() ;

    /**
     * @return la duree en minute
     */
    int getDuree() ;

    /**
     * @return une description textuelle de l'activité
     */
    String getDescription() ;

    /**
     * Calcule les calories brûlées selon une stratégie
     * @param pStrategy la stratégie du calcul
     * @return nombre de calories estimées
     */
    double getCalories(CalculStrategy pStrategy) ;
}
