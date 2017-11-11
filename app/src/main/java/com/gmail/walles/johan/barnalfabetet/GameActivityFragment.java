package com.gmail.walles.johan.barnalfabetet;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.IOException;
import java.util.Random;

import timber.log.Timber;

/**
 * A placeholder fragment containing a simple view.
 */
public class GameActivityFragment extends Fragment implements View.OnClickListener {
    private static final String[] PRAISE = { "bra", "fint", "utmärkt" };

    @Nullable
    private TextToSpeech textToSpeech;
    private int ttsStatus = TextToSpeech.ERROR;

    private Challenge challenge;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        textToSpeech =
                new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int i) {
                        ttsStatus = i;
                        if (ttsStatus == TextToSpeech.SUCCESS) {
                            pickNewLetter();
                        } else {
                            Timber.e("Error setting up TTS: status=%d", i);
                        }
                    }
                });

        textToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
            @Override
            public void onStart(String s) {
                // This method intentionally left blank
            }

            @Override
            public void onDone(String s) {
                // This method intentionally left blank
            }

            @Override
            public void onError(String s) {
                Timber.e("Speech failed: id=<%s>", s);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);

        view.findViewById(R.id.answer1).setOnClickListener(this);
        view.findViewById(R.id.answer2).setOnClickListener(this);
        view.findViewById(R.id.answer3).setOnClickListener(this);

        return view;
    }

    private void speak(String phrase, boolean flush) {
        if (textToSpeech == null) {
            Timber.e("Text to speech not initialized");
            return;
        }

        if (ttsStatus != TextToSpeech.SUCCESS) {
            Timber.e("Text to speech status unsuccessful: %d", ttsStatus);
            return;
        }

        int queueMode = flush ? TextToSpeech.QUEUE_FLUSH : TextToSpeech.QUEUE_ADD;
        int status = textToSpeech.speak(phrase, queueMode, null);
        if (status == TextToSpeech.SUCCESS) {
            Timber.i("Spoken: flush=%s, phrase=<%s>", flush, phrase);
        } else {
            Timber.e("Speech failed: flush=%s, phrase=<%s>", flush, phrase);
        }
    }

    private boolean ttsInitializing() {
        if (textToSpeech == null) {
            return true;
        }

        if (ttsStatus != TextToSpeech.SUCCESS) {
            return true;
        }

        return false;
    }

    /**
     * Pick a new letter, update the buttons and speak the new phrase to the user.
     */
    private void pickNewLetter() {
        if (ttsInitializing()) {
            Timber.w("TTS initializing, not picking any new letter");
            return;
        }

        Timber.i("Picking new letter...");

        Alphabet alphabet;
        try {
            alphabet = new Alphabet(getContext());
        } catch (IOException e) {
            Timber.e("Getting the Alphabet failed", e);
            return;
        }

        challenge = alphabet.createChallenge();
        ((Button)getView().findViewById(R.id.answer1)).setText(challenge.options[0]);
        ((Button)getView().findViewById(R.id.answer2)).setText(challenge.options[1]);
        ((Button)getView().findViewById(R.id.answer3)).setText(challenge.options[2]);
        speak(challenge.question, false);
    }

    @Override
    public void setUserVisibleHint(boolean visible)
    {
        super.setUserVisibleHint(visible);

        // From: https://stackoverflow.com/a/25101237/473672
        if (visible && isResumed()) {
            pickNewLetter();
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (!getUserVisibleHint()) {
            return;
        }

        pickNewLetter();
    }

    @Override
    public void onClick(View view) {
        if (!(view instanceof Button)) {
            return;
        }

        Button button = (Button)view;
        if (TextUtils.equals(button.getText(), challenge.answer)) {
            // Praise the user and pick a new letter
            String praise = PRAISE[new Random().nextInt(PRAISE.length)];
            speak("\"" + challenge.answer + "\", " + praise + "!", true);
            pickNewLetter();
        } else {
            // Prompt the user to try again
            speak("\"" + button.getText() + "\" var fel, försök hitta \"" + challenge.answer + "\"!", true);
        }
    }
}
