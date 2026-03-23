package com.fittrack.dao;

import com.fittrack.model.Activite;
import com.fittrack.model.ExerciceCardio;
import com.fittrack.model.ExerciceForce;
import com.fittrack.model.Seance;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation JDNC du DAO pour H2
 */
public class H2SeanceDAO implements SeanceDAO {

    // Attribut de la classe
    // une source de connexion à la base de données
    //Le concept de DataSource est de fournir une interface centralisée pour
    //gérer les connexions
    private DataSource dataSource;

    //Création du constructeur de la classe
    public H2SeanceDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    //On implémente les méthodes de l'interface SeanceDAO
    @Override
    public int save(Seance seance) throws SQLException {
        //Sauvegarder / Ajouter une séance
        //on crée les requêtes SQL d'insertion
        String sqlInsertSeance = "INSERT INTO SEANCE (nom, date) VALUES (?, ?)";
        String sqlInsertExercice = "INSERT INTO EXERCICE (seance_id, type, nom, description, duree, series, repetitions) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        //On vérifie la connexion avec la base de données
        try (Connection connection = dataSource.getConnection()){
            connection.setAutoCommit(false); //transaction
            // On désactive le mode de validation automatique pour la connexion
            //Le pilote JDBC démarre implicitement une nouvelle transaction après chaque validation

            try {
                //1. Insérer la séance
                int seanceId;
                //poour extraire les clés générées automatiquement qui sont créées par une instruction INSERT
                // On crée une instance de type PreparedStatement pour remplacer les ? d'une requête sql par une méthode getter de la classe requise
                try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInsertSeance, Statement.RETURN_GENERATED_KEYS)){
                    preparedStatement.setString(1, seance.getNomSeance());
                    preparedStatement.setDate(2, Date.valueOf(seance.getDateSeance())); //Date.valueOf
                    // accepte une chaîne de caractères représentant une date
                    // exécution du preparedStatement dans la base de données qui la met à jour
                    preparedStatement.executeUpdate();

                    //Récupère l'ID généré à l'aide de ResultSet qui récupère les données d'une
                    // requête sous forme d'un tableau
                    try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                        //lire generatedKeys pour trouver la valeur de seanceId
                        if (generatedKeys.next()) {
                            seanceId = generatedKeys.getInt(1);
                            // remplacer l'attribut de idSeance de l'instance seance avec cet ID genere
                            seance.setIdSeance(seanceId);
                    } else {
                        //Lancer une exception s'il n'y a pas de ID genere
                            throw new SQLException("Echec de la creation de la seance, pas d ID generee !");
                        }
                    }
                }
                // 2. Inserer les exercices
                //On vérifie la connexion avec la base pour créer une instance de PreparedStatement
                try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInsertExercice)) {
                    //une séance est composée de plusieurs exercices, donc on crée une boucle
                    //pour insérer chaque exercice dans la base de données
                    for (Activite activite : seance.getListeActivitesSeance()){
                        //C'est la même séance donc le seanceId ne change pas
                        preparedStatement.setInt(1, seanceId);

                        //Déterminer le type d'exercice "Cardio" ou "force"
                        String type ;
                        //Initialiser les variables qui ont une valeur par défaut 0
                        int series = 0;
                        int repetitions = 0;

                        //définir le type de l'exercice de par l'instance activite
                        if (activite instanceof ExerciceCardio){
                            type = "CARDIO";
                        }else if (activite instanceof ExerciceForce){
                            type = "FORCE";
                            // on initialise les deux variables supplémentaires de l'exercice de type force
                            ExerciceForce exerciceForce = (ExerciceForce) activite; //transtypage
                            series = exerciceForce.getSeries();
                            repetitions = exerciceForce.getRepetitions();
                        }else {
                            //la valeur du type est inconnue
                            type = "UNKNOW";
                        }

                        //On remplie les points d'interrogation de la requête pour ajouter un exercice à la Base de données
                        preparedStatement.setString(2, type);
                        preparedStatement.setString(3, activite.getNom());
                        preparedStatement.setString(4, activite.getDescription());
                        preparedStatement.setInt(5, activite.getDuree());
                        preparedStatement.setInt(6, series);
                        preparedStatement.setInt(7, repetitions);

                        //on exécute le preparedStatement pour mettre à jour la base de données
                        preparedStatement.executeUpdate();
                    }
                }

                connection.commit(); //Validation de la transaction
                return seanceId ;
            } catch (SQLException sqlException) {
                //La transaction ne s'est pas bien passée, une exception est faite
                //Annulation de la transaction
                connection.rollback();
                throw sqlException;
            }

        }
    }
    //Méthode récupérer une séance par la recherche de son id
    @Override
    public Seance findById(int id) throws SQLException {
        //requête sql pour lire la séance du id en paramètre
        String sqlSeance = "SELECT * FROM SEANCE WHERE id = ?";
        //requete sql pour lire l'exercice du id de la seance
        String sqlExercice = "SELECT * FROM EXERCICE WHERE seance_id = ?";

        //Vérifier la connexion à la base de données
        try (Connection connection = dataSource.getConnection()){
            //oui
            // 1. Récupérer la seance
            Seance seance;
            //Vérifier que l'instance preparedStatemment est bien connecté à la requête sqlSeance de la base de données
            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlSeance)){
                //oui
                // On initialise le premier ?  De la requête sqlSeance
                preparedStatement.setInt(1, id);

                //on lit le résultat de la requête grâce à l'instance resultatSet
                //preparedStatement.executeQuery() exécute la requête en retournant le résultat du SELECT
                try (ResultSet resultSet = preparedStatement.executeQuery()){
                    //S'il le resultSet est vide, on retourne null
                    if (!resultSet.next()){
                        return null; //Seance non trouvée
                    }
                    //sinon on récupère les attributs nom et date du rsultatSet et on initialise seance
                    String nom = resultSet.getString("nom");
                    LocalDate date = resultSet.getDate("date").toLocalDate(); // Convertir la date  en LocalDate
                    //On initialise seance
                    seance = new Seance(nom, date);
                    //on remplace le id
                    seance.setIdSeance(id);
                } //fin de la lecture
            }// fin du try 1. Récupérer la seance

            // 2. Récupérer les exercices de la seance
            // Vérifier que l'instance preparedStatemment est bien connecté à la requête sqlExercice
            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlExercice)){
                //Oui
                //On a  une séance composée de plusieurs exercices
                preparedStatement.setInt(1, id);

                //On récupère les exercices de la seance par le lecture du SELECT de la requête sqlExercice
                try (ResultSet resultSet = preparedStatement.executeQuery()){
                    //On lit les exercices de la seance id
                    while (resultSet.next()){
                        // on récupère les attributs d'une activité
                        String type = resultSet.getString("type");
                        String nom = resultSet.getString("nom");
                        String description = resultSet.getString("description");
                        int duree = resultSet.getInt("duree");

                        //On déclare une instance de Activite et on initialise l'exercice
                        // selon son type "Cardio" ou "Force"
                        Activite activite;
                        if ("CARDIO".equals(type)){
                            activite = new ExerciceCardio(nom, duree, description);
                        } else if ("FORCE".equals(type)){
                            //on initialise  les attributs supplémentaires de l'exercice de type FORCE
                            int series = resultSet.getInt("series");
                            int repetitions = resultSet.getInt("repetitions");
                            //on initialise activite avec le constructeur de la classe ExerciceForce
                            activite = new ExerciceForce(nom, duree, description, series, repetitions);
                        }else {
                            continue; // Type inconnu, on ignore
                        }
                        //fin de l'initiation de l'exercice, on peut l'ajouter à la seance
                        seance.ajouterActivite(activite);
                    } // fin de la lecture de tous les exercices de la seance
                }
            } // fin de try 2. Récupérer les exercices
            //on peut retourner la seance
            return seance;
        }//fin du try connection datasource
    }//fin de la méthode findById

    //Implémentation de la méthode findAll retournant la liste de toutes les séances
    public List<Seance> findAll() throws SQLException {
        //On crée une List de type Seance
        List<Seance> listeSeances = new ArrayList<>();
        // On fait une requête qui retourne tous les id des séances créer par date de la plus récente à la plus vieille
        String sqlSeance = "SELECT * " +
                            "FROM SEANCE " +
                            "ORDER BY date DESC";

        //On vérifie la connexion à dataSource (la base de données )
        try (Connection connection = dataSource.getConnection();
        // On crée une instance statement pour exécuter le requête
             Statement statement = connection.createStatement();
            //On met le résultat de la requête dans la variable resultSet de type ResultSet
             //pour ensuite pourvoir lire le résultat du SELECT
             ResultSet resultSet = statement.executeQuery(sqlSeance)  ){
            //on lit resultSet qui est une liste de id
            while (resultSet.next()){
                //on récupère l' id de la seance lu en cours
                int id = resultSet.getInt("id");
                // On initialise la seance grâce à la méthode finById
                Seance seance = findById(id); //retourne la seance du id
                //on vérifie que la seance est bien réelle avant de l'ajouter à la liste
                if (seance != null){
                    listeSeances.add(seance);
                }
            }
        }
        return listeSeances;
    }

    //Implementation de la méthode delete qui supprime la seance du ud
    @Override
    public boolean delete(int id) throws SQLException {
        // On crée la requête sql qui cherche id dans la table SEANCE et efficace la seance
        String sqlDeleteSeance = "DELETE FROM SEANCE WHERE id = ?";

        //On vérifie la connexion à la base de données puis on crée un preparedStatement
        //pour initier le id de sqlDeleteSeance
        try (Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sqlDeleteSeance)) {

            preparedStatement.setInt(1, id);
            //on récupère le Nombre de lignes qui a supprimé par l'exécution du preparedStatement
            int rowsAffected = preparedStatement.executeUpdate();
            // on retourne True si ce nombre est positif sinon false
            return rowsAffected > 0;
        }
    }
}
