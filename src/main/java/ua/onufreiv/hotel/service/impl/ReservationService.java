package ua.onufreiv.hotel.service.impl;

import ua.onufreiv.hotel.dao.DaoFactory;
import ua.onufreiv.hotel.dao.IFormDao;
import ua.onufreiv.hotel.entities.Form;
import ua.onufreiv.hotel.service.IReservationService;

/**
 * Created by yurii on 1/1/17.
 */
public class ReservationService implements IReservationService {
    @Override
    public void makeNewReservation(Form form) {
        IFormDao formDao = DaoFactory.getDAOFactory(DaoFactory.FactoryType.MYSQL_DB).getFormDao();
        formDao.insert(form);
    }
}
