package com.city.datadelivery;

import com.city.datadelivery.base.Message;
import com.city.datadelivery.base.MessageBuilder;
import com.city.datadelivery.base.consumer.MessageConsumer;

public class GreaterAgeConsumer implements MessageConsumer {

	private int ageThreshold;

	public GreaterAgeConsumer(int ageThreshold) {
		this.ageThreshold = ageThreshold;
	}

	@Override
	public void processMessage(Message message) {
		Integer age = message.getAge();
		if ((age == null) || (age < this.ageThreshold)) {
			return;
		}

		Message producedMessage =
				new MessageBuilder(message.getId())
						.withName(message.getName())
						.withPostalCode(message.getPostalCode())
						.withCity(message.getCity())
						.withAddress(message.getAddress())
						.withAge(age)
						.createMessage();

		this.processProducedMessage(producedMessage);
	}

	public void processProducedMessage(Message message) {
		// TODO
		System.out.println(message.getId() + "\t" + message.getAge());
	}
}
