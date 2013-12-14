package com.citi.datadelivery;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.citi.datadelivery.base.Message;
import com.citi.datadelivery.base.consumer.MessageConsumer;

public class AverageAgeConsumer implements MessageConsumer {

	private Lock lockObj = new ReentrantLock();

	private double averageAge = 0;

	private int processedMessagesCount = 0;

	@Override
	public void processMessage(Message message) {
		Integer age = message.getAge();
		if (age == null) {
			return;
		}

		try {
			this.lockObj.lock();
			this.averageAge =
					((this.averageAge * this.processedMessagesCount) + age)
							/ (this.processedMessagesCount + 1);
			this.processedMessagesCount += 1;
		} finally {
			this.lockObj.unlock();
		}
	}

	public double getAverageAge() {
		try {
			this.lockObj.lock();
			return this.averageAge;
		} finally {
			this.lockObj.unlock();
		}
	}
}
