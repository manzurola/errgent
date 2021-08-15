package com.github.manzurola.errgent.core;

import com.github.manzurola.errant4j.core.GrammaticalError;
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
     * Generate all possible grammatical errors from given doc.
     *
     * @param target the target from which grammatically incorrect variances will be produced.
     * @return a list of {@link Inflection} objects containing the newly inflected document and the list of grammatical
     * errors within it.
     */
    List<Inflection> generate(Doc target);

    /**
     * Generate grammatical errors contained in {@code errors} from given doc.
     *
     * @param target the target from which grammatically incorrect variances will be produced.
     * @return a list of {@link Inflection} objects containing the newly inflected document and the list of grammatical
     * errors within it.
     */
    List<Inflection> generate(Doc target, List<GrammaticalError> errors);

}
