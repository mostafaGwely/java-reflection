package com.reflection.module2;

import com.reflection.util.FieldSystem;
import com.reflection.util.Metamodel;

import java.util.List;

public class Demo {


    public static void main(String[] args) {
        Metamodel<Person> personMetamodel = new Metamodel<Person>(Person.class);

        List<FieldSystem> columns = personMetamodel.getColumns();
        FieldSystem primaryKey = personMetamodel.getPrimaryKey();

        System.out.println(primaryKey);
        for (FieldSystem column : columns) {
            System.out.println(column);
        }

    }


}
