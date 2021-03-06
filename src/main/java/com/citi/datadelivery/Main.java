package com.citi.datadelivery;

import java.io.FileInputStream;
import java.io.InputStream;

import com.citi.datadelivery.base.DeliveryManager;

public class Main {

	private static final int PRODUCER_THREADS_NUM = 10;

	private static final int CONSUMER_THREADS_NUM = 10;

	private static final int MESSAGE_QUEUE_CAPACITY = 30;

	public static void main(String[] args) throws Exception {

		// The major component of the system
		DeliveryManager deliveryManager =
				new DeliveryManager(
						PRODUCER_THREADS_NUM,
						CONSUMER_THREADS_NUM,
						MESSAGE_QUEUE_CAPACITY);

		// Add as many consumers, as you want
		deliveryManager.addConsumers(
				new CityMatchingConsumer("Coil", System.out),
				new GreaterAgeConsumer(50, System.out),
				new AverageAgeConsumer());

		// Start thread, which delivering messages to consumers
		deliveryManager.forkDeliveryThread();

		// Configure input streams for message producers
		InputStream inputStream1 = new FileInputStream("/tmp/sampleData/input-0.csv");
		InputStream inputStream2 = new FileInputStream("/tmp/sampleData/input-50000.csv");
		InputStream inputStream3 = new FileInputStream("/tmp/sampleData/input-100000.csv");

		// Start threads with producers
		deliveryManager.forkProducers(
				new InputStreamMessageProducer(inputStream1),
				new InputStreamMessageProducer(inputStream2),
				new InputStreamMessageProducer(inputStream3));

		System.out.println("Message delivery started");

		deliveryManager.waitUntilAllProducersAreStopped();

		// This method will send 'poison pill message' to stop delivery thread
		deliveryManager.waitUntilQueueIsEmpty();

		deliveryManager.waitUntilAllConsumersAreStopped();

		inputStream1.close();
		inputStream2.close();
		inputStream3.close();
	}
}
