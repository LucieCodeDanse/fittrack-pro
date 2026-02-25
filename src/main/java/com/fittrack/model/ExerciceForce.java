package com.fittrack.model;

import com.fittrack.strategy.CalculStrategy;

/**
 * Exercice de type force (musculation, pompes, tractions ...)
 */
public class ExerciceForce extends Exercice {
    ///Classe ExerciceForce qui hérite de la classe Exercice

    //Attributs spécifiques à la classe ExerciceForce
    private int series ;
    private int repetitions;

    //Creation du constructeur de la classe ExerciceForce
    // en appelant celui de la classe mère, Exercice, pour initialiser
    // ses attributs et initialiser ceux de la classe fille, ExerciceForce, à 0.
    public ExerciceForce(String pNom, int pDuree, String pDescription) {
        //Appel du constructeur de la classe mère, Exercice
        super(pNom, pDuree, pDescription);
        this.series = 0;
        this.repetitions = 0;
    }

    //en appelant celui de la classe mère, Exercice, pour initialiser ses attributs
    //et initialiser ceux de la classe fille, ExerciceForce, avec les paramètres du constructeur
    public ExerciceForce(String pNom, int pDuree, String pDescription, int pSerie, int pRepetitions) {
        //Appel au constructeur mère, Exercice
        super(pNom, pDuree, pDescription);
        //Initialise les attributs de la classe ExerciceForce avec les paramètres du constructeur
        this.series = pSerie;
        this.repetitions = pRepetitions;
    }

    /**
     * Méthodes spécifiques de la classe ExerciceForce
     */

    //Cree le corps de la méthode abstraite getCalories de Exercice
    //en fonction de l'exercice Force
    @Override
    public double getCalories(CalculStrategy pStrategy){
        //Cacule les calories pour l'exercice Force
        //Les exercices de Force brûlent 20% de calories en plus
        return this.duree * pStrategy.getMet() * 1.2;
    }

    public String getType(){
        //Methoide qui retourne le type d'exercice
        return "Exercice de FORCE";
    }
    public int getSeries(){
        //Methode qui retourne l'attribut series de la classe
        return this.series;
    }
    public int getRepetitions(){
        //Méthode qui retourne l'attribut repetitions de la classe
        return this.repetitions;
    }

    @Override
    public String toString() {
        //Methode qui retourne l'attribut attributs de la classe mère Exercice et celle de la classe fille
        if (this.series > 0 && this.repetitions > 0) {
            return String.format(" %s \n Series : %d \n Repetitions : %d",
                    super.toString(),this.series, this.repetitions);
        }
        return super.toString(); //Affichage des attributs de la classe mère, Exercice
    }

}
