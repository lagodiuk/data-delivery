package com.city.datadelivery;

import java.util.concurrent.LinkedBlockingQueue;

public class MessageQueue {

	private LinkedBlockingQueue<Message> messageQueue = new LinkedBlockingQueue<Message>();

	public void addMessage(Message message) {
		this.messageQueue.add(message);
	}

	public Message getMessage() {
		try {
			Message message = this.messageQueue.take();
			return message;
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

}
