package com.fittrack.model;

import com.fittrack.strategy.CalculStrategy;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

 //Builder Pattern permet de construire des objets complexes séparant sa construction de sa
// représentation et ainsi pouvoir créer différents types et représentations d'un objet en utilisant le
// même processus de construction
/**
 * Séance d'entraînement composée de plusieurs activités
 */
public class Seance {

    //Déclaration des attributs de la classe Seance
    private int idSeance ;
    private String nomSeance ;
    private LocalDate dateSeance ;
    private List<Activite> listeActivitesSeance; //liste de type Activite

    //Création du constructeur de la classe Seance privé pour forcer l'utilisation du Builder
    //que l'on a déclaré en interne
    private Seance(Builder pBuilder){
        this.nomSeance = pBuilder.nomSeance;
        this.dateSeance = pBuilder.dateSeance;
        this.listeActivitesSeance = new ArrayList<>(pBuilder.listeActivitesSeance);
    }



    //Creation du constructeur de la classe Seance compatible avec les tests déjà existants
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
        return String.format("Nom de la seance : '%s' \nDate : %s \nNombre d'activites : %d \nDuree totale : %d minutes",
                this.nomSeance, this.dateSeance,  this.getNombreActivites(), this.getDureeTotale());
    }

    /**
     * Builder pour construire une Seance de manière fluide
     */

    //On crée une classe statique Builder public
    public static class Builder {
        //On définit les mêmes attributs que ceux de la classe Seance
        private String nomSeance ;
        private LocalDate dateSeance ;
        private List<Activite> listeActivitesSeance = new ArrayList<>();

        //on définit également ses méthodes getters et setters rassemblé de type Builder

        public Builder setNomSeance(String pNomSeance) {
            this.nomSeance = pNomSeance ; //setter
            return this; // getter
        }

        public Builder setDateSeance(LocalDate pDateSeance) {
            this.dateSeance = pDateSeance ;
            return this;
        }
        //ajouter une activité
        public Builder addActivite(Activite pActivite) {
            this.listeActivitesSeance.add(pActivite) ;
            return this;
        }

        //ajouter une liste d'activités
        public Builder addActivites(List<Activite> pListeActivites) {
            this.listeActivitesSeance.addAll(pListeActivites) ; // méthode addAll() ajoute les éléments de la liste
            return this;
        }

        //On définit les exceptions par la méthode build de type Seance
        public Seance build(){
            //si le nom est nul ou une chaîne de caractères vide
            if (nomSeance == null || nomSeance.trim().isEmpty()){
                throw new IllegalStateException("Le nom de la seance est demande !");
            } //sinon si la date est nul
            else if (dateSeance == null){
                throw new IllegalStateException("La date de seance est demande !");
            }
            else {
                //Sinon on retourne l'instance de la la classe Seance que l'on a créé grâce à la classe Builder
                return new Seance(this); // this contient tous les retours des  méthodes de type Builder
            }
        }
    }


}
