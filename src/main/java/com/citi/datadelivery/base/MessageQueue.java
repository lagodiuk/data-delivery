package com.citi.datadelivery.base;

import java.util.concurrent.LinkedBlockingQueue;

public class MessageQueue {

	private LinkedBlockingQueue<Message> messageQueue = new LinkedBlockingQueue<Message>();

	public void addMessage(Message message) {
		this.messageQueue.add(message);
	}

	public Message getMessage() throws InterruptedException {
		Message message = this.messageQueue.take();
		return message;
	}

	public boolean isEmpty() {
		return this.messageQueue.isEmpty();
	}
}
