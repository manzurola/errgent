package io.github.manzurola.errgent.core;

import io.github.manzurola.errant4j.core.errors.GrammaticalError;
import io.github.manzurola.spacy4j.api.containers.Doc;

import java.util.List;

/**
 * Base interface for grammatical error generators.
 */
public interface Generator {

    /**
     * Generate inflected docs with the specified grammatical error. A utility
     * method that filters results from {@link Generator#generateErrors(String)}
     * that contain the specified error
     *
     * @param sourceText the target from which grammatically incorrect variances
     *                   will be produced.
     * @return a list of {@link Doc} objects containing the specified
     * grammatical error. Returns an empty list if no matching errors could be
     * produced.
     */
    List<GeneratedError> generateErrors(
        String sourceText,
        GrammaticalError error
    );

    /**
     * Generate inflected docs with all possible grammatical errors.
     *
     * @param sourceText the target from which grammatically incorrect variances
     *                   will be produced.
     * @return a list of {@link Doc} objects containing the specified
     * grammatical error. Returns an empty list if no matching errors could be
     * produced.
     */
    List<GeneratedError> generateErrors(String sourceText);

}
