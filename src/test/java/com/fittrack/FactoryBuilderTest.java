package com.fittrack;

import com.fittrack.model.*;
import com.fittrack.strategy.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Voici les tests des patterns Factory et Builder ")
public class FactoryBuilderTest {

    @Test
    @DisplayName("Factory cree un exercice de type  cardio")
    void testFactoryCardio(){
        //creation de liste de coupe <String, Object>
        Map<String, Object> listeParams = new HashMap<>();
        listeParams.put("pNom", "Natation");
        listeParams.put("pDuree", 40);
        listeParams.put("pDescription", "Crawl");

        //Création de l'activité Cardio avec listeParams et ActiviteFactory
        Activite activite = ActiviteFactory.createActivite("cardio", listeParams);

        //Vérifier que l'exercice Cardio est bien créer
        assertNotNull(activite); //activite n'est pas null
        assertTrue(activite instanceof ExerciceCardio); // activite a une instance de la classe ExerciceCardio
        //On vérifier les valeurs des attributs nom et duree de l'instance activite
        assertEquals("Natation", activite.getNom());
        assertEquals(40, activite.getDuree());
    }

    @Test
    @DisplayName("Factory cree un exercice de force")
    void testFactoryForce(){
        Map<String, Object> listeParams = new HashMap<>();
        listeParams.put("pNom", "Developpe couche");
        listeParams.put("pDuree", 20);
        listeParams.put("pDescription", "Pectoraux");
        listeParams.put("pSeries", 5);
        listeParams.put("pRepetitions", 8);

        Activite activite = ActiviteFactory.createActivite("force", listeParams);

        //Vérifications
        assertTrue(activite instanceof ExerciceForce);
        //Transtypage ou cast : conversion d'une expression d'un certain type en une expression d'un autre type
        //C'est plus compréhensible
        ExerciceForce exerciceForce = (ExerciceForce) activite;
        //On vérifie les valeurs des attributs series et repetitions de l'instance exerciceForce
        assertEquals(5, exerciceForce.getSeries());
        assertEquals(8, exerciceForce.getRepetitions());
    }

    @Test
    @DisplayName("Factory lance un exception pour un type d'activite inconnu ")
    void testFactoryTypeActiviteInconnu(){
        Map<String, Object> listeParams = new HashMap<>();
        listeParams.put("pNom", "Test");
        listeParams.put("pDuree", 10);

        //Création d'une exception sur le type inconnu
        assertThrows(IllegalArgumentException.class, () -> ActiviteFactory.createActivite("yoga", listeParams));
    }

    @Test
    @DisplayName("Bulder construit une seance complete ")
    void testBuilderSeanceComplete(){
        Seance seance = new Seance.Builder()
                .setNomSeance("Full Body Workout")
                .setDateSeance(LocalDate.of(2026, 1, 25)) // date choisie 25.01.202
                .addActivite(ActiviteFactory.createCardio("Echauffement", 10, "Tapes"))
                .addActivite(ActiviteFactory.createForce("Squats", 15, "Jambes", 4, 12))
                .addActivite(ActiviteFactory.createCardio("Course", 20, "Enurance"))
                .build();


        //Verification de la création de la séance complète avec Builder
        assertEquals("Full Body Workout", seance.getNomSeance());
        assertEquals(3, seance.getNombreActivites());
        assertEquals(45, seance.getDureeTotale());
    }

    @Test
    @DisplayName("Builder verifie que la date est requise")
    void testBuilderDateRequise(){
        //déclenchement de l'exception la date est demandé
        assertThrows(IllegalStateException.class,
                () -> {
            new Seance.Builder()
                    .setNomSeance("Test")
                    .build();
                });
    }

    @Test
    @DisplayName("Exemple d'utilisation fluide du Builder ")
    void testBuilderFluent(){
        //Demonstraion de la syntaxe fluide et lisible
        Seance seance = new Seance.Builder()
                .setNomSeance("Seance HTT")
                .setDateSeance(LocalDate.now())
                .addActivite(new ExerciceCardio("Burpees", 3, "Full body"))
                .addActivite(new ExerciceCardio("Mountain climbers", 3, "Core"))
                .addActivite(new ExerciceForce("Pompes", 2, "Pectoraux", 3, 15))
                .addActivite(new ExerciceCardio("Jumping jacks", 2, "Cardio"))
                .build();

        //On crée un stratégie intensité maximale
        CalculStrategy intensiteMax = new IntensiteMaximale();

        //On vérifie la durée totale de la séance et le nombre totale de calories avec la stratégie intensité max
        assertTrue(seance.getDureeTotale() > 0);
        assertTrue(seance.getCaloriesTotales(intensiteMax) > 0);
    }


}


