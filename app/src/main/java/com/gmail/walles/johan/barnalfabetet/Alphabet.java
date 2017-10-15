package com.gmail.walles.johan.barnalfabetet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class Alphabet {
    private Map<Character, Set<String>> lettersAndWords = new HashMap<>();
    private Random random = new Random();

    public Alphabet() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        try (InputStream wordStream = classLoader.getResourceAsStream("wordlist.txt")) {
            try (BufferedReader wordReader = new BufferedReader(
                    new InputStreamReader(wordStream, StandardCharsets.UTF_8))) {
                while (true) {
                    String line = wordReader.readLine();
                    if (line == null) {
                        break;
                    }

                    line = line.trim();
                    if (line.isEmpty()) {
                        continue;
                    }
                    if (line.startsWith("#")) {
                        continue;
                    }

                    addExampleWord(line);
                }
            }
        }
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
