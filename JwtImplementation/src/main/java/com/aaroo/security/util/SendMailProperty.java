package com.aaroo.security.util;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

public class SendMailProperty extends Authenticator {

	public static Session mailProperty() {

		final String username = ConstantAction.FROM_EMAIL_ID;
		final String password = ConstantAction.FROM_PASSWORD;
		Properties props = new Properties();
		props.put(ConstantAction.PROP_SMTP_HOST, ConstantAction.SMTP_HOST);
		props.put(ConstantAction.PROP_SMTP_AUTH, ConstantAction.SMTP_AUTH);
		props.put(ConstantAction.PROP_SMTP_PORT, ConstantAction.SMTP_PORT);
		props.put(ConstantAction.PROP_SMTP_SOCKET_FACTORY, ConstantAction.SMTP_SOCKET_FACTORY);
		// props.put("mail.smtp.socketFactory.fallback", "false");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.socketFactory.fallback", "true"); // Should be true
		props.put("mail.user", username);
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {

			protected PasswordAuthentication getPasswordAuthentication() {

				return new PasswordAuthentication(username, password);
			}

		});

		return session;
	}
}
