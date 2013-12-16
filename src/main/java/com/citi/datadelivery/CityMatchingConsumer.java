package com.citi.datadelivery;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.citi.datadelivery.base.Message;
import com.citi.datadelivery.base.MessageBuilder;
import com.citi.datadelivery.base.MessageConverter;
import com.citi.datadelivery.base.consumer.MessageConsumer;

public class CityMatchingConsumer implements MessageConsumer {

	private static final char DELIMITER = ' ';

	private final String city;

	private final PrintWriter writer;

	private final MessageConverter messageConverter;

	private final Lock writerLock = new ReentrantLock();

	public CityMatchingConsumer(String city, OutputStream out) {
		this(city, out, new MessageConverterImpl());
	}

	public CityMatchingConsumer(String city, OutputStream out, MessageConverter messageConverter) {
		this.city = city;
		this.writer = new PrintWriter(out, true);
		this.messageConverter = messageConverter;

	}

	@Override
	public void processMessage(Message message) {
		String messageCity = message.getCity();
		if ((messageCity == null) || (!this.city.equals(messageCity))) {
			return;
		}

		String name = this.transformName(message.getName());

		Message producedMessage =
				new MessageBuilder(message.getId())
						.withName(name)
						.withPostalCode(message.getPostalCode())
						.withCity(messageCity)
						.withAddress(message.getAddress())
						.withAge(message.getAge())
						.createMessage();

		this.processProducedMessage(producedMessage);
	}

	/**
	 * "Lastname, Firstname" -> "Firstname Lastname"
	 */
	String transformName(String name) {
		if (name.matches("^(.+),(.+)$")) {
			String[] parts = name.split(",");
			String lastName = parts[0].trim();
			String firstName = parts[1].trim();
			name = firstName + DELIMITER + lastName;
		}
		return name;
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
