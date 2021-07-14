package com.github.manzurola.errgent;

import io.languagetoys.spacy4j.api.containers.Doc;
import io.languagetoys.spacy4j.api.containers.Token;

import java.util.List;

public interface Errgent {

    Doc parse(String text);

    List<Inflection> generate(List<Token> target, InflectionFilter filter);

    List<Inflection> generate(List<Token> target);
}