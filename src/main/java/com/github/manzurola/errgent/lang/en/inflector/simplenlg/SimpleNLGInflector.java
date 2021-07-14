package com.github.manzurola.errgent.lang.en.inflector.simplenlg;

import com.github.manzurola.errgent.core.inflectors.InflectedToken;
import com.github.manzurola.errgent.core.inflectors.Inflector;
import io.languagetoys.errant4j.lang.en.utils.wordlist.HunspellWordList;
import io.languagetoys.errant4j.lang.en.utils.wordlist.WordList;
import io.languagetoys.spacy4j.api.containers.Token;
import simplenlg.features.*;
import simplenlg.framework.NLGElement;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public final class SimpleNLGInflector implements Inflector {

    private final SimpleNLG simpleNLG;
    private final WordList wordList;

    public SimpleNLGInflector(SimpleNLG simpleNLG) {
        this.simpleNLG = simpleNLG;
        this.wordList = new HunspellWordList();
    }

    @Override
    public final Stream<InflectedToken> inflect(Token token) {
        List<NLGElement> inflections = new ArrayList<>();
        collectInflections(token, inflections::add);
        return inflections
                .stream()
                .parallel()
                .map(simpleNLG::realise)
                .filter(s -> !token.lower().equals(s))
                .map(s -> new InflectedToken(token, s))
                .filter(t -> wordList.contains(t.token().text()))
                .distinct();
    }

    protected void collectInflections(Token token, Consumer<NLGElement> results) {
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
