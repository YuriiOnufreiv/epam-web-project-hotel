package ua.onufreiv.hotel.persistence.dao;

import ua.onufreiv.hotel.entity.Bill;

/**
 * DAO for {@link Bill} entity; contains additional method.
 *
 * @author Yurii Onufreiv
 * @version 1.0
 * @since 1/10/17.
 */
public interface BillDao extends Dao<Bill> {
    /**
     * Finds bill by book request id
     *
     * @param id id of book request
     * @return bill object in case of success, {@code null} otherwise
     */
    Bill findByBookRequestId(int id);
}
