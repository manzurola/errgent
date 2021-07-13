package com.github.manzurola.errgent.inflectors.simplenlg;

import io.languagetoys.spacy4j.api.containers.Token;
import simplenlg.features.*;
import simplenlg.framework.NLGElement;

import java.util.function.Consumer;

public class SimpleNLGInflectorImpl extends SimpleNLGInflector {
    public SimpleNLGInflectorImpl(SimpleNLG simpleNLG) {
        super(simpleNLG);
    }

    @Override
    protected boolean canHandle(Token token) {
        return true;
    }

    @Override
    protected void inflect(Token token, SimpleNLG simpleNLG, Consumer<NLGElement> results) {
        results.accept(simpleNLG.createElement(token, Feature.IS_COMPARATIVE, true));
        results.accept(simpleNLG.createElement(token, Feature.IS_COMPARATIVE, false));
        results.accept(simpleNLG.createElement(token, Feature.NUMBER, NumberAgreement.SINGULAR));
        results.accept(simpleNLG.createElement(token, Feature.NUMBER, NumberAgreement.PLURAL));
        results.accept(simpleNLG.createElement(token, Feature.PERSON, Person.FIRST));
        results.accept(simpleNLG.createElement(token, Feature.PERSON, Person.SECOND));
        results.accept(simpleNLG.createElement(token, Feature.PERSON, Person.THIRD));
        results.accept(simpleNLG.createElement(token, Feature.POSSESSIVE, true));
        results.accept(simpleNLG.createElement(token, Feature.POSSESSIVE, false));
        results.accept(simpleNLG.createElement(token, Feature.PROGRESSIVE, true));
        results.accept(simpleNLG.createElement(token, Feature.IS_SUPERLATIVE, true));
        results.accept(simpleNLG.createElement(token, Feature.IS_SUPERLATIVE, false));
        results.accept(simpleNLG.createElement(token, Feature.FORM, Form.BARE_INFINITIVE));
        results.accept(simpleNLG.createElement(token, Feature.FORM, Form.GERUND));
        results.accept(simpleNLG.createElement(token, Feature.FORM, Form.IMPERATIVE));
        results.accept(simpleNLG.createElement(token, Feature.FORM, Form.INFINITIVE));
        results.accept(simpleNLG.createElement(token, Feature.FORM, Form.NORMAL));
        results.accept(simpleNLG.createElement(token, Feature.FORM, Form.PAST_PARTICIPLE));
        results.accept(simpleNLG.createElement(token, Feature.FORM, Form.PRESENT_PARTICIPLE));
        results.accept(simpleNLG.createElement(token, Feature.TENSE, Tense.PAST));
        results.accept(simpleNLG.createElement(token, Feature.TENSE, Tense.PRESENT));
        results.accept(simpleNLG.createElement(token, Feature.TENSE, Tense.FUTURE));
    }
}
