package com.conference.organizer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.conference.business.Talk;
import com.conference.business.TimeNotAllowedException;
import com.conference.business.Track;
import com.conference.util.IntegerCombinations;

public class TalkOrganizer {

	private static final int MAX_MINUTES_IN_ONE_DAY = 180 + 240;

	private List<Talk> talks;
	private boolean found;
	private List<List<Integer>> result = new ArrayList<>();

	public TalkOrganizer(List<Talk> talks) {
		this.talks = new ArrayList<>(talks);
		Collections.sort(talks);
	}

	public List<Track> arrangeAll() {
		arrangeAll(getAllTalkDurations(), new ArrayList<>(), calculateTotalTracks());
		return convertResultInTracks();
	}

	private List<Track> convertResultInTracks() {
		try {
			List<Track> tracks = new ArrayList<>();
			for (int i = 0; i < result.size(); i = i + 2) {
				Track track = createTrack(result.get(i), result.get(i+1));
				tracks.add(track);
			}
			return tracks;
		} catch (Exception e) {
			throw new RuntimeException("Cannot generate tracks", e);
		}
	}

	private Track createTrack(List<Integer> morningDurations, List<Integer> afternoonDurations) throws TimeNotAllowedException {
		Track track = new Track();
		fillSchedules(morningDurations, track);
		track.addLunchSchedule();
		fillSchedules(afternoonDurations, track);
		track.addNetworkingEvent();
		return track;
	}

	private void fillSchedules(List<Integer> durations, Track track) throws TimeNotAllowedException {
		for (Integer duration : durations) {
			Talk talk = retrieveTalkWithDuration(duration);
			track.scheduleNext(talk);
		}
	}

	private Talk retrieveTalkWithDuration(Integer duration) {
		Talk talk = talks.stream()
				.filter(t -> t.getDurationInMinutes() == duration)
				.findFirst()
				.get();
		talks.remove(talk);
		return talk;
	}

	public int calculateTotalMinutes() {
		return talks.stream()
				.collect(Collectors.summingInt(Talk::getDurationInMinutes))
				.intValue();
	}

	private Integer[] getAllTalkDurations() {
		return talks.stream()
				.map(Talk::getDurationInMinutes)
				.collect(Collectors.toList())
				.toArray(new Integer[0]);
	}

	private int calculateTotalTracks() {
		double days = calculateTotalMinutes() / (double) MAX_MINUTES_IN_ONE_DAY;
		return (int) Math.ceil(days);
	}

	private void arrangeAll(Integer[] durations, List<List<Integer>> combinations, int tracks) {
		arrangeTrack(durations, combinations, tracks == 1);
		for (int i = 0; i < combinations.size(); i = i + 2) {
			List<Integer> morning = combinations.get(i);
			List<Integer> afternoon = combinations.get(i + 1);
			if (tracks == 1) {
				populateTrack(morning, afternoon);
				found = true;
				return;
			} else {
				List<Integer> remaining = new ArrayList<>(Arrays.asList(durations));
				removeElements(morning, remaining);
				removeElements(afternoon, remaining);
				arrangeAll(remaining.toArray(new Integer[0]), new ArrayList<>(), --tracks);
				if (found) {
					populateTrack(morning, afternoon);
					return;
				}
				++tracks;
			}
		}
	}

	private void arrangeTrack(Integer[] durations, List<List<Integer>> combinations, boolean lastTrack) {
		List<List<Integer>> combinationsMorning = IntegerCombinations.combinate(durations, 180);
		for (List<Integer> morning : combinationsMorning) {
			List<Integer> durationsCopy = new ArrayList<>(Arrays.asList(durations));
			removeElements(morning, durationsCopy);
			List<List<Integer>> combinationsAfternoon = IntegerCombinations.combinate(durationsCopy.toArray(new Integer[0]), 180, 240, lastTrack);
			for (List<Integer> afternoon : combinationsAfternoon) {
				combinations.add(morning);
				combinations.add(afternoon);
			}
		}
	}

	private void removeElements(List<Integer> source, List<Integer> target) {
		for (Integer a : source) {
			target.remove(a);
		}
	}

	private void populateTrack(List<Integer> morning, List<Integer> afternoon) {
		result.add(morning);
		result.add(afternoon);
	}

}
