package com.github.manzurola.errgent.core;

import com.github.manzurola.errant4j.core.Annotator;
import com.github.manzurola.errant4j.core.Errant;
import com.github.manzurola.errgent.lang.en.EnInflector;
import com.github.manzurola.spacy4j.api.SpaCy;

public interface Errgent {

    static Generator forEnglish(SpaCy spaCy) {
        Annotator annotator = Errant.forEnglish(spaCy);
        return new GeneratorImpl(
            new EnInflector(), annotator
        );
    }
}
