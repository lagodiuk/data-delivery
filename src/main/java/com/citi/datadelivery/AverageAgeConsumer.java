package com.citi.datadelivery;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.citi.datadelivery.base.Message;
import com.citi.datadelivery.base.consumer.MessageConsumer;

public class AverageAgeConsumer implements MessageConsumer {

	private final Lock lockObj = new ReentrantLock();

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

	public Result getResult() {
		try {
			this.lockObj.lock();
			return new Result(this.averageAge, this.processedMessagesCount);
		} finally {
			this.lockObj.unlock();
		}
	}

	public static class Result {
		public final double averageAge;
		public final int processedMessagesCount;

		public Result(double averageAge, int processedMessagesCount) {
			this.averageAge = averageAge;
			this.processedMessagesCount = processedMessagesCount;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			long temp;
			temp = Double.doubleToLongBits(this.averageAge);
			result = (prime * result) + (int) (temp ^ (temp >>> 32));
			result = (prime * result) + this.processedMessagesCount;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (this.getClass() != obj.getClass()) {
				return false;
			}
			Result other = (Result) obj;
			if (Double.doubleToLongBits(this.averageAge) != Double.doubleToLongBits(other.averageAge)) {
				return false;
			}
			if (this.processedMessagesCount != other.processedMessagesCount) {
				return false;
			}
			return true;
		}
	}
}
