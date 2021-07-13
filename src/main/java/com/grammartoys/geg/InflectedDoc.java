package com.grammartoys.geg;

import io.languagetoys.errant4j.core.Annotation;

import javax.print.Doc;
import java.util.List;

public class InflectedDoc {

    private final Doc source;
    private final Doc target;
    private final List<Annotation> annotations;

    public InflectedDoc(Doc source, Doc target, List<Annotation> annotations) {
        this.source = source;
        this.target = target;
        this.annotations = annotations;
    }
}
