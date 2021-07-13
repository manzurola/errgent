package com.github.manzurola.errgent;

import io.languagetoys.aligner.edit.Edit;
import io.languagetoys.errant4j.core.Annotation;
import io.languagetoys.errant4j.core.Annotator;
import io.languagetoys.errant4j.core.Errant;
import io.languagetoys.errant4j.core.GrammaticalError;
import io.languagetoys.spacy4j.adapters.corenlp.CoreNLPAdapter;
import io.languagetoys.spacy4j.api.SpaCy;
import io.languagetoys.spacy4j.api.containers.Doc;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

public class ErrgentTest {

    @Test
    void name() {

        SpaCy spaCy = SpaCy.create(CoreNLPAdapter.create());
        Annotator annotator = Errant.create().annotator("en", spaCy);

        Errgent errgent = new ErrgentImpl(annotator);

        Doc source = errgent.parse("My girls like to has fun.");
        Doc target = errgent.parse("My girls like to have fun.");

        Annotation annotation = Edit.builder()
                .substitute("has")
                .with("have")
                .atPosition(4, 4)
                .project(source.tokens(), target.tokens())
                .transform(Annotation::of)
                .setGrammaticalError(GrammaticalError.REPLACEMENT_SUBJECT_VERB_AGREEMENT);
        Inflection expected = new Inflection(annotation);

        GrammaticalErrorFilter filter =
                new GrammaticalErrorFilter(Set.of(GrammaticalError.REPLACEMENT_SUBJECT_VERB_AGREEMENT));

        List<Inflection> inflections = errgent.inflect(target.tokens(), filter);
        Inflection actual = inflections.get(0);

        Assertions.assertEquals(expected, actual);


    }
}
