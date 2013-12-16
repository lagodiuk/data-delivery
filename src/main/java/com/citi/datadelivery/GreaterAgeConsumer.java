package com.citi.datadelivery;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.citi.datadelivery.base.Message;
import com.citi.datadelivery.base.MessageConverter;
import com.citi.datadelivery.base.consumer.MessageConsumer;

public class GreaterAgeConsumer implements MessageConsumer {

	private final int ageThreshold;

	private final PrintWriter writer;

	private final MessageConverter messageConverter;

	private final Lock writerLock = new ReentrantLock();

	public GreaterAgeConsumer(int ageThreshold, OutputStream out) {
		this(ageThreshold, out, new MessageConverterImpl());
	}

	public GreaterAgeConsumer(int ageThreshold, OutputStream out, MessageConverter messageConverter) {
		this.ageThreshold = ageThreshold;
		this.writer = new PrintWriter(out, true);
		this.messageConverter = messageConverter;
	}

	@Override
	public void processMessage(Message message) {
		Integer age = message.getAge();
		if ((age == null) || (age < this.ageThreshold)) {
			return;
		}
		this.processProducedMessage(message);
	}

	public void processProducedMessage(Message message) {
		try {
			this.writerLock.lock();
			String messageStr = this.messageConverter.messageToString(message);
			this.writer.println(messageStr);
		} finally {
			this.writerLock.unlock();
		}
	}
}
