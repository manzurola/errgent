package com.grammartoys.geg.inflectors.simplenlg;

import io.languagetoys.spacy4j.api.containers.Token;
import simplenlg.features.Feature;
import simplenlg.framework.NLGElement;

import java.util.function.Consumer;

import static io.languagetoys.errant4j.lang.en.classify.rules.common.Predicates.isVerb;

public class ProgressiveInflector extends SimpleNLGInflector {

    public ProgressiveInflector(SimpleNLG simpleNLG) {
        super(simpleNLG);
    }

    @Override
    protected void inflect(Token token,
                           SimpleNLG simpleNLG,
                           Consumer<NLGElement> results) {
        results.accept(simpleNLG.createElement(token, Feature.PROGRESSIVE, true));
    }

    @Override
    protected boolean canHandle(Token token) {
        return token.matches(isVerb());
    }
}
