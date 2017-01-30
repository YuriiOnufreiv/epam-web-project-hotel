package ua.onufreiv.hotel.persistence.dao;

import ua.onufreiv.hotel.entity.Bill;

/**
 * Created by yurii on 1/10/17.
 */
public interface BillDao extends Dao<Bill> {
    Bill findByBookRequestId(int id);
}
