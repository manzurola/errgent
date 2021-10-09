package com.github.manzurola.errgent.lang.en;

import com.github.manzurola.aligner.edit.Edit;
import com.github.manzurola.errant4j.core.Annotation;
import com.github.manzurola.errant4j.core.Annotator;
import com.github.manzurola.errant4j.core.Errant;
import com.github.manzurola.errant4j.core.GrammaticalError;
import com.github.manzurola.errgent.core.Errgent;
import com.github.manzurola.errgent.core.Generator;
import com.github.manzurola.errgent.core.GeneratedError;
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
    void generateSingleError() {
        SpaCy spacy = SpaCy.create(CoreNLPAdapter.create());
        Annotator annotator = Errant.newAnnotator("en", spacy);
        Generator generator = Errgent.newGenerator("en", annotator);

        Doc target = annotator.parse("My girls like to have fun.");

        Doc expectedDoc = annotator.parse("My girls like to has fun.");
        GeneratedError expectedError = GeneratedError.of(
                expectedDoc,
                Annotation.of(
                        Edit.builder()
                                .substitute(expectedDoc.token(4))
                                .with(target.token(4))
                                .atPosition(4, 4),
                        REPLACEMENT_SUBJECT_VERB_AGREEMENT)
        );

        GeneratedError actualError = generator
                .generateError(target, REPLACEMENT_SUBJECT_VERB_AGREEMENT)
                .orElseThrow();

        Assertions.assertEquals(expectedError, actualError);
    }

    @Test
    void generateAllErrors() {
        SpaCy spacy = SpaCy.create(CoreNLPAdapter.create());
        Annotator annotator = Errant.newAnnotator("en", spacy);
        Generator generator = Errgent.newGenerator("en", annotator);

        Doc expected1 = annotator.parse("My girls like to has fun.");
        Doc expected2 = annotator.parse("My girl like to has fun.");
        Doc target = annotator.parse("My girls like to have fun.");

        List<GeneratedError> generatedErrors = generator.generateErrors(target);

        List<Annotation> annotations = annotator
                .annotate(expected1.tokens(), target.tokens())
                .stream()
                .filter(annotation -> !annotation.grammaticalError().equals(GrammaticalError.NONE))
                .collect(Collectors.toList());

        Annotation expectedError = annotations.get(0);

//        Assertions.assertEquals(expected1, generatedError.doc());
        Assertions.assertEquals(expectedError.grammaticalError(), REPLACEMENT_SUBJECT_VERB_AGREEMENT);
    }
}
