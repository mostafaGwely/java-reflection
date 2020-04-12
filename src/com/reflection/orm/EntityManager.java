package com.reflection.orm;

import java.sql.SQLException;

public interface EntityManager<T> {
    public static  <T> EntityManager<T> of(Class<T> tClass){
        return new EntityManagerImpl<T>();
    }

    void persist(T t) throws SQLException, IllegalAccessException;
}
