package com.github.manzurola.errgent.core;

import com.github.manzurola.errgent.core.filter.InflectionFilter;
import com.github.manzurola.errgent.core.inflect.Inflector;
import io.languagetoys.errant4j.core.Annotation;
import io.languagetoys.errant4j.core.Annotator;
import io.languagetoys.spacy4j.api.containers.Doc;
import io.languagetoys.spacy4j.api.containers.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

public class GeneratorImpl implements Generator {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Annotator annotator;
    private final Inflector inflector;

    public GeneratorImpl(Annotator annotator, Inflector inflector) {
        this.annotator = annotator;
        this.inflector = inflector;
    }

    @Override
    public Doc parse(String text) {
        return annotator.parse(text);
    }

    @Override
    public List<Inflection> generate(List<Token> target, InflectionFilter filter) {
        DocFactory docFactory = new DocFactory(annotator);
        return target
                .stream()
                .parallel()
                .flatMap(token -> inflector.inflect(token, docFactory))
                .map(inflectedDoc -> {
                    List<Annotation> errors = annotator.annotate(inflectedDoc.tokens(), target)
                            .stream()
                            .filter(annotation -> !annotation.grammaticalError().isNone())
                            .collect(Collectors.toList());
                    return Inflection.of(inflectedDoc, errors);
                })
                .filter(filter::filter)
                .collect(Collectors.toList());

    }

}
