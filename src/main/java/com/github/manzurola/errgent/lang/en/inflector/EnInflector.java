package com.github.manzurola.errgent.lang.en.inflector;

import com.github.manzurola.errgent.core.inflect.Inflection;
import com.github.manzurola.errgent.core.inflect.*;
import com.github.manzurola.errgent.lang.en.inflector.simplenlg.SimpleNLG;
import com.github.manzurola.errgent.lang.en.inflector.simplenlg.SimpleNLGInflector;
import com.github.manzurola.spacy4j.api.containers.Token;

import java.util.stream.Stream;

public final class EnInflector implements Inflector {

    private final Inflector impl;

    public EnInflector() {
        this.impl = new CompositeInflector(new SimpleNLGInflector(new SimpleNLG()),
                                           new TokenRemovingInflector());
    }

    @Override
    public Stream<Inflection> inflect(Token token, InflectionFactory inflectionFactory) {
        return impl.inflect(token, inflectionFactory);
    }
}
