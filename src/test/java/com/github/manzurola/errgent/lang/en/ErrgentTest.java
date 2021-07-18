package com.github.manzurola.errgent.lang.en;

import com.github.manzurola.errgent.core.*;
import com.github.manzurola.errgent.core.filter.InflectionFilter;
import com.github.manzurola.errgent.lang.en.inflector.EnInflector;
import io.languagetoys.aligner.edit.Edit;
import io.languagetoys.errant4j.core.Annotation;
import io.languagetoys.errant4j.core.Annotator;
import io.languagetoys.errant4j.core.Errant;
import io.languagetoys.spacy4j.adapters.corenlp.CoreNLPAdapter;
import io.languagetoys.spacy4j.api.SpaCy;
import io.languagetoys.spacy4j.api.containers.Doc;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.languagetoys.errant4j.core.GrammaticalError.REPLACEMENT_SUBJECT_VERB_AGREEMENT;
import static io.languagetoys.errant4j.core.GrammaticalError.REPLACEMENT_VERB;

public class ErrgentTest {

    @Test
    void singleInflection() {
        SpaCy spacy = SpaCy.create(CoreNLPAdapter.create());
        Annotator annotator = Errant.newAnnotator("en", spacy);
        Generator generator = new GeneratorImpl(annotator, new EnInflector());

        Doc source = generator.parse("My girls like to has fun.");
        Doc target = generator.parse("My girls like to have fun.");

        Annotation annotation = Edit.builder()
                .substitute("has")
                .with("have")
                .atPosition(4, 4)
                .project(source.tokens(), target.tokens())
                .transform(e -> Annotation.of(e, REPLACEMENT_SUBJECT_VERB_AGREEMENT));

        Inflection expected = Inflection.of(source, List.of(annotation));

        InflectionFilter filter = InflectionFilter.matchesAllErrors(REPLACEMENT_SUBJECT_VERB_AGREEMENT);

        List<Inflection> inflections = generator.generate(target.tokens(), filter);

        Inflection actual = inflections.get(0);
        Assertions.assertEquals(expected, actual);
    }

    @Disabled
    @Test
    void multipleInflections() {
        SpaCy spaCy = SpaCy.create(CoreNLPAdapter.create());
        Annotator annotator = Errant.newAnnotator("en", spaCy);
        Generator generator = new GeneratorImpl(annotator, new EnInflector());

        Doc source = generator.parse("My girls likes to has fun.");
        Doc target = generator.parse("My girls like to have fun.");

        Annotation annotation1 = Edit.builder()
                .substitute("has")
                .with("have")
                .atPosition(4, 4)
                .project(source.tokens(), target.tokens())
                .transform(e -> Annotation.of(e, REPLACEMENT_SUBJECT_VERB_AGREEMENT));

        Annotation annotation2 = Edit.builder()
                .substitute("likes")
                .with("like")
                .atPosition(2, 2)
                .project(source.tokens(), target.tokens())
                .transform(e -> Annotation.of(e, REPLACEMENT_VERB));

        Inflection expected = Inflection.of(source, List.of(annotation1, annotation2));

        InflectionFilter filter = InflectionFilter.matchesAllErrors(REPLACEMENT_SUBJECT_VERB_AGREEMENT);

        List<Inflection> inflections = generator.generate(target.tokens(), filter);

        Inflection actual = inflections.get(0);
        Assertions.assertEquals(expected, actual);
    }
}
