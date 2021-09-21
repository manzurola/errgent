package com.github.manzurola.errgent.lang.en;

import com.github.manzurola.errant4j.core.Annotator;
import com.github.manzurola.errant4j.core.Errant;
import com.github.manzurola.errgent.core.Errgent;
import com.github.manzurola.errgent.core.Generator;
import com.github.manzurola.errgent.core.inflection.Inflection;
import com.github.manzurola.spacy4j.adapters.corenlp.CoreNLPAdapter;
import com.github.manzurola.spacy4j.api.SpaCy;
import com.github.manzurola.spacy4j.api.containers.Doc;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.github.manzurola.errant4j.core.GrammaticalError.*;


public class UsageExamples {

    private static SpaCy spacy;
    private static Annotator annotator;
    private static Generator generator;

    @BeforeAll
    static void setup() {
        // Get a spaCy instance
        spacy = SpaCy.create(CoreNLPAdapter.create());
        // Create an English error annotator
        annotator = Errant.newAnnotator("en", spacy);
        // Create an English error generator
        generator = Errgent.newGenerator("en", annotator);
    }

    @Test
    void generateSingleError() {

        Doc target = generator.parse("I need to remember to feed my cat.");

        // Generate all documents that contain the specified error
        List<Inflection> inflections = generator.generateSingleError(target, REPLACEMENT_NOUN_NUMBER);
        for (Inflection inflection : inflections) {
            System.out.printf("Inflection: %s%n%d errors:%n%s%n%n",
                              inflection.text(),
                              inflection.numOfErrors(),
                              inflection.errors());
        }
    }

    @Test
    void generateAnyError() {
        Doc target = generator.parse("I need to remember to feed my cat.");

        List<Inflection> inflections = generator.generateAnyError(target,
                                                                  List.of(REPLACEMENT_PARTICLE,
                                                                          REPLACEMENT_NOUN_POSSESSIVE,
                                                                          REPLACEMENT_VERB_TENSE,
                                                                          REPLACEMENT_NOUN_NUMBER));
        for (Inflection inflection : inflections) {
            System.out.printf("Inflection: %s%n%d errors:%n%s%n%n",
                              inflection.text(),
                              inflection.numOfErrors(),
                              inflection.errors());
        }
    }
}
