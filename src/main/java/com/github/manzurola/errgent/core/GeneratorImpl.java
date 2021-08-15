package com.github.manzurola.errgent.core;

import com.github.manzurola.errant4j.core.Annotation;
import com.github.manzurola.errant4j.core.Annotator;
import com.github.manzurola.errant4j.core.GrammaticalError;
import com.github.manzurola.errgent.core.inflect.DocFactory;
import com.github.manzurola.errgent.core.inflect.Inflector;
import com.github.manzurola.spacy4j.api.containers.Doc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class GeneratorImpl implements Generator {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Annotator annotator;
    private final Inflector inflector;

    public GeneratorImpl(Annotator annotator, Inflector inflector) {
        this.annotator = annotator;
        this.inflector = inflector;
    }

    @Override
    public final Doc parse(String text) {
        return annotator.parse(text);
    }

    @Override
    public List<Inflection> generate(Doc target) {
        return generate(target, List.of(GrammaticalError.values()));
    }

    @Override
    public final List<Inflection> generate(Doc target, List<GrammaticalError> errors) {
        DocFactory docFactory = new DocFactory(annotator);
        return target
                .stream()
                .parallel()
                .flatMap(token -> inflector.inflect(token, docFactory))
                .map(inflectedDoc -> {
                    List<Annotation> annotations = annotator.annotate(inflectedDoc.tokens(), target.tokens())
                            .stream()
                            .filter(annotation -> !annotation.grammaticalError().isNone())
                            .collect(Collectors.toList());
                    return Inflection.of(inflectedDoc, annotations);
                })
                .filter(filter(errors))
                .collect(Collectors.toList());
    }

    private Predicate<Inflection> filter(List<GrammaticalError> errors) {
        return inflection -> inflection.errors()
                .stream()
                .anyMatch(annotation -> errors.contains(annotation.grammaticalError()));
    }

}
