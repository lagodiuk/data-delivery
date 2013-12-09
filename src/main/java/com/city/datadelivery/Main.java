package com.city.datadelivery;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

	public static void main(String[] args) {
		ExecutorService executorService = Executors.newCachedThreadPool();
		MessageQueue messageQueue = new MessageQueue();

		DeliveryManager deliveryManager = new DeliveryManager(messageQueue, executorService);
		deliveryManager.addMessageProcessor(new MessageProcessor() {
			@Override
			public void processMessage(Message message) {
				System.out.println("Processor 1: " + message.getBody());
			}
		});
		deliveryManager.addMessageProcessor(new MessageProcessor() {
			@Override
			public void processMessage(Message message) {
				System.out.println("Processor 2: " + message.getBody());
			}
		});

		executorService.submit(deliveryManager);

		MessageProducerTask messageProducer = new MessageProducerTask(messageQueue, new MessageProducer() {
			private int count = 10;

			@Override
			public Message nextMessage() {
				Message message = new Message("Id: " + this.count);
				this.count -= 1;
				return message;
			}

			@Override
			public boolean hasMoreMessages() {
				return this.count > 0;
			}
		});

		executorService.submit(messageProducer);
	}
}
