package com.bancow.bancowback.common.util;

import org.springframework.security.crypto.bcrypt.BCrypt;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PasswordUtils {

	public static String encryptedPassword(String password) {
		return BCrypt.hashpw(password, BCrypt.gensalt());
	}

	// 암호화한 패스워드 리턴
	public static boolean equalPassword(String password, String encryptedPassword) {
		try {
			return BCrypt.checkpw(password, encryptedPassword);
		} catch (IllegalArgumentException e) {
			return false;
		}
	}
}
