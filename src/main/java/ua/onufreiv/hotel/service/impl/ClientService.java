package ua.onufreiv.hotel.service.impl;

import ua.onufreiv.hotel.dao.DaoFactory;
import ua.onufreiv.hotel.dao.IBookRequestDao;
import ua.onufreiv.hotel.entities.BookRequest;
import ua.onufreiv.hotel.service.IClientService;

import java.util.List;

/**
 * Created by yurii on 1/1/17.
 */
public class ClientService implements IClientService {
    @Override
    public void makeNewReservation(BookRequest bookRequest) {
        IBookRequestDao formDao = DaoFactory.getDAOFactory(DaoFactory.FactoryType.MYSQL_DB).getBookRequestDao();
        formDao.insert(bookRequest);
    }

    @Override
    public List<BookRequest> getUserForm(int id) {
        IBookRequestDao formDao = DaoFactory.getDAOFactory(DaoFactory.FactoryType.MYSQL_DB).getBookRequestDao();
        return formDao.findByUserId(id);
    }
}
