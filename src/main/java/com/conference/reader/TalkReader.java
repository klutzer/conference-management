package com.conference.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.conference.business.Talk;

public class TalkReader {

    public static List<Talk> readFromInputStream(InputStream input) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
            List<Talk> talks = new ArrayList<>();
            reader.lines().forEach(line -> talks.add(readSingleTalk(line)));
            return talks;
        }
    }

    private static Talk readSingleTalk(String line) {
        Talk talk = new Talk();
        talk.setDurationInMinutes(extractDuration(line));
        talk.setDescription(line);
        return talk;
    }

    private static int extractDuration(String line) {
        if (line.endsWith("lightning")) {
            return 5;
        }
        return Integer.parseInt(line.replaceAll("\\D", ""));
    }

}
