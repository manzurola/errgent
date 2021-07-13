package com.github.manzurola.errgent;


import io.languagetoys.spacy4j.api.containers.Token;

import java.util.Set;

public interface Inflector {

    Set<String> inflect(Token token);

}
