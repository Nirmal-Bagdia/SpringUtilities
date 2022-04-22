package com.aaroo.mail.controller;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aaroo.mail.service.MailSenderService;

@RestController
@RequestMapping("MailSender")
public class MailSenderController {

	@Autowired
	MailSenderService mailSenderService;

	@PostMapping("/sendMailWithAttachment")
	public Object sendMailWithAttachment(String toEmail, String body, String subject, String attachment)
			throws MessagingException {
		return mailSenderService.sendMailWithAttachment(toEmail, body, subject, attachment);
	}
}
