package com.github.manzurola.errgent.core.merge;

import com.github.manzurola.errgent.core.Inflection;

import java.util.List;

public interface Merger {

    Inflection merge(List<Inflection> docs);
}
