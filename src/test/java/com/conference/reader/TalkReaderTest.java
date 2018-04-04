package com.conference.reader;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.List;

import org.junit.Test;

import com.conference.business.Talk;

public class TalkReaderTest {

	@Test
	public void shouldReadAllTalks() throws Exception {
		List<Talk> talks = Talk.loadAll();
		assertThat(talks, hasSize(19));
	}

	@Test
	public void shouldExtractMinutesFromLine() {
		String description = "JShell improvements 45min";
		Talk talk = TalkReader.readSingleTalk(description);
		assertThat(talk.getDescription(), equalTo(description));
		assertThat(talk.getDurationInMinutes(), equalTo(45));
	}

	@Test
	public void shouldExtractMinutesFromLineWithLightning() {
		String description = "Android powered by Kotlin lightning";
		Talk talk = TalkReader.readSingleTalk(description);
		assertThat(talk.getDescription(), equalTo(description));
		assertThat(talk.getDurationInMinutes(), equalTo(5));
	}

}
