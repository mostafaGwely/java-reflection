package com.reflection.util;

import java.lang.reflect.Field;

public  class FieldSystem {
    Field field;

    public FieldSystem(Field field) {
        this.field = field;
    }

    public String getName(){
        return field.getName();
    }
    @Override
    public String toString() {
        return "field name: " + field.getName() +
                "type: "+ field.getType().getSimpleName();
    }

    public Class<?> getType() {
        return field.getType();
    }

    public Field getField() {
        return this.field;
    }
}
