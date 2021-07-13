package com.github.manzurola.errgent;

import com.github.manzurola.errgent.inflectors.simplenlg.SimpleNLG;
import com.github.manzurola.errgent.inflectors.simplenlg.SimpleNLGInflector;
import io.languagetoys.errant4j.core.Annotation;
import io.languagetoys.errant4j.core.Annotator;
import io.languagetoys.spacy4j.api.containers.Doc;
import io.languagetoys.spacy4j.api.containers.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ErrgentImpl implements Errgent {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Annotator annotator;
    private final Inflector inflector;

    public ErrgentImpl(Annotator annotator) {
        this.annotator = annotator;
        this.inflector = new SimpleNLGInflector(new SimpleNLG());
    }

    @Override
    public Doc parse(String text) {
        return annotator.parse(text);
    }

    @Override
    public List<Inflection> inflect(List<Token> target, AnnotationFilter filter) {
        return target
                .stream()
                .flatMap(this::inflectToken)
                .map(this::applyTokenInflection)
                .map(source -> annotator.annotate(source.tokens(), target))
                .flatMap(Collection::stream)
                .filter(filter::filter)
                .map(Inflection::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<Inflection> inflect(List<Token> target) {
        return inflect(target, Annotation::isError);
    }

    public Stream<InflectedToken> inflectToken(Token token) {
        return inflector.inflect(token)
                .stream()
                .map(s -> new InflectedToken(token, s));
    }

    public Doc applyTokenInflection(InflectedToken inflection) {
        Token token = inflection.token();
        Doc target = token.doc();
        String newSentence = replaceToken(target.text(), token, inflection.replacement());
        Doc doc = annotator.parse(newSentence);
        logger.info(String.format("Parsed doc '%s'", doc.text()));
        return doc;
    }

    String replaceToken(String text, Token token, String replacement) {
        return new StringBuilder(text)
                .replace(token.charStart(), token.charEnd(), replacement)
                .toString();
    }

    public static final class InflectedToken {

        private final Token token;
        private final String replacement;

        public InflectedToken(Token token, String replacement) {
            this.token = token;
            this.replacement = replacement;
        }

        public Token token() {
            return token;
        }

        public String replacement() {
            return replacement;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            InflectedToken that = (InflectedToken) o;
            return token.equals(that.token) &&
                    replacement.equals(that.replacement);
        }

        @Override
        public int hashCode() {
            return Objects.hash(token, replacement);
        }
    }
}
