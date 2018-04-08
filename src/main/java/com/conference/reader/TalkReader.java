package com.conference.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.conference.business.Talk;

public class TalkReader {

	public static List<Talk> readFromString(String input) throws IOException {
		return readFromInputStream(TalkReader.class.getClassLoader().getResourceAsStream(input));
	}

	public static List<Talk> readFromInputStream(InputStream input) throws IOException {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
			List<Talk> talks = new ArrayList<>();
			reader.lines().forEach(line -> talks.add(readSingleTalk(line)));
			return talks;
		}
	}

	static Talk readSingleTalk(String line) {
		return new Talk(line, extractDuration(line));
	}

	private static int extractDuration(String line) {
		if (line.endsWith("lightning")) {
			return 5;
		}
		return Integer.parseInt(line.replaceAll("\\D", ""));
	}

}
