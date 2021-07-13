package com.github.manzurola.errgent;

import io.languagetoys.errant4j.core.Annotation;

import java.util.Objects;

public class Inflection {

    private final Annotation annotation;

    public Inflection(Annotation annotation) {
        this.annotation = annotation;
    }

    public Annotation annotation() {
        return annotation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Inflection that = (Inflection) o;
        return annotation.equals(that.annotation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(annotation);
    }

    @Override
    public String toString() {
        return "Inflection{" +
               "annotation=" + annotation +
               '}';
    }


}
