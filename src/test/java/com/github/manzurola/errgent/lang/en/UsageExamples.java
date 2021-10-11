package com.github.manzurola.errgent.lang.en;

import com.github.manzurola.errant4j.core.Annotator;
import com.github.manzurola.errant4j.core.Errant;
import com.github.manzurola.errgent.core.Errgent;
import com.github.manzurola.errgent.core.GeneratedError;
import com.github.manzurola.errgent.core.Generator;
import com.github.manzurola.spacy4j.adapters.corenlp.CoreNLPAdapter;
import com.github.manzurola.spacy4j.api.SpaCy;
import com.github.manzurola.spacy4j.api.containers.Doc;
import org.junit.jupiter.api.Test;

import java.util.List;


public class UsageExamples {

    @Test
    void generateErrors() {

        SpaCy spacy = SpaCy.create(CoreNLPAdapter.create());
        Annotator annotator = Errant.newAnnotator("en", spacy);
        Generator generator = Errgent.newGenerator("en", annotator);

        Doc target = annotator.parse("I need to remember to feed my cat.");

        // Generate all documents that contain the specified error
        List<GeneratedError> generatedErrors = generator.generateErrors(target);
        for (GeneratedError generatedError : generatedErrors) {
            System.out.printf("Error: %s%n", generatedError.text());
        }
    }

}
