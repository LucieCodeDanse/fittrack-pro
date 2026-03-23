package com.fittrack.dao;

import com.fittrack.model.Seance;

import java.sql.SQLException;
import java.util.List;

/**
 * Interface définissant les opérations GRUD (Create, Read, Update, Delete ) pour les séances
 */
public interface SeanceDAO {

    /**
     * Sauvegarder une séance en base de données
     * @param seance la séance à sauvegarder
     * @return l'ID généré
     */
    int save(Seance seance) throws SQLException;

    /**
     * Recupere une séance par son ID
     * @param id l'identifiant
     * @return la séance ou null si non trouvée
     */
    Seance findById(int id) throws SQLException;

    /**
     * Recupere toutes les séances
     * @return liste de toutes les séances
     */
    List<Seance> findAll() throws SQLException;

    /**
     * Supprimer une séance
     * @param id l'identifiant
     * @return true si supprimée
     */
    boolean delete(int id) throws SQLException;
}
