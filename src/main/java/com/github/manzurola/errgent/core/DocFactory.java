package com.github.manzurola.errgent.core;

import io.languagetoys.errant4j.core.Annotator;
import io.languagetoys.spacy4j.api.containers.Doc;
import io.languagetoys.spacy4j.api.containers.Span;
import io.languagetoys.spacy4j.api.containers.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Stream;

public class DocFactory {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Annotator annotator;

    public DocFactory(Annotator annotator) {
        this.annotator = annotator;
    }

    public Stream<Doc> create(Token token, String replacement) {
        return create(token, List.of(replacement));
    }

    public Stream<Doc> create(Token token, List<String> replacements) {
        return create(token.doc().text(), token.charStart(), token.charEnd(), replacements);
    }

    public Stream<Doc> create(Span span, String replacement) {
        return create(span, List.of(replacement));
    }

    public Stream<Doc> create(Span span, List<String> replacements) {
        return create(span.doc().text(), span.startChar(), span.endChar(), replacements);
    }

    public Stream<Doc> create(Doc doc, int charStart, int charEnd, String replacement) {
        return create(doc.text(), charStart, charEnd, replacement);
    }

    public Stream<Doc> create(Doc doc, int charStart, int charEnd, List<String> replacements) {
        String target = doc.text();
        return create(target, charStart, charEnd, replacements);
    }

    public Stream<Doc> create(String source, int charStart, int charEnd, String replacement) {
        return create(source, charStart, charEnd, List.of(replacement));
    }

    public Stream<Doc> create(String source, int charStart, int charEnd, List<String> replacements) {
        return replacements.stream()
                .map(replacement -> {
                    String inflectedDocText = new StringBuilder(source)
                            .replace(charStart, charEnd, replacement)
                            .toString();
                    Doc inflectedDoc = annotator.parse(inflectedDocText);
                    logger.info(String.format("Parsed doc '%s'", inflectedDoc.text()));
                    return inflectedDoc;
                });
    }

}
