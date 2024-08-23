package br.ufscar.dc.dsw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufscar.dc.dsw.model.Message;
import br.ufscar.dc.dsw.service.RabbitMQSender;

@RestController
@RequestMapping(value = "/publisher/")
public class PublisherController {

	@Autowired
	RabbitMQSender rabbitMQSender;

	@GetMapping(value = "/publish")
	public String producer() {

		String toName      = System.getenv().getOrDefault("TO_NAME", "Delano Beder");
        String toAddress   = System.getenv().getOrDefault("TO_ADDRESS", "delano@ufscar.br");
		String fromName    = System.getenv().getOrDefault("FROM_NAME", "Delano Beder");
        String fromAddress = System.getenv().getOrDefault("FROM_ADDRESS", "delanobeder@gmail.com");
		        
		Message msg = new Message();
		msg.setToName(toName);
		msg.setToAddress(toAddress);
		msg.setFromName(fromName);
		msg.setFromAddress(fromAddress);
		msg.setSubject("Teste subject mensagem");
		msg.setBody("Teste body mensagem");
		rabbitMQSender.send(msg);

		return "Message sent to the queue Successfully";
	}

}
