package com.conference;

import java.io.IOException;
import java.util.List;

import com.conference.business.Talk;
import com.conference.business.Track;
import com.conference.organizer.TalkOrganizer;
import com.conference.reader.TalkReader;

public final class Main {

	public static void main(String[] args) throws IOException {
		System.out.print("Reading talks... ");
		List<Talk> talks = TalkReader.readFromString("input.txt");
		System.out.println("Done!");
		TalkOrganizer organizer = new TalkOrganizer(talks);
		System.out.println("Total minutes: " + organizer.calculateTotalMinutes());
		System.out.print("Arranging... ");
		List<Track> tracks = organizer.arrangeAll();
		System.out.print("Done!\n\n");
		showResults(tracks);
	}

	private static void showResults(List<Track> tracks) {
		if (tracks.isEmpty()) {
			System.out.println("There's no possibilities!");
		}
		for (int i = 0; i < tracks.size(); i++) {
			System.out.println("Track " + (i + 1) + ":\n" + tracks.get(i));
		}
	}

}
