package com.conference.reader;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.List;

import org.junit.Test;

import com.conference.business.Talk;

public class TalkReaderTest {

    @Test
    public void test() throws Exception {
        List<Talk> talks = Talk.loadAll();
        assertThat(talks, hasSize(19));
    }

}
