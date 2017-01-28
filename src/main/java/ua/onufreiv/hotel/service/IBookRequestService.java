package ua.onufreiv.hotel.service;

import ua.onufreiv.hotel.entity.BookRequest;

import java.util.List;

/**
 * Created by yurii on 1/8/17.
 */
public interface IBookRequestService {
    boolean insertBookRequest(BookRequest bookRequest);

    BookRequest findById(int id);

    List<BookRequest> findNotProcessed();

    List<BookRequest> findByUserId(int id);
}
