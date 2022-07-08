package io.github.manzurola.errgent.core.inflection;

import io.github.manzurola.spacy4j.api.containers.Token;

import java.util.stream.Stream;

public interface Inflector {

    Stream<Inflection> inflectToken(Token token);
}
