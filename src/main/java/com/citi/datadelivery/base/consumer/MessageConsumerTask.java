package com.citi.datadelivery.base.consumer;

import com.citi.datadelivery.base.Message;

public class MessageConsumerTask implements Runnable {

	private final Message message;

	private final MessageConsumer messageProcessor;

	public MessageConsumerTask(Message message, MessageConsumer messageProcessor) {
		this.message = message;
		this.messageProcessor = messageProcessor;
	}

	@Override
	public void run() {
		this.messageProcessor.processMessage(this.message);
	}
}
