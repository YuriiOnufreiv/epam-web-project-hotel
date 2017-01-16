package ua.onufreiv.hotel.persistence.dao;

import ua.onufreiv.hotel.entity.AbstractEntity;

import java.util.List;

/**
 * Created by yurii on 12/23/16.
 */
public interface IDao<T extends AbstractEntity> {
    int insert(T entity);

    boolean delete(int id);

    T find(int id);

    List<T> findAll();

    boolean update(T entity);
}
