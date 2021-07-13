package com.github.manzurola.errgent.inflectors.simplenlg;

import io.languagetoys.spacy4j.api.containers.Token;
import simplenlg.features.Feature;
import simplenlg.framework.NLGElement;

import java.util.function.Consumer;

import static io.languagetoys.errant4j.lang.en.classify.rules.common.Predicates.isNoun;
import static io.languagetoys.errant4j.lang.en.classify.rules.common.Predicates.isPronoun;

public final class PossessiveInflector extends SimpleNLGInflector {
    public PossessiveInflector(SimpleNLG simpleNLG) {
        super(simpleNLG);
    }

    @Override
    protected final void inflect(Token token, SimpleNLG simpleNLG, Consumer<NLGElement> results) {
        results.accept(simpleNLG.createElement(token, Feature.POSSESSIVE, true));
    }

    @Override
    protected boolean canHandle(Token token) {
        return token.matches(isPronoun().or(isNoun()));
    }
}
