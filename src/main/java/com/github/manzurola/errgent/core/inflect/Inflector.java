package com.github.manzurola.errgent.core.inflect;

import com.github.manzurola.spacy4j.api.containers.Doc;
import com.github.manzurola.spacy4j.api.containers.Token;

import java.util.stream.Stream;

public interface Inflector {

    Stream<Doc> inflect(Token token, DocFactory docFactory);
}
