package com.citi.datadelivery;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.concurrent.Executors;

import com.citi.datadelivery.base.DeliveryManager;
import com.citi.datadelivery.base.MessageQueue;

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

		InputStream inputStream1 = new FileInputStream("/Users/yura/sandbox/city_bank/sampleData/input-0.csv");
		InputStream inputStream2 = new FileInputStream("/Users/yura/sandbox/city_bank/sampleData/input-50000.csv");
		InputStream inputStream3 = new FileInputStream("/Users/yura/sandbox/city_bank/sampleData/input-100000.csv");
		deliveryManager.forkProducers(
				new ReadingFileProducer(inputStream1),
				new ReadingFileProducer(inputStream2),
				new ReadingFileProducer(inputStream3));

		deliveryManager.forkDeliveryThread();

		// Do whatever you want

		deliveryManager.waitUntillAllProducersAreStopped();
		deliveryManager.waitUntilQueueIsEmpty();
		deliveryManager.interruptDeliveryThread();
		deliveryManager.waitUntilAllConsumersAreStopped();

		inputStream1.close();
		inputStream2.close();
		inputStream3.close();
	}
}
