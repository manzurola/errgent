package com.github.manzurola.errgent.core.inflection;

import com.github.manzurola.errant4j.core.Annotation;
import com.github.manzurola.errant4j.core.GrammaticalError;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public interface InflectionFilter extends Predicate<Inflection> {

    static InflectionFilter matchAnyError(List<GrammaticalError> errors) {
        final Set<GrammaticalError> expectedErrors = new HashSet<>(errors);
        return inflection -> inflection
                .errors()
                .stream()
                .map(Annotation::grammaticalError)
                .anyMatch(expectedErrors::contains);
    }

    static InflectionFilter matchAllErrors(List<GrammaticalError> errors) {
        final Set<GrammaticalError> expectedErrors = new HashSet<>(errors);
        return inflection -> inflection
                .errors()
                .stream()
                .map(Annotation::grammaticalError)
                .allMatch(expectedErrors::contains);

    }

    static InflectionFilter matchExactErrors(List<GrammaticalError> errors) {
        final Set<GrammaticalError> expectedErrors = new HashSet<>(errors);
        return inflection -> {
            Set<GrammaticalError> actualErrors = inflection.errors()
                    .stream()
                    .map(Annotation::grammaticalError)
                    .collect(Collectors.toSet());
            return expectedErrors.equals(actualErrors);
        };
    }
}
