package com.city.datadelivery.base;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import com.city.datadelivery.base.consumer.MessageConsumer;
import com.city.datadelivery.base.consumer.MessageConsumerTask;
import com.city.datadelivery.base.producer.MessageProducer;
import com.city.datadelivery.base.producer.MessageProducerTask;

public class DeliveryManager implements Runnable {

	private List<MessageConsumer> messageConsumers = new LinkedList<MessageConsumer>();

	private List<MessageProducer> messageProducers = new LinkedList<MessageProducer>();

	private MessageQueue messageQueue;

	private ExecutorService executorService;

	public DeliveryManager(MessageQueue messageQueue, ExecutorService executorService) {
		this.messageQueue = messageQueue;
		this.executorService = executorService;
	}

	public void addMessageConsumer(MessageConsumer messageProcessor) {
		this.messageConsumers.add(messageProcessor);
	}

	public void addMessageProducer(MessageProducer messageProducer) {
		this.messageProducers.add(messageProducer);
	}

	public void start() {
		// Fork all producers
		for (MessageProducer messageProducer : this.messageProducers) {
			MessageProducerTask task = new MessageProducerTask(this.messageQueue, messageProducer);
			this.executorService.submit(task);
		}

		// Start process for dispatching produced messages to consumers
		this.executorService.submit(this);
	}

	@Override
	public void run() {
		while (true) {
			Message message = this.messageQueue.getMessage();
			for (MessageConsumer consumer : this.messageConsumers) {
				// Fork consuming process
				// (message is immutable)
				MessageConsumerTask task = new MessageConsumerTask(message, consumer);
				this.executorService.submit(task);
			}
		}
	}
}
