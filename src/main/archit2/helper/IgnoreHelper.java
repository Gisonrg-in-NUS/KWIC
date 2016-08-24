package main.archit2.helper;

import java.util.Set;

public class IgnoreHelper {
	private static Set<String> listOfIgnore;

	public static void init(Set<String> list) {
		listOfIgnore = list;
	}

	public static boolean ifIgnore(String str) {
		return listOfIgnore.contains(str.toLowerCase());
	}
}
