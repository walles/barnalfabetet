package com.gmail.walles.johan.barnalfabetet;

public class Challenge {
    /**
     * The correct answer. Should match one of the {@link #options}.
     */
    public final String answer;

    /**
     * The question to ask.
     */
    public final String question;

    /**
     * The options to show to the user. One of these is the {@link #answer}.
     */
    public final String[] options;

    public Challenge(String question, String answer, String[] options) {
        this.answer = answer;
        this.question = question;
        this.options = options;
    }
}
