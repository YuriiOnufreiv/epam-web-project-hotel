package ua.onufreiv.hotel.dao;

import ua.onufreiv.hotel.entities.Bill;

/**
 * Created by yurii on 1/10/17.
 */
public interface IBillDao extends IDao<Bill> {
    Bill findByBookRequestId(int id);
}
