package com.github.manzurola.errgent;

import io.languagetoys.spacy4j.api.containers.Token;

import java.util.Objects;

public final class InflectedToken {

    private final Token token;
    private final String replacement;

    public InflectedToken(Token token, String replacement) {
        this.token = token;
        this.replacement = replacement;
    }

    public final Token token() {
        return token;
    }

    public final String replacement() {
        return replacement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InflectedToken that = (InflectedToken) o;
        return token.equals(that.token) &&
               replacement.equals(that.replacement);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token, replacement);
    }
}
