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

	private MessageQueue messageQueue;

	private ExecutorService executorService;

	public DeliveryManager(MessageQueue messageQueue, ExecutorService executorService) {
		this.messageQueue = messageQueue;
		this.executorService = executorService;
	}

	@Override
	public void run() {
		while (true) {
			Message message = this.messageQueue.getMessage();
			for (MessageConsumer consumer : this.messageConsumers) {
				MessageConsumerTask task = new MessageConsumerTask(message, consumer);
				this.executorService.submit(task);
			}
		}
	}

	public void addMessageConsumer(MessageConsumer messageProcessor) {
		this.messageConsumers.add(messageProcessor);
	}

	public void addMessageProducer(MessageProducer messageProducer) {
		MessageProducerTask task = new MessageProducerTask(this.messageQueue, messageProducer);
		this.executorService.submit(task);
	}

	public void start() {
		this.executorService.submit(this);
	}
}
