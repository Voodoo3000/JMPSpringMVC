package kz.epam.jmp.dao;

import kz.epam.jmp.entity.Entity;

import java.util.List;

public interface GenericDao<T extends Entity> {

    //create
    void add(T entity) throws DaoException;

    //read
    List<T> getAll() throws DaoException;

    T getById(int id) throws DaoException;

    //update
    void update(T entity) throws DaoException;

    //delete
    void remove(T entity) throws DaoException;

}
