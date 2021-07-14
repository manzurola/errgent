package com.github.manzurola.errgent.lang.en;

import com.github.manzurola.errgent.core.Errgent;
import com.github.manzurola.errgent.core.ErrgentImpl;
import com.github.manzurola.errgent.core.filters.GrammaticalErrorFilter;
import com.github.manzurola.errgent.core.Inflection;
import io.languagetoys.aligner.edit.Edit;
import io.languagetoys.errant4j.core.Annotation;
import io.languagetoys.errant4j.core.Annotator;
import io.languagetoys.errant4j.core.Errant;
import io.languagetoys.spacy4j.adapters.corenlp.CoreNLPAdapter;
import io.languagetoys.spacy4j.api.SpaCy;
import io.languagetoys.spacy4j.api.containers.Doc;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static io.languagetoys.errant4j.core.GrammaticalError.REPLACEMENT_SUBJECT_VERB_AGREEMENT;

public class ErrgentTest {

    @Test
    void name() {

        SpaCy spaCy = SpaCy.create(CoreNLPAdapter.create());
        Annotator annotator = Errant.create().annotator("en", spaCy);
        Errgent errgent = new ErrgentImpl(annotator);

        Doc source = errgent.parse("My girls like to has fun.");
        Doc target = errgent.parse("My girls like to have fun.");

        Inflection expected = Edit.builder()
                .substitute("has")
                .with("have")
                .atPosition(4, 4)
                .project(source.tokens(), target.tokens())
                .transform(e -> Annotation.of(e, REPLACEMENT_SUBJECT_VERB_AGREEMENT))
                .transform(a -> Inflection.of(a, source));

        GrammaticalErrorFilter filter =
                new GrammaticalErrorFilter(Set.of(REPLACEMENT_SUBJECT_VERB_AGREEMENT));

        List<Inflection> inflections = errgent.generate(target.tokens(), filter);

        Inflection actual = inflections.get(0);
        Assertions.assertEquals(expected, actual);

        // assert expected text with error
        Doc inflectedDoc = actual.doc();
        Assertions.assertEquals(source, inflectedDoc);

    }
}
