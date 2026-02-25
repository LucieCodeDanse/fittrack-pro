package com.fittrack.strategy;

public class IntensiteModeree implements CalculStrategy{
    //Utilisation des signatures des méthodes de l'interface CalculStrategy
    // pour ensuite implémenter le corps de celles ci

    @Override
    public double getMet(){
        //Jogging ou vélo modéré
        return 6.0;
    }

    @Override
    public String getNom(){
        return "Intensite Moderee";
    }
}
