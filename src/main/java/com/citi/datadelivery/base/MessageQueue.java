package com.citi.datadelivery.base;

import java.util.concurrent.ArrayBlockingQueue;

public class MessageQueue {

	private static final int DEFAULT_CAPACITY = 1024;

	private final ArrayBlockingQueue<Message> messageQueue;

	public MessageQueue() {
		this(DEFAULT_CAPACITY);
	}

	public MessageQueue(int capacity) {
		this.messageQueue = new ArrayBlockingQueue<Message>(capacity);
	}

	public void addMessage(Message message) throws InterruptedException {
		this.messageQueue.put(message);
	}

	public Message getMessage() throws InterruptedException {
		Message message = this.messageQueue.take();
		return message;
	}

	public boolean isEmpty() {
		return this.messageQueue.isEmpty();
	}
}
