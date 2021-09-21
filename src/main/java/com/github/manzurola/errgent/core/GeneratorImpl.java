package com.github.manzurola.errgent.core;

import com.github.manzurola.errant4j.core.Annotator;
import com.github.manzurola.errant4j.core.GrammaticalError;
import com.github.manzurola.errgent.core.inflection.Inflection;
import com.github.manzurola.errgent.core.inflection.InflectionFactory;
import com.github.manzurola.errgent.core.inflection.InflectionFilter;
import com.github.manzurola.errgent.core.inflection.inflectors.Inflector;
import com.github.manzurola.spacy4j.api.containers.Doc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
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
    public List<Inflection> generateSingleError(Doc target, GrammaticalError error) {
        return generateAndFilter(target, InflectionFilter.matchExactErrors(List.of(error)));
    }

    @Override
    public List<Inflection> generateAnyError(Doc target, List<GrammaticalError> errors) {
        return generateAndFilter(target, InflectionFilter.matchAnyError(errors));
    }

    @Override
    public List<Inflection> generateAllErrors(Doc target, List<GrammaticalError> errors) {
        return generateAndFilter(target, InflectionFilter.matchAllErrors(errors));
    }

    public List<Inflection> generateAndFilter(Doc target, InflectionFilter filter) {
        InflectionFactory inflectionFactory = new InflectionFactory(annotator, target);
        return target
                .stream()
                .parallel()
                .flatMap(token -> inflector.inflect(token, inflectionFactory))
                .filter(filter)
                .collect(Collectors.toList());
    }

}
