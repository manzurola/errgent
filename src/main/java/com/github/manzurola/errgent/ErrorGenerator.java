package com.github.manzurola.errgent;

import io.languagetoys.errant4j.core.Annotation;
import io.languagetoys.spacy4j.api.containers.Token;

import java.util.List;

public interface ErrorGenerator {

    List<Annotation> generate(List<Token> tokens);
}
