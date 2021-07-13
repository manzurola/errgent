package com.github.manzurola.errgent.inflectors.simplenlg;

import io.languagetoys.spacy4j.api.containers.Token;
import simplenlg.features.Feature;
import simplenlg.features.Tense;
import simplenlg.framework.NLGElement;

import java.util.function.Consumer;

import static io.languagetoys.errant4j.lang.en.classify.rules.common.Predicates.isVerb;

public class VerbTenseInflector extends SimpleNLGInflector {
    public VerbTenseInflector(SimpleNLG simpleNLG) {
        super(simpleNLG);
    }

    @Override
    protected void inflect(Token token, SimpleNLG simpleNLG, Consumer<NLGElement> results) {
        results.accept(simpleNLG.createElement(token, Feature.TENSE, Tense.PAST));
        results.accept(simpleNLG.createElement(token, Feature.TENSE, Tense.PRESENT));
        results.accept(simpleNLG.createElement(token, Feature.TENSE, Tense.FUTURE));
    }

    @Override
    protected boolean canHandle(Token token) {
        return token.matches(isVerb());
    }
}
