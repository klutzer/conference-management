package com.conference.util;

import java.util.ArrayList;
import java.util.List;

public final class IntegerCombinations {

	public static List<List<Integer>> combinate(List<Integer> candidates, int min, int max) {
		List<List<Integer>> result = new ArrayList<List<Integer>>();
		List<Integer> list = new ArrayList<Integer>();
		backTrack(result, list, candidates, min, max, 0);
		return result;
	}
	
	public static List<List<Integer>> combinate(List<Integer> candidates, int target) {
		return combinate(candidates, target, target);
	}

	private static void backTrack(List<List<Integer>> result, List<Integer> list, List<Integer> candidates, int min, int max, int position) {
		int sum = 0;
		for (int x : list) {
			sum += x;
		}

		if (sum <= max && sum >= min) {
			result.add(new ArrayList<Integer>(list));
			return;
		}

		if (sum < min) {
			for (int i = position; i < candidates.size(); i++) {
				if (position != i && candidates.get(i) == candidates.get(i - 1)) {
					continue;
				}
				list.add(candidates.get(i));
				backTrack(result, list, candidates, min, max, i + 1);
				list.remove(list.size() - 1);
			}
		}
	}

}
