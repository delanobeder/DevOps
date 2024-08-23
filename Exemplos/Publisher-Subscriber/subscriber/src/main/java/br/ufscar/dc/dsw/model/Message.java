package br.ufscar.dc.dsw.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "id", scope = Message.class)
public class Message {

	private String toName;
	private String toAddress;
	private String fromName;
	private String fromAddress;
	private String subject;
	private String body;

	public String getToName() {
		return toName;
	}

	public void setToName(String toName) {
		this.toName = toName;
	}

	public String getToAddress() {
		return toAddress;
	}

	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String getFromAddress() {
		return fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Message [to = " + toName + "<" + toAddress + ">");
		sb.append(", from = " + fromName + "<" + fromAddress + ">");
		sb.append(", subject = " + subject);
		sb.append(", body = " + body + "]");
		return sb.toString();
	}
}
