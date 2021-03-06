package io.github.manzurola.errgent.lang.en;

import io.github.manzurola.errant4j.lang.en.utils.wordlist.HunspellWordList;
import io.github.manzurola.errant4j.lang.en.utils.wordlist.WordList;
import io.github.manzurola.errgent.core.inflection.Inflection;

import java.util.Arrays;
import java.util.Optional;

public class EnInflectionFilter implements InflectionFilter {

    private final WordList wordList;

    public EnInflectionFilter() {
        this.wordList = new HunspellWordList();
    }

    @Override
    public boolean test(Inflection inflection) {
        return Optional.of(inflection)
            .filter(replacementNotEqualToOriginal())
            .filter(isRealWord())
            .isPresent();
    }

    public InflectionFilter replacementNotEqualToOriginal() {
        return inflection -> !inflection
            .replacementText()
            .equals(inflection.originalText());
    }

    public InflectionFilter isRealWord() {
        return inflection -> {
            String[] words = inflection.replacementText().trim().split(" ");
            return Arrays
                .stream(words)
                .allMatch(word -> !word.isBlank() && wordList.contains(word));
        };
    }

}
