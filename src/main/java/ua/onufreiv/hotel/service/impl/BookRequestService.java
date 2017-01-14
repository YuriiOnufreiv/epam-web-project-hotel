package ua.onufreiv.hotel.service.impl;

import ua.onufreiv.hotel.dao.DaoFactory;
import ua.onufreiv.hotel.dao.IBookRequestDao;
import ua.onufreiv.hotel.entities.BookRequest;
import ua.onufreiv.hotel.service.IBookRequestService;

import java.util.List;

/**
 * Created by yurii on 1/8/17.
 */
public class BookRequestService implements IBookRequestService {
    @Override
    public List<BookRequest> getNotProcessedRequests() {
        return DaoFactory.getDAOFactory(DaoFactory.FactoryType.MYSQL_DB).getBookRequestDao().getNotProcessedRequests();
    }

    @Override
    public BookRequest getById(int id) {
        return DaoFactory.getDAOFactory(DaoFactory.FactoryType.MYSQL_DB).getBookRequestDao().find(id);
    }

    @Override
    public void makeNewRequest(BookRequest bookRequest) {
        IBookRequestDao formDao = DaoFactory.getDAOFactory(DaoFactory.FactoryType.MYSQL_DB).getBookRequestDao();
        formDao.insert(bookRequest);
    }

    @Override
    public List<BookRequest> getRequestsByUserId(int id) {
        IBookRequestDao formDao = DaoFactory.getDAOFactory(DaoFactory.FactoryType.MYSQL_DB).getBookRequestDao();
        return formDao.findByUserId(id);
    }
}
