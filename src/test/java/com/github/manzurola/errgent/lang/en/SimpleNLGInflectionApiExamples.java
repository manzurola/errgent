package com.github.manzurola.errgent.lang.en;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import simplenlg.features.*;
import simplenlg.framework.NLGElement;
import simplenlg.framework.NLGFactory;
import simplenlg.lexicon.Lexicon;
import simplenlg.phrasespec.*;
import simplenlg.realiser.english.Realiser;

public class SimpleNLGInflectionApiExamples {

    private final Lexicon lexicon = Lexicon.getDefaultLexicon();
    private final NLGFactory nlgFactory = new NLGFactory(lexicon);
    private final Realiser realiser = new Realiser(lexicon);

    @Test
    void inflectSingular_requiresNoun() {
        // TODO get nsubj from sentence to supply as word
        NPPhraseSpec element = nlgFactory.createNounPhrase();
        String word = "birds";
        element.setNoun(word);
        element.setFeature(Feature.NUMBER, NumberAgreement.SINGULAR);
        NLGElement realise = realiser.realise(element);
        String actual = realise.getRealisation();
        Assertions.assertEquals("bird", actual);
    }

    @Test
    void inflectPlural_requiresNoun() {
        NPPhraseSpec element = nlgFactory.createNounPhrase();
        String word = "bread";
        element.setNoun(word);
        element.setFeature(Feature.NUMBER, NumberAgreement.PLURAL);
        NLGElement realise = realiser.realise(element);
        String actual = realise.getRealisation();
        Assertions.assertEquals("breads", actual);
    }

    @Test
    void inflectPerfect_requiresVerb() {
        String word = "pass";
        VPPhraseSpec element = nlgFactory.createVerbPhrase();
        element.setVerb(word);
        element.setFeature(Feature.PERFECT, true);
        NLGElement realise = realiser.realise(element);
        String actual = realise.getRealisation();
        Assertions.assertEquals("has passed", actual);
    }

    @Test
    void inflectPerson_requiresPronounOrVerb() {
        SPhraseSpec element = nlgFactory.createClause();

        // first person
        element.setObject("we");
        element.setFeature(Feature.PERSON, Person.FIRST);
        String actual = realiser.realise(element).getRealisation();
        Assertions.assertEquals("us", actual);

        // second person
        element.setObject("I");
        element.setFeature(Feature.PERSON, Person.SECOND);
        String actual2 = realiser.realise(element).getRealisation();
        Assertions.assertEquals("me", actual2);

        // third person
        element.setObject("she");
        element.setFeature(Feature.PERSON, Person.THIRD);
        String actual3 = realiser.realise(element).getRealisation();
        Assertions.assertEquals("her", actual3);
    }

    @Test
    void inflectPossessive_requiresNounOrPronoun() {
        NPPhraseSpec element = nlgFactory.createNounPhrase();

        // noun
        element.setNoun("dog");
        element.setFeature(Feature.POSSESSIVE, true);
        String actual = realiser.realise(element).getRealisation();
        Assertions.assertEquals("dog's", actual);

        // pronoun
        element.setNoun("we");
        element.setFeature(Feature.POSSESSIVE, true);
        String actual2 = realiser.realise(element).getRealisation();
        Assertions.assertEquals("ours", actual2);
    }

    @Test
    void inflectProgressive_requiresVerb() {
        VPPhraseSpec element = nlgFactory.createVerbPhrase();
        element.setVerb("draw");
        element.setFeature(Feature.PROGRESSIVE, true);
        String actual = realiser.realise(element).getRealisation();
        Assertions.assertEquals("is drawing", actual);
    }

    @Test
    void inflectForm_requiresVerb() {
        VPPhraseSpec element = nlgFactory.createVerbPhrase();
        element.setVerb("look");

        element.setFeature(Feature.FORM, Form.BARE_INFINITIVE);
        String actual1 = realiser.realise(element).getRealisation();
        Assertions.assertEquals("look", actual1);

        element.setFeature(Feature.FORM, Form.GERUND);
        String actual2 = realiser.realise(element).getRealisation();
        Assertions.assertEquals("looking", actual2);

        element.setFeature(Feature.FORM, Form.IMPERATIVE);
        String actual3 = realiser.realise(element).getRealisation();
        Assertions.assertEquals("look", actual3);

        element.setFeature(Feature.FORM, Form.INFINITIVE);
        String actual4 = realiser.realise(element).getRealisation();
        Assertions.assertEquals("to look", actual4);

        element.setFeature(Feature.FORM, Form.NORMAL);
        String actual5 = realiser.realise(element).getRealisation();
        Assertions.assertEquals("looks", actual5);

        element.setFeature(Feature.FORM, Form.PAST_PARTICIPLE);
        String actual6 = realiser.realise(element).getRealisation();
        Assertions.assertEquals("looked", actual6);

        element.setFeature(Feature.FORM, Form.PRESENT_PARTICIPLE);
        String actual7 = realiser.realise(element).getRealisation();
        Assertions.assertEquals("looking", actual7);
    }

    @Test
    void inflectTense_requiresVerb() {
        VPPhraseSpec element = nlgFactory.createVerbPhrase();
        element.setVerb("create");

        element.setFeature(Feature.TENSE, Tense.PAST);
        String actual1 = realiser.realise(element).getRealisation();
        Assertions.assertEquals("created", actual1);

        element.setFeature(Feature.TENSE, Tense.PRESENT);
        String actual2 = realiser.realise(element).getRealisation();
        Assertions.assertEquals("creates", actual2);

        element.setFeature(Feature.TENSE, Tense.FUTURE);
        String actual3 = realiser.realise(element).getRealisation();
        Assertions.assertEquals("will create", actual3);
    }

    @Test
    void inflectComparative_requiresAdjectiveOrAdverb() {
        AdjPhraseSpec adj = nlgFactory.createAdjectivePhrase();
        adj.setAdjective("big");

        adj.setFeature(Feature.IS_COMPARATIVE, true);
        String actual1 = realiser.realise(adj).getRealisation();
        Assertions.assertEquals("bigger", actual1);

        AdvPhraseSpec adv = nlgFactory.createAdverbPhrase();
        adv.setAdverb("fast");

        adv.setFeature(Feature.IS_COMPARATIVE, true);
        String actual2 = realiser.realise(adv).getRealisation();
        Assertions.assertEquals("faster", actual2);
    }

    @Test
    void inflectSuperlative_requiresAdjectiveOrAdverb() {
        AdjPhraseSpec adj = nlgFactory.createAdjectivePhrase();
        adj.setAdjective("big");

        adj.setFeature(Feature.IS_SUPERLATIVE, true);
        String actual1 = realiser.realise(adj).getRealisation();
        Assertions.assertEquals("biggest", actual1);

        AdvPhraseSpec adv = nlgFactory.createAdverbPhrase();
        adv.setAdverb("fast");

        adv.setFeature(Feature.IS_SUPERLATIVE, true);
        String actual2 = realiser.realise(adv).getRealisation();
        Assertions.assertEquals("fastest", actual2);
    }


}
