package com.city.datadelivery;

import java.util.concurrent.Executors;

import com.city.datadelivery.base.DeliveryManager;
import com.city.datadelivery.base.MessageQueue;

public class Main {

	private static final int PRODUCER_THREADS_NUM = 10;
	private static final int CONSUMER_THREADS_NUM = 10;

	public static void main(String[] args) throws Exception {
		MessageQueue messageQueue = new MessageQueue();

		DeliveryManager deliveryManager =
				new DeliveryManager(
						messageQueue,
						Executors.newFixedThreadPool(PRODUCER_THREADS_NUM),
						Executors.newFixedThreadPool(CONSUMER_THREADS_NUM));

		deliveryManager.addConsumers(
				new CityMatchingConsumer("City of Coil"),
				new GreaterAgeConsumer(50),
				new AverageAgeConsumer());

		deliveryManager.forkProducers(
				new ReadingFileProducer("/Users/yura/sandbox/city_bank/sampleData/input-0.csv"),
				new ReadingFileProducer("/Users/yura/sandbox/city_bank/sampleData/input-50000.csv"),
				new ReadingFileProducer("/Users/yura/sandbox/city_bank/sampleData/input-100000.csv"));

		deliveryManager.forkDeliveryThread();

		// Do whatever you want

		deliveryManager.waitUntillAllProducersAreStopped();
		deliveryManager.waitUntilQueueIsEmpty();
		deliveryManager.interruptDeliveryThread();
		deliveryManager.waitUntilAllConsumersAreStopped();
	}
}
