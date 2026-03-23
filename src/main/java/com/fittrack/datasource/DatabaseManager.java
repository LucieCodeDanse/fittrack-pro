package com.fittrack.datasource;

import org.h2.jdbcx.JdbcDataSource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Gere la connexion à la base de données H2 et l'utilisation des tables
 */

public class DatabaseManager {

    private DataSource dataSource;

    //Constructeur de la classe
    public DatabaseManager() {
        JdbcDataSource jdbcDataSource = new JdbcDataSource();
        //Base de données en mémoire
        jdbcDataSource.setURL("jdbc:h2:mem:fittrackbd;DB_CLOSE_DELAY=-1");
        jdbcDataSource.setUser("sa");
        jdbcDataSource.setPassword("");
        this.dataSource = jdbcDataSource;
    }

    /**
     * Retourne la source de données
     */
    public DataSource getDataSource() {
        return dataSource;
    }

    /**
     * Cree les tables nécessaire
     */

    public void createTables() throws SQLException {
        //on vérifie la connexion avec la base de données, si c'est ok on crée un statement qui permet de
        //réaliser une requête sql, c'est un curseur
        try (Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement()) {
            System.out.println("Connexion avec la base de donnees est etablie !");

            //Table des séances
            // Exécuter les requêtes sql grâce à statement
            // id est une clé première et elle est incrementee automatiquement
            statement.execute(
                    "CREATE TABLE IF NOT EXISTS SEANCE (" +
                            "id INT AUTO_INCREMENT PRIMARY KEY," +
                            "nom VARCHAR(255) NOT NULL," +
                            "date DATE NOT NULL" +
                            ")"

            );

            //Table des exercice
            //seance_id est une clé étrangère qui fait référence à la classe SEANCE
            statement.execute(
                    "CREATE TABLE IF NOT EXISTS EXERCICE (" +
                            "  id INT AUTO_INCREMENT PRIMARY KEY," +
                            "  seance_id INT NOT NULL," +
                            "  type VARCHAR(50) NOT NULL," +
                            "  nom VARCHAR(255) NOT NULL," +
                            "  description VARCHAR(500)," +
                            "  duree INT NOT NULL," +
                            "  series INT DEFAULT 0," +
                            "  repetitions INT DEFAULT 0," +
                            "  FOREIGN KEY (seance_id) REFERENCES SEANCE(id) ON DELETE CASCADE" +
                            ")"

            );

            System.out.println("Tables crees avec succes !");
        }
    }

    /**
     * Supprime toutes les tables (utile pour les tests )
     */
    public void dropTables() throws SQLException {
        //On vérifie la connexion à la base de données
        try (Connection connection = dataSource.getConnection();
        //on crée un statement
             Statement statement = connection.createStatement()
        ){
            statement.execute("DROP TABLE IF EXISTS EXERCICE");
            statement.execute("DROP TABLE IF EXISTS SEANCE");

            System.out.println("Tables supprimees !");
        }
    }

}
