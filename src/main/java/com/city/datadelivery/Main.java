package com.city.datadelivery;

import java.util.concurrent.Executors;

import com.city.datadelivery.base.DeliveryManager;
import com.city.datadelivery.base.MessageQueue;

public class Main {

	private static final int NUMBER_OF_PRODUCERS = 10;
	private static final int NUMBER_OF_CONSUMERS = 10;

	public static void main(String[] args) throws Exception {
		DeliveryManager deliveryManager =
				new DeliveryManager(
						new MessageQueue(),
						Executors.newFixedThreadPool(NUMBER_OF_PRODUCERS),
						Executors.newFixedThreadPool(NUMBER_OF_CONSUMERS));

		deliveryManager.addConsumers(
				new CityMatchingConsumer("City of Coil"),
				new GreaterAgeConsumer(50),
				new AverageAgeConsumer());

		deliveryManager.forkProducers(
				new ReadingFileProducer("/Users/yura/sandbox/city_bank/sampleData/input-0.csv"),
				new ReadingFileProducer("/Users/yura/sandbox/city_bank/sampleData/input-50000.csv"),
				new ReadingFileProducer("/Users/yura/sandbox/city_bank/sampleData/input-100000.csv"));

		deliveryManager.performDelivery();
	}
}
