package com.reflection.util;

import com.reflection.annotations.Column;
import com.reflection.annotations.PrimaryKey;

import java.lang.reflect.Field;

public class FieldSystem {
    Field field;
    PrimaryKey primaryKey;
    Column column;

    public FieldSystem(Field field) {
        this.field = field;
        primaryKey = field.getAnnotation(primaryKey.getClass());
        column = field.getAnnotation(column.getClass());
    }

    public String getName() {
        if (primaryKey != null)
            return primaryKey.name();
        else
            return column.name();
    }

    @Override
    public String toString() {
        return "field name: " + field.getName() +
                "type: " + field.getType().getSimpleName();
    }

    public Class<?> getType() {
        return field.getType();
    }

    public Field getField() {
        return this.field;
    }
}
