package com.fittrack.model;

import java.util.Map;

/**
 * Factory pour créer des activités de manière centralisée
 */

public class ActiviteFactory {

    /**
     * Crée une activité selon le type spécifique
     *Les noms des paramètres doivent être les mêmes définis dans params pour le constructeur !!!!!!!!!!!!!!!!!!!!!!
     *
     * @param type "cardio" ou "force"
     * @param params map contenant pNom, pDuree, pDescription, pSeries, pRepetitions
     * @return une instance d'Activite
     */

    public static Activite createActivite(String type, Map<String, Object> params){
        //On initie les valeurs des paramètres du constructeur, pNom, pDescrption et pDuree (de la classe mere).
        String nom = (String)params.get("pNom");
        int duree = (int)params.get("pDuree");
        String description = (String)params.getOrDefault("pDescription", ""); //il y une chaîne de caractères complète ou vide


        //On définie plusieurs choix grâce au type de l'exercice
        switch (type.toLowerCase()) {
            // On définit la chaîne de caractères en minuscule

            //si l'exercice est de type cardio, on appelle le constructeur ExerciceCardio pour créer l'instance exerciceCardio avec les paramètres que l'on a défini
            case "cardio":
                ExerciceCardio exerciceCardio = new ExerciceCardio(nom,duree,description);
                return exerciceCardio;

            //Sinon si l'exercice est de type force, on appelle le constructeur ExerciceForce pour créer l'instance exerciceForce avec les paramètres que l'on a défini
            // plus series et repetitions
            case "force":
                //On initialise les deux nouvelles valeurs pour faire appel au constructeur ExerciceForce (classe fille )
                int series = (int)params.get("pSeries");
                int repetitions = (int)params.get("pRepetitions");
                //On verifit que les deux paramètres en plus pour créer l'exercice de type force existe bien
                if (series > 0 && repetitions > 0){ // J'ai du modifier pour prendre cette condition en compte

                    //On crée l'instance exerciceForce
                    ExerciceForce exerciceForce = new ExerciceForce(nom,duree,description,series,repetitions);
                    return exerciceForce;
                }else {
                    //Si ces deux paramètres n'existent pas, on fait appel au deuxième constructeur de ExerciceForce
                    // avec series et repetition initialisés 0
                    ExerciceForce exerciceForce = new ExerciceForce(nom,duree,description);
                    return exerciceForce;
                }
            //Pour tout autre cas, une exception signalant un argument illegal est appelé
            default:
                throw new IllegalArgumentException("Type inconnu : "  +  type);
        }
    }

    /**
     * Methodes de convenance pour créer rapidement des activités
     */
    public static Activite createCardio(String pNom, int pDuree, String pDescription){
        //Méthode qui retourne une instance  d'un exercice de type Cardio avec les données en paramètres
        ExerciceCardio exerciceCardio = new ExerciceCardio(pNom,pDuree,pDescription);
        return exerciceCardio;
    }

    public static Activite createForce(String pNom, int pDuree, String pDescription, int pSeries, int pRepetitions){
        //Méthode qui retourne une instance  d'un exercice de type Force avec les données en paramètres
        ExerciceForce exerciceForce = new ExerciceForce(pNom,pDuree,pDescription,pSeries,pRepetitions);
        return exerciceForce;
    }


}
