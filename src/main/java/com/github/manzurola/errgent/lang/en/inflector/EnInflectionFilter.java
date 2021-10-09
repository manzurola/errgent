package com.github.manzurola.errgent.lang.en.inflector;

import com.github.manzurola.errant4j.lang.en.utils.wordlist.HunspellWordList;
import com.github.manzurola.errant4j.lang.en.utils.wordlist.WordList;
import com.github.manzurola.errgent.core.inflection.Inflection;
import com.github.manzurola.errgent.core.inflection.InflectionFilter;

import java.util.Arrays;

public class EnInflectionFilter implements InflectionFilter {

    private final WordList wordList;

    public EnInflectionFilter() {
        this.wordList = new HunspellWordList();
    }

    @Override
    public boolean test(Inflection inflection) {
        String[] words = inflection.replacementText().trim().split(" ");
        return Arrays.stream(words).allMatch(word -> !word.isBlank() && wordList.contains(word));
    }
}