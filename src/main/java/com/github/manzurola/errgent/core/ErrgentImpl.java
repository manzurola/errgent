package com.github.manzurola.errgent.core;

import com.github.manzurola.errgent.core.filters.InflectionFilter;
import com.github.manzurola.errgent.core.inflectors.CompositeInflector;
import com.github.manzurola.errgent.core.inflectors.InflectedToken;
import com.github.manzurola.errgent.core.inflectors.Inflector;
import com.github.manzurola.errgent.core.inflectors.TokenRemovingInflector;
import com.github.manzurola.errgent.lang.en.inflector.simplenlg.SimpleNLG;
import com.github.manzurola.errgent.lang.en.inflector.simplenlg.SimpleNLGInflector;
import io.languagetoys.errant4j.core.Annotator;
import io.languagetoys.spacy4j.api.containers.Doc;
import io.languagetoys.spacy4j.api.containers.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

public class ErrgentImpl implements Errgent {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Annotator annotator;
    private final Inflector inflector;

    public ErrgentImpl(Annotator annotator) {
        this.annotator = annotator;
        this.inflector = new CompositeInflector(new SimpleNLGInflector(new SimpleNLG()),
                                                new TokenRemovingInflector());
    }

    @Override
    public Doc parse(String text) {
        return annotator.parse(text);
    }

    @Override
    public List<Inflection> generate(List<Token> target, InflectionFilter filter) {
        return target
                .stream()
                .parallel()
                .flatMap(inflector::inflect)
                .map(this::applyTokenInflection)
                .flatMap(inflectedDoc -> annotator.annotate(inflectedDoc.tokens(), target)
                        .stream()
                        .map(annotation -> Inflection.of(annotation, inflectedDoc)))
                .filter(filter::filter)
                .collect(Collectors.toList());

    }

    @Override
    public List<Inflection> generate(List<Token> target) {
        return generate(target, inflection -> !inflection.grammaticalError().isNoneOrIgnored());
    }

    public Doc applyTokenInflection(InflectedToken inflection) {
        Token token = inflection.token();
        Doc target = token.doc();
        String inflectedDocText = new StringBuilder(target.text())
                .replace(token.charStart(), token.charEnd(), inflection.replacement())
                .toString();
        Doc inflectedDoc = annotator.parse(inflectedDocText);
        logger.info(String.format("Parsed doc '%s'", inflectedDoc.text()));
        return inflectedDoc;
    }


}
