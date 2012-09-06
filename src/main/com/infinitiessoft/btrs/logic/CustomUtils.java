package com.infinitiessoft.btrs.logic;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class CustomUtils {
	
	public static <K1, K2, V> Map<K1, Map<K2, V>> deepCopy(Map<K1, Map<K2, V>> original) {

		Map<K1, Map<K2, V>> copy = new HashMap<K1, Map<K2, V>>();
		for (Entry<K1, Map<K2, V>> entry : original.entrySet()) {
			copy.put(entry.getKey(), new HashMap<K2, V>(entry.getValue()));
		}
		return copy;
	}
	
	public static Integer safeDifference(Integer one, Integer two) {
		if (one == null && two != null) {
			return two;
		} else if (one != null && two == null) {
			return one;
		} else {
			return Math.abs(one - two);
		}
	}
	
	public static Integer safeSum(Integer one, Integer two) {
		if (one == null && two != null) {
			return two;
		} else if (one != null && two == null) {
			return one;
		} else {
			return one + two;
		}
	}
	
	public static String capitalizeFirst(String s) {
		return s.substring(0,1).toUpperCase() + s.substring(1);
	}
	
}
