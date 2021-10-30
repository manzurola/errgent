package com.github.manzurola.errgent.lang.en;

import com.github.manzurola.errant4j.core.errors.GrammaticalError;
import com.github.manzurola.errgent.core.Errgent;
import com.github.manzurola.errgent.core.GeneratedError;
import com.github.manzurola.errgent.core.Generator;
import com.github.manzurola.spacy4j.adapters.corenlp.CoreNLPAdapter;
import com.github.manzurola.spacy4j.api.SpaCy;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.github.manzurola.errant4j.core.errors.GrammaticalError.REPLACEMENT_VERB_TENSE;


public class UsageExamples {

    @Test
    void generateAnError() {

        SpaCy spacy = SpaCy.create(CoreNLPAdapter.forEnglish());
        Generator generator = Errgent.forEnglish(spacy);

        String sourceText = "If I were you, I would go home.";

        // Generate all documents that contain the specified error
        GeneratedError generatedError = generator
            .generateErrors(sourceText, REPLACEMENT_VERB_TENSE)
            .stream()
            .findFirst()
            .orElseThrow();

        System.out.println(generatedError);
    }

    @Test
    void generateAllErrors() {

        SpaCy spacy = SpaCy.create(CoreNLPAdapter.forEnglish());
        Generator errgent = Errgent.forEnglish(spacy);

        // Generate a specific grammatical error in the target doc. Since a
        // sentence can contain multiple errors at once, all such possible
        // errors are returned.
        List<GeneratedError> generatedErrors = errgent.generateErrors(
            "If I were you, I would go home.",
            GrammaticalError.REPLACEMENT_SUBJECT_VERB_AGREEMENT
        );

        // Print out the results. The markedText() method retrieves the
        // erroneous text with the error marked by an asterisk on both sides.
        // We can also access the char offsets of the error using charStart
        // and charEnd methods of GeneratedError.
        for (GeneratedError generatedError : generatedErrors) {
            String text = generatedError.markedText();
            System.out.printf(
                "%s, %s%n",
                text,
                generatedError.error()
            );
        }
    }

}
