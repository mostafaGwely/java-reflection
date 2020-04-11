package com.reflection.util;

import java.lang.reflect.Field;

public  class FieldSystem {
    Field field;

    public FieldSystem(Field field) {
        this.field = field;
    }

    @Override
    public String toString() {
        return "field name: " + field.getName() +
                "type: "+ field.getType().getSimpleName();
    }
}
