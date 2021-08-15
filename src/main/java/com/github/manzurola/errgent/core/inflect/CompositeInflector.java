package com.github.manzurola.errgent.core.inflect;

import com.github.manzurola.spacy4j.api.containers.Token;

import java.util.List;
import java.util.stream.Stream;

public class CompositeInflector implements Inflector {

    private final List<Inflector> inflectors;

    public CompositeInflector(Inflector... inflectors) {
        this.inflectors = List.of(inflectors);
    }

    @Override
    public Stream<Inflection> inflect(Token token, InflectionFactory inflectionFactory) {
        return inflectors
                .stream()
                .parallel()
                .flatMap(inflector -> inflector.inflect(token, inflectionFactory));
    }
}
