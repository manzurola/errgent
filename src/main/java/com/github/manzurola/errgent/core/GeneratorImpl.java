package com.github.manzurola.errgent.core;

import com.github.manzurola.errant4j.core.Annotator;
import com.github.manzurola.errant4j.core.GrammaticalError;
import com.github.manzurola.errgent.core.inflect.Inflection;
import com.github.manzurola.errgent.core.inflect.InflectionFactory;
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
    public List<Doc> generate(Doc target, GrammaticalError error) {
        InflectionFactory inflectionFactory = new InflectionFactory(annotator, target);
        return target
                .stream()
                .parallel()
                .flatMap(token -> inflector.inflect(token, inflectionFactory))
                .filter(filter(List.of(error)))
                .map(Inflection::doc)
                .collect(Collectors.toList());
    }

    private Predicate<Inflection> filter(List<GrammaticalError> errors) {
        return inflection -> inflection.errors()
                .stream()
                .anyMatch(annotation -> errors.contains(annotation.grammaticalError()));
    }

}
