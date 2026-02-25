package com.fittrack.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Represente une séance d'entraînement contenant plusieurs exercices
 */
public class SeanceSimple {

    private String nom; //Nom de l'exercice
    private LocalDate date; //Date du jour
    private List<ExerciceSimple> exercices; // Liste d'exercice

    // Constructeur de la classe
    public SeanceSimple( String p_nom, LocalDate p_date) {
        nom = p_nom;
        date = p_date;
        exercices = new ArrayList<>(); //création d'une liste
    }

    /**
     * Ajoute un exercice à la séance
     */
    public void ajouterExercice(ExerciceSimple p_exercice) {
        //Ajouter l'exercice à la liste exercices
        exercices.add(p_exercice);
    }

    /**
    * Calcul la durée totale de la séance
     * @return durée totale en minutes
     */
    public int getDureeTotale(){
        int duree_total = 0;
        // Boucle qui lit les éléments de la liste exercices
        for (ExerciceSimple unExercice : exercices) {
            duree_total = duree_total + unExercice.getDuree(); // additionne chaque durée d'un exercice de la liste exercices
        }
        return duree_total;
    }

    /**
     * Retourne le nombre d'exercices
     * @return nombre_exercices
     */
    public int getNombreExercices(){
        //Retourner la taille de la liste exercices
        int nombre_exercices = exercices.size();
        return nombre_exercices;
    }
    //Méthode pour retourner les données des attributs de l'objet seanceSimple
    public String getNom() {
        return nom;
    }
    public LocalDate getDate() {
        return date;
    }
    public List<ExerciceSimple> getExercices() {
        return new ArrayList<>(exercices); // copie defensive
    }

    @Override
    public String toString() {
        return String.format("Seance '%s' du %s - %d exercice (%d minutes) ", nom, date, getNombreExercices(), getDureeTotale());
    }
}
