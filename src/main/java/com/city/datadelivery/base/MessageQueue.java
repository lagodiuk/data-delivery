package com.city.datadelivery.base;

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

	public void waitUntilQueueIsEmpty() throws InterruptedException {
		synchronized (this) {
			this.wait();
		}
	}

	public void notifyWaitersIfQueueIsEmpty() {
		if (this.messageQueue.isEmpty()) {
			synchronized (this) {
				this.notifyAll();
			}
		}
	}
}
