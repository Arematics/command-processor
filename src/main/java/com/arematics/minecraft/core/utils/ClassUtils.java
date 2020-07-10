package com.arematics.minecraft.core.utils;

import com.arematics.minecraft.core.generics.UncheckedFunction;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;

/**
 * @author Enrico
 */
public class ClassUtils {

    public static <T extends Annotation> Optional<T> findAnnotation(Object watchingClass, Class<T> theClass){
        return (Optional<T>) Arrays.stream(watchingClass.getClass().getAnnotations())
                .filter(annotation -> annotation.annotationType() == theClass)
                .findFirst();
    }

    public static <T extends Annotation, R> Optional<R> fetchAnnotationValueSave(Object watchingClass,
                                                                                 Class<T> theClass,
                                                                                 Function<T, R> execute){
        Optional<T> optional = findAnnotation(watchingClass, theClass);
        return optional.map(execute);
    }

    public static <T> boolean execute(Class<T> theClass, Method method, UncheckedFunction<Method, Boolean> func)
            throws Exception {
        if(Arrays.stream(method.getDeclaredAnnotations()).anyMatch(classes -> classes.annotationType() == theClass))
            return func.apply(method);
        return false;
    }
}
