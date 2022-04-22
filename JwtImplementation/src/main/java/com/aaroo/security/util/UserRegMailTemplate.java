package com.aaroo.security.util;

public class UserRegMailTemplate {

	public static String userregtemplate(String clientname, String clientMail, String clientPass, String ClientDomain) {

		String html = "<h3 font-weight: bold;> Dear " + clientname + "</h3>" + "\r\n"
				+ "Welcome to Ergodemy Learning Management System" + "\r\n" + "<br>"
				+ "You have been successfully registered and your login credentials are as follows." + "\r\n" + "<br>"
				+ "Username: " + clientMail + " " + "\r\n" + "<br>" + "Password: " + clientPass + "" + "\r\n" + "<br>"
				+ "Login URL: " + ClientDomain + "<br>" + "\r\n" + "<br>" + "If you need further help please contact"
				+ "\r\n" + "<br>" + "Admin@argodemy.com" + "\r\n" + "<br>" + "Thanks and regards" + "\r\n" + "<br>"
				+ " Admin Ergodemy";
		return html;

	}
}
