package com.easy.common.core.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

public class StringMatchUtils {
	private static PathMatcher pathMatcher = new AntPathMatcher();

	private static Map<String, Pattern> patternCache = Collections
			.synchronizedMap(new HashMap<String, Pattern>());

	public static boolean stringMatch(String source, String[] pattern) {
		if (pattern.length == 1) {
			return stringMatch(source, pattern[0]);
		}
		boolean b = false;
		for (String s : pattern) {
			b = stringMatch(source, s);
			if (b) {
				return true;
			}
		}

		return false;
	}

	public static boolean stringMatch(String source, String pattern) {
		if (pathMatcher.match(pattern, source))
			return true;
		try {
			Pattern sp = null;
			if (patternCache.containsKey(pattern)) {
				sp = (Pattern) patternCache.get(pattern);
			} else {
				sp = Pattern.compile(pattern);
				patternCache.put(pattern, sp);
			}
			Matcher matcher = sp.matcher(source);
			if (matcher.find())
				return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}