package com.conference.organizer;

import static org.exparity.hamcrest.date.LocalTimeMatchers.sameOrAfter;
import static org.exparity.hamcrest.date.LocalTimeMatchers.sameOrBefore;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

import org.junit.Test;

import com.conference.business.Schedule;
import com.conference.business.Talk;
import com.conference.business.Track;
import com.conference.reader.TalkReader;

public class TalkOrganizerTest {

	@Test
	public void shouldOrganizeOneTrack() throws Exception {
		TalkOrganizer organizer = createOrganizer("input1Track.txt");
		List<Track> tracks = organizer.arrangeAll();
		assertThat(tracks.get(0).getSchedules(), hasSize(11));
		assertCorrectTraks(tracks, 1);
	}

	@Test
	public void shouldOrganizeTwoTracks() throws Exception {
		TalkOrganizer organizer = createOrganizer("input2Tracks.txt");
		List<Track> tracks = organizer.arrangeAll();
		assertCorrectTraks(tracks, 2);
	}

	@Test
	public void shouldOrganizeThreeTracks() throws Exception {
		TalkOrganizer organizer = createOrganizer("input3Tracks.txt");
		List<Track> tracks = organizer.arrangeAll();
		assertCorrectTraks(tracks, 3);
	}

	@Test
	public void shouldOrganizeSixTracks() throws Exception {
		TalkOrganizer organizer = createOrganizer("input6Tracks.txt");
		List<Track> tracks = organizer.arrangeAll();
		assertCorrectTraks(tracks, 6);
	}

	private TalkOrganizer createOrganizer(String input) throws IOException {
		List<Talk> talks = TalkReader.readFromString(input);
		return new TalkOrganizer(talks);
	}

	private void assertCorrectTraks(List<Track> tracks, int expectedSize) {
		assertThat(tracks, hasSize(expectedSize));		
		for (Track track : tracks) {
			Schedule lunch = getLunch(track);
			assertThat(lunch, notNullValue());
			assertThat(lunch.getBeginTime(), equalTo(LocalTime.of(12, 0)));
			Schedule networking = getNetworking(track);
			assertThat(networking, notNullValue());
			assertThat(networking.getBeginTime(), sameOrAfter(LocalTime.of(16, 0)));
			assertThat(networking.getBeginTime(), sameOrBefore(LocalTime.of(17, 0)));
		}
	}

	private Schedule getLunch(Track track) {
		for (Schedule schedule : track.getSchedules()) {
			if (schedule.getTalk().getDescription().equals("Lunch")) {
				return schedule;
			}
		}
		return null;
	}

	private Schedule getNetworking(Track track) {
		Schedule schedule = track.getSchedules().get(track.getSchedules().size() - 1);
		if (schedule.getTalk().getDescription().equals("Networking event")) {
			return schedule;
		}
		return null;
	}

}
