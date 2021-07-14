package com.github.manzurola.errgent;

import io.languagetoys.aligner.edit.Edit;
import io.languagetoys.errant4j.core.Annotation;
import io.languagetoys.errant4j.core.GrammaticalError;
import io.languagetoys.spacy4j.api.containers.Doc;
import io.languagetoys.spacy4j.api.containers.Token;

import java.util.Objects;

public class Inflection {

    private final Annotation annotation;
    private final Doc doc;

    private Inflection(Annotation annotation, Doc doc) {
        this.annotation = Objects.requireNonNull(annotation);
        this.doc = Objects.requireNonNull(doc);
    }

    public static Inflection of(Annotation annotation, Doc source) {
        return new Inflection(annotation, source);
    }

    public GrammaticalError grammaticalError() {
        return annotation.grammaticalError();
    }

    public Edit<Token> edit() {
        return annotation.edit();
    }

    public final Doc doc() {
        return doc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Inflection that = (Inflection) o;
        return annotation.equals(that.annotation) && doc.equals(that.doc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(annotation, doc);
    }

    @Override
    public String toString() {
        return "Inflection{" +
               "annotation=" + annotation +
               ", doc=" + doc +
               '}';
    }


}
