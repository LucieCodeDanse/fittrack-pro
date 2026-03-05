package com.fittrack;

import com.fittrack.model.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class App {
    public static void main(String[] args) {

       // System.out.print("Bonjour à tous !");

        //Creer un exercice de type Cardio de manière centralisée
        //parametre est une liste de couples (nom_attribut , valeur_attribut)
        Map<String, Object> parametres = new HashMap<>();
        //Inisalise la liste parametres
        parametres.put("pNom", "Course");
        parametres.put("pDuree", 30);
        parametres.put("pDescription", "5km");
        // création de l'exercice cardio grâce à la classe ActiviteFactory
        Activite activiteExCardio = ActiviteFactory.createActivite("Cardio", parametres);

        //meme chose avec l'activité force
        Map<String, Object> listeParams = new HashMap<>();
        listeParams.put("pNom", "Developpe couche");
        listeParams.put("pDuree", 20);
        listeParams.put("pDescription", "Pectoraux");
        listeParams.put("pSeries", 5);
        listeParams.put("pRepetitions", 8);

        //création de l'exercice de type force grâce à la classe ActiviteFactory
        Activite activiteForce = ActiviteFactory.createActivite("Force", listeParams);

        String valExCardio = activiteExCardio.toString();
        //System.out.println("Voici les attributs de l activite : \n"
          //      + valExCardio);

        ExerciceForce exerciceForce = (ExerciceForce) activiteForce;
        //String valExForce = activiteForce.toString();
        String valForce = exerciceForce.toString();
        //System.out.println("Voici les attributs de l exercice Force : \n"+valExForce);
      ///  System.out.println("Voici les attributs de l exercice Force : \n"+valForce);
        int serie = exerciceForce.getSeries();
        int repetitions = exerciceForce.getRepetitions();
        System.out.println("Serie : " + serie + " \nRepetitions : " + repetitions);

        ExerciceForce exerciceForce1 = new ExerciceForce("Muscu", 10, "Bras", 2, 5);
        int serie1 = exerciceForce1.getSeries();
        int repetitions1 = exerciceForce1.getRepetitions();
        //System.out.println("Serie : " + serie1 + " \nRepetitions : " + repetitions1);
        //System.out.println("execiceForce1 : \n" + exerciceForce1.toString());

        //Créations des séances de manière fluide et lisible à l'aide du pattern Builder
        Seance seanceAvecBuilder = new Seance.Builder()
                .setNomSeance("Seance HIIT")
                .setDateSeance(LocalDate.now())
                .addActivite(new ExerciceCardio("Burpees", 3, "Full body"))
                .addActivite(new ExerciceCardio("Mountain climbers",3,"Core"))
                .addActivite(new ExerciceForce("Pompes", 2, "Pectoraux", 3, 15))
                .addActivite(new ExerciceCardio("Jumping jacks", 2, "Cardio"))
                .build();

        //System.out.println("Affichage de la seance cree a l aide du pattern Builder : \n"
           //     + seanceAvecBuilder.toString());


    }
}
