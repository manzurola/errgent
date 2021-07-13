package com.grammartoys.geg;


import com.grammartoys.geg.inflectors.InflectorFactory;
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

public class WordInflectingErrorGeneratorTest {

    private static final Annotator annotator = Errant.create().annotator("en", SpaCy.create(CoreNLPAdapter.create()));
    private static final InflectorFactory inflectorFactory = InflectorFactory.createDefault();

    @Test
    void subjectVerbAgreementError() {
        ErrorGenerator generator = new SVAErrorGenerator(annotator, inflectorFactory);
        Doc source = annotator.parse("My girls like to have fun.");
        Doc target = annotator.parse("My girls like to has fun.");
        List<Annotation> actual = generator.generate(source.tokens());
        Annotation expected = Edit.builder()
                .substitute("have")
                .with("has")
                .atPosition(4, 4)
                .project(source.tokens(), target.tokens())
                .transform(Annotation::of)
                .setGrammaticalError(GrammaticalError.REPLACEMENT_SUBJECT_VERB_AGREEMENT);
        assertSingleError(expected, actual);
    }

    @Test
    void nounNumberError() {
        ErrorGenerator generator = new NounNumberErrorGenerator(annotator, inflectorFactory);
        Doc source = annotator.parse("My girls like to have fun.");
        Doc target = annotator.parse("My girl like to have fun.");
        List<Annotation> actual = generator.generate(source.tokens());
        Annotation expected = Edit.builder()
                .substitute("girls")
                .with("girl")
                .atPosition(1, 1)
                .project(source.tokens(), target.tokens())
                .transform(Annotation::of)
                .setGrammaticalError(GrammaticalError.REPLACEMENT_NOUN_NUMBER);

        assertSingleError(expected, actual);
    }


    private void assertSingleError(Annotation expected, List<Annotation> actual) {
        Assertions.assertEquals(1, actual.size());
        Assertions.assertEquals(expected, actual.get(0));
    }

    private List<Annotation> generateErrors(Doc doc, ErrorGenerator generator) {
        return generator.generate(doc.tokens());
    }

}
