package com.github.manzurola.errgent;

import com.github.manzurola.errgent.inflectors.Inflection;
import com.github.manzurola.errgent.inflectors.Inflector;
import io.languagetoys.errant4j.core.Annotation;
import io.languagetoys.errant4j.core.Annotator;
import io.languagetoys.spacy4j.api.containers.Span;
import io.languagetoys.spacy4j.api.containers.Token;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WordInflectingErrorGenerator implements ErrorGenerator {

    private final Annotator annotator;
    private final Inflector inflector;

    public WordInflectingErrorGenerator(Annotator annotator, Inflector inflector) {
        this.annotator = annotator;
        this.inflector = inflector;
    }

    @Override
    public List<Annotation> generate(List<Token> tokens) {
        return tokens
                .stream()
                .flatMap(this::inflectWord)
                .map(this::newSentenceWithReplacement)
                .map(source -> annotator.annotate(source.tokens(), tokens))
                .flatMap(Collection::stream)
                .filter(Annotation::isError)
                .collect(Collectors.toList());

    }

    public Stream<Inflection> inflectWord(Token token) {
        return inflector.inflect(token)
                .stream()
                .map(s -> new Inflection(token, s));
    }

    public Span newSentenceWithReplacement(Inflection inflection) {
        Token token = inflection.token();
        String newSentence = replaceToken(token.doc().text(), token, inflection.replacement());
        Span withError = annotator.parse(newSentence).sentences().get(0);
        return withError;
    }

    String replaceToken(String text, Token token, String replacement) {
        return new StringBuilder(text)
                .replace(token.charStart(), token.charEnd(), replacement)
                .toString();
    }
}
