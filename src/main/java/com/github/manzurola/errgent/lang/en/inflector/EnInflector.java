package com.github.manzurola.errgent.lang.en.inflector;

import com.github.manzurola.errgent.core.inflection.Inflection;
import com.github.manzurola.errgent.core.inflection.*;
import com.github.manzurola.errgent.core.inflection.inflectors.CompositeInflector;
import com.github.manzurola.errgent.core.inflection.inflectors.Inflector;
import com.github.manzurola.errgent.core.inflection.inflectors.TokenRemovingInflector;
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
