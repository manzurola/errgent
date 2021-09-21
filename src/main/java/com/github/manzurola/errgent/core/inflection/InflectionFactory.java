package com.github.manzurola.errgent.core.inflection;

import com.github.manzurola.errant4j.core.Annotation;
import com.github.manzurola.errant4j.core.Annotator;
import com.github.manzurola.spacy4j.api.containers.Doc;
import com.github.manzurola.spacy4j.api.containers.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InflectionFactory {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Annotator annotator;
    private final Doc originalDoc;

    public InflectionFactory(Annotator annotator, Doc originalDoc) {
        this.annotator = annotator;
        this.originalDoc = originalDoc;
    }

    public Stream<Inflection> substitute(Token token, String replacement) {
        return create(token.doc().text(), token.charStart(), token.charEnd(), List.of(replacement));
    }

    public Stream<Inflection> delete(Token token) {
        int charStart = token.charStart();
        int charEnd = token.charEnd() + token.spaceAfter().length();
        return create(token.doc().text(), charStart, charEnd, List.of(""));
    }

    public Stream<Inflection> create(String sourceDocText, int charStart, int charEnd, List<String> replacements) {
        return replacements.stream()
                .map(replacement -> {
                    String inflectedDocText = new StringBuilder(sourceDocText)
                            .replace(charStart, charEnd, replacement)
                            .toString();
                    Doc inflectedDoc = annotator.parse(inflectedDocText);
                    logger.info(String.format("Parsed doc '%s'", inflectedDoc.text()));
                    return inflectedDoc;
                })
                .map(inflectedDoc -> {
                    List<Annotation> annotations = annotator.annotate(inflectedDoc.tokens(), originalDoc.tokens())
                            .stream()
                            .filter(annotation -> !annotation.grammaticalError().isNone())
                            .collect(Collectors.toList());
                    return Inflection.of(inflectedDoc, annotations);
                });
    }

}
