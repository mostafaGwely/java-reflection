package com.reflection.util;

import com.reflection.annotations.Column;
import com.reflection.annotations.PrimaryKey;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Metamodel {
    private Class<?> clss;

    public static Metamodel of(Class<?> clss) {
        return new Metamodel(clss);
    }

    public Metamodel(Class clss) {
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

    public String buildInsertRequest() {
        //insert into Person (id,name,age) values (?,?,?)
        String columnElement = getColumnsElement();

        String questionMarkElement = getQuestionMarkElement();

        return "INSERT INTO "+ this.clss.getSimpleName() +
                "("+columnElement+")"+ "values"+
                "("+questionMarkElement+")";

     }

    private String getQuestionMarkElement() {
        int numberOfColumns = getColumns().size()+1;
        return IntStream.range(0, numberOfColumns).mapToObj(index -> "?").collect(Collectors.joining(", "));
    }

    private String getColumnsElement() {
        String primaryKey = getPrimaryKey().getName();
        List<String> columnsNames = getColumns().stream()
                .map(c -> c.getName()).collect(Collectors.toList());
        columnsNames.add(0,primaryKey);

        return String.join(", ", columnsNames);
    }
}
