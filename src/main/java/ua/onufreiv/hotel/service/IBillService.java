package ua.onufreiv.hotel.service;

import ua.onufreiv.hotel.entity.Bill;

/**
 * Created by yurii on 1/10/17.
 */
public interface IBillService {
    boolean createNewBill(Bill bill);

    Bill getByBookRequestId(int id);
}
