package ua.onufreiv.hotel.service;

import ua.onufreiv.hotel.entities.BookRequest;

import java.util.List;

/**
 * Created by yurii on 1/1/17.
 */
public interface IClientService {
    void makeNewReservation(BookRequest bookRequest);
    List<BookRequest> getUserForm(int id);
}
