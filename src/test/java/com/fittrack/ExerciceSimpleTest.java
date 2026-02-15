package com.fittrack;

import com.fittrack.model.ExerciceSimple; //le package du nom de la classe à tester
import org.junit.jupiter.api.Test; // verifit le comportement d'une classe ou d'une méthode attendu
import static org.junit.jupiter.api.Assertions.*;


/*
* Tests pour la classe ExerciceSimple
 */

public class ExerciceSimpleTest {
    //le nom de la classe doit précéder à Test et ne doit pas être Test!!!!!

    @Test
    void testCreationExercice(){
        // Préparation avec le nom de l'exercice et la durée
        String nom = "Course";
        int duree = 30 ;

        //Appel au constructeur de la classe Exemplaires pour créer l'exercice
        ExerciceSimple exercice = new ExerciceSimple(nom, duree);

        //Verification avec Assertion
        assertEquals(nom, exercice.getNom());
        assertEquals(duree, exercice.getDuree());
    }

    @Test
    void testModificationDuree() {
        //Test le remplacement de la durée
        ExerciceSimple exercice = new ExerciceSimple("Velo", 45);
        //Remplacer la durée de l'exercice
        exercice.setDuree(60);
        //Vérification du changement de la durée
        assertEquals(60, exercice.getDuree());
    }

    // Méthode Test pour afficher les donnees des attributs de la classe ExerciceSimple
    @Test
    void testToString(){
        ExerciceSimple exercice = new ExerciceSimple("Natation", 40);

        //Récupère les attributs de exercice
        String resultat = exercice.toString();

        //Vérification avec la méthode contains qui vérifie que la chaîne de caractères et bien dans la variable résultat
        assertTrue(resultat.contains("Natation"));
        assertTrue(resultat.contains("40"));
    }
}
