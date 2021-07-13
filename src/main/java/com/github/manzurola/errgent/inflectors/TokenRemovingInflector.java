package com.github.manzurola.errgent.inflectors;

import com.github.manzurola.errgent.InflectedToken;
import com.github.manzurola.errgent.Inflector;
import io.languagetoys.spacy4j.api.containers.Token;

import java.util.stream.Stream;

public class TokenRemovingInflector implements Inflector {
    @Override
    public Stream<InflectedToken> inflect(Token token) {
        return Stream.of(new InflectedToken(token, ""));
    }
}
