package com.github.manzurola.errgent.core.inflection.inflectors;

import com.github.manzurola.errgent.core.inflection.Inflection;
import com.github.manzurola.errgent.core.inflection.InflectionFactory;
import com.github.manzurola.spacy4j.api.containers.Token;

import java.util.stream.Stream;

public interface Inflector {

    Stream<Inflection> inflect(Token token, InflectionFactory inflectionFactory);
}
