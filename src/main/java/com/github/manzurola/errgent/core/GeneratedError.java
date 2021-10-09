package com.github.manzurola.errgent.core;

import com.github.manzurola.errant4j.core.Annotation;
import com.github.manzurola.errant4j.core.GrammaticalError;
import com.github.manzurola.spacy4j.api.containers.Doc;

import java.util.Objects;

public final class GeneratedError {

    private final Doc doc;
    private final Annotation annotation;

    private GeneratedError(Doc doc, Annotation annotation) {
        this.doc = Objects.requireNonNull(doc);
        this.annotation = Objects.requireNonNull(annotation);
    }

    public static GeneratedError of(Doc doc, Annotation annotation) {
        return new GeneratedError(doc, annotation);
    }

    public final Doc doc() {
        return doc;
    }

    public final String text() {
        return doc.text();
    }

    public final Annotation annotation() {
        return annotation;
    }

    public final GrammaticalError grammaticalError() {
        return annotation.grammaticalError();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeneratedError that = (GeneratedError) o;
        return Objects.equals(doc, that.doc) && Objects.equals(annotation, that.annotation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(doc, annotation);
    }

    @Override
    public final String toString() {
        return doc.text();
    }

}
