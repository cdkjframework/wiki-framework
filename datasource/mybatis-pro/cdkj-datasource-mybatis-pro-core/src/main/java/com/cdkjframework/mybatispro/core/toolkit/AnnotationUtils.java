package com.cdkjframework.mybatispro.core.toolkit;

import lombok.experimental.UtilityClass;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.mybatispro.core.toolkit
 * @ClassName: AnnotationUtils
 * @Description: Java 类说明
 * @Author: xiaLin
 * @Version: 1.0
 */
@UtilityClass
public class AnnotationUtils {

  public <T extends Annotation> T findFirstAnnotation(Class<T> annotationClazz, Field field) {
    return getAnnotation(annotationClazz, new HashSet<>(), field.getDeclaredAnnotations());
  }

  public <T extends Annotation> T findFirstAnnotation(Class<T> annotationClazz, Class<?> clz) {
    Set<Class<? extends Annotation>> hashSet = new HashSet<>();
    T annotation = getAnnotation(annotationClazz, hashSet, clz.getDeclaredAnnotations());
    if (annotation != null) {
      return annotation;
    }
    Class<?> currentClass = clz.getSuperclass();
    while (currentClass != null) {
      annotation = getAnnotation(annotationClazz, hashSet, currentClass.getDeclaredAnnotations());
      if (annotation != null) {
        return annotation;
      }
      currentClass = currentClass.getSuperclass();
    }
    return null;
  }

  public <T extends Annotation> T findFirstAnnotation(Class<T> annotationClazz, Method method) {
    return getAnnotation(annotationClazz, new HashSet<>(), method.getDeclaredAnnotations());
  }

  @SuppressWarnings("unchecked")
  private <T extends Annotation> T getAnnotation(Class<T> annotationClazz, Set<Class<? extends Annotation>> annotationSet, Annotation... annotations) {
    for (Annotation annotation : annotations) {
      if (annotationSet.add(annotation.annotationType())) {
        if (annotationClazz.isAssignableFrom(annotation.annotationType())) {
          return (T) annotation;
        }
        annotation = getAnnotation(annotationClazz, annotationSet, annotation.annotationType().getDeclaredAnnotations());
        if (annotation != null) {
          return (T) annotation;
        }
      }
    }
    return null;
  }

}
