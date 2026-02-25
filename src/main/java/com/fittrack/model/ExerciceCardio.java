package com.fittrack.model;

import com.fittrack.strategy.CalculStrategy;

/**
 * Exercice de type cardio (course , vélo, notation ...)
 */
public class ExerciceCardio extends Exercice {
    //La classe ExerciceCardio hérite de la classe Exercice
    // (elle possède ses méthodes et ses attributs )

    //Création du constructeur de la classe ExerciceCardio
    public ExerciceCardio(String pNom, int pDuree, String pDescription){
        //Appel au constructeur de la classe mère, la classe  Exercice
        super(pNom, pDuree, pDescription);
    }

    /**
     * Méthode spécifique aux exercices Cardio
     */

    //Cree le corps de la méthode abstraite getCalories de Exercice
    //en fonction de l'exercice Cardio
    @Override
    public double getCalories(CalculStrategy pStrategy){
        //Calcule les calories pour l'exercice Cardio
        //Formule simple : duree x MET
        return this.duree * pStrategy.getMet();
    }
    public String getType(){
        //Methode qui retourne le type de l'exercice en l'occurrence le Cardio
        return "Exercice de CARDIO";
    }

}
