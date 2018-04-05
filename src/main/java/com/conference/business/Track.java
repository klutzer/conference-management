package com.conference.business;

import java.util.ArrayList;
import java.util.List;

/**
 * @author erico.lutzer
 *
 */
public class Track {

    private List<Schedule> schedules;

	public List<Schedule> getSchedules() {
		return schedules;
	}

	public void addSchedule(Schedule schedule) {
		if (schedules == null) {
			schedules = new ArrayList<>();
		}
		schedules.add(schedule);
	}
    
}
