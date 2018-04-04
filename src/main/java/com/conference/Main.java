package com.conference;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

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
	}
	
	private Talk retrieveTalkWithDuration(int duration) {
		return talks.stream()
				.filter(t -> t.getDurationInMinutes() == duration)
				.findFirst().orElse(null);
	}
}
