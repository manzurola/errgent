package com.github.manzurola.errgent.core;

import com.github.manzurola.errgent.core.filter.InflectionFilter;
import io.languagetoys.spacy4j.api.containers.Doc;
import io.languagetoys.spacy4j.api.containers.Token;

import java.util.List;

public interface Generator {

    Doc parse(String text);

    List<Inflection> generate(List<Token> target, InflectionFilter filter);
}
