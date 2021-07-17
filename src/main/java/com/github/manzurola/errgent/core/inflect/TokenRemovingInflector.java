package com.github.manzurola.errgent.core.inflect;

import com.github.manzurola.errgent.core.DocFactory;
import io.languagetoys.spacy4j.api.containers.Doc;
import io.languagetoys.spacy4j.api.containers.Token;

import java.util.stream.Stream;

public class TokenRemovingInflector implements Inflector {

    @Override
    public Stream<Doc> inflect(Token token, DocFactory docFactory) {
        return docFactory.create(token, "");
    }
}
