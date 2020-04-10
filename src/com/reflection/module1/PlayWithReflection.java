package com.reflection.module1;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

public class PlayWithReflection {
    public static void main(String[] args) throws ClassNotFoundException {
        var className = "com.reflection.module1.Person";
        Class<?> aClass = Class.forName(className);
        System.out.println(aClass);

        Field[] fields = aClass.getDeclaredFields();
        System.out.println(Arrays.toString(fields));


        Method[] methods = aClass.getDeclaredMethods();
        System.out.println(Arrays.toString(methods));

        Arrays.stream(methods)
                .filter(m-> Modifier.isStatic(m.getModifiers()))
                .forEach(System.out::println);
    }
}
