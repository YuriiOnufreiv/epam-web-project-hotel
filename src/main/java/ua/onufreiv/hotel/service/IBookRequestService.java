package ua.onufreiv.hotel.service;

import ua.onufreiv.hotel.entities.BookRequest;

import java.util.List;

/**
 * Created by yurii on 1/8/17.
 */
public interface IBookRequestService {
    List<BookRequest> getNotProcessedRequests();
    void makeNewRequest(BookRequest bookRequest);
    List<BookRequest> getRequestById(int id);
}
