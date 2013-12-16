package com.citi.datadelivery.base;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.citi.datadelivery.base.consumer.MessageConsumer;
import com.citi.datadelivery.base.consumer.MessageConsumerTask;
import com.citi.datadelivery.base.producer.MessageProducer;
import com.citi.datadelivery.base.producer.MessageProducerTask;

public class DeliveryManager implements Runnable {

	private static final TimeUnit TIME_UNIT = TimeUnit.HOURS;

	private static final int TIME_UNIT_AMOUNT = 1;

	private final List<MessageConsumer> messageConsumers = new LinkedList<MessageConsumer>();

	private final MessageQueue messageQueue;

	private final ExecutorService consumersExecutor;

	private final ExecutorService producersExecutor;

	private Thread deliveryThread;

	public DeliveryManager(
			int expectedNumberOfProducers,
			int expectedNumberOfConsumers,
			int messageQueueCapacity) {

		this.producersExecutor = Executors.newFixedThreadPool(expectedNumberOfProducers);
		this.consumersExecutor = Executors.newFixedThreadPool(expectedNumberOfConsumers);
		this.messageQueue = new MessageQueue(messageQueueCapacity);
	}

	public void forkDeliveryThread() throws InterruptedException {
		this.deliveryThread = new Thread(this);
		this.deliveryThread.setDaemon(true);
		this.deliveryThread.start();
	}

	@Override
	public void run() {
		while (true) {
			Message message = null;
			try {
				message = this.messageQueue.getMessage();
			} catch (InterruptedException e) {
				break;
			}

			if (message == Message.POISON_PILL) {
				break;
			}

			this.forkAllConsumers(message);
		}

		this.notifyThatQueueIsEmpty();
	}

	public void forkProducers(MessageProducer... messageProducers) {
		for (MessageProducer messageProducer : messageProducers) {
			MessageProducerTask task = new MessageProducerTask(this.messageQueue, messageProducer);
			this.producersExecutor.submit(task);
		}
	}

	public synchronized void addConsumers(MessageConsumer... messageConsumers) {
		for (MessageConsumer messageConsumer : messageConsumers) {
			this.messageConsumers.add(messageConsumer);
		}
	}

	private synchronized void forkAllConsumers(Message message) {
		for (MessageConsumer consumer : this.messageConsumers) {
			MessageConsumerTask task = new MessageConsumerTask(message, consumer);
			this.consumersExecutor.submit(task);
		}
	}

	public void waitUntilAllConsumersAreStopped() throws InterruptedException {
		this.consumersExecutor.shutdown();
		this.consumersExecutor.awaitTermination(TIME_UNIT_AMOUNT, TIME_UNIT);
	}

	public void waitUntillAllProducersAreStopped() throws InterruptedException {
		this.producersExecutor.shutdown();
		this.producersExecutor.awaitTermination(TIME_UNIT_AMOUNT, TIME_UNIT);
	}

	public synchronized void waitUntilQueueIsEmpty() throws InterruptedException {
		this.messageQueue.addMessage(Message.POISON_PILL);
		this.wait();
	}

	private synchronized void notifyThatQueueIsEmpty() {
		this.notifyAll();
	}
}
