package com.grammartoys.geg.inflectors;

import io.languagetoys.spacy4j.api.containers.Span;
import io.languagetoys.spacy4j.api.containers.Token;

import java.util.Objects;

public final class Inflection {

    private final Token token;
    private final String replacement;

    public Inflection(Token token, String replacement) {
        this.token = token;
        this.replacement = replacement;
    }

    public Token token() {
        return token;
    }

    public String replacement() {
        return replacement;
    }

    public Span sentence() {
        return token.sentence();
    }

    public boolean isDiff() {
        return !token.text().equals(replacement);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Inflection that = (Inflection) o;
        return token.equals(that.token) &&
                replacement.equals(that.replacement);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token, replacement);
    }
}
