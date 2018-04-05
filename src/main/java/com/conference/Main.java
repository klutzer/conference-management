package com.conference;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
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
		new Main(TalkReader.readFromInputStream(Talk.class.getClassLoader().getResourceAsStream("input2.txt")));
	}

	private List<Talk> talks;
	
	public Main(List<Talk> talks) {
		this.talks = talks;
		Collections.sort(talks);
		talks.stream().forEach(System.out::println);
		System.out.println("Total tracks: " + calculateTotalTracks());
		System.out.println("");
		arrangeAll();
	}
	
	private Talk retrieveTalkWithDuration(int duration) {
		return talks.stream()
				.filter(t -> t.getDurationInMinutes() == duration)
				.findFirst().orElse(null);
	}
	
	private Long calculateTotalMinutes() {
	    return talks.stream()
	            .collect(Collectors.summarizingInt(Talk::getDurationInMinutes))
	            .getSum();
	}
	
	private List<Integer> getDurations() {
		return talks.stream()
				.map(Talk::getDurationInMinutes)
				.collect(Collectors.toList());
	}
	
	private int calculateTotalTracks() {
		double days = calculateTotalMinutes() / (double) MAX_MINUTES_IN_ONE_DAY;
		return (int) Math.ceil(days);
	}
	
	private void arrangeTrack(List<Integer> durations) {
		List<List<Integer>> combinationsMorning = IntegerCombinations.combinate(durations, 180);
		for (List<Integer> comb : combinationsMorning) {
			List<Integer> durations2 = new ArrayList<>(durations);
			removeElements(comb, durations2);
			//durations2.removeAll(comb);
			List<List<Integer>> combinationsAfternoon = IntegerCombinations.combinate(durations2, 180, 240);
			for (List<Integer> comb2 : combinationsAfternoon) {
				if (comb.size() + comb2.size() == durations.size()) {
					System.out.println("Morning: " + comb + " - Afternoon: " + comb2);
					//return;
				}
			}
		}
	}
	
	private void removeElements(List<Integer> source, List<Integer> target) {
		for (Integer a : source) {
			target.remove(a);
		}
	}
	
	private void arrangeAll() {
		List<Integer> allDurations = getDurations();
		arrangeTrack(allDurations);
	}
	
}
