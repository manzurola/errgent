package com.github.manzurola.errgent;

import com.github.manzurola.errgent.inflectors.InflectorFactory;
import com.github.manzurola.errgent.inflectors.simplenlg.PersonInflector;
import io.languagetoys.errant4j.core.Annotator;

public final class SVAErrorGenerator extends WordInflectingErrorGenerator {

    public SVAErrorGenerator(Annotator annotator, InflectorFactory inflectorFactory) {
        super(annotator, inflectorFactory.getInstance(PersonInflector.class));
    }

}
