package com.github.manzurola.errgent;

import com.github.manzurola.errgent.inflectors.InflectorFactory;
import com.github.manzurola.errgent.inflectors.simplenlg.NumberInflector;
import io.languagetoys.errant4j.core.Annotator;

public class NounNumberErrorGenerator extends WordInflectingErrorGenerator {
    public NounNumberErrorGenerator(Annotator annotator, InflectorFactory inflectorFactory) {
        super(annotator, inflectorFactory.getInstance(NumberInflector.class));
    }
}
