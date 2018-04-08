package com.conference.business;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.time.LocalTime;

import org.junit.Test;

public class TrackTest {

	@Test
	public void shouldAddFirstTalkOfDay() throws Exception {
		Track track = new Track();
		track.scheduleNext(new Talk("First", 10));
		assertThat(track.getSchedules(), hasSize(1));
		assertThat(track.getSchedules().get(0).getBeginTime(), equalTo(LocalTime.of(9, 0)));
	}

	@Test
	public void shouldAddLunch() throws Exception {
		Track track = new Track();
		track.scheduleNext(new Talk("First", 180));
		track.addLunchSchedule();
		assertThat(track.getSchedules(), hasSize(2));
		assertThat(track.getSchedules().get(0).getBeginTime(), equalTo(LocalTime.of(9, 0)));
		assertThat(track.getSchedules().get(1).getBeginTime(), equalTo(LocalTime.of(12, 0)));
	}

	@Test(expected = TimeNotAllowedException.class)
	public void shouldNotAddLunch() throws Exception {
		Track track = new Track();
		track.scheduleNext(new Talk("First", 160));
		track.addLunchSchedule();
	}

	@Test(expected = TimeNotAllowedException.class)
	public void shouldNotAddLunchOnEmptyTrack() throws Exception {
		Track track = new Track();
		track.addLunchSchedule();
	}

	@Test
	public void shouldAddNetworkingEventAt4PM() throws Exception {
		Track track = new Track();
		track.scheduleNext(new Talk("First", 180));
		track.addLunchSchedule();
		track.scheduleNext(new Talk("Post lunch", 180));
		track.addNetworkingEvent();
		assertThat(track.getSchedules(), hasSize(4));
		assertThat(track.getSchedules().get(3).getBeginTime(), equalTo(LocalTime.of(16, 0)));
	}

	@Test
	public void shouldAddNetworkingEventAt4_30PM() throws Exception {
		Track track = new Track();
		track.scheduleNext(new Talk("First", 180));
		track.addLunchSchedule();
		track.scheduleNext(new Talk("Post lunch", 210));
		track.addNetworkingEvent();
		assertThat(track.getSchedules(), hasSize(4));
		assertThat(track.getSchedules().get(3).getBeginTime(), equalTo(LocalTime.of(16, 30)));
	}

}
