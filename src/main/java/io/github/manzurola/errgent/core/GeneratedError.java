package io.github.manzurola.errgent.core;

import io.github.manzurola.errant4j.core.errors.GrammaticalError;

import java.util.Objects;
import java.util.function.Function;

public final class GeneratedError {

    private final String text;
    private final int charStart;
    private final int charEnd;
    private final GrammaticalError grammaticalError;

    public GeneratedError(
        String text,
        int charStart,
        int charEnd,
        GrammaticalError grammaticalError
    ) {
        this.text = Objects.requireNonNull(text);
        Objects.checkFromToIndex(charStart, charEnd, text.length());
        this.charStart = charStart;
        this.charEnd = charEnd;
        this.grammaticalError = Objects.requireNonNull(grammaticalError);
    }

    public final String text() {
        return text;
    }

    public final int charStart() {
        return charStart;
    }

    public final int charEnd() {
        return charEnd;
    }

    public final GrammaticalError error() {
        return grammaticalError;
    }

    public final String markedText() {
        return markedText(s -> "*" + s + "*");
    }

    public final String markedText(Function<String, String> errorDecorator) {
        String decoratedMistake = errorDecorator.apply(errorSpan());
        return new StringBuilder(text)
            .replace(charStart, charEnd, decoratedMistake)
            .toString();
    }

    /**
     * Get the text span that is marked as error. A utility method that replaces
     * text.substring(charStart, charEnd).
     */
    public final String errorSpan() {
        return text.substring(charStart, charEnd);
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GeneratedError that = (GeneratedError) o;
        return charStart == that.charStart &&
               charEnd == that.charEnd &&
               text.equals(that.text) &&
               grammaticalError == that.grammaticalError;
    }

    @Override
    public final int hashCode() {
        return Objects.hash(charStart, charEnd, text, grammaticalError);
    }

    @Override
    public final String toString() {
        return "[" +
               text +
               ", " +
               error() +
               " at " +
               charStart +
               ", " +
               charEnd +
               " ]";
    }

}
