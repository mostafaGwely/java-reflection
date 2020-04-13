package com.reflection.orm;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

public interface EntityManager<T> {
    public static  <T> EntityManager<T> of(Class<T> tClass){
        return new H2EntityManager();
    }

    void persist(T t) throws SQLException, IllegalAccessException;
    T find(Class<T> clss, long id) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;
}
