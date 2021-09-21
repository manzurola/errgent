package com.github.manzurola.errgent.core.inflection;

import com.github.manzurola.errant4j.core.Annotation;
import com.github.manzurola.spacy4j.api.containers.Doc;

import java.util.List;
import java.util.Objects;

public final class Inflection {

    private final Doc doc;
    private final List<Annotation> errors;

    private Inflection(Doc doc, List<Annotation> errors) {
        this.doc = Objects.requireNonNull(doc);
        this.errors = List.copyOf(errors);
    }

    public static Inflection of(Doc doc, List<Annotation> errors) {
        return new Inflection(doc, errors);
    }

    public final Doc doc() {
        return doc;
    }

    public final String text() {
        return doc.text();
    }

    public final List<Annotation> errors() {
        return errors;
    }

    public final int numOfErrors() {
        return errors.size();
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Inflection that = (Inflection) o;
        return doc.equals(that.doc) && errors.equals(that.errors);
    }

    @Override
    public final int hashCode() {
        return Objects.hash(doc, errors);
    }

    @Override
    public final String toString() {
        return doc.text();
    }

}
