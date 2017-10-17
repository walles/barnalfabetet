package com.gmail.walles.johan.barnalfabetet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Locale;

import timber.log.Timber;

public class WordListDownloader {
    private static final int TIMEOUT_MS = 2000;

    public static void downloadWordList(File destination) throws IOException {
        String WORD_LIST_URL =
                "https://raw.githubusercontent.com/walles/barnalfabetet/master/app/src/main/resources/wordlist.txt";

        URL url = new URL(WORD_LIST_URL);

        URLConnection urlConnection = url.openConnection();
        urlConnection.setConnectTimeout(TIMEOUT_MS);
        urlConnection.setReadTimeout(TIMEOUT_MS);

        File inProgress = File.createTempFile("wordListDownload", "inprogress", destination.getParentFile());
        try (InputStream inputStream = urlConnection.getInputStream()) {
            try (OutputStream outputStream = new FileOutputStream(inProgress)) {
                byte[] buffer = new byte[1024 * 8];

                int bytesRead = 0;
                while ((bytesRead = inputStream.read(buffer, 0, buffer.length)) >= 0) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }
        }

        if (!inProgress.renameTo(destination)) {
            if (!inProgress.delete()) {
                Timber.w("Failed to delete {}", inProgress.getAbsolutePath());
            }
            throw new IOException(String.format(Locale.ENGLISH, "Renaming %s into %s failed",
                    inProgress.getAbsolutePath(),
                    destination.getAbsolutePath()));
        }
    }
}
