package com.grammartoys.geg.inflectors;


import io.languagetoys.spacy4j.api.containers.Token;

import java.util.Set;

public interface Inflector {

    Set<String> inflect(Token token);

    interface Handler {
        boolean canHandle(Token token);

        Set<String> handle(Token token);
    }
}
