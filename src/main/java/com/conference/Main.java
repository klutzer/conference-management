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
		new Main(TalkReader.readFromInputStream(Talk.class.getClassLoader().getResourceAsStream("input3.txt")));
	}

	private List<Talk> talks;
	private int talkSize;
	
	public Main(List<Talk> talks) {
		this.talks = talks;
		talkSize = talks.size();
		Collections.sort(talks);
		talks.stream().forEach(System.out::println);
		System.out.println("Total tracks: " + calculateTotalTracks());
		System.out.println("");
		List<Integer> allDurations = getDurations();
        List<List<Integer>> combinations = new ArrayList<>();
        //arrangeAll();
		arrangeAll(allDurations, combinations, new ArrayList<>(), calculateTotalTracks(), 0);
		for (int i=0; i<c.size(); i=i+2) {
            System.out.println("Track " + i/2 + ": Morning: " + c.get(i) + " - Afternoon: " + c.get(i+1));
        }
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
	
	private void arrangeTrack(List<Integer> durations, List<List<Integer>> combinations) {
		List<List<Integer>> combinationsMorning = IntegerCombinations.combinate(durations, 180);
		for (List<Integer> comb : combinationsMorning) {
			List<Integer> durations2 = new ArrayList<>(durations);
			removeElements(comb, durations2);
			//durations2.removeAll(comb);
			List<List<Integer>> combinationsAfternoon = IntegerCombinations.combinate(durations2, 180, 240);
			for (List<Integer> comb2 : combinationsAfternoon) {
			    combinations.add(comb);
			    combinations.add(comb2);
				//if (comb.size() + comb2.size() == durations.size()) {
					//System.out.println("Morning: " + comb + " - Afternoon: " + comb2);
					//return;
				//}
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
		List<List<Integer>> combinations = new ArrayList<>();
		arrangeTrack(allDurations, combinations);
		System.out.println("Total de " + combinations.size() + " combinações");
		for (int i=0; i<combinations.size(); i=i+2) {
		    List<Integer> morning = combinations.get(i);
		    List<Integer> afternoon = combinations.get(i+1);
		    //System.out.println("Track: Morning:   " + morning);
            //System.out.println("Track: Afternoon: " + afternoon);
		    List<Integer> restantes = new ArrayList<>(allDurations);
		    removeElements(morning, restantes);
		    removeElements(afternoon, restantes);
		    //System.out.println("Restantes: " + restantes);
		    List<List<Integer>> combinations2 = new ArrayList<>();
		    arrangeTrack(restantes, combinations2);
		    
		    for (int j=0; j<combinations2.size(); j=j+2) {
		        List<Integer> morning2 = combinations2.get(j);
	            List<Integer> afternoon2 = combinations2.get(j+1);
	            
	            if (morning.size() + afternoon.size() + morning2.size() + afternoon2.size() == allDurations.size()) {
	                System.out.println("Track 1: Morning " + morning + " - Afternoon: " + afternoon + "    -   " + "Track 2: Morning " + morning2 + " - Afternoon: " + afternoon2);
	            }
		    }
		    
		}
	}
	
	private void arrangeAll(List<Integer> durations, List<List<Integer>> combinations, List<List<Integer>> savedCombinations, int tracks, int totalCount) {
        arrangeTrack(durations, combinations);
        for (int i=0; !found && i<combinations.size(); i=i+2) {
            List<Integer> morning = combinations.get(i);
            List<Integer> afternoon = combinations.get(i+1);
            //System.out.println("Track: Morning:   " + morning);
            //System.out.println("Track: Afternoon: " + afternoon);
            List<Integer> restantes = new ArrayList<>(durations);
            removeElements(morning, restantes);
            removeElements(afternoon, restantes);
            savedCombinations.add(morning);
            savedCombinations.add(afternoon);
            if (restantes.isEmpty() && tracks == 1) {
//            if (totalCount + morning.size() + afternoon.size() == talkSize && tracks == 1) {
                this.c = savedCombinations;
                found = true;
                return;
            } else {
                List<List<Integer>> combinations2 = new ArrayList<>();
                totalCount += morning.size() + afternoon.size();
                arrangeAll(restantes, combinations2, savedCombinations, --tracks, totalCount);
                if (found) {
                    break;
                }
                savedCombinations.remove(morning);
                savedCombinations.remove(afternoon);
            }
        }
    }
	
	private boolean found = false;
	private List<List<Integer>> c = new ArrayList<>();

	private boolean reachAllDurations(List<List<Integer>> combinations, int totalTalks) {
	    int count = 0;
	    for (List<Integer> combination : combinations) {
	        count += combination.size();
	    }
	    return totalTalks == count;
	}
	
}
