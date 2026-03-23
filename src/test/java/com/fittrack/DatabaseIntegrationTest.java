package com.fittrack;

import com.fittrack.dao.H2SeanceDAO;
import com.fittrack.dao.SeanceDAO;
import com.fittrack.datasource.DatabaseManager;
import com.fittrack.model.ExerciceCardio;
import com.fittrack.model.ExerciceForce;
import com.fittrack.model.Seance;
import com.fittrack.strategy.CalculStrategy;
import com.fittrack.strategy.IntensiteHaute;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@DisplayName("Tests d integration avec la base de donnees H2")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DatabaseIntegrationTest {

    //Attributs privés de la classe fournissant les données de connexion à la base H2
    //L'instance SeanceDAO les méthodes de la classe à tester
    private static DatabaseManager databaseManager;
    private static SeanceDAO seanceDAO;

    //Avant tous les tests
    @BeforeAll
    public static void setUp() throws SQLException { //ne pas oublier l'exception SQL !!!
        //on  initialise databaseMenager avec une instance de la classe DatabaseMenager
        databaseManager = new DatabaseManager();
        //on crée les tables avec la méthode de cette classe
        databaseManager.createTables();
        //on initialise seanceDAO en faisant appel au constructeur de la classe H2SeanceDAO
        // avec en paramètre les données de connexion à la base de données H2
        seanceDAO = new H2SeanceDAO(databaseManager.getDataSource());
    }

    //Avant chaque test
    @BeforeEach
    void cleanDatabase() throws SQLException {
        //Nettoyer entre chaque test
        //effacer les tables crees
        databaseManager.dropTables();
        //Initialiser les tables
        databaseManager.createTables();
    }

    @Test
    @Order(1)  // premier test
    @DisplayName("Premier test : Sauvegarder une seance simple ")
    void testSaveSeance() throws SQLException {
        Seance seance = new Seance("Test Seance", LocalDate.now());
        seance.ajouterActivite(new ExerciceCardio("Course", 30, "5 km"));

        //on récupére le id de la séance sauvegardé
        int id = seanceDAO.save(seance);

        //on  vérifie que la séance a bien été sauvegardée
        assertTrue(id > 0);
        assertEquals(id, seance.getIdSeance());
    }
    @Test
    @Order(2)
    @DisplayName("Deuxieme test : Recuperer une seance par ID")
    void testFindById() throws SQLException {
        //Sauvegarder
        Seance original = new Seance("Morning Run", LocalDate.of(2026, 1, 25));
        original.ajouterActivite(new ExerciceCardio("Jogging", 40, "Park"));
        original.ajouterActivite(new ExerciceForce("Pompes", 10, "Pectoraux", 3, 15));

        //On sauvegarde la séance original et on récupère son id
        int id = seanceDAO.save(original);

        //On récupère original par son id
        Seance loader = seanceDAO.findById(id);

        //On vérifie les données de loarder
        assertNotNull(loader); // Si loarder existe bien
        //on vérifie les données des attributs de loarder
        assertEquals(original.getNomSeance(), loader.getNomSeance());
        assertEquals(original.getDateSeance(), loader.getDateSeance());
        assertEquals(2, loader.getNombreActivites());
        assertEquals(50, loader.getDureeTotale());
    }

    @Test
    @Order(3)
    @DisplayName("Troisieme test : Les calories sont correctement recalculees")
    void testCaloriesApresChargement() throws SQLException {
        //On crée une séance original et on ajoute deux exercices de différents types
        Seance original = new Seance("HITT Session", LocalDate.now());
        original.ajouterActivite(new ExerciceCardio("Burpees", 5, "Full body"));
        original.ajouterActivite(new ExerciceForce("Squats", 5, "jambes", 4, 20));

        //On initialise une stratégie à haute intensité
        CalculStrategy strategy = new IntensiteHaute();
        //on récupère le nombre de calories de la séance original
        double nombreCaloriesOriginal = original.getCaloriesTotales(strategy);

        //On sauvegarde original et on récupère son id
        int id = seanceDAO.save(original);

        //On récupère original sauvegardée par son id
        Seance loader = seanceDAO.findById(id);
        //on récupère le nombre de calories totale de la séance loader
        double nombreCaloriesLoader = loader.getCaloriesTotales(strategy);

        //On vérifie que le nombre de calories totale de chaque séance sont bien les mêmes avec un delta de 0.01
        assertEquals(nombreCaloriesOriginal, nombreCaloriesLoader, 0.01);
    }

    @Test
    @Order(4)
    @DisplayName("Quatrieme test : Recuperer toutes les seances ")
    void testFindAll() throws SQLException {
        // Sauvegarder plusieurs séances
        // On utilise seance.Builder pour créer une séance plus facilement et directement les sauvegarder
        seanceDAO.save(new Seance.Builder()
                        .setNomSeance("Seance 1")
                        .setDateSeance(LocalDate.now())
                        .addActivite(new ExerciceCardio("Course", 20, "Test"))
                        .build());

        seanceDAO.save(new Seance.Builder()
                        .setNomSeance("Seance 2")
                        .setDateSeance(LocalDate.now().minusDays(1))
                        .addActivite(new ExerciceForce("Pompes", 10, "Test"))
                .build());

        seanceDAO.save(new Seance.Builder()
                        .setNomSeance("Seance 3")
                        .setDateSeance(LocalDate.now().minusDays(2))
                        .addActivite(new ExerciceCardio("Velo", 30, "Test"))
                .build());

        // On crée la liste des 3 séances
        List<Seance> ListeDesSeances = seanceDAO.findAll();

        //On vérifie le nombre de séance
        assertEquals(3, ListeDesSeances.size());
    }

    @Test
    @Order(5)
    @DisplayName("Cinquieme test : Supprimer une seance ")
    void testDelete() throws SQLException {
        //On crée une séance et on ajoute un exercice
        Seance seance = new Seance("To Delete", LocalDate.now());
        seance.ajouterActivite(new ExerciceCardio("Test", 10, "Test"));

        //Sauvegarde la séance et on récupère son id
        int id = seanceDAO.save(seance);

        //On Supprime la séance et on récupère le booléen de la méthode delete
        boolean deleted = seanceDAO.delete(id);
        //on vérifie que le delete est bien vrai
        assertTrue(deleted);

        //On charge la séance effacée précédemment
        Seance loader = seanceDAO.findById(id);
        //On vérifie qu'elle est bien nulle
        assertNull(loader);
    }

    @Test
    @Order(6)
    @DisplayName("Sixieme test : Seance non trouvee retourne null")
    void testFindByIdNonExistant() throws SQLException {
        //On initie une seance avec un id non sauvegardée
        Seance seanceNonExistante = seanceDAO.findById(9999);
        //On vérifie si la seance est bien nulle
        assertNull(seanceNonExistante);
    }
}
