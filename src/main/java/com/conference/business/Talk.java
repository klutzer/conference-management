package com.conference.business;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.conference.reader.TalkReader;

public class Talk {

    private String description;
    private int durationInMinutes;

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDurationInMinutes() {
        return this.durationInMinutes;
    }

    public void setDurationInMinutes(int durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }

    public static List<Talk> loadAll() throws IOException {
        return TalkReader.readFromInputStream(Talk.class.getClassLoader().getResourceAsStream("input.txt"));
    }
}
