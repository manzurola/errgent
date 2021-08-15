package com.github.manzurola.errgent.core;

import com.github.manzurola.errgent.core.inflect.Inflector;
import com.github.manzurola.errgent.lang.en.inflector.EnInflector;
import com.github.manzurola.errant4j.core.Annotator;

import java.util.Map;
import java.util.function.Function;

public final class Errgent {

    private static final Map<String, Function<Annotator, Generator>> generators;

    static {
        generators = Map.of(
                "en", annotator -> new GeneratorImpl(annotator, new EnInflector())
        );
    }

    private Errgent() {
    }

    public static Generator enGenerator(Annotator annotator) {
        return newGenerator(annotator, new EnInflector());
    }

    public static Generator newGenerator(Annotator annotator, Inflector inflector) {
        return new GeneratorImpl(annotator, inflector);
    }

    public static Generator newGenerator(String language, Annotator annotator) {
        if (generators.containsKey(language)) {
            return generators.get(language).apply(annotator);
        } else {
            throw new IllegalArgumentException(String.format("Unsupported Errgent language %s", language));
        }
    }
}
