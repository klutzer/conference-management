package com.conference.business;

import java.time.LocalTime;

public class Schedule implements Comparable<Schedule> {

	private Talk talk;
	private LocalTime beginTime;

	public Schedule(Talk talk, LocalTime beginTime) {
		this.talk = talk;
		this.beginTime = beginTime;
	}

	public LocalTime getBeginTime() {
		return this.beginTime;
	}

	public void setBeginTime(LocalTime beginTime) {
		this.beginTime = beginTime;
	}

	public Talk getTalk() {
		return this.talk;
	}

	public void setTalk(Talk talk) {
		this.talk = talk;
	}

	@Override
	public String toString() {
		return beginTime + " " + talk;
	}

	@Override
	public int compareTo(Schedule o) {
		return o == null ? -1 : beginTime.compareTo(o.beginTime);
	}

}
