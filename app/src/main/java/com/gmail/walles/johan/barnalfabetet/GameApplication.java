package com.gmail.walles.johan.barnalfabetet;

import android.app.Application;

import timber.log.Timber;

public class GameApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());
    }
}
