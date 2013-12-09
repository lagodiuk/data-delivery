package com.city.datadelivery;

public class MessageProducerTask implements Runnable {

	private MessageQueue messageQueue;

	private MessageProducer messageProducer;

	public MessageProducerTask(MessageQueue messageQueue, MessageProducer messageProducer) {
		this.messageQueue = messageQueue;
		this.messageProducer = messageProducer;
	}

	@Override
	public void run() {
		while (this.messageProducer.hasMoreMessages()) {
			Message message = this.messageProducer.nextMessage();
			this.messageQueue.addMessage(message);
		}
	}
}
