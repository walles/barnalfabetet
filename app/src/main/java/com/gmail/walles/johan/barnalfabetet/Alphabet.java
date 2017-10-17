package com.gmail.walles.johan.barnalfabetet;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import timber.log.Timber;

public class Alphabet {
    private Map<Character, Set<String>> lettersAndWords = new HashMap<>();
    private Random random = new Random();

    public Alphabet(Context context) throws IOException {
        try (InputStream wordStream = new FileInputStream(new File(context.getCacheDir(), "wordlist.txt"))) {
            for (String word: getWords(wordStream)) {
                addExampleWord(word);
            }
            return;
        } catch (IOException e) {
            Timber.w("Loading cached word list failed: %s", e.getMessage());
        }

        ClassLoader classLoader = getClass().getClassLoader();
        try (InputStream wordStream = classLoader.getResourceAsStream("wordlist.txt")) {
            for (String word: getWords(wordStream)) {
                addExampleWord(word);
            }
        }
    }

    private static Iterable<String> getWords(InputStream source) throws IOException {
        List<String> returnMe = new LinkedList<>();
        try (BufferedReader wordReader = new BufferedReader(
                new InputStreamReader(source, StandardCharsets.UTF_8))) {
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

                returnMe.add(line);
            }
        }

        return returnMe;
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
