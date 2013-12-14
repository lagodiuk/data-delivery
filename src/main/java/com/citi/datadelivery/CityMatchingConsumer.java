package com.citi.datadelivery;

import com.citi.datadelivery.base.Message;
import com.citi.datadelivery.base.MessageBuilder;
import com.citi.datadelivery.base.consumer.MessageConsumer;

public class CityMatchingConsumer implements MessageConsumer {

	private static final char DELIMITER = ' ';

	private String city;

	public CityMatchingConsumer(String city) {
		this.city = city;
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
	private String transformName(String name) {
		if (name.matches("^(.+),(.+)$")) {
			String[] parts = name.split(",");
			String lastName = parts[0].trim();
			String firstName = parts[1].trim();
			name = firstName + DELIMITER + lastName;
		}
		return name;
	}

	public void processProducedMessage(Message message) {
		// TODO
		System.out.println("Matched by city: " + message);
	}
}
