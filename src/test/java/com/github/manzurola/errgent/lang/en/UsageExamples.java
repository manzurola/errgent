package com.github.manzurola.errgent.lang.en;

import com.github.manzurola.errant4j.core.Annotator;
import com.github.manzurola.errant4j.core.Errant;
import com.github.manzurola.errgent.core.Errgent;
import com.github.manzurola.errgent.core.Generator;
import com.github.manzurola.spacy4j.adapters.corenlp.CoreNLPAdapter;
import com.github.manzurola.spacy4j.api.SpaCy;
import com.github.manzurola.spacy4j.api.containers.Doc;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.github.manzurola.errant4j.core.GrammaticalError.REPLACEMENT_SUBJECT_VERB_AGREEMENT;

public class UsageExamples {

    @Test
    void generateSingleError() {
        // Get a spaCy instance
        SpaCy spacy = SpaCy.create(CoreNLPAdapter.create());
        // Create an English error annotator
        Annotator annotator = Errant.newAnnotator("en", spacy);
        // Create an English error generator
        Generator generator = Errgent.newGenerator("en", annotator);

        Doc target = generator.parse("My friends like to have fun.");

        // Generate all documents that contain the specified error
        List<Doc> inflections = generator.generate(target, REPLACEMENT_SUBJECT_VERB_AGREEMENT);
        for (Doc inflection : inflections) {
            System.out.println(inflection.text());
        }
    }
}
