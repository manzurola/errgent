package com.github.manzurola.errgent.inflectors;

import java.util.List;

public interface InflectorFactory {

    Inflector getInstance(Class<? extends Inflector> type);

    List<Inflector> getAll();

    static InflectorFactory createDefault() {
        return new DefaultInflectorFactory();
    }
}
