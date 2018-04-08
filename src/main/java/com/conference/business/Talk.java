package com.conference.business;

public class Talk implements Comparable<Talk> {

	private String description;
	private int durationInMinutes;

	public Talk(String description, int durationInMinutes) {
		this.description = description;
		this.durationInMinutes = durationInMinutes;
	}

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
