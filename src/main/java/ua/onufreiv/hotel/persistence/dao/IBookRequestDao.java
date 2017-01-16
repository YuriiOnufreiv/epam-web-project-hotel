package ua.onufreiv.hotel.persistence.dao;

import ua.onufreiv.hotel.entity.BookRequest;

import java.util.List;

/**
 * Created by yurii on 1/1/17.
 */
public interface IBookRequestDao extends IDao<BookRequest> {
    List<BookRequest> findByUserId(int id);
    List<BookRequest> getNotProcessedRequests();
}
