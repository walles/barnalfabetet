package com.gmail.walles.johan.barnalfabetet;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class WordListDownloaderTest {
    @Test
    public void testDownloadWordList() throws Exception {
        File target = File.createTempFile("testDownloadWordList", ".txt");
        target.deleteOnExit();
        try {
            WordListDownloader.downloadWordList(target);
            Assert.assertThat(target.getAbsolutePath(), 0L, not(is(target.length())));
        } finally {
            if (!target.delete()) {
                System.out.println("Unable to delete test result temp file: " + target.getAbsolutePath());
            }
        }
    }

    @Test
    public void testDownloadWordListBadDestination() {
        File unwritable = new File("/");
        try {
            WordListDownloader.downloadWordList(unwritable);
            Assert.fail();
        } catch (IOException e) {
            // Expected exception intentionally ignored
        }
    }
}
