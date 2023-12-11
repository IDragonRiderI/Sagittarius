package de.spacepotato.sagittarius.util;

import java.util.regex.Pattern;

public class UUIDUtil {

	private static final Pattern PATTERN = Pattern.compile("(.{8})(.{4})(.{4})(.{4})(.{12})");
	
	public static String addDashes(String uuid) {
		return PATTERN.matcher(uuid).replaceAll("$1-$2-$3-$4-$5");
	}
	
}
