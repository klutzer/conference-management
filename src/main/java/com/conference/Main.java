package com.conference;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.conference.business.Schedule;
import com.conference.business.Talk;
import com.conference.business.Track;
import com.conference.reader.TalkReader;
import com.conference.util.IntegerCombinations;

public class Main {

	private static final int MAX_MINUTES_IN_ONE_DAY = 180 + 240;

	public static void main(String[] args) throws IOException {
		new Main(TalkReader.readFromInputStream(Talk.class.getClassLoader().getResourceAsStream("input5.txt")));
	}

	private List<Talk> talks;
	private boolean found = false;
	private List<List<Integer>> result = new ArrayList<>();

	public Main(List<Talk> talks) {
		this.talks = talks;
		Collections.sort(talks);
		talks.stream().forEach(System.out::println);
		System.out.println("Total tracks: " + calculateTotalTracks());
		System.out.println("Total minutes: " + calculateTotalMinutes());
		System.out.println("");
		List<Integer> allDurations = getDurations();
		List<List<Integer>> combinations = new ArrayList<>();
		// arrangeAll();
		arrangeAll(allDurations.toArray(new Integer[0]), combinations, calculateTotalTracks());
		for (int i = 0; i < result.size(); i = i + 2) {
			System.out.println("Track " + i / 2 + ": Morning: " + result.get(i) + " - Afternoon: " + result.get(i + 1));
		}
	}

	private Talk retrieveTalkWithDuration(int duration) {
		return talks.stream().filter(t -> t.getDurationInMinutes() == duration).findFirst().orElse(null);
	}

	private Long calculateTotalMinutes() {
		return talks.stream().collect(Collectors.summarizingInt(Talk::getDurationInMinutes)).getSum();
	}

	private List<Integer> getDurations() {
		return talks.stream().map(Talk::getDurationInMinutes).collect(Collectors.toList());
	}

	private int calculateTotalTracks() {
		double days = calculateTotalMinutes() / (double) MAX_MINUTES_IN_ONE_DAY;
		return (int) Math.ceil(days);
	}

	private void arrangeTrack(Integer[] durations, List<List<Integer>> combinations) {
		List<List<Integer>> combinationsMorning = IntegerCombinations.combinate(durations, 180);
		for (List<Integer> comb : combinationsMorning) {
			List<Integer> durations2 = new ArrayList<>(Arrays.asList(durations));
			removeElements(comb, durations2);
			// durations2.removeAll(comb);
			List<List<Integer>> combinationsAfternoon = IntegerCombinations.combinate(durations2.toArray(new Integer[0]), 180, 240);
			for (List<Integer> comb2 : combinationsAfternoon) {
				combinations.add(comb);
				combinations.add(comb2);
				// if (comb.size() + comb2.size() == durations.size()) {
				// System.out.println("Morning: " + comb + " - Afternoon: " +
				// comb2);
				// return;
				// }
			}
		}
	}

	private void removeElements(List<Integer> source, List<Integer> target) {
		for (Integer a : source) {
			target.remove(a);
		}
	}

	private void arrangeAll(Integer[] durations, List<List<Integer>> combinations, int tracks) {
		arrangeTrack(durations, combinations);
		for (int i = 0; i < combinations.size(); i = i + 2) {
			List<Integer> morning = combinations.get(i);
			List<Integer> afternoon = combinations.get(i + 1);
			List<Integer> remaining = new ArrayList<>(Arrays.asList(durations));
			removeElements(morning, remaining);
			removeElements(afternoon, remaining);
			if (remaining.isEmpty() && tracks == 1) {
				populateTrack(morning, afternoon);
				found = true;
				return;
			} else if (tracks > 1) {
				arrangeAll(remaining.toArray(new Integer[0]), new ArrayList<>(), --tracks);
				if (found) {
					populateTrack(morning, afternoon);
					return;
				}
				++tracks;
			}
		}
	}

	private void populateTrack(List<Integer> morning, List<Integer> afternoon) {
		result.add(morning);
		result.add(afternoon);
	}

}
