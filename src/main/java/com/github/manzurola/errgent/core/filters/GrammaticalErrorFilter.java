package com.github.manzurola.errgent.core.filters;

import com.github.manzurola.errgent.core.Inflection;
import io.languagetoys.errant4j.core.GrammaticalError;

import java.util.Set;

public class GrammaticalErrorFilter implements InflectionFilter {

    private final Set<GrammaticalError> errors;

    public GrammaticalErrorFilter(Set<GrammaticalError> errors) {
        this.errors = errors;
    }

    @Override
    public boolean filter(Inflection inflection) {
        return errors.contains(inflection.grammaticalError());
    }
}
