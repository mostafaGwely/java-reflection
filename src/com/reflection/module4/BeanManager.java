package com.reflection.module4;

import com.reflection.annotations.Inject;
import com.reflection.annotations.Provides;
import com.reflection.orm.EntityManagerImpl;
import com.reflection.provider.H2ConnectionProvider;

import java.awt.datatransfer.SystemFlavorMap;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class BeanManager {
    private static BeanManager instance = new BeanManager();
    private Map<Class<?>, Supplier<?>> registry = new HashMap<>();

    private BeanManager() {
        List<Class<?>> classes = List.of(H2ConnectionProvider.class);
        for (Class<?> aClass : classes) {
            Method[] declaredMethods = aClass.getDeclaredMethods();
            for (Method declaredMethod : declaredMethods) {
                Provides annotation = declaredMethod.getAnnotation(Provides.class);
                if (annotation != null) {
                    Class<?> returnType = declaredMethod.getReturnType();
                    Supplier<?> supplier = () -> {

                        try {
                            if (Modifier.isStatic(declaredMethod.getModifiers())) {
                                Object o = aClass.getConstructor().newInstance();
                                return declaredMethod.invoke(o);

                            } else {
                                return declaredMethod.invoke(null);
                            }
                        } catch (Exception e) {
                            throw new RuntimeException();
                        }
                    };
                    registry.put(returnType, supplier);
                }
            }
        }

    }

    public static BeanManager getInstance() {
        return instance;
    }

    public <T> T getInstance(Class<T> tClass) throws Exception {
        T t = tClass.getConstructor().newInstance();
        Field[] declaredFields = tClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            Inject annotation = declaredField.getAnnotation(Inject.class);
            if (annotation != null) {
                Class<?> type = declaredField.getType();
                Supplier<?> supplier = registry.get(type);
                Object o = supplier.get();
                declaredField.setAccessible(true);
                declaredField.set(t, o);
            }
        }
        return t;
    }
}
