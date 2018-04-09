package com.conference.util;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.List;

import org.junit.Test;

public class IntegerCombinationsTest {

	@Test
	public void testWithoutAnyCombination() {
		List<List<Integer>> combinations = IntegerCombinations.combinate(new Integer[] { 2, 2, 3, 4 }, 10);
		assertThat(combinations, hasSize(0));
	}

	@Test
	public void testWithOnePossibleCombination() {
		List<List<Integer>> combinations = IntegerCombinations.combinate(new Integer[] { 1, 2, 3 }, 4);
		assertThat(combinations, hasSize(1));
		assertThat(combinations.get(0), contains(1, 3));
	}

	@Test
	public void testWithMultipleCombinations() {
		List<List<Integer>> combinations = IntegerCombinations.combinate(new Integer[] { 1, 2, 3 }, 3);
		assertThat(combinations, hasSize(2));
		assertThat(combinations.get(0), contains(1, 2));
		assertThat(combinations.get(1), contains(3));
	}

	@Test
	public void testWithoutAnyCombinationWithMinAndMax() {
		List<List<Integer>> combinations = IntegerCombinations.combinate(new Integer[] { 2, 3, 10 }, 6, 9);
		assertThat(combinations, hasSize(0));
	}

	@Test
	public void testWithOnePossibleCombinationWithMinAndMax() {
		List<List<Integer>> combinations = IntegerCombinations.combinate(new Integer[] { 2, 3, 10 }, 6, 10);
		assertThat(combinations, hasSize(1));
		assertThat(combinations.get(0), contains(10));
	}

	@Test
	public void testWithMultipleCombinationsWithMinAndMax() {
		List<List<Integer>> combinations = IntegerCombinations.combinate(new Integer[] { 1, 2, 3, 4 }, 3, 4);
		assertThat(combinations, hasSize(4));
		assertThat(combinations.get(0), contains(1, 2));
		assertThat(combinations.get(1), contains(1, 3));
		assertThat(combinations.get(2), contains(3));
		assertThat(combinations.get(3), contains(4));
	}

	@Test
	public void testWithMultipleCombinationsAndRepeated() {
		List<List<Integer>> combinations = IntegerCombinations.combinate(new Integer[] { 1, 3, 3, 3, 4 }, 10, 11);
		assertThat(combinations, hasSize(3));
		assertThat(combinations.get(0), contains(1, 3, 3, 3));
		assertThat(combinations.get(1), contains(1, 3, 3, 4));
		assertThat(combinations.get(2), contains(3, 3, 4));
	}

	@Test
	public void testWithMultipleCombinationsRepeatedWithMinAndMax() {
		List<List<Integer>> combinations = IntegerCombinations.combinate(new Integer[] { 1, 3, 3, 3, 4 }, 1, 11);
		assertThat(combinations, hasSize(13));
		assertThat(combinations.get(0), contains(1));
		assertThat(combinations.get(1), contains(1, 3));
		assertThat(combinations.get(2), contains(1, 3, 3));
		assertThat(combinations.get(3), contains(1, 3, 3, 3));
		assertThat(combinations.get(4), contains(1, 3, 3, 4));
		assertThat(combinations.get(5), contains(1, 3, 4));
		assertThat(combinations.get(6), contains(1, 4));
		assertThat(combinations.get(7), contains(3));
		assertThat(combinations.get(8), contains(3, 3));
		assertThat(combinations.get(9), contains(3, 3, 3));
		assertThat(combinations.get(10), contains(3, 3, 4));
		assertThat(combinations.get(11), contains(3, 4));
		assertThat(combinations.get(12), contains(4));
	}

	@Test
	public void shouldContainsPredefinedCombination() {
		Integer[] ints = new Integer[] {
				60, 45, 30, 45, 45, 5, 60, 45, 30, 30, 45, 60, 60, 45, 30, 30,
				60, 30, 30, 90, 60, 60, 60, 45, 45, 45, 30, 20, 10, 5, 3, 2 };
		List<List<Integer>> combinations = IntegerCombinations.combinate(ints, 180, 240);
		assertThat(containsFixedCombination(combinations), is(true));
	}

	private boolean containsFixedCombination(List<List<Integer>> combinations) {
		for (List<Integer> comb : combinations) {
			if (comb.size() == 8 &&
					comb.get(0) == 2 &&
					comb.get(1) == 3 &&
					comb.get(2) == 10 &&
					comb.get(3) == 30 &&
					comb.get(4) == 30 &&
					comb.get(5) == 45 &&
					comb.get(6) == 60 &&
					comb.get(7) == 60) {
				return true;
			}
		}
		return false;
	}

}
