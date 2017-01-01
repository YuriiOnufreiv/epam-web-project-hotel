package ua.onufreiv.hotel.dao;

import ua.onufreiv.hotel.entities.AbstractEntity;

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