package com.fittrack;

import com.fittrack.model.ExerciceCardio;
import com.fittrack.model.ExerciceForce;
import com.fittrack.model.Seance;
import com.fittrack.strategy.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Voici les tests du pattern Strategy pour les calories des calories ")
public class CalculStrategyTest {

    @Test
    @DisplayName("Premier test : exercice de type Cardio avec un intensite modere ")
    void testCardioIntensiteModeree(){
        //Création de l'exercice Cardio
        ExerciceCardio exerciceCardioCourse = new ExerciceCardio(
                "Course",
                30,
                "5km");
        //Creation d'une stratégie en faisant appel au constructeur de la classe IntensiteModeree
        CalculStrategy strategieIntensiteModeree = new IntensiteModeree();

        //On initiale la variable calories avec la méthode getCalories(strategieIntensiteModeree)
        // de l'intense exerciceCardioCourse
        double caloriesCourseMo = exerciceCardioCourse.getCalories(strategieIntensiteModeree);

        //On vérifie le calcul des calories
        //30 min x 6.0 MET = 180 calories
        assertEquals(180.0, caloriesCourseMo, 0.01); //delta = 0.01
    }

    @Test
    @DisplayName("Deuxieme test : Exercice de type Force brulant 20% de calories en plus  ")
    void testForceAvecBonus() {
        //Creation de l'exercice pompe de type Force
        ExerciceForce exerciceForcePompes = new ExerciceForce(
                "Pompes",
                10,
                "Pectoraux"
        );
        //Creation d'une stratégie en faisant appel au constructeur de la classe IntenseModeree
        CalculStrategy strategieModeree = new IntensiteModeree();

        //On initialise la variable caloriesPompesMo avec la méthode de l'instance exerciceForcePompes
        double caloriesPompesMo = exerciceForcePompes.getCalories(strategieModeree);

        //On verifit le calcul des Calories
        // 10 min x 6.0 MET * 1.2 = 72 calories
        assertEquals(72.0, caloriesPompesMo, 0.01);

    }

    @Test
    @DisplayName("Troisieme test : on prend le meme exercice mais avec des strategies differentes donc on aura des resultats differents ")
    void testDifferentesStrategies(){
        //On crée un Exercice de type Cardio
        ExerciceCardio exCardioVelo = new ExerciceCardio("Velo", 45, "Route");

        //Initialise les variables selon tous les types de catégorie par rapport à l'exercice velo
        double caloriesFaible = exCardioVelo.getCalories(new IntensiteFaible());
        double caloriesModeree = exCardioVelo.getCalories(new IntensiteModeree());
        double caloriesHaute = exCardioVelo.getCalories(new IntensiteHaute());
        double caloriesMax = exCardioVelo.getCalories(new IntensiteMaximale());

        //On vérifie que plus l'intensité augmente plus le nombre de calories dépensées est grand
        //assertTrue(conditionAverifier)
        assertTrue(caloriesFaible < caloriesModeree);
        assertTrue(caloriesModeree < caloriesHaute);
        assertTrue(caloriesHaute < caloriesMax);
    }

    @Test
    @DisplayName("Quatrieme test : on calcule le nombre total de calories d'une seance d exercice ")
    void testCaloriesTotaleSeance() {
        //On Initialise une Seance avec son nom et l'horaire actuelle
        Seance seance = new Seance("Morning Workout", LocalDate.now());

        //On ajoute des exercices à la seance
        seance.ajouterActivite(new ExerciceCardio("Course", 20, "5km"));
        seance.ajouterActivite(new ExerciceForce("Squats", 15, "Jambes"));
        seance.ajouterActivite(new ExerciceCardio("Velo", 10, "Cool down"));

        //On crée une stratégie avec une intensité Haute
        CalculStrategy strategieHaute = new IntensiteHaute();

        //On calcule le nombre de calories totale de cette séance avec cette stratégie (8.0 MET
        double nbCaloriesTotal = seance.getCaloriesTotales(strategieHaute);

        //On Vérifie le résultat
        // Course : 20 x 8.5 = 170.0
        // Squats :  15 x 8.5 x 1.2 = 153.0
        // Velo : 10 x 8.5 =  85.0
        // Total : 170 + 153 + 85 = 408.0
        assertEquals(408.0, nbCaloriesTotal, 0.01);
    }

}
