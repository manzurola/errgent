package com.github.manzurola.errgent.core.filter;

import com.github.manzurola.errgent.core.Inflection;
import io.languagetoys.errant4j.core.GrammaticalError;

import java.util.Objects;
import java.util.Set;

public class AllErrorsInflectionFilter implements InflectionFilter {

    private final Set<GrammaticalError> expected;

    public AllErrorsInflectionFilter(Set<GrammaticalError> expected) {
        this.expected = Objects.requireNonNull(expected);
    }

    @Override
    public boolean filter(Inflection inflection) {
        return inflection.errors()
                .stream()
                .allMatch(annotation -> expected.contains(annotation.grammaticalError()));
    }
}
