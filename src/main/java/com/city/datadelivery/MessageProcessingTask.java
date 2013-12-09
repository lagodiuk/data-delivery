package com.city.datadelivery;

public class MessageProcessingTask implements Runnable {

	private final Message message;

	private final MessageProcessor messageProcessor;

	public MessageProcessingTask(Message message, MessageProcessor messageProcessor) {
		this.message = message;
		this.messageProcessor = messageProcessor;
	}

	@Override
	public void run() {
		this.messageProcessor.processMessage(this.message);
	}
}
