package com.city.datadelivery;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.city.datadelivery.base.DeliveryManager;
import com.city.datadelivery.base.MessageQueue;

public class Main {

	public static void main(String[] args) throws Exception {
		ExecutorService executorService = Executors.newCachedThreadPool();
		MessageQueue messageQueue = new MessageQueue();

		DeliveryManager deliveryManager = new DeliveryManager(messageQueue, executorService);

		deliveryManager.addMessageConsumer(new CityMatchingConsumer("City of Coil"));
		deliveryManager.addMessageConsumer(new GreaterAgeConsumer(50));
		deliveryManager.addMessageConsumer(new AverageAgeConsumer());

		deliveryManager.addMessageProducer(new ReadingFileProducer("/Users/yura/sandbox/city_bank/sampleData/input-0.csv"));
		deliveryManager.addMessageProducer(new ReadingFileProducer("/Users/yura/sandbox/city_bank/sampleData/input-50000.csv"));
		deliveryManager.addMessageProducer(new ReadingFileProducer("/Users/yura/sandbox/city_bank/sampleData/input-100000.csv"));

		deliveryManager.start();
	}
}
