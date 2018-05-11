package com.spring.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtil {

	private static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

	public static String getPasswordHash(String rawPassword) {
		String hash = null;
		hash = encoder.encode(rawPassword);
		return hash;
	}

	public static boolean passwordCheck(String rawPassword, String hash) {
		return encoder.matches(rawPassword, hash);
	}
}