package com.conference;

import java.io.IOException;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.conference.business.Talk;

public class Main {

	public static void main(String[] args) throws IOException {
		new Main(Talk.loadAll());
	}

	private List<Talk> talks;
	
	public Main(List<Talk> talks) {
		this.talks = talks;
		Collections.sort(talks);
		talks.stream().forEach(System.out::println);
		System.out.println("Total minutes: " + calculateTracks());
	}
	
	private Talk retrieveTalkWithDuration(int duration) {
		return talks.stream()
				.filter(t -> t.getDurationInMinutes() <= duration)
				.findFirst().orElse(null);
	}
	
	private Long calculateTracks() {
	    return talks.stream()
	            .collect(Collectors.summarizingInt(Talk::getDurationInMinutes))
	            .getSum();
	}
	
	private boolean validateTrackArrangement() {
	    
	}
}
