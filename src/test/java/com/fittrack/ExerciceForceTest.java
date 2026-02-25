package com.fittrack;

import com.fittrack.model.ExerciceForce;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

public class ExerciceForceTest {
    // Classe pour tester les méthodes de la classe ExerciceForce

    @DisplayName("Premier test : Creation d'un exercice de type Force et verifit les attributs ")
    @Test
     void testCreationExerciceForce() {
        //Initialisation de l'instance exercice de la classe ExerciceForce
        ExerciceForce exerciceForce1 = new ExerciceForce(
                "Pompes",
                10,
                "Pectoraux et triceps",
                4,
                15
        );

        //Verifit les méthodes gettes (retournes) de la classe ExerciceFotce
        assertEquals("Pompes", exerciceForce1.getNom());
        assertEquals(10, exerciceForce1.getDuree());
        assertEquals("Pectoraux et triceps", exerciceForce1.getDescription());
        assertEquals(4, exerciceForce1.getSeries());
        assertEquals(15, exerciceForce1.getRepetitions());
    }

    @DisplayName("Deuxieme test : Verifit le declenchement de l'exception lorsque l'attribut nom est vide ")
    @Test
    void testNomVideDeclenchementErreur(){
        assertThrows(IllegalArgumentException.class,
                () -> {new ExerciceForce("", 10, "Description");
        }); // Création de l'exercice Force lorsque cette fonction sera appelée. La fonction ne prend aucun paramètre
        //La flèche représente une expression lambda , une manière courte décrire une fonction sans lui donner de nom
    }

    @DisplayName("Troisieme test : Verifit le declenchement de l'exception lorsque l'attribut duree est negatif ")
    @Test
    void testDureeNegativeDeclenchementErreur(){
        assertThrows(IllegalArgumentException.class,
                () -> {new ExerciceForce("", -5, "Description");
        });
    }

}
