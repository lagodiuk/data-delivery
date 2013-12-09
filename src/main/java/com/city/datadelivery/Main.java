package com.city.datadelivery;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.city.datadelivery.base.DeliveryManager;
import com.city.datadelivery.base.Message;
import com.city.datadelivery.base.MessageQueue;
import com.city.datadelivery.base.producer.MessageProducer;
import com.city.datadelivery.base.producer.MessageProducerTask;

public class Main {

	public static void main(String[] args) {
		ExecutorService executorService = Executors.newCachedThreadPool();
		MessageQueue messageQueue = new MessageQueue();

		DeliveryManager deliveryManager = new DeliveryManager(messageQueue, executorService);

		MessageProducerTask messageProducer = new MessageProducerTask(messageQueue, new MessageProducer() {
			@Override
			public Message nextMessage() {
				return null;
			}

			@Override
			public boolean hasMoreMessages() {
				return false;
			}
		});

		executorService.submit(deliveryManager);
		executorService.submit(messageProducer);
	}
}
