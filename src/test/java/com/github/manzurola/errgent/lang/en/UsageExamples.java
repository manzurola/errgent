package com.github.manzurola.errgent.lang.en;

import com.github.manzurola.errgent.core.Generator;
import com.github.manzurola.errgent.core.GeneratorImpl;
import com.github.manzurola.errgent.core.Inflection;
import com.github.manzurola.errgent.lang.en.inflector.EnInflector;
import com.github.manzurola.errant4j.core.Annotator;
import com.github.manzurola.errant4j.core.Errant;
import com.github.manzurola.spacy4j.adapters.corenlp.CoreNLPAdapter;
import com.github.manzurola.spacy4j.api.SpaCy;
import com.github.manzurola.spacy4j.api.containers.Doc;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.github.manzurola.errant4j.core.GrammaticalError.REPLACEMENT_SUBJECT_VERB_AGREEMENT;

public class UsageExamples {

    @Test
    void generateSingleError() {
        SpaCy spacy = SpaCy.create(CoreNLPAdapter.create());
        Annotator annotator = Errant.newAnnotator("en", spacy);

        Generator generator = new GeneratorImpl(annotator, new EnInflector());

        Doc target = generator.parse("My friends like to have fun.");

        List<Inflection> inflections = generator.generate(target, List.of(REPLACEMENT_SUBJECT_VERB_AGREEMENT));
        for (Inflection inflection : inflections) {
            System.out.println(inflection.doc().text());
        }
    }
}
