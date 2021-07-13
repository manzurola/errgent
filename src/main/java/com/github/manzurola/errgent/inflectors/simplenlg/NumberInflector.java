package com.github.manzurola.errgent.inflectors.simplenlg;

import io.languagetoys.spacy4j.api.containers.Token;
import simplenlg.features.Feature;
import simplenlg.features.NumberAgreement;
import simplenlg.framework.NLGElement;

import java.util.function.Consumer;

import static io.languagetoys.errant4j.lang.en.classify.rules.common.Predicates.isNoun;

public final class NumberInflector extends SimpleNLGInflector {

    public NumberInflector(SimpleNLG simpleNLG) {
        super(simpleNLG);
    }

    @Override
    protected final void inflect(Token token, SimpleNLG simpleNLG, Consumer<NLGElement> results) {
        results.accept(simpleNLG.createElement(token, Feature.NUMBER, NumberAgreement.SINGULAR));
        results.accept(simpleNLG.createElement(token, Feature.NUMBER, NumberAgreement.PLURAL));
    }

    @Override
    protected boolean canHandle(Token token) {
        return token.matches(isNoun());
    }

}
