package com.github.manzurola.errgent.lang.en;

import com.github.manzurola.errant4j.core.Annotator;
import com.github.manzurola.errant4j.core.Errant;
import com.github.manzurola.errgent.core.Errgent;
import com.github.manzurola.errgent.core.Generator;
import com.github.manzurola.errgent.core.GeneratedError;
import com.github.manzurola.spacy4j.adapters.corenlp.CoreNLPAdapter;
import com.github.manzurola.spacy4j.api.SpaCy;
import com.github.manzurola.spacy4j.api.containers.Doc;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;


public class UsageExamples {

    private static Annotator annotator;
    private static Generator generator;

    @BeforeAll
    static void setup() {
        // Get a spaCy instance
        SpaCy spacy = SpaCy.create(CoreNLPAdapter.create());
        // Create an English error annotator
        annotator = Errant.newAnnotator("en", spacy);
        // Create an English error generator
        generator = Errgent.newGenerator("en", annotator);
    }

    @Test
    void generateSingleError() {

        Doc target = annotator.parse("I need to remember to feed my cat.");

        // Generate all documents that contain the specified error
        List<GeneratedError> generatedErrors = generator.generateErrors(target);
        for (GeneratedError generatedError : generatedErrors) {
            System.out.printf("Error: %s", generatedError.text());
        }
    }

}
