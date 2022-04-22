package com.aaroo.security.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

public class MailSender implements Runnable {

	@Value("${spring.mail.host}")
	String emailHost;

	@Value("${spring.mail.smtp.port}")
	String emailPort;

	@Value("${spring.mail.properties.mail.smtp.socketFactory.port}")
	String emailSocketFactoryPort;

	@Value("${spring.mail.properties.mail.smtp.socketFactory.fallback}")
	String emailSocketFactoryFallBack;

	@Value("${spring.mail.properties.mail.smtp.auth}")
	String emailAuth;

	@Value("${spring.mail.properties.mail.debug}")
	String emailDebug;

	@Value("${spring.mail.store.protocol}")
	String emailStoreProtocol;

	@Value("${spring.mail.transport.protocol}")
	String emailTransportProtocol;

	@Value("${spring.mail.properties.mail.smtp.starttls.enable}")
	String emailStarTTLS;

	final private Logger LOGGER = LoggerFactory.getLogger(MailSender.class);
	static Date currentDate = new Date();
	SimpleDateFormat ft = new SimpleDateFormat("E yyyy.MM.dd 'at' hh:mm:ss");
	String receiverEmailid[];
	String name;
	String mailId;
	String password;
	String username;
	String description;
	Address[] address;
	String eventSubject;
	String attachmentFilePath;

	public void attachmentPath(String attachmentFilePath) {
		this.attachmentFilePath = attachmentFilePath;
	}

	public void setEmailDetails(String receiverEmail[], String mailId, String password, String username,
			String description, String eventSubject, String name) {
		this.receiverEmailid = receiverEmail;
		this.mailId = mailId;
		this.password = password;
		this.username = username;
		this.description = description;
		this.eventSubject = eventSubject;
		this.name = name;
	}

	@Override
	public void run() {
		try {
			this.MailVerification(receiverEmailid, username);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int MailVerification(String receiverEmail[], String username) {
		int resp = 0;
		final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
		Properties props = new Properties();
		props.put("mail.debug", emailDebug);
		props.put("mail.smtp.auth", emailAuth);
		props.setProperty("mail.smtp.host", emailHost);
		props.setProperty("mail.smtp.port", emailPort);
		props.put("mail.smtp.starttls.enable", emailStarTTLS);
		props.put("mail.store.protocol", emailStoreProtocol);
		props.put("mail.transport.protocol", emailTransportProtocol);
		props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
		props.setProperty("mail.smtp.socketFactory.port", emailSocketFactoryPort);
		props.setProperty("mail.smtp.socketFactory.fallback", emailSocketFactoryFallBack);

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(mailId, password);
			}
		});

		try {
			MimeMessage message = new MimeMessage(session);
			Multipart multipart = new MimeMultipart();
			Address[] receiverAddr = new Address[receiverEmail.length];
			int counter = 0;
			for (String recipient : receiverEmail) {
				receiverAddr[counter] = new InternetAddress(recipient.trim());
				counter++;
			}
			message.addRecipients(Message.RecipientType.TO, receiverAddr);
			message.setSubject(eventSubject);
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(getHTML(description, name), "text/html");
			multipart.addBodyPart(messageBodyPart);
			MimeBodyPart attachPart = new MimeBodyPart();
			String attachFile = attachmentFilePath;
			if (attachFile != null) {
				DataSource source = new FileDataSource(attachFile);
				attachPart.setDataHandler(new DataHandler(source));
				attachPart.setFileName(new File(attachFile).getName());
				multipart.addBodyPart(attachPart);
			}
			message.setContent(multipart);
			Transport.send(message);
			LOGGER.info("Email Sent Successfully");
			resp = 1;
		} catch (MessagingException e) {
			resp = 0;
			e.printStackTrace();
		}
		return resp;
	}

	public String getHTML(String description, String name) {
		String msg = "<!DOCTYPE html>\r\n" + "<html>\r\n" + "<head>\r\n" + "<title>Email</title>\r\n"
				+ "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
				+ "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css\">\r\n"
				+ "<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js\"></script>\r\n"
				+ "<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js\"></script>\r\n"
				+ "</head>\r\n" + "<body>\r\n"
				+ "	<div id=\"wrapper\" style=\"min-width: 320px; max-width: 600px;\">\r\n" + "		<div \">\r\n"
				+ "			<form>\r\n" + "				<table style=\"width: 100%;\">\r\n"
				+ "					<thead>\r\n" + "						<tr>\r\n"
				+ "							<th style=\"background: #02B95C;\">\r\n"
				+ "								<h2 class=\"clearfix\" style=\"margin: 0; padding: 15px; color: #fff; font-weight: bold; font-style: 34px; line-height: 30px;\"><img style=\"float: left; margin: 0 10px 0 0;\">Ergodemy</h2>\r\n"
				+ "							</th>\r\n" + "						</tr>\r\n"
				+ "					</thead>\r\n" + "					<tbody>\r\n" + "						\r\n"
				+ "						<tr>\r\n" + "							<td style=\"padding: 0 30px 20px;\">"
				+ description + "</td>\r\n" + "						</tr>\r\n" + "					</tbody>\r\n"
				+ "				</table>\r\n" + "			</form>\r\n" + "		</div>\r\n" + "	</div>\r\n"
				+ "</body>\r\n" + "</html>";
		return msg;
	}

}
