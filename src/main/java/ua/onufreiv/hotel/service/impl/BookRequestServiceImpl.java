package ua.onufreiv.hotel.service.impl;

import ua.onufreiv.hotel.entity.BookRequest;
import ua.onufreiv.hotel.persistence.ConnectionManager;
import ua.onufreiv.hotel.persistence.dao.BookRequestDao;
import ua.onufreiv.hotel.persistence.dao.DaoFactory;
import ua.onufreiv.hotel.service.BookRequestService;

import java.util.List;

/**
 * Created by yurii on 1/8/17.
 */
public class BookRequestServiceImpl implements BookRequestService {
    private static BookRequestServiceImpl instance;

    private final BookRequestDao bookRequestDao;

    private BookRequestServiceImpl() {
        DaoFactory daoFactory = DaoFactory.getDAOFactory(ConnectionManager.databaseType);
        bookRequestDao = daoFactory.getBookRequestDao();
    }

    public synchronized static BookRequestServiceImpl getInstance() {
        if (instance == null) {
            instance = new BookRequestServiceImpl();
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
