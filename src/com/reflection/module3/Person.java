package com.reflection.module3;

import com.reflection.annotations.Column;
import com.reflection.annotations.PrimaryKey;

public class Person {

    @PrimaryKey(name="k_id")
    private long id;
    @Column(name="c_name")
    private String name;
    @Column(name="c_age")
    private int age;

    public Person() {
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
    public static Person of(String name, int age){
        return new Person(name,age);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
