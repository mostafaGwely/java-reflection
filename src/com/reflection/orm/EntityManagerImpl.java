package com.reflection.orm;

import com.reflection.annotations.Inject;
import com.reflection.util.FieldSystem;
import com.reflection.util.Metamodel;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.concurrent.atomic.AtomicLong;

public   class EntityManagerImpl<T> implements EntityManager<T> {
    private AtomicLong idGenerator = new AtomicLong(0L);

    @Inject
    Connection connection;

    @Override
    public void persist(T t) throws SQLException, IllegalAccessException {
        Metamodel metamodel = new Metamodel(t.getClass());
        String sql = metamodel.buildInsertRequest();
        try (PreparedStatement statement = PreparedStatementWith(sql).andParameters(t)) {
            statement.executeUpdate();
        }
    }

    @Override
    public T find(Class<T> tClass, long id) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Metamodel metamodel = Metamodel.of(tClass);
        String sql = metamodel.buildSelectRequest();
        try (PreparedStatement statement = PreparedStatementWith(sql).andPrimaryKey(id);
             ResultSet resultSet = statement.executeQuery()) {

            return buildInstanceFrom(tClass, resultSet);
        }

    }

    private T buildInstanceFrom(Class<T> tClass, ResultSet resultSet) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, SQLException {
        T t = tClass.getConstructor().newInstance();
        Metamodel metamodel = Metamodel.of(tClass);
        String name = metamodel.getPrimaryKey().getName();
        Field field = metamodel.getPrimaryKey().getField();
        Class<?> type = metamodel.getPrimaryKey().getType();

        resultSet.next();
        if (type == Long.class) {
            int anInt = resultSet.getInt(name);
            field.setAccessible(true);
            field.set(t, anInt);
        }

        for (FieldSystem column : metamodel.getColumns()) {
            Field field1 = column.getField();
            String name1 = column.getName();
            Class<?> type1 = column.getType();

            field1.setAccessible(true);

            if (type1 == int.class) {
                int anInt = resultSet.getInt(name1);
                field1.set(t, anInt);
            } else if (type1 == String.class) {
                String string = resultSet.getString(name1);
                field1.set(t, string);
            }
        }
        return t;
    }

    private PreparedStatementWrapper PreparedStatementWith(String sql) throws SQLException {
         PreparedStatement statement = connection.prepareStatement(sql);
        return new PreparedStatementWrapper(statement);
    }

    private class PreparedStatementWrapper {
        PreparedStatement statement;

        public PreparedStatementWrapper(PreparedStatement statement) {
            this.statement = statement;
        }

        public PreparedStatement andParameters(T t) throws SQLException, IllegalAccessException {
            Metamodel metamodel = Metamodel.of(t.getClass());
            Class<?> primaryKeyType = metamodel.getPrimaryKey().getType();
            if (primaryKeyType == long.class) {
                long id = idGenerator.incrementAndGet();
                statement.setLong(1, id);
                Field field = metamodel.getPrimaryKey().getField();
                field.setAccessible(true);
                field.set(t, id);
            }
            for (int columnIndex = 0; columnIndex < metamodel.getColumns().size(); columnIndex++) {
                FieldSystem fieldSystem = metamodel.getColumns().get(columnIndex);
                Class<?> type = fieldSystem.getType();
                Field field = fieldSystem.getField();
                field.setAccessible(true);
                Object value = field.get(t);
                if (type == int.class)
                    statement.setInt(columnIndex + 2, (Integer) value);
                if (type == String.class)
                    statement.setString(columnIndex + 2, (String) value);

            }
            return null;
        }

        public PreparedStatement andPrimaryKey(Object id) throws SQLException {
            if (id.getClass() == Long.class)
                statement.setLong(1, (Long) id);
            return statement;
        }
    }
}
