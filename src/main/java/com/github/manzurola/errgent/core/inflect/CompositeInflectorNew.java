package com.github.manzurola.errgent.core.inflect;

import com.github.manzurola.errgent.core.DocFactory;
import io.languagetoys.spacy4j.api.containers.Doc;
import io.languagetoys.spacy4j.api.containers.Token;

import java.util.List;
import java.util.stream.Stream;

public class CompositeInflectorNew implements Inflector {

    private final List<Inflector> inflectors;

    public CompositeInflectorNew(Inflector... inflectors) {
        this.inflectors = List.of(inflectors);
    }

    @Override
    public Stream<Doc> inflect(Token token, DocFactory docFactory) {
        return inflectors
                .stream()
                .parallel()
                .flatMap(inflector -> inflector.inflect(token, docFactory));
    }
}
