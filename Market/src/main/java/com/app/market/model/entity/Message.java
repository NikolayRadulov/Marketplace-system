package com.app.market.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "messages")
public class Message extends BaseEntity {

	@Column(nullable = false)
	private String text;
	
	@ManyToOne
	private User sender;
	
	@ManyToOne
	private User reciever;
	
	
	public Message(String text) {
		this.text = text;
	}

	public Message() {
		// TODO Auto-generated constructor stub
	}
	

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public User getReciever() {
		return reciever;
	}

	public void setReciever(User reciever) {
		this.reciever = reciever;
	}

}
