package br.ufscar.dc.dsw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.ufscar.dc.dsw.model.Message;
import br.ufscar.dc.dsw.service.RabbitMQSender;

@RestController
@RequestMapping(value = "/publisher/")
public class RabbitMQWebController {

	@Autowired
	RabbitMQSender rabbitMQSender;

	@GetMapping(value = "/publish")
	public String producer() {

		Message msg = new Message();
		msg.setToName("Delano Beder");
		msg.setToAddress("delano@ufscar.br");
		msg.setFromName("Delano Beder");
		msg.setFromAddress("delanobeder@gmail.com");
		msg.setSubject("Teste subject mensagem");
		msg.setBody("Teste body mensagem");
		rabbitMQSender.send(msg);

		return "Message sent to the queue Successfully";
	}

}
