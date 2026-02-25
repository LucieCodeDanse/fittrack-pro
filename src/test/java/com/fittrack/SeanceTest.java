package com.fittrack;

import com.fittrack.model.*; //classes du model

import org.junit.jupiter.api.Test; //la classe Test pour effectuer des tests sur les méthodes de la classe Seance
import org.junit.jupiter.api.BeforeEach;//la classe BeforeEach pour effectuer un test avant tout les autres tests de la classe
import org.junit.jupiter.api.DisplayName; //Pour afficher des messages de tests sur la sortie standard

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Voici les tests de la classe Seance")
public class SeanceTest {
    //Classes pour effectuer les tests des méthodes de la classe Seance

    //Définition d'une instance de la classe Seance
    private Seance seance; //Attribut de la classe

    //Premier test avant chaque test
    @BeforeEach
    void setUp() {
        //Méthode qui appelle le constructeur de la classe Seance et initiale le nom de la séance et la date du jour
        seance = new Seance("Seance Full Body !!", LocalDate.now());
    }

    @Test
    @DisplayName("Premier test : une seance vide a une duree de 0 ")
    void testSeanceVide() {
        assertEquals(0, seance.getDureeTotale()); // durée totale de la séance faut 0
        assertEquals(0, seance.getNombreActivites()); // la séance ne contient pas d'activité
    }

    @Test
    @DisplayName("Deuxieme test : une seance peut etre composee de differents types d'activites (test ajouter une activite) ")
    void testAjouterDifferentsTypesActivites(){
        // Polymrphisme : Activite peut être de type Cardio ou Force
        //Initialise les exercice Cardio et Force
        Activite exerciceCardio1 = new ExerciceCardio("Course",30, "5km");
        //Pompes : 15 min, 4 séries, 20 fois
        Activite exerciceForce1 = new ExerciceForce("Pompes", 15, "4 series", 4, 20);

        //Ajouter ces deux activités à la séance
        seance.ajouterActivite(exerciceCardio1);
        seance.ajouterActivite(exerciceForce1);

        //Tests du nombre d'activités et de la durée totale de la séance
        // 2 activités, 45 min duree totale
        assertEquals(2, seance.getNombreActivites());
        assertEquals(45, seance.getDureeTotale());
    }

    @Test
    @DisplayName("Troisieme test : la duree totale de la seance est correctement calculee ")
    void testCalculerDureeTotale(){
        //Initialisation des exercices
        Activite exerciceCadioVelo = new ExerciceCardio("Velo", 25, "10km");
        Activite exerciceCardioCourse = new ExerciceCardio("Course", 20, "3km");
        Activite exerciceForceSquats = new ExerciceForce("Squats", 10, "Jambes");

        //Ajoute les activités dans la liste
        seance.ajouterActivite(exerciceCadioVelo);
        seance.ajouterActivite(exerciceCardioCourse);
        seance.ajouterActivite(exerciceForceSquats);

        //Test  de la durée totale de la séance
        //10+20+25=55 min
        assertEquals(55, seance.getDureeTotale());

    }
}
