package ua.onufreiv.hotel.service;

import ua.onufreiv.hotel.entity.Bill;

/**
 * Created by yurii on 1/10/17.
 */
public interface BillService {
    boolean insertBill(Bill bill);

    Bill findByBookRequestId(int id);

    Bill createAndInsertBill(int bookRequestId, int roomId);
}
