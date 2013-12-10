package com.city.datadelivery;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.city.datadelivery.base.DeliveryManager;
import com.city.datadelivery.base.Message;
import com.city.datadelivery.base.MessageQueue;
import com.city.datadelivery.base.consumer.MessageConsumer;
import com.city.datadelivery.base.producer.MessageProducerTask;

public class Main {

	public static void main(String[] args) throws Exception {
		ExecutorService executorService = Executors.newCachedThreadPool();
		MessageQueue messageQueue = new MessageQueue();

		DeliveryManager deliveryManager = new DeliveryManager(messageQueue, executorService);
		deliveryManager.addMessageConsumer(new MessageConsumer() {
			@Override
			public void processMessage(Message message) {
				System.out.println(message.getId() + "\t" + message.getName());
			}
		});

		MessageProducerTask messageProducer1 =
				new MessageProducerTask(
						messageQueue,
						new ReadingFileMessageProducer("/Users/yura/sandbox/city_bank/sampleData/input-0.csv"));

		MessageProducerTask messageProducer2 =
				new MessageProducerTask(
						messageQueue,
						new ReadingFileMessageProducer("/Users/yura/sandbox/city_bank/sampleData/input-50000.csv"));

		executorService.submit(deliveryManager);
		executorService.submit(messageProducer1);
		executorService.submit(messageProducer2);
	}
}
