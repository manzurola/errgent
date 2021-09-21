package com.github.manzurola.errgent.core;

import com.github.manzurola.errant4j.core.GrammaticalError;
import com.github.manzurola.errgent.core.inflection.Inflection;
import com.github.manzurola.spacy4j.api.containers.Doc;

import java.util.List;

/**
 * Base interface for grammatical error generators.
 */
public interface Generator {

    /**
     * Utility method to apply NLP to a text using the underlying spacy instance.
     *
     * @param text the text to parse
     * @return a parsed Doc object
     */
    Doc parse(String text);

    /**
     * Generate inflected docs with the specified grammatical error.
     *
     * @param target the target from which grammatically incorrect variances will be produced.
     * @return a list of {@link Doc} objects containing the specified grammatical error. Returns an empty list if no
     * matching errors could be produced.
     */
    List<Inflection> generateSingleError(Doc target, GrammaticalError error);

    List<Inflection> generateAnyError(Doc target, List<GrammaticalError> errors);

    List<Inflection> generateAllErrors(Doc target, List<GrammaticalError> errors);

}
