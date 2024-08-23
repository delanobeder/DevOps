package br.ufscar.dc.dsw.service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender emailSender;

	public void send(InternetAddress from, InternetAddress to, String subject, String body) {

		try {
			MimeMessage message = emailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);

			helper.setFrom(from);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(body);

			emailSender.send(message);

			System.out.println("Mensagem enviada com sucesso!");

		} catch (MessagingException e) {
			System.out.println("Mensagem não enviada!");
			e.printStackTrace();
		}
	}
}
