package com.gmail.shelkovich.anton.dao;

import com.gmail.shelkovich.anton.HibernateUtils;
import org.hibernate.Session;

import java.util.List;

public class UniDaoImpl<T> implements UniDao<T> {
    private Class<T> clazz;
    private Session session;

    public UniDaoImpl(Class<T> clazz){
        this.clazz = clazz;
        session = HibernateUtils.getSessionFactory().getCurrentSession();
    }

    @Override
    public void add(T bean){
        session.persist(bean);
    }

    @Override
    public T getById(Long id){
        return session.get(clazz, id);
    }

    @Override
    public List<T> getAll(){
        return session.createQuery("FROM "+clazz.getName()).list();
    }

    @Override
    public Session getSession() {
        return session;
    }
}
