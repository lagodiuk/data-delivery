package com.city.datadelivery;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;

public class DeliveryManager implements Runnable {

	private LinkedBlockingQueue<MessageProcessor> messageProcessors = new LinkedBlockingQueue<MessageProcessor>();

	private MessageQueue messageQueue;

	private ExecutorService executorService;

	public DeliveryManager(MessageQueue messageQueue, ExecutorService executorService) {
		this.messageQueue = messageQueue;
		this.executorService = executorService;
	}

	@Override
	public void run() {
		while (true) {
			Message message = this.messageQueue.getMessage();
			for (MessageProcessor messageProcessor : this.messageProcessors) {
				MessageProcessingTask task = new MessageProcessingTask(message, messageProcessor);
				this.executorService.submit(task);
			}
		}
	}

	public void addMessageProcessor(MessageProcessor messageProcessor) {
		this.messageProcessors.add(messageProcessor);
	}
}
