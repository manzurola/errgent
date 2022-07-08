package io.github.manzurola.errgent.core;

import io.github.manzurola.errant4j.core.Annotator;
import io.github.manzurola.errant4j.core.Errant;
import io.github.manzurola.errgent.lang.en.EnInflector;
import io.github.manzurola.spacy4j.api.SpaCy;

public interface Errgent {

    static Generator forEnglish(SpaCy spaCy) {
        Annotator annotator = Errant.forEnglish(spaCy);
        return new GeneratorImpl(
            new EnInflector(), annotator
        );
    }
}
