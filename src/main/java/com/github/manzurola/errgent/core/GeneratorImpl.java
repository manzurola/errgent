package com.github.manzurola.errgent.core;

import com.github.manzurola.errant4j.core.Annotation;
import com.github.manzurola.errant4j.core.Annotator;
import com.github.manzurola.errant4j.core.GrammaticalError;
import com.github.manzurola.errgent.core.inflection.Inflection;
import com.github.manzurola.errgent.core.inflection.InflectionFilter;
import com.github.manzurola.errgent.core.inflection.Inflector;
import com.github.manzurola.spacy4j.api.containers.Doc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public final class GeneratorImpl implements Generator {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Annotator annotator;
    private final Inflector inflector;
    private final InflectionFilter inflectionFilter;

    public GeneratorImpl(Annotator annotator,
                         Inflector inflector,
                         InflectionFilter inflectionFilter) {
        this.annotator = annotator;
        this.inflector = inflector;
        this.inflectionFilter = inflectionFilter;
    }

    @Override
    public final Optional<GeneratedError> generateError(Doc target, GrammaticalError error) {
        return generateErrors(target)
                .stream()
                .filter(generatedError -> generatedError.grammaticalError().equals(error))
                .findFirst();
    }

    @Override
    public final List<GeneratedError> generateErrors(Doc target) {
        return target
                .tokens()
                .stream()
                .flatMap(inflector::inflectToken)
                .distinct()
                .parallel()
                .filter(inflectionFilter)
                .map(inflection -> applyInflection(inflection, target))
                .map(inflectedDoc -> annotateSingleError(inflectedDoc, target))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    private Doc applyInflection(Inflection inflection, Doc target) {
        String inflectedText = new StringBuilder(target.text())
                .replace(inflection.charStart(), inflection.charEnd(), inflection.replacementText())
                .toString();
        return annotator.parse(inflectedText);
    }

    private Optional<GeneratedError> annotateSingleError(Doc inflectedDoc, Doc target) {
        List<Annotation> annotations = annotator.annotate(inflectedDoc.tokens(), target.tokens());
        return annotations
                .stream()
                .filter(Annotation::hasError)
                .findFirst()
                .map(annotation -> GeneratedError.of(inflectedDoc, annotation));
    }

}
