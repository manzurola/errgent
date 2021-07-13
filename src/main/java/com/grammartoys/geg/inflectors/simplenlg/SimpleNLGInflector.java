package com.grammartoys.geg.inflectors.simplenlg;

import com.grammartoys.geg.inflectors.Inflector;
import io.languagetoys.spacy4j.api.containers.Token;
import simplenlg.framework.NLGElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public abstract class SimpleNLGInflector implements Inflector {

    private final SimpleNLG simpleNLG;

    public SimpleNLGInflector(SimpleNLG simpleNLG) {
        this.simpleNLG = simpleNLG;
    }

    @Override
    public final Set<String> inflect(Token token) {
        List<NLGElement> inflections = new ArrayList<>();

        if (canHandle(token)) {
            inflect(token, simpleNLG, inflections::add);
        }

        return inflections
                .stream()
                .map(simpleNLG::realise)
                .filter(s -> !token.lower().equals(s))
                .collect(Collectors.toUnmodifiableSet());
    }

    protected abstract boolean canHandle(Token token);

    protected abstract void inflect(Token token, SimpleNLG simpleNLG, Consumer<NLGElement> results);


}
