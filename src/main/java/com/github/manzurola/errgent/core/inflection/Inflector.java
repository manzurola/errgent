package com.github.manzurola.errgent.core.inflection;

import com.github.manzurola.spacy4j.api.containers.Token;

import java.util.stream.Stream;

public interface Inflector {

    Stream<Inflection> inflectToken(Token token);
}
