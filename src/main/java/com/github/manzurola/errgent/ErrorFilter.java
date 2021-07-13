package com.github.manzurola.errgent;


import io.languagetoys.errant4j.core.Annotation;

public interface ErrorFilter {

    boolean filter(Annotation annotation);
}
