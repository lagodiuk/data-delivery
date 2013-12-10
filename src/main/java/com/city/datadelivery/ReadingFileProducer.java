package com.city.datadelivery;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.city.datadelivery.base.Message;
import com.city.datadelivery.base.MessageBuilder;
import com.city.datadelivery.base.producer.MessageProducer;

public class ReadingFileProducer implements MessageProducer {

	private static final String DELIMITER_REGEXP = "\\|";

	private static final Logger LOGGER =
			Logger.getLogger(ReadingFileProducer.class.getName());

	private String fileName;

	private BufferedReader reader;

	private String nextLine;

	public ReadingFileProducer(String fileName) throws FileNotFoundException {
		this.reader = new BufferedReader(new FileReader(fileName));
		this.fileName = fileName;
	}

	@Override
	public boolean hasMoreMessages() {
		try {
			this.nextLine = this.reader.readLine();
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "Error while reading file " + this.fileName);
			return false;
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
			LOGGER.log(
					Level.WARNING,
					"Error while closing file " + this.fileName);
		}
	}

	private Message parse(String s) {
		String[] parts = s.split(DELIMITER_REGEXP);

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
