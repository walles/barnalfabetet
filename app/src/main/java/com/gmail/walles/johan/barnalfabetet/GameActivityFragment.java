package com.gmail.walles.johan.barnalfabetet;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A placeholder fragment containing a simple view.
 */
public class GameActivityFragment extends Fragment {

    public GameActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game, container, false);
    }

    // FIXME: When this view becomes visible, select a new letter and speak its phrase

    // FIXME: If the user presses the correct button, praise them and pick a new letter

    // FIXME: If the user presses the wrong button, prompt the user to try again
}
