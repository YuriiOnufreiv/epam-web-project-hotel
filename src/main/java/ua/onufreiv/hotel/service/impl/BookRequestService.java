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
    private static BookRequestService instance;

    private final IBookRequestDao bookRequestDao;

    private BookRequestService() {
        DaoFactory daoFactory = DaoFactory.getDAOFactory(ConnectionManager.databaseType);
        bookRequestDao = daoFactory.getBookRequestDao();
    }

    public synchronized static BookRequestService getInstance() {
        if (instance == null) {
            instance = new BookRequestService();
        }
        return instance;
    }

    @Override
    public boolean insertBookRequest(BookRequest bookRequest) {
        return bookRequestDao.insert(bookRequest) != -1;
    }

    @Override
    public BookRequest findById(int id) {
        return bookRequestDao.find(id);
    }

    @Override
    public List<BookRequest> findNotProcessed() {
        return bookRequestDao.findAllNotProcessed();
    }

    @Override
    public List<BookRequest> findByUserId(int id) {
        return bookRequestDao.findByUserId(id);
    }
}
