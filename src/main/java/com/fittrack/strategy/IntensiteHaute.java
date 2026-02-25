package com.fittrack.strategy;

public class IntensiteHaute implements CalculStrategy{
    //Utilisation des signatures des méthodes de l'interface CalculStrategy
    // pour ensuite implémenter le corps de celles ci
    @Override
    public double getMet(){
        //Course rapide ou HIIT
        return 8.5;
    }

    @Override
    public String getNom(){
        return "Intensite Haute";
    }
}
