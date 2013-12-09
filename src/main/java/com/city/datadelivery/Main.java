package com.city.datadelivery;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.city.datadelivery.base.DeliveryManager;
import com.city.datadelivery.base.Message;
import com.city.datadelivery.base.MessageQueue;
import com.city.datadelivery.base.consumer.MessageConsumer;
import com.city.datadelivery.base.producer.MessageProducer;
import com.city.datadelivery.base.producer.MessageProducerTask;

public class Main {

	public static void main(String[] args) {
		ExecutorService executorService = Executors.newCachedThreadPool();
		MessageQueue messageQueue = new MessageQueue();

		DeliveryManager deliveryManager = new DeliveryManager(messageQueue, executorService);
		deliveryManager.addMessageConsumer(new MessageConsumer() {
			@Override
			public void processMessage(Message message) {
				System.out.println("Processor 1: " + message.getBody());
			}
		});
		deliveryManager.addMessageConsumer(new MessageConsumer() {
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
