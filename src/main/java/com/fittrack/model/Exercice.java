package com.fittrack.model;

import com.fittrack.strategy.CalculStrategy;

/**
 * Classe abstraite représentant un exercice physique.
 * Factorise le code commun à tous les exercices.
 */
public abstract class Exercice implements Activite {
    //Les attributs de la classe Exercice
    protected String nom;
    protected int duree;
    protected String description;

    // Constructeur de la classe
    public Exercice(String pNom, int pDuree, String pDescription) {
        // Si le nom est nul ou si la chaîne est vide
        //la méthode trim() permet de retirer les blancs en début et fin de chaîne.
        //La méthode isEmpty() vérifie si une chaîne de caractères à une longueur de zéro.
        //Elle retourne true si la chaîne est vide sinon false.
        if (pNom == null || pNom.trim().isEmpty() == true){
            // déclenchement de l'exception
            throw new IllegalArgumentException("Le nom ne peut pas être vide !");
        }

        if (pDuree <= 0) {
            //si la duree est inférieure ou égale à , l'exception est déclenchée
            throw new IllegalArgumentException("La duree doit être positive !!") ;
        }
        //Initialise les attributs de la classe avec les paramètres du constructeur
        this.nom = pNom;
        this.duree = pDuree;
        this.description = pDescription;
    }

    //Initialise les méthodes de la classe avec les signatures de l'interface Activite

    @Override
    public String getNom(){
        //Methode qui retourne l'attribut nom
        return this.nom;
    }

    @Override
    public int getDuree(){
        //Méthode qui retourne l'attribut duree
        return this.duree;
    }

    @Override
    public String getDescription(){
        //Methode qui retourne l'attribut description
        return this.description;
    }

    /**
     * Méthode abstraite : chaque type d'exercice calcule ses calories différemment
     */
    @Override
    public abstract double getCalories(CalculStrategy pStrategy);

    //Methode toString() qui retourne l'attribut attributs de la classe Exercice en format String
    @Override
    public String toString(){
        return  String.format(" nom : %s \n duree : %d minutes \n description : %s",
                this.getNom(), this.getDuree(), this.getDescription());
    }
}
