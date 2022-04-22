package com.aaroo.mail;

import javax.mail.MessagingException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
@SpringBootApplication
public class SpringMailUtilityApplication {

//	@Autowired
//	MailSenderServiceImpl mailSenderService;

	public static void main(String[] args) throws MessagingException {
		SpringApplication.run(SpringMailUtilityApplication.class, args);
	}

//	@EventListener(ApplicationReadyEvent.class)
//	public void triggerMail() throws MessagingException {
//		mailSenderService.sendMailWithAttachment("nirmalbagdia21@gmail.com", "This is body", "This is subject",
//				"C:\\Users\\MDP\\Documents\\Urjas.txt");
//	}

}
