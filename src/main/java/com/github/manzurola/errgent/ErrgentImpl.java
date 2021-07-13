package com.github.manzurola.errgent;

import com.github.manzurola.errgent.inflectors.ConcatenatingInflector;
import com.github.manzurola.errgent.inflectors.TokenRemovingInflector;
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
import java.util.stream.Collectors;

public class ErrgentImpl implements Errgent {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Annotator annotator;
    private final Inflector inflector;

    public ErrgentImpl(Annotator annotator) {
        this.annotator = annotator;
        this.inflector = new ConcatenatingInflector(new SimpleNLGInflector(new SimpleNLG()),
                                                    new TokenRemovingInflector());
    }

    @Override
    public Doc parse(String text) {
        return annotator.parse(text);
    }

    @Override
    public List<Inflection> generate(List<Token> target, AnnotationFilter filter) {
        return target
                .stream()
                .parallel()
                .flatMap(inflector::inflect)
                .map(this::applyTokenInflection)
                .map(source -> annotator.annotate(source.tokens(), target))
                .flatMap(Collection::stream)
                .filter(filter::filter)
                .map(Inflection::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<Inflection> generate(List<Token> target) {
        return generate(target, Annotation::isError);
    }

    public Doc applyTokenInflection(InflectedToken inflection) {
        Token token = inflection.token();
        Doc target = token.doc();
        String inflectedDocText = new StringBuilder(target.text())
                .replace(token.charStart(), token.charEnd(), inflection.replacement())
                .toString();
        Doc doc = annotator.parse(inflectedDocText);
        logger.info(String.format("Parsed doc '%s'", doc.text()));
        return doc;
    }


}
