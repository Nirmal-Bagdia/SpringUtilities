package com.aaroo.mail.serviceImpl;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.aaroo.mail.constant.MailContant;
import com.aaroo.mail.service.MailSenderService;

@Service
public class MailSenderServiceImpl implements MailSenderService {

	private final static Logger LOGGER = LoggerFactory.getLogger(MailSenderServiceImpl.class);

	@Autowired
	JavaMailSender javaMailSender;

	@Override
	public Object sendMailWithAttachment(String toEmail, String body, String subject, String attachment)
			throws MessagingException {
		Boolean emailSendStatus = false;
		try {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
			FileSystemResource fileSystemResource = new FileSystemResource(attachment);

			mimeMessageHelper.setFrom(MailContant.FromMail);
			mimeMessageHelper.setTo(toEmail);
			mimeMessageHelper.setSentDate(new Date());
			mimeMessageHelper.setSubject(subject);
			mimeMessageHelper.setText(body);
			mimeMessageHelper.addAttachment(fileSystemResource.getFilename(), fileSystemResource);

			javaMailSender.send(mimeMessage);
			LOGGER.info("Email sent successfully to :" + toEmail);
			emailSendStatus = true;
		} catch (Exception e) {
			LOGGER.error("MailSenderServiceImpl.Exception :" + e);
		}
		return "Email send status : " + emailSendStatus;
	}

}
