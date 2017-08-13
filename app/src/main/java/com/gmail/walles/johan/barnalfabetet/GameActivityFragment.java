package com.gmail.walles.johan.barnalfabetet;

import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import timber.log.Timber;

/**
 * A placeholder fragment containing a simple view.
 */
public class GameActivityFragment extends Fragment {
    @Nullable
    private TextToSpeech textToSpeech;
    private int ttsStatus = TextToSpeech.ERROR;

    private char letter;

    public GameActivityFragment() {
    }

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
                            Timber.e("Error setting up TTS: status={}", i);
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
                Timber.e("Speech failed: id=<{}>", s);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game, container, false);
    }

    private void speak(String phrase, boolean flush) {
        if (textToSpeech == null) {
            Timber.e("Text to speech not initialized");
            return;
        }

        if (ttsStatus != TextToSpeech.SUCCESS) {
            Timber.e("Text to speech status unsuccessful: {}", ttsStatus);
            return;
        }

        int queueMode = flush ? TextToSpeech.QUEUE_FLUSH : TextToSpeech.QUEUE_ADD;
        int status = textToSpeech.speak(phrase, queueMode, null);
        if (status == TextToSpeech.SUCCESS) {
            Timber.i("Spoken: flush={}, phrase=<{}>", flush, phrase);
        } else {
            Timber.e("Speech failed: flush={}, phrase=<{}>", flush, phrase);
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

        // FIXME: Pick the new letter
        letter = 'A';

        // FIXME: Update the buttons
        // FIXME: Put the correct answer in a random location
        ((Button)getView().findViewById(R.id.answer1)).setText("Z");
        ((Button)getView().findViewById(R.id.answer2)).setText("A");
        ((Button)getView().findViewById(R.id.answer3)).setText("X");

        // FIXME: Speak the new phrase to the user
        speak("A som i apa", false);
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

    // FIXME: If the user presses the correct button, praise them and pick a new letter

    // FIXME: If the user presses the wrong button, prompt the user to try again
}
