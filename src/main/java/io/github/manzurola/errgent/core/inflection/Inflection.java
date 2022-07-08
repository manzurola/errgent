package io.github.manzurola.errgent.core.inflection;

import io.github.manzurola.errant4j.core.Annotator;
import io.github.manzurola.spacy4j.api.containers.Doc;
import io.github.manzurola.spacy4j.api.containers.Token;

import java.util.Objects;

public final class Inflection {

    private final int charStart;
    private final int charEnd;
    private final String originalText;
    private final String replacementText;

    private Inflection(
        int charStart,
        int charEnd,
        String originalText,
        String replacementText
    ) {
        this.charStart = charStart;
        this.charEnd = charEnd;
        this.originalText = Objects.requireNonNull(originalText);
        this.replacementText = Objects.requireNonNull(replacementText);
    }

    private static Inflection of(
        int charStart,
        int charEnd,
        String originalText,
        String replacementText
    ) {
        return new Inflection(
            charStart,
            charEnd,
            originalText,
            replacementText
        );
    }

    public static Inflection deleteToken(Token token) {
        int charStart = token.charStart();
        int charEnd = token.charEnd() + token.spaceAfter().length();
        return of(charStart, charEnd, token.text(), "");
    }

    public static Inflection substituteToken(
        Token token,
        String replacementText
    ) {
        int charStart = token.charStart();
        int charEnd = token.charEnd();
        return of(charStart, charEnd, token.text(), replacementText);
    }

    public Doc applyTo(String text, Annotator annotator) {
        String inflectedText = new StringBuilder(text)
            .replace(
                charStart(),
                charEnd(),
                replacementText()
            )
            .toString();
        return annotator.parse(inflectedText);
    }

    public int charStart() {
        return charStart;
    }

    public int charEnd() {
        return charEnd;
    }

    public String originalText() {
        return originalText;
    }

    public String replacementText() {
        return replacementText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Inflection that = (Inflection) o;
        return charStart == that.charStart &&
               charEnd == that.charEnd &&
               originalText.equals(that.originalText) &&
               replacementText.equals(that.replacementText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(charStart, charEnd, originalText, replacementText);
    }

    @Override
    public String toString() {
        return String.format("'%s' -> '%s'", originalText, replacementText);
    }


}
