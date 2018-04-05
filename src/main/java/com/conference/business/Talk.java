package com.conference.business;

import java.io.IOException;
import java.util.List;

import com.conference.reader.TalkReader;

public class Talk implements Comparable<Talk> {

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

	@Override
	public int compareTo(Talk o) {
		if (o == null) {
			return -1;
		}
		return Integer.compare(durationInMinutes, o.durationInMinutes);
	}

	@Override
	public String toString() {
		return description;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Talk) {
			return description.equals(((Talk) obj).description);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return description.hashCode();
	}
}
