package com.reflection.module3;

import com.reflection.module2.Person;
import com.reflection.orm.EntityManager;

import java.sql.SQLException;

public class Demo {
    public static void main(String[] args) throws Exception {
        EntityManager<Person> entityManager =   EntityManager.of(Person.class);

        Person linda = new Person("linda", 21);
        Person james = new Person("james", 21);
        Person susan = new Person("susan", 21);
        Person john = new Person("john", 21);

        System.out.println(linda);
        System.out.println(james);
        System.out.println(susan);
        System.out.println(john);

        System.out.println("writing to the db...");

        entityManager.persist(linda);
        entityManager.persist(james);
        entityManager.persist(susan);
        entityManager.persist(john);

        System.out.println(linda);
        System.out.println(james);
        System.out.println(susan);
        System.out.println(john);
    }
}
