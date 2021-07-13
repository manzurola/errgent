package com.grammartoys.geg.inflectors.simplenlg;

import io.languagetoys.spacy4j.api.containers.Token;
import simplenlg.features.Feature;
import simplenlg.features.Person;
import simplenlg.framework.NLGElement;

import java.util.function.Consumer;

import static io.languagetoys.errant4j.lang.en.classify.rules.common.Predicates.isPronoun;
import static io.languagetoys.errant4j.lang.en.classify.rules.common.Predicates.isVerb;

public final class PersonInflector extends SimpleNLGInflector {

    public PersonInflector(SimpleNLG simpleNLG) {
        super(simpleNLG);
    }

    @Override
    protected final void inflect(Token token, SimpleNLG simpleNLG, Consumer<NLGElement> results) {
        results.accept(simpleNLG.createElement(token, Feature.PERSON, Person.FIRST));
        results.accept(simpleNLG.createElement(token, Feature.PERSON, Person.SECOND));
        results.accept(simpleNLG.createElement(token, Feature.PERSON, Person.THIRD));
    }

    @Override
    protected boolean canHandle(Token token) {
        return token.matches(isPronoun().or(isVerb()));
    }

}
