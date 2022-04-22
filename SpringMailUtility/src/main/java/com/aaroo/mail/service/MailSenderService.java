package com.aaroo.mail.service;

import javax.mail.MessagingException;

public interface MailSenderService {

	Object sendMailWithAttachment(String toEmail, String body, String subject, String attachment)
			throws MessagingException;

}
