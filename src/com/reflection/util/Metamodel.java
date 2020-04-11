package com.reflection.util;

import com.reflection.annotations.Column;
import com.reflection.annotations.PrimaryKey;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Metamodel<T> {
    private Class<T> clss;

    public static <T> Metamodel<T> of(Class<T> clss) {
        return new Metamodel<T>(clss);
    }

    public Metamodel(Class<T> clss) {
        this.clss = clss;
    }

    public FieldSystem getPrimaryKey()   {
        Field[] declaredFields = clss.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            PrimaryKey annotation = declaredField.getAnnotation(PrimaryKey.class);
            if (annotation != null) {
                return new FieldSystem(declaredField);
            }
        }
        throw new IllegalArgumentException("no PrimaryKey field in class "+ clss.getSimpleName());
    }

    public List<FieldSystem> getColumns()   {
        Field[] declaredFields = clss.getDeclaredFields();
        List<FieldSystem> columnFields = new ArrayList<>();
        for (Field declaredField : declaredFields) {
            Column annotation = declaredField.getAnnotation(Column.class);
            if (annotation != null) {
                columnFields.add(new FieldSystem(declaredField));
            }
        }
        if(columnFields.size() == 0)
            throw new IllegalArgumentException("no columns field in class "+ clss.getSimpleName());
        return  columnFields;
    }
}
