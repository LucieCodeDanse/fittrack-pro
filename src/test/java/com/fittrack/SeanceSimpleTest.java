package com.fittrack;

import com.fittrack.model.ExerciceSimple;
import com.fittrack.model.SeanceSimple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;



public class SeanceSimpleTest {
    //Classe de test du modèle SeanceSimple

    private SeanceSimple seance; // membre privé n'est pas accessible de l'extérieur de la classe

    @BeforeEach
    void setUp() {
        //Exécuté avant chaque test
        seance = new SeanceSimple("Seance du matin ", LocalDate.now());
    }

    @Test
    void testSeanceVide(){
        //Test d'une séance sans exercice et sans durée
        assertEquals(0, seance.getNombreExercices());
        assertEquals(0,seance.getDureeTotale());
    }

    @Test
    void testAjouterUnExercice() {
        //Test pour vérifier l'ajout d'un exercice dans une séance

        //Appel au constructeur de la classe ExempleSimple pour créer une instance exercice
        ExerciceSimple exercice = new ExerciceSimple("Pompes", 10);

        //On ajoute cet exercice à la seance
        seance.ajouterExercice(exercice);

        //On vérifie que l'ajout est bien réalisé
        assertEquals(1, seance.getNombreExercices()); //un exercice dans la liste des exercice de la séance
        assertEquals(10, seance.getDureeTotale()); // cet exercice fait 10 minutes
    }

    @Test
    void testAjouterPlusieursExercices(){
        //Test pour vérifier l'ajoute de plusieurs exercices dans une séance

        //Appel au constructeur de la classe ExempleSimple pour créer des instances exercices
        ExerciceSimple exercice_1 = new ExerciceSimple("Couse", 30 );
        ExerciceSimple exercice_2 = new ExerciceSimple("Velo", 45 );
        ExerciceSimple exercice_3 = new ExerciceSimple("Natation", 20 );

        //On ajoute ces exercices à la séance
        seance.ajouterExercice(exercice_1);
        seance.ajouterExercice(exercice_2);
        seance.ajouterExercice(exercice_3);

        //On vérifie que les ajouts ont bien été réalisés
        assertEquals(3, seance.getNombreExercices()); // 3 exercices sont dans la listes des exercices de la séance
        assertEquals(95, seance.getDureeTotale()); // 30 + 45 + 20 = 95 min durée de la séance
    }
}

