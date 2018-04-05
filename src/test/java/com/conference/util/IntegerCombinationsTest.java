package com.conference.util;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class IntegerCombinationsTest {

	@Test
	public void testWithoutAnyCombination() {
		List<List<Integer>> combinations = IntegerCombinations.combinate(Arrays.asList(2, 2, 3, 4), 10);
		assertThat(combinations, hasSize(0));
	}

	@Test
	public void testWithOnePossibleCombination() {
		List<List<Integer>> combinations = IntegerCombinations.combinate(Arrays.asList(1, 2, 3), 4);
		assertThat(combinations, hasSize(1));
		assertThat(combinations.get(0), contains(1, 3));
	}

	@Test
	public void testWithMultipleCombinations() {
		List<List<Integer>> combinations = IntegerCombinations.combinate(Arrays.asList(1, 2, 3), 3);
		assertThat(combinations, hasSize(2));
		assertThat(combinations.get(0), contains(1, 2));
		assertThat(combinations.get(1), contains(3));
	}

}
