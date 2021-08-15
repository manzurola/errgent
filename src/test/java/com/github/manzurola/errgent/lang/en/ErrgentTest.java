package com.github.manzurola.errgent.lang.en;

import com.github.manzurola.errant4j.core.Annotation;
import com.github.manzurola.errant4j.core.Annotator;
import com.github.manzurola.errant4j.core.Errant;
import com.github.manzurola.errant4j.core.GrammaticalError;
import com.github.manzurola.errgent.core.Errgent;
import com.github.manzurola.errgent.core.Generator;
import com.github.manzurola.spacy4j.adapters.corenlp.CoreNLPAdapter;
import com.github.manzurola.spacy4j.api.SpaCy;
import com.github.manzurola.spacy4j.api.containers.Doc;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static com.github.manzurola.errant4j.core.GrammaticalError.REPLACEMENT_SUBJECT_VERB_AGREEMENT;

public class ErrgentTest {

    @Test
    void singleInflection() {
        SpaCy spacy = SpaCy.create(CoreNLPAdapter.create());
        Annotator annotator = Errant.newAnnotator("en", spacy);
        Generator generator = Errgent.newGenerator("en", annotator);

        Doc expected = generator.parse("My girls like to has fun.");
        Doc target = generator.parse("My girls like to have fun.");

        List<Doc> inflections = generator.generate(target, REPLACEMENT_SUBJECT_VERB_AGREEMENT);
        Doc actual = inflections.get(0);

        List<Annotation> annotations = annotator.annotate(actual.tokens(), target.tokens())
                .stream()
                .filter(annotation -> !annotation.grammaticalError().equals(GrammaticalError.NONE))
                .collect(Collectors.toList());

        Annotation expectedError = annotations.get(0);

        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expectedError.grammaticalError(), REPLACEMENT_SUBJECT_VERB_AGREEMENT);
    }
}
