package ua.onufreiv.hotel.service;

import ua.onufreiv.hotel.entity.BookRequest;

import java.util.List;

/**
 * Created by yurii on 1/8/17.
 */
public interface IBookRequestService {
    List<BookRequest> getNotProcessedRequests();
    BookRequest getById(int id);
    void makeNewRequest(BookRequest bookRequest);
    List<BookRequest> getRequestsByUserId(int id);
}
