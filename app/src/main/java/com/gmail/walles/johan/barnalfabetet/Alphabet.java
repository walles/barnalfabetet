package com.gmail.walles.johan.barnalfabetet;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Alphabet {
    private Map<Character, String> lettersAndPhrases = new HashMap<>();
    private Random random = new Random();

    public Alphabet() {
        lettersAndPhrases.put('a', "A som i apa");
        lettersAndPhrases.put('b', "B som i björn");
        lettersAndPhrases.put('c', "C som i cykel");
        lettersAndPhrases.put('d', "D som i dromedar");
        lettersAndPhrases.put('e', "E som i eko");
        lettersAndPhrases.put('f', "F som i flodhäst");
        lettersAndPhrases.put('g', "G som i gris");
        lettersAndPhrases.put('h', "H som i häst");
        lettersAndPhrases.put('i', "I som i isbjörn");
        lettersAndPhrases.put('j', "J som i julgran");
        lettersAndPhrases.put('k', "K som i kaka");
        lettersAndPhrases.put('l', "L som i lampa");
        lettersAndPhrases.put('m', "M som i mamma");
        lettersAndPhrases.put('n', "N som i noshörning");
        lettersAndPhrases.put('o', "O som i ortoped");
        lettersAndPhrases.put('p', "P som i pappa");
        lettersAndPhrases.put('r', "R som i randig");
        lettersAndPhrases.put('s', "S som i soppa");
        lettersAndPhrases.put('t', "T som i tokig");
        lettersAndPhrases.put('u', "U som i ubåt");
        lettersAndPhrases.put('v', "V som i viktig");
        lettersAndPhrases.put('x', "X som i xylofon");
        lettersAndPhrases.put('y', "Y som i yla");
        lettersAndPhrases.put('z', "Z som i zebra");
        lettersAndPhrases.put('å', "Å som i åka");
        lettersAndPhrases.put('ä', "Ä som i ärta");
        lettersAndPhrases.put('ö', "Ö som i önska");
    }

    public char getRandomLetter() {
        int randomIndex = random.nextInt(lettersAndPhrases.keySet().size());
        int i = 0;
        for (char randomLetter : lettersAndPhrases.keySet()) {
            if (i++ == randomIndex) {
                return randomLetter;
            }
        }

        throw new RuntimeException("This should never happen");
    }

    public String getPhrase(char letter) {
        return lettersAndPhrases.get(letter);
    }
}
