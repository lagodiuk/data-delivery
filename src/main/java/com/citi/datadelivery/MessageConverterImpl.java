package com.citi.datadelivery;

import com.citi.datadelivery.base.Message;
import com.citi.datadelivery.base.MessageBuilder;
import com.citi.datadelivery.base.MessageConverter;

public class MessageConverterImpl implements MessageConverter {

	private static final String DELIMITER = "|";

	private static final String DELIMITER_REGEXP = "\\|";

	@Override
	public Message stringToMessage(String str) {
		String[] parts = str.split(DELIMITER_REGEXP);

		MessageBuilder messageBuilder =
				new MessageBuilder(parts[0])
						.withName(parts[1])
						.withAge(parts[2])
						.withAddress(parts[3])
						.withCity(parts[4])
						.withPostalCode(parts[5]);

		return messageBuilder.createMessage();
	}

	@Override
	public String messageToString(Message msg) {
		StringBuilder builder = new StringBuilder();

		builder.append(msg.getId());
		builder.append(DELIMITER);

		if (msg.getName() != null) {
			builder.append(msg.getName());
		}
		builder.append(DELIMITER);

		if (msg.getAge() != null) {
			builder.append(msg.getAge());
		}
		builder.append(DELIMITER);

		if (msg.getAddress() != null) {
			builder.append(msg.getAddress());
		}
		builder.append(DELIMITER);

		if (msg.getCity() != null) {
			builder.append(msg.getCity());
		}
		builder.append(DELIMITER);

		if (msg.getPostalCode() != null) {
			builder.append(msg.getPostalCode());
		}

		return builder.toString();
	}

}
