package com.github.manzurola.errgent.inflectors;

import com.github.manzurola.errgent.InflectedToken;
import com.github.manzurola.errgent.Inflector;
import io.languagetoys.spacy4j.api.containers.Token;

import java.util.List;
import java.util.stream.Stream;

public class ConcatenatingInflector implements Inflector {

    private final List<Inflector> inflectors;

    public ConcatenatingInflector(Inflector... inflectors) {
        this.inflectors = List.of(inflectors);
    }

    @Override
    public Stream<InflectedToken> inflect(Token token) {
        return inflectors
                .stream()
                .parallel()
                .flatMap(inflector -> inflector.inflect(token));
    }
}
