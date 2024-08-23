package br.ufscar.dc.dsw.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import br.ufscar.dc.dsw.model.Message;

@Service
public class RabbitMQSender {
	
	@Autowired
	private AmqpTemplate amqpTemplate;
	
	@Value("${rabbitmq.exchange}")
	private String exchange;
	
	@Value("${rabbitmq.routingkey}")
	private String routingkey;	
	
	public void send(Message company) {
		amqpTemplate.convertAndSend(exchange, routingkey, company);
		System.out.println("Sent message = " + company + ", using routingkey = " + routingkey);
	}
}