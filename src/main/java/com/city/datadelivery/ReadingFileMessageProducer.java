package com.city.datadelivery;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.city.datadelivery.base.Message;
import com.city.datadelivery.base.MessageBuilder;
import com.city.datadelivery.base.producer.MessageProducer;

public class ReadingFileMessageProducer implements MessageProducer {

	private BufferedReader reader;

	private String nextLine;

	public ReadingFileMessageProducer(String file) throws FileNotFoundException {
		this.reader = new BufferedReader(new FileReader(file));
	}

	@Override
	public boolean hasMoreMessages() {
		try {
			this.nextLine = this.reader.readLine();
		} catch (IOException e) {
			// TODO: log
		}
		return this.nextLine != null;
	}

	@Override
	public Message nextMessage() {
		Message message = this.parse(this.nextLine);
		return message;
	}

	@Override
	public void cleanUp() {
		try {
			this.reader.close();
		} catch (IOException e) {
			// TODO: log
		}
	}

	private Message parse(String s) {
		String[] parts = s.split("\\|");

		MessageBuilder messageBuilder =
				new MessageBuilder(parts[0])
						.withName(parts[1])
						.withAge(parts[2])
						.withAddress(parts[3])
						.withCity(parts[4])
						.withPostalCode(parts[5]);

		return messageBuilder.createMessage();
	}
}
