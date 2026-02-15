package com.fittrack.model;

/**
 * Représentant un exercice simple avec un nom et une durée
 */
public class ExerciceSimple {

    private String nom; // Nom de l'utilisateur
    private int duree; // en minutes

    //Création du constructeur de la classe
    public ExerciceSimple(String nom, int duree) {
        this.nom = nom;
        this.duree = duree;
    }

    // Création des méthodes de la classe
    // Méthodes pour retourner le nom et la durée
    public String getNom() {
        return nom;
    }
    public int getDuree() {
        return duree;
    }

    // Méthodes pour remplacer les attributs de la classe
    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    //Afficher les informations de l'exercice
    @Override
    public String toString() {
        return nom + " - " + duree + " minutes";
    }
}
