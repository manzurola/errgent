package com.github.manzurola.errgent.lang.en.inflector;

import com.github.manzurola.errgent.core.inflect.DocFactory;
import com.github.manzurola.errgent.core.inflect.CompositeInflector;
import com.github.manzurola.errgent.core.inflect.Inflector;
import com.github.manzurola.errgent.core.inflect.TokenRemovingInflector;
import com.github.manzurola.errgent.lang.en.inflector.simplenlg.SimpleNLG;
import com.github.manzurola.errgent.lang.en.inflector.simplenlg.SimpleNLGInflector;
import io.languagetoys.spacy4j.api.containers.Doc;
import io.languagetoys.spacy4j.api.containers.Token;

import java.util.stream.Stream;

public final class EnInflector implements Inflector {

    private final Inflector impl;

    public EnInflector() {
        this.impl = new CompositeInflector(new SimpleNLGInflector(new SimpleNLG()),
                                           new TokenRemovingInflector());
    }

    @Override
    public Stream<Doc> inflect(Token token, DocFactory docFactory) {
        return impl.inflect(token, docFactory);
    }
}
