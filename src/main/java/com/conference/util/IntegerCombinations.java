package com.conference.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class IntegerCombinations {

	public static List<List<Integer>> combinate(Integer[] candidates, int min, int max, boolean usingAllCandidates) {
		List<List<Integer>> result = new ArrayList<List<Integer>>();
		if (usingAllCandidates) {
			combineAllCandidates(candidates, result, min, max);
			return result;
		}
		List<Integer> list = new ArrayList<Integer>();
		Arrays.sort(candidates);
		backTrack(result, list, candidates, min, max, 0);
		return result;
	}

	public static List<List<Integer>> combinate(Integer[] candidates, int min, int max) {
		return combinate(candidates, min, max, false);
	}

	public static List<List<Integer>> combinate(Integer[] candidates, int target) {
		return combinate(candidates, target, target, false);
	}

	private static void combineAllCandidates(Integer[] candidates, List<List<Integer>> result, int min, int max) {
		List<Integer> list = new ArrayList<Integer>(Arrays.asList(candidates));
		int sum = sumAll(list);
		if (sum <= max && sum >= min) {
			result.add(list);
		}
	}

	private static void backTrack(List<List<Integer>> result, List<Integer> list, Integer[] candidates, int min, int max, int position) {
		int sum = sumAll(list);
		if (sum <= max && sum >= min) {
			result.add(new ArrayList<Integer>(list));
			return;
		}
		if (sum < min) {
			for (int i = position; i < candidates.length; i++) {
				if (position != i && candidates[i] == candidates[i - 1]) {
					continue;
				}
				list.add(candidates[i]);
				backTrack(result, list, candidates, min, max, i + 1);
				list.remove(list.size() - 1);
			}
		}
	}

	private static int sumAll(List<Integer> list) {
		int sum = 0;
		for (int x : list) {
			sum += x;
		}
		return sum;
	}

}
