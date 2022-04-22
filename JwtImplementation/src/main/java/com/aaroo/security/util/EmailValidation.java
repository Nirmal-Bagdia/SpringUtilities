package com.aaroo.security.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidation {
	public boolean isValid(String email) {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
		Pattern pat = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE);
		if (email == null)
			return false;
		return pat.matcher(email).matches();
	}

	public boolean specialCharacterCheck(String userName) {
		Pattern pattern = Pattern.compile("[^A-Za-z0-9]");
		Matcher match = pattern.matcher(userName);
		return match.find();
	}
}
