package com.grammartoys.geg.inflectors;

import com.grammartoys.geg.inflectors.simplenlg.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultInflectorFactory implements InflectorFactory {

    private final Map<Class<? extends Inflector>, Inflector> inflectors;

    public DefaultInflectorFactory() {
        this.inflectors = defineMapping();
    }

    protected Map<Class<? extends Inflector>, Inflector> defineMapping() {
        Map<Class<? extends Inflector>, Inflector> inflectors = new HashMap<>();
        SimpleNLG simpleNLG = new SimpleNLG();

        inflectors.put(PersonInflector.class, new PersonInflector(simpleNLG));
        inflectors.put(NumberInflector.class, new NumberInflector(simpleNLG));
        inflectors.put(ProgressiveInflector.class, new ProgressiveInflector(simpleNLG));
        inflectors.put(VerbFormInflector.class, new VerbFormInflector(simpleNLG));
        inflectors.put(VerbTenseInflector.class, new VerbTenseInflector(simpleNLG));
        inflectors.put(ComparativeInflector.class, new ComparativeInflector(simpleNLG));

        return inflectors;
    }

    @Override
    public Inflector getInstance(Class<? extends Inflector> type) {
        return inflectors.get(type);
    }

    @Override
    public List<Inflector> getAll() {
        return List.copyOf(inflectors.values());
    }
}
