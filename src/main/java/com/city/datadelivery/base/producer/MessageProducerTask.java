package com.city.datadelivery.base.producer;

import com.city.datadelivery.base.Message;
import com.city.datadelivery.base.MessageQueue;

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
		this.messageProducer.cleanUp();
	}
}
