package ua.onufreiv.hotel.persistence.dao;

import ua.onufreiv.hotel.entity.AbstractEntity;

import java.util.List;

/**
 * General DAO interface that contains basic CRUD operations
 *
 * @param <T> type of entity; must be subclass of {@link AbstractEntity}
 * @author Yurii Onufreiv
 * @version 1.0
 * @since 12/23/16.
 */
public interface Dao<T extends AbstractEntity> {
    /**
     * Inserts new entity
     *
     * @param entity entity to insert
     * @return id of inserted entity
     */
    int insert(T entity);

    /**
     * Deletes entity by id
     *
     * @param id id f entity to delete
     * @return true if entity was deleted, false otherwise
     */
    boolean delete(int id);

    /**
     * Finds entity by id
     *
     * @param id if of entity to find
     * @return entity that was found, {@code null} if no entity found
     */
    T find(int id);

    /**
     * List of all entities
     *
     * @return list of entities
     */
    List<T> findAll();

    /**
     * Updates entity based on it's id
     *
     * @param entity entity to update
     * @return true if entity was updated, false otherwise
     */
    boolean update(T entity);
}
