package com.github.manzurola.errgent.core;

import com.github.manzurola.errant4j.core.Annotation;
import com.github.manzurola.errant4j.core.Annotator;
import com.github.manzurola.errant4j.core.errors.GrammaticalError;
import com.github.manzurola.errgent.core.inflection.Inflector;
import com.github.manzurola.spacy4j.api.containers.Doc;
import com.github.manzurola.spacy4j.api.containers.Span;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public final class GeneratorImpl implements Generator {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Inflector inflector;
    private final Annotator annotator;

    public GeneratorImpl(Inflector inflector, Annotator annotator) {
        this.annotator = annotator;
        this.inflector = inflector;
        logger.info("Loaded grammatical error generator");
    }

    @Override
    public List<GeneratedError> generateErrors(
        String sourceText, GrammaticalError error
    ) {
        return generateErrors(sourceText)
            .stream()
            .filter(generatedError -> error.equals(generatedError.error()))
            .collect(Collectors.toList());
    }

    @Override
    public final List<GeneratedError> generateErrors(String sourceText) {
        final Doc sourceDoc = annotator.parse(sourceText);
        return sourceDoc
            .tokens()
            .stream()
            .flatMap(inflector::inflectToken)
            .distinct()
            .parallel()
            .map(inflection -> inflection.applyTo(sourceDoc.text(), annotator))
            .map(inflectedDoc -> annotateSingleError(inflectedDoc, sourceDoc))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toList());
    }

    private Optional<GeneratedError> annotateSingleError(
        final Doc inflectedDoc,
        final Doc originalDoc
    ) {
        List<Annotation> annotations = annotator
            .annotate(
                inflectedDoc.tokens(),
                originalDoc.tokens()
            )
            .stream()
            .filter(annotation -> !annotation.error().isNone())
            .collect(Collectors.toList());

        if (annotations.size() > 1) {
            return Optional.empty();
        }

        return Optional.of(markError(annotations.get(0), inflectedDoc));
    }

    private GeneratedError markError(Annotation annotation, Doc inflectedDoc) {
        String generatedText = inflectedDoc.text();
        Span span = inflectedDoc.spanOf(
            annotation.sourcePosition(),
            annotation.sourcePosition() +
            annotation.sourceTokens().size()
        );
        int charStart = span.startChar();
        int charEnd = span.endChar();
        GrammaticalError grammaticalError = annotation.error();
        return new GeneratedError(
            generatedText, charStart, charEnd, grammaticalError
        );
    }

}
