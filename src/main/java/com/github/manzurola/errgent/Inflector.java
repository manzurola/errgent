package com.github.manzurola.errgent;


import io.languagetoys.spacy4j.api.containers.Token;

import java.util.stream.Stream;

public interface Inflector {

    Stream<InflectedToken> inflect(Token token);

}
