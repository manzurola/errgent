package io.github.manzurola.errgent.lang.en;

import io.github.manzurola.errgent.core.inflection.Inflection;
import io.github.manzurola.errgent.core.inflection.Inflector;
import io.github.manzurola.spacy4j.api.containers.Token;
import simplenlg.features.*;
import simplenlg.framework.NLGElement;
import simplenlg.framework.NLGFactory;
import simplenlg.lexicon.Lexicon;
import simplenlg.phrasespec.NPPhraseSpec;
import simplenlg.phrasespec.SPhraseSpec;
import simplenlg.phrasespec.VPPhraseSpec;
import simplenlg.realiser.english.Realiser;

import java.util.List;
import java.util.stream.Stream;

import static io.github.manzurola.errant4j.lang.en.classify.rules.common.Predicates.*;

public final class EnInflector implements Inflector {

    private final NLGFactory nlgFactory;
    private final Realiser realiser;
    private final InflectionFilter filter;

    public EnInflector() {
        this.nlgFactory = new NLGFactory(Lexicon.getDefaultLexicon());
        this.realiser = new Realiser();
        this.filter = new EnInflectionFilter();
    }

    @Override
    public Stream<Inflection> inflectToken(Token token) {
        Stream<Inflection> substitutions = getNlgElements(token)
            .stream()
            .map(nlgElement -> realiser.realise(nlgElement).getRealisation())
            .map(inflectedText -> Inflection.substituteToken(
                token,
                inflectedText
            ));
        return Stream.concat(
            Stream.of(Inflection.deleteToken(token)),
            substitutions
        ).filter(filter);
    }

    private List<NLGElement> getNlgElements(Token token) {
        return List.of(
            createNLGElement(token, Feature.IS_COMPARATIVE, true),
            createNLGElement(token, Feature.IS_COMPARATIVE, false),
            createNLGElement(token, Feature.NUMBER, NumberAgreement.SINGULAR),
            createNLGElement(token, Feature.NUMBER, NumberAgreement.PLURAL),
            createNLGElement(token, Feature.PERSON, Person.FIRST),
            createNLGElement(token, Feature.PERSON, Person.SECOND),
            createNLGElement(token, Feature.PERSON, Person.THIRD),
            createNLGElement(token, Feature.POSSESSIVE, true),
            createNLGElement(token, Feature.POSSESSIVE, false),
            createNLGElement(token, Feature.PROGRESSIVE, true),
            createNLGElement(token, Feature.IS_SUPERLATIVE, true),
            createNLGElement(token, Feature.IS_SUPERLATIVE, false),
            createNLGElement(token, Feature.FORM, Form.BARE_INFINITIVE),
            createNLGElement(token, Feature.FORM, Form.GERUND),
            createNLGElement(token, Feature.FORM, Form.IMPERATIVE),
            createNLGElement(token, Feature.FORM, Form.INFINITIVE),
            createNLGElement(token, Feature.FORM, Form.NORMAL),
            createNLGElement(token, Feature.FORM, Form.PAST_PARTICIPLE),
            createNLGElement(token, Feature.FORM, Form.PRESENT_PARTICIPLE),
            createNLGElement(token, Feature.TENSE, Tense.PAST),
            createNLGElement(token, Feature.TENSE, Tense.PRESENT),
            createNLGElement(token, Feature.TENSE, Tense.FUTURE)
        );
    }

    private NLGElement createNLGElement(
        Token token,
        String feature,
        Object value
    ) {
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

        result.setFeature(feature, value);
        return result;
    }
}
