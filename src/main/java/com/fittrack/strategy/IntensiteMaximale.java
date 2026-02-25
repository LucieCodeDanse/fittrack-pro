package com.fittrack.strategy;

public class IntensiteMaximale implements CalculStrategy{
    //Utilisation des signatures des méthodes de l'interface CalculStrategy
    // pour ensuite implémenter le corps de celles ci
    @Override
    public double getMet(){
        //Sprint ou CrossFit intense
        return 12.0;
    }

    @Override
    public String getNom(){
        return "Intensite Maximale";
    }
}
