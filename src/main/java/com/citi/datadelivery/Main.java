package com.citi.datadelivery;

import java.io.FileInputStream;
import java.io.InputStream;

import com.citi.datadelivery.base.DeliveryManager;

public class Main {

	private static final int PRODUCER_THREADS_NUM = 10;

	private static final int CONSUMER_THREADS_NUM = 10;

	private static final int MESSAGE_QUEUE_CAPACITY = 30;

	public static void main(String[] args) throws Exception {
		DeliveryManager deliveryManager =
				new DeliveryManager(
						PRODUCER_THREADS_NUM,
						CONSUMER_THREADS_NUM,
						MESSAGE_QUEUE_CAPACITY);

		deliveryManager.addConsumers(
				new CityMatchingConsumer("Coil", System.out),
				new GreaterAgeConsumer(50, System.out),
				new AverageAgeConsumer());

		deliveryManager.forkDeliveryThread();

		InputStream inputStream1 = new FileInputStream("/Users/cafebabe/workspaces/sandbox/citi_bank/sampleData/input-0.csv");
		InputStream inputStream2 = new FileInputStream("/Users/cafebabe/workspaces/sandbox/citi_bank/sampleData/input-50000.csv");
		InputStream inputStream3 = new FileInputStream("/Users/cafebabe/workspaces/sandbox/citi_bank/sampleData/input-100000.csv");
		deliveryManager.forkProducers(
				new InputStreamMessageProducer(inputStream1),
				new InputStreamMessageProducer(inputStream2),
				new InputStreamMessageProducer(inputStream3));

		// Do whatever you want

		deliveryManager.waitUntillAllProducersAreStopped();
		deliveryManager.waitUntilQueueIsEmpty();
		deliveryManager.waitUntilAllConsumersAreStopped();

		inputStream1.close();
		inputStream2.close();
		inputStream3.close();
	}
}
