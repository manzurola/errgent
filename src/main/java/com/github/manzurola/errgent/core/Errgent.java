package com.github.manzurola.errgent.core;

import com.github.manzurola.errant4j.core.Annotator;
import com.github.manzurola.errgent.lang.en.inflector.EnInflectionFilter;
import com.github.manzurola.errgent.lang.en.inflector.EnInflector;

import java.util.Map;
import java.util.function.Function;

public final class Errgent {

    private static final Map<String, Function<Annotator, Generator>> generators;

    static {
        generators = Map.of(
                "en", annotator -> new GeneratorImpl(annotator, new EnInflector(), new EnInflectionFilter())
        );
    }

    private Errgent() {
    }

    public static Generator newGenerator(String language, Annotator annotator) {
        if (generators.containsKey(language)) {
            return generators.get(language).apply(annotator);
        } else {
            throw new IllegalArgumentException(String.format("Unsupported Errgent language %s", language));
        }
    }
}
