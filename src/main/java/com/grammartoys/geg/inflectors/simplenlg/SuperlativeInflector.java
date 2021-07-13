package com.grammartoys.geg.inflectors.simplenlg;

import io.languagetoys.spacy4j.api.containers.Token;
import simplenlg.features.Feature;
import simplenlg.framework.NLGElement;

import java.util.function.Consumer;

import static io.languagetoys.errant4j.lang.en.classify.rules.common.Predicates.isAdjective;
import static io.languagetoys.errant4j.lang.en.classify.rules.common.Predicates.isAdverb;

public class SuperlativeInflector extends SimpleNLGInflector {
    public SuperlativeInflector(SimpleNLG simpleNLG) {
        super(simpleNLG);
    }

    @Override
    protected boolean canHandle(Token token) {
        return token.matches(isAdjective().or(isAdverb()));
    }

    @Override
    protected void inflect(Token token, SimpleNLG simpleNLG, Consumer<NLGElement> results) {
        results.accept(simpleNLG.createElement(token, Feature.IS_SUPERLATIVE, true));
        results.accept(simpleNLG.createElement(token, Feature.IS_SUPERLATIVE, false));
    }
}
