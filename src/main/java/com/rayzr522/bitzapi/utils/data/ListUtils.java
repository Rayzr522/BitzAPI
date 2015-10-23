package com.rayzr522.bitzapi.utils.data;

import java.util.ArrayList;
import java.util.List;

import com.rayzr522.bitzapi.utils.commands.TextUtils;

public class ListUtils {

	public static List<String> colorList(List<String> list) {
		if (list == null || isEmpty(list)) {
			return list;
		}
		List<String> coloredList = new ArrayList<String>();
		for (String string : list) {
			coloredList.add(TextUtils.color(string));
		}
		return coloredList;
	}

	public static List<String> reverseColorList(List<String> list) {
		if (list == null || isEmpty(list)) {
			return list;
		}
		List<String> coloredList = new ArrayList<String>();
		for (String string : list) {
			coloredList.add(TextUtils.reverseColor(string));
		}
		return coloredList;
	}

	public static boolean isEmpty(List<?> list) {

		return list.size() < 1;

	}

	public static <T> List<T> empty() {
		return new ArrayList<T>();
	}

}
