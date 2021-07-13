package com.grammartoys.geg.inflectors.simplenlg;

import io.languagetoys.spacy4j.api.containers.Token;
import simplenlg.features.Feature;
import simplenlg.features.Form;
import simplenlg.framework.NLGElement;

import java.util.function.Consumer;

import static io.languagetoys.errant4j.lang.en.classify.rules.common.Predicates.isVerb;

public class VerbFormInflector extends SimpleNLGInflector {
    public VerbFormInflector(SimpleNLG simpleNLG) {
        super(simpleNLG);
    }

    @Override
    protected void inflect(Token token, SimpleNLG simpleNLG, Consumer<NLGElement> results) {
        results.accept(simpleNLG.createElement(token, Feature.FORM, Form.BARE_INFINITIVE));
        results.accept(simpleNLG.createElement(token, Feature.FORM, Form.GERUND));
        results.accept(simpleNLG.createElement(token, Feature.FORM, Form.IMPERATIVE));
        results.accept(simpleNLG.createElement(token, Feature.FORM, Form.INFINITIVE));
        results.accept(simpleNLG.createElement(token, Feature.FORM, Form.NORMAL));
        results.accept(simpleNLG.createElement(token, Feature.FORM, Form.PAST_PARTICIPLE));
        results.accept(simpleNLG.createElement(token, Feature.FORM, Form.PRESENT_PARTICIPLE));
    }

    @Override
    protected boolean canHandle(Token token) {
        return token.matches(isVerb());
    }
}
