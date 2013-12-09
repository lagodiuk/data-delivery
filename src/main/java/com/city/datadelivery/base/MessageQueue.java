package com.city.datadelivery.base;

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
			throw new MessageQueueException("Interrupted while waiting for new messages");
		}
	}
}
