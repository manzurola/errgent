package com.github.manzurola.errgent;

import java.lang.annotation.Annotation;

public interface ErrorFilter {

    boolean filter(Annotation annotation);
}
