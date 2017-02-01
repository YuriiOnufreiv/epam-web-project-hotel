package ua.onufreiv.hotel.persistence.dao;

import ua.onufreiv.hotel.entity.BookRequest;

import java.util.List;

/**
 * DAO for {@link BookRequest} entity; contains additional methods.
 *
 * @author Yurii Onufreiv
 * @version 1.0
 * @since 1/1/17.
 */
public interface BookRequestDao extends Dao<BookRequest> {
    /**
     * Finds all book requests by user id
     *
     * @param id id of user
     * @return list object with book requests
     */
    List<BookRequest> findByUserId(int id);

    /**
     * Finds all book requests that are not processed
     *
     * @return list object with not processed book requests
     */
    List<BookRequest> findAllNotProcessed();
}
