package com.github.manzurola.errgent.inflectors.simplenlg;

import io.languagetoys.spacy4j.api.containers.Token;
import simplenlg.framework.NLGElement;
import simplenlg.framework.NLGFactory;
import simplenlg.lexicon.Lexicon;
import simplenlg.phrasespec.NPPhraseSpec;
import simplenlg.phrasespec.SPhraseSpec;
import simplenlg.phrasespec.VPPhraseSpec;
import simplenlg.realiser.english.Realiser;

import static io.languagetoys.errant4j.lang.en.classify.rules.common.Predicates.*;

public final class SimpleNLG {

    private final NLGFactory nlgFactory = new NLGFactory(Lexicon.getDefaultLexicon());
    private final Realiser realiser = new Realiser();

    public final String realise(NLGElement element) {
        return realiser.realise(element).getRealisation();
    }

    public final NLGElement createElement(Token token, String feature, Object value) {
        NLGElement element = createElement(token);
        element.setFeature(feature, value);
        return element;
    }

    public final NLGElement createElement(Token token) {
        return createElementFromToken(token);
    }

    private NLGElement createElementFromToken(Token token) {
        NLGElement result;
        String word = token.text();

        if (token.matches(isPronoun())) {
            SPhraseSpec element = nlgFactory.createClause();
            element.setObject(word);
            result = element;

        } else if (token.matches(isVerb())) {
            VPPhraseSpec element = nlgFactory.createVerbPhrase();
            element.setVerb(word);
            result = element;

        } else if (token.matches(isNoun())) {
            NPPhraseSpec element = nlgFactory.createNounPhrase();
            element.setNoun(word);
            result = element;

        } else if (token.matches(isAdjective())) {
            result = nlgFactory.createAdjectivePhrase(word);

        } else if (token.matches(isAdverb())) {
            result = nlgFactory.createAdverbPhrase(word);

        } else {
            SPhraseSpec element = nlgFactory.createClause();
            element.setObject(word);
            result = element;
        }

        return result;
    }

}
