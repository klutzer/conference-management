package com.conference.business;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Track {

	private static final LocalTime TRACK_STARTS = LocalTime.of(9, 0);
	private static final LocalTime LUNCH_STARTS = LocalTime.of(12, 0);
	private static final LocalTime MIN_TIME_FOR_NETWORKING = LocalTime.of(16, 0);
	private static final LocalTime MAX_TIME_FOR_NETWORKING = LocalTime.of(17, 0);

	private List<Schedule> schedules;
	private Schedule lastAdded;

	public void scheduleNext(Talk talk) throws TimeNotAllowedException {
		if (schedules == null) {
			schedules = new ArrayList<>();
			schedules.add(lastAdded = new Schedule(talk, TRACK_STARTS));
			return;
		}
		lastAdded = new Schedule(talk, calculateNextBeginTime());
		schedules.add(lastAdded);
	}

	public void addNetworkingEvent() throws TimeNotAllowedException {
		LocalTime time = calculateNextBeginTime();
		if (time.isBefore(MIN_TIME_FOR_NETWORKING) || time.isAfter(MAX_TIME_FOR_NETWORKING)) {
			throw new TimeNotAllowedException("Networking event must start between 4:00PM and 5:00PM");
		}
		schedules.add(lastAdded = new Schedule(new Talk("Networking event", 0), time));
	}

	public void addLunchSchedule() throws TimeNotAllowedException {
		LocalTime time = calculateNextBeginTime();
		if (!time.equals(LUNCH_STARTS)) {
			throw new TimeNotAllowedException("Lunch time should be exactly at 12:00PM");
		}
		schedules.add(lastAdded = new Schedule(new Talk("Lunch", 60), time));
	}

	private LocalTime calculateNextBeginTime() throws TimeNotAllowedException {
		if (lastAdded == null) {
			throw new TimeNotAllowedException("Track is not initialized yet. Please add the first schedule");
		}
		return lastAdded.getBeginTime().plusMinutes(lastAdded.getTalk().getDurationInMinutes());
	}

	public List<Schedule> getSchedules() {
		return Collections.unmodifiableList(schedules);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (Schedule schedule : schedules) {
			builder.append(schedule.toString());
			builder.append('\n');
		}
		return builder.toString();
	}

}
