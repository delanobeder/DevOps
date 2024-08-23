package br.ufscar.dc.dsw.listener;

import java.io.UnsupportedEncodingException;

import javax.mail.internet.InternetAddress;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ufscar.dc.dsw.model.Message;
import br.ufscar.dc.dsw.service.EmailService;

@Component
public class RabbitMQListener {

	@Autowired
	EmailService service;

	@RabbitListener(queues = "${rabbitmq.queue}")
	public void receivedMessage(Message msg) throws UnsupportedEncodingException {
		System.out.println("Received Message From RabbitMQ: " + msg);

		InternetAddress from = new InternetAddress(msg.getFromAddress(), msg.getFromName());
		InternetAddress to = new InternetAddress(msg.getToAddress(), msg.getToName());

		// Envio sem anexo
		service.send(from, to, msg.getSubject(), msg.getBody());
	}
}