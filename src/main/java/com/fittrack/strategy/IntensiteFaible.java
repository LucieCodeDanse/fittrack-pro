package com.fittrack.strategy;

public class IntensiteFaible implements CalculStrategy {

    //Utilisation des signatures des méthodes de l'interface CalculStrategy
    // pour ensuite implémenter le corps de celles ci
    @Override
    public double getMet(){
        //Marche lente ou yoga doux
        return 3.5;
    }

    @Override
    public String getNom(){
        return "Intensite Faible";
    }
}
