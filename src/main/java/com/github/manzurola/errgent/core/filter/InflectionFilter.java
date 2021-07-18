package com.github.manzurola.errgent.core.filter;

import com.github.manzurola.errgent.core.Inflection;
import io.languagetoys.errant4j.core.GrammaticalError;

import java.util.Set;

public interface InflectionFilter {

    boolean filter(Inflection inflection);

    static InflectionFilter matchesAllErrors(GrammaticalError... errors) {
        return new AllErrorsInflectionFilter(Set.of(errors));
    }

    static InflectionFilter matchesAnyError(GrammaticalError... errors) {
        return new AnyErrorInflectionFilter(Set.of(errors));
    }
}
