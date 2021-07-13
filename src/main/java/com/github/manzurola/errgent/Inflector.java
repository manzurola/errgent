package com.github.manzurola.errgent;


import io.languagetoys.spacy4j.api.containers.Token;

import java.util.List;

public interface Inflector {

    List<InflectedToken> inflect(Token token);

}
