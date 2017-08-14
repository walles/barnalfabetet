package com.gmail.walles.johan.barnalfabetet;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class Alphabet {
    private Map<Character, Set<String>> lettersAndWords = new HashMap<>();
    private Random random = new Random();

    public Alphabet() {
        // Strings are Swedish, just like my kids :)
        addExampleWord("apa");
        addExampleWord("björn");
        addExampleWord("bajs");
        addExampleWord("cykel");
        addExampleWord("dromedar");
        addExampleWord("eko");
        addExampleWord("flygplan");
        addExampleWord("gris");
        addExampleWord("häst");
        addExampleWord("isbjörn");
        addExampleWord("Johan");
        addExampleWord("kaka");
        addExampleWord("krabba");
        addExampleWord("kiss");
        addExampleWord("lampa");
        addExampleWord("mamma");
        addExampleWord("Malva");
        addExampleWord("Melvin");
        addExampleWord("noshörning");
        addExampleWord("ost");
        addExampleWord("pappa");
        addExampleWord("parkeringsplats");
        addExampleWord("prutta");
        addExampleWord("randig");
        addExampleWord("Sofia");
        addExampleWord("saxlyft");
        addExampleWord("tunnelbana");
        addExampleWord("ubåt");
        addExampleWord("viktig");
        addExampleWord("Walles");
        addExampleWord("xylofon");
        addExampleWord("yla");
        addExampleWord("zebra");
        addExampleWord("åka");
        addExampleWord("ädelsten");
        addExampleWord("ödla");
    }

    private void addExampleWord(String word) {
        char letter = word.charAt(0);

        // We do only caps for now
        letter = Character.toUpperCase(letter);

        if (!lettersAndWords.containsKey(letter)) {
            lettersAndWords.put(letter, new HashSet<String>());
        }
        lettersAndWords.get(letter).add(word);
    }

    private <E> E getRandom(Set<E> set) {
        int randomIndex = random.nextInt(set.size());
        int i = 0;
        for (E element : set) {
            if (i++ == randomIndex) {
                return element;
            }
        }

        throw new RuntimeException("This should never happen");
    }

    public char getRandomLetter() {
        return getRandom(lettersAndWords.keySet());
    }

    public String getPhrase(char letter) {
        Set<String> words = lettersAndWords.get(letter);
        return "\"" + letter + "\" som i \"" + getRandom(words) + "\"";
    }
}
