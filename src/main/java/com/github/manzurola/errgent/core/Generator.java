package com.github.manzurola.errgent.core;

import com.github.manzurola.errant4j.core.GrammaticalError;
import com.github.manzurola.spacy4j.api.containers.Doc;

import java.util.List;
import java.util.Optional;

/**
 * Base interface for grammatical error generators.
 */
public interface Generator {

    /**
     * Generate inflected docs with the specified grammatical error.
     *
     * @param target the target from which grammatically incorrect variances will be produced.
     * @return a list of {@link Doc} objects containing the specified grammatical error. Returns an empty list if no
     * matching errors could be produced.
     */
    Optional<GeneratedError> generateError(Doc target, GrammaticalError error);

    /**
     * Generate inflected docs with the specified grammatical error.
     *
     * @param target the target from which grammatically incorrect variances will be produced.
     * @return a list of {@link Doc} objects containing the specified grammatical error. Returns an empty list if no
     * matching errors could be produced.
     */
    List<GeneratedError> generateErrors(Doc target);

}
