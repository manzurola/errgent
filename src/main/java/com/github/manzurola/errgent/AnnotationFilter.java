package com.github.manzurola.errgent;


import io.languagetoys.errant4j.core.Annotation;

public interface AnnotationFilter {

    boolean filter(Annotation annotation);
}
