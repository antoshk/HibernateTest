package com.gmail.shelkovich.anton.dao;

import org.hibernate.Session;

import java.util.List;

public interface UniDao<T> {
    void add(T bean);

    T getById(Long id);

    List<T> getAll();

    Session getSession();
}
