package com.github.manzurola.errgent.lang.en;

import com.github.manzurola.errant4j.core.errors.GrammaticalError;
import com.github.manzurola.errgent.core.Errgent;
import com.github.manzurola.errgent.core.GeneratedError;
import com.github.manzurola.errgent.core.Generator;
import com.github.manzurola.spacy4j.adapters.corenlp.CoreNLPAdapter;
import com.github.manzurola.spacy4j.api.SpaCy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.github.manzurola.errant4j.core.errors.GrammaticalError.REPLACEMENT_SUBJECT_VERB_AGREEMENT;

public class ErrgentTest {

    private static Generator generator;

    @BeforeAll
    public static void setup() {
        SpaCy spacy = SpaCy.create(CoreNLPAdapter.forEnglish());
        generator = Errgent.forEnglish(spacy);
    }

    @Test
    void generateSingleError() {
        String originalText = "My girls like to have fun.";
        GrammaticalError grammaticalError =
            REPLACEMENT_SUBJECT_VERB_AGREEMENT;

        String expectedText = "My girls like to has fun.";
        GeneratedError expectedError = new GeneratedError(
            expectedText,
            17,
            20,
            grammaticalError
        );

        GeneratedError actualError = generator
            .generateErrors(originalText, grammaticalError)
            .stream()
            .findFirst()
            .orElseThrow();

        Assertions.assertEquals(expectedError, actualError);
    }

}
