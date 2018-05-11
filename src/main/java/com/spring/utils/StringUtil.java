package com.spring.utils;

public class StringUtil {
	public static boolean isEmpty(String str) {
		if(str == null || str.isEmpty())
			return true;
		return false;
	}
}
