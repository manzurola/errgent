package com.github.manzurola.errgent;

import com.github.manzurola.errgent.inflectors.InflectedToken;
import com.github.manzurola.errgent.inflectors.Inflector;
import com.github.manzurola.errgent.inflectors.simplenlg.SimpleNLG;
import com.github.manzurola.errgent.inflectors.simplenlg.SimpleNLGInflectorImpl;
import io.languagetoys.errant4j.core.Annotation;
import io.languagetoys.errant4j.core.Annotator;
import io.languagetoys.spacy4j.api.containers.Doc;
import io.languagetoys.spacy4j.api.containers.Span;
import io.languagetoys.spacy4j.api.containers.Token;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ErrgentImpl implements Errgent {

    private final Annotator annotator;
    private final Inflector inflector;

    public ErrgentImpl(Annotator annotator) {
        this.annotator = annotator;
        this.inflector = new SimpleNLGInflectorImpl(new SimpleNLG());
    }

    @Override
    public Doc parse(String text) {
        return annotator.parse(text);
    }

    @Override
    public List<Inflection> inflect(List<Token> target, ErrorFilter filter) {
        return target
                .stream()
                .flatMap(this::inflectToken)
                .map(this::newSentenceWithReplacement)
                .map(source -> annotator.annotate(source.tokens(), target))
                .flatMap(Collection::stream)
                .filter(filter::filter)
                .map(Inflection::new)
                .collect(Collectors.toList());
    }

    public Stream<InflectedToken> inflectToken(Token token) {
        return inflector.inflect(token)
                .stream()
                .map(s -> new InflectedToken(token, s));
    }

    public Span newSentenceWithReplacement(InflectedToken inflection) {
        Token token = inflection.token();
        String newSentence = replaceToken(token.doc().text(), token, inflection.replacement());
        return annotator.parse(newSentence).sentences().get(0);
    }

    String replaceToken(String text, Token token, String replacement) {
        return new StringBuilder(text)
                .replace(token.charStart(), token.charEnd(), replacement)
                .toString();
    }
}
