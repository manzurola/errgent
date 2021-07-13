package com.grammartoys.geg;

import com.grammartoys.geg.inflectors.InflectorFactory;
import com.grammartoys.geg.inflectors.simplenlg.PersonInflector;
import io.languagetoys.errant4j.core.Annotator;

public final class SVAErrorGenerator extends WordInflectingErrorGenerator {

    public SVAErrorGenerator(Annotator annotator, InflectorFactory inflectorFactory) {
        super(annotator, inflectorFactory.getInstance(PersonInflector.class));
    }

}
