package me.ftsos.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;

public class ReflectionUtils {
    public static Class<?> getGenericClassOfType(Object object){
        Class<?> clazz = (Class<?>) ((ParameterizedType) object.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return clazz;
    }

    public static <T> T getDefaultInstance(Class<T> clazz) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        T instance = null;
        Constructor<T>[] constructors = (Constructor<T>[]) clazz.getDeclaredConstructors();
        Constructor<T> constructor = null;
        for (Constructor loopConstructor : constructors) {
            //Only if default constructor
            if (loopConstructor.getParameters().length == 0) {
                constructor = (Constructor<T>) loopConstructor;
                break;
            }
        }
        if (constructor != null) {
            constructor.setAccessible(true);
            instance = constructor.newInstance();
        }

        return instance;
    }
}
