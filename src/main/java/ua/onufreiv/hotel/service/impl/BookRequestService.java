package ua.onufreiv.hotel.service.impl;

import ua.onufreiv.hotel.entity.BookRequest;
import ua.onufreiv.hotel.persistence.ConnectionManager;
import ua.onufreiv.hotel.persistence.dao.DaoFactory;
import ua.onufreiv.hotel.persistence.dao.IBookRequestDao;
import ua.onufreiv.hotel.service.IBookRequestService;

import java.util.List;

/**
 * Created by yurii on 1/8/17.
 */
public class BookRequestService implements IBookRequestService {
    @Override
    public List<BookRequest> getNotProcessedRequests() {
        return DaoFactory.getDAOFactory(ConnectionManager.databaseType).getBookRequestDao().getNotProcessedRequests();
    }

    @Override
    public BookRequest getById(int id) {
        return DaoFactory.getDAOFactory(ConnectionManager.databaseType).getBookRequestDao().find(id);
    }

    @Override
    public boolean makeNewRequest(BookRequest bookRequest) {
        IBookRequestDao formDao = DaoFactory.getDAOFactory(ConnectionManager.databaseType).getBookRequestDao();
        return formDao.insert(bookRequest) != -1;
    }

    @Override
    public List<BookRequest> getRequestsByUserId(int id) {
        IBookRequestDao formDao = DaoFactory.getDAOFactory(ConnectionManager.databaseType).getBookRequestDao();
        return formDao.findByUserId(id);
    }
}
