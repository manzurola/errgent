package com.grammartoys.geg;

import com.grammartoys.geg.inflectors.InflectorFactory;
import com.grammartoys.geg.inflectors.simplenlg.NumberInflector;
import io.languagetoys.errant4j.core.Annotator;

public class NounNumberErrorGenerator extends WordInflectingErrorGenerator {
    public NounNumberErrorGenerator(Annotator annotator, InflectorFactory inflectorFactory) {
        super(annotator, inflectorFactory.getInstance(NumberInflector.class));
    }
}
