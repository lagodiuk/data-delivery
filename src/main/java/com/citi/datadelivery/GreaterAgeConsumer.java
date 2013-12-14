package com.citi.datadelivery;

import java.util.concurrent.atomic.AtomicLong;

import com.citi.datadelivery.base.Message;
import com.citi.datadelivery.base.MessageBuilder;
import com.citi.datadelivery.base.consumer.MessageConsumer;

public class GreaterAgeConsumer implements MessageConsumer {

	private int ageThreshold;

	private AtomicLong cnt = new AtomicLong(0);

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
		System.out.println("Matched by age: " + message);
		System.out.println(cnt.incrementAndGet());
	}
}
