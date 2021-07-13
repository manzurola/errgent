package com.grammartoys.geg;

import java.lang.annotation.Annotation;

public interface ErrorFilter {

    boolean filter(Annotation annotation);
}
