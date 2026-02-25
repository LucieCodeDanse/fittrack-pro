package com.fittrack.model;

import com.fittrack.strategy.CalculStrategy;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Séance d'entraînement composée de plusieurs activités
 */
public class Seance {

    //Déclaration des attributs de la classe Seance
    private int idSeance ;
    private String nomSeance ;
    private LocalDate dateSeance ;
    private List<Activite> listeActivitesSeance; //liste de type Activite

    //Création du constructeur de la classe Seance
    public Seance(String pNomSeance, LocalDate pDateSeance) {
        this.nomSeance = pNomSeance ;
        this.dateSeance = pDateSeance ;
        this.listeActivitesSeance = new ArrayList<Activite>(); //Creation de la liste de type Activite
    }

    /**
     * Ajoute une activité à la séance
     */
    public void ajouterActivite(Activite pActivite) {
        //Methode qui ajoute pActivite à la liste des activités de la séance
        this.listeActivitesSeance.add(pActivite) ;
    }
    /**
     * Calcule la durée totale de la séance
     */
    public  int getDureeTotale(){
        int duree_total = 0;
        for (Activite uneActivite : this.listeActivitesSeance){
            duree_total = duree_total + uneActivite.getDuree();
        }
        return duree_total;
    }

    /**
     * Calcule les calories totales selon une stratégie d'une séance d'exercice
     */
    public double getCaloriesTotales(CalculStrategy pStrategy){
        Double caloriesTotales = 0.0;
        for (Activite uneActivite : this.listeActivitesSeance){
            caloriesTotales = caloriesTotales + uneActivite.getCalories(pStrategy);
        }
        return caloriesTotales;
    }

    /**
     *Retourner la taille de la liste listeActivitesSeance
     */
    public int getNombreActivites(){
        return this.listeActivitesSeance.size();
    }

    /**
     * Méthodes pour retourner les attributs de la classe et pour les remplacer
     * Getters/Setters
     */
    public int getIdSeance() {
        return idSeance;
    }
    public void setIdSeance(int idSeance) {
        this.idSeance = idSeance;
    }
    public String getNomSeance() {
        return nomSeance;
    }
    public LocalDate getDateSeance() {
        return dateSeance;
    }
    public List<Activite> getListeActivitesSeance() {
        return new ArrayList<>(listeActivitesSeance); //copie defensive
    }

    /**
     * Methode toString() qui retourne les attributs de la classe sous format String
     */
    @Override
    public String toString(){
        return String.format("Nom de la seance : '%s' \nDate : %s \nNombre d'activités : %d \nDuree totale : %d minutes",
                this.nomSeance, this.dateSeance,  this.getNombreActivites(), this.getDureeTotale());
    }

}
